package org.oscarehr.e2e.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.exceptions.ObjectDisposedException;
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.formatters.interfaces.IFormatterParseResult;
import org.marc.everest.formatters.xml.datatypes.r1.DatatypeFormatter;
import org.marc.everest.formatters.xml.datatypes.r1.R1FormatterCompatibilityMode;
import org.marc.everest.formatters.xml.its1.XmlIts1Formatter;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.xml.XMLStateStreamWriter;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.dao.ProviderDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.BodyConstants;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.extension.ObservationWithConfidentialityCode;
import org.oscarehr.e2e.lens.common.EverestBugLens;
import org.oscarehr.e2e.model.CreatePatient;

/**
 * The Class EverestUtils.
 */
public class EverestUtils {
	private static final Logger log = Logger.getLogger(EverestUtils.class.getName());
	private static final Map<Integer, Demographic> demographicCache = new HashMap<>();
	private static final Map<Integer, Provider> providerCache = new HashMap<>();
	private static Map<String, String> preventionTypeCodes = null;
	static {
		try (InputStream is = EverestUtils.class.getResourceAsStream("/PreventionItems.xml")) {
			Element root = new SAXBuilder().build(is).getRootElement();
			preventionTypeCodes = root.getChildren("item").stream()
					.filter(e -> e.getAttribute("atc") != null && !isNullorEmptyorWhitespace(e.getAttribute("atc").getValue()))
					.collect(Collectors.toMap(e -> e.getAttribute("name").getValue(), e -> e.getAttribute("atc").getValue()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private static final XmlIts1Formatter fmtr = new XmlIts1Formatter() {{
		getGraphAides().add(new DatatypeFormatter(R1FormatterCompatibilityMode.ClinicalDocumentArchitecture));
		addCachedClass(ClinicalDocument.class);
		registerXSITypeName("POCD_MT000040UV.Observation", ObservationWithConfidentialityCode.class);
	}};

	EverestUtils() {
		throw new UnsupportedOperationException();
	}

	// Lambda Functions
	/** Maps a component to a list of iis. */
	public static Function<Component3, LIST<II>> componentToII = e -> {
		LIST<II> result = null;
		if(e.getSection() != null) {
			result = e.getSection().getTemplateId();
		}
		return result;
	};

	/** Looks for section entry with entries only. */
	public static BiPredicate<LIST<II>, BodyConstants.AbstractBodyConstants> filledEntryFilter = (e, bc) -> {
		Boolean result = false;
		if(e != null && !e.isNull() && !e.isEmpty()) {
			result = e.contains(new II(bc.WITH_ENTRIES_TEMPLATE_ID));
		}
		return result;
	};

	/** Looks for section entry with or without entries. */
	public static BiPredicate<LIST<II>, BodyConstants.AbstractBodyConstants> entryFilter = (e, bc) -> {
		Boolean result = false;
		if(e != null && !e.isNull() && !e.isEmpty()) {
			result = filledEntryFilter.test(e, bc) || e.contains(new II(bc.WITHOUT_ENTRIES_TEMPLATE_ID));
		}
		return result;
	};

	/** Looks for entryRelationships of specified oid type. */
	public static BiPredicate<EntryRelationship, String> isEntryRelationshipType = (e, oid) -> {
		Boolean result = false;
		if(e != null && e.getTemplateId() != null && !e.getTemplateId().isEmpty()) {
			result = e.getTemplateId().contains(new II(oid));
		}
		return result;
	};

	// General Everest Utility Functions
	/**
	 * Check String for Null, Empty or Whitespace
	 *
	 * @param str the str
	 * @return true if null, empty or whitespace; false otherwise
	 */
	public static Boolean isNullorEmptyorWhitespace(final String str) {
		return str == null || str.trim().isEmpty();
	}

	/**
	 * Generate Document Function.
	 *
	 * @param clinicalDocument the clinical document
	 * @param validation boolean to run validation
	 * @return the xml string
	 */
	public static String generateDocumentToString(final ClinicalDocument clinicalDocument, final Boolean validation) {
		String output = null;
		if(clinicalDocument == null) {
			return output;
		}

		try (StringWriter sw = new StringWriter()) {
			fmtr.setValidateConformance(validation);
			XMLStateStreamWriter xssw = new XMLStateStreamWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(sw));

			xssw.writeStartDocument(Constants.XML.ENCODING, Constants.XML.VERSION);
			xssw.writeStartElement("hl7", "ClinicalDocument", "urn:hl7-org:v3");
			xssw.writeNamespace("xs", "http://www.w3.org/2001/XMLSchema");
			xssw.writeNamespace("hl7", "urn:hl7-org:v3");
			xssw.writeNamespace("e2e", "http://standards.pito.bc.ca/E2E-DTC/cda");
			xssw.writeAttribute("xmlns", "xsi", "xsi", "http://www.w3.org/2001/XMLSchema-instance");
			xssw.writeAttribute("xsi", "schemaLocation", "schemaLocation", "urn:hl7-org:v3 Schemas/CDA-PITO-E2E.xsd");
			xssw.writeDefaultNamespace("urn:hl7-org:v3"); // Default hl7 namespace

			IFormatterGraphResult result = fmtr.graph(xssw, clinicalDocument);

			xssw.writeEndElement();
			xssw.writeEndDocument();
			xssw.close();

			output = new EverestBugLens().get(prettyFormatXML(sw.toString(), Constants.XML.INDENT));

			if(validation) {
				E2EEverestValidator.isValidCDAGraph(result);
				E2EXSDValidator.isValidXML(output);
			}
		} catch (IOException|XMLStreamException e) {
			log.error(e.toString());
		}

		return output;
	}

	/**
	 * Parse Document Function.
	 *
	 * @param document the xml string
	 * @param validation boolean to run validation
	 * @return the clinical document
	 */
	public static ClinicalDocument parseDocumentFromString(final String document, final Boolean validation) {
		ClinicalDocument clinicalDocument = null;
		if(document == null) {
			return clinicalDocument;
		}

		String input = new EverestBugLens().put(document);
		try (InputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))) {
			fmtr.setValidateConformance(validation);
			IFormatterParseResult result = fmtr.parse(is);
			clinicalDocument = (ClinicalDocument) result.getStructure();

			if(validation) {
				E2EEverestValidator.isValidCDAParse(result);
				E2EXSDValidator.isValidXML(document);
			}
		} catch (IOException|ObjectDisposedException e) {
			log.error(e.toString());
		}

		return clinicalDocument;
	}

	/**
	 * Pretty Print XML.
	 *
	 * @param input the input string
	 * @param indent the indent amount
	 * @return the formatted string
	 */
	public static String prettyFormatXML(final String input, final Integer indent) {
		if(!isNullorEmptyorWhitespace(input)) {
			try {
				Source xmlInput = new StreamSource(new StringReader(input));
				StreamResult xmlOutput = new StreamResult(new StringWriter());

				Transformer tf = TransformerFactory.newInstance().newTransformer();
				tf.setOutputProperty(OutputKeys.ENCODING, Constants.XML.ENCODING);
				tf.setOutputProperty(OutputKeys.INDENT, "yes");
				tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
				tf.transform(xmlInput, xmlOutput);

				return xmlOutput.getWriter().toString().replaceFirst("<Clin", "\n<Clin");
			} catch (TransformerException e) {
				log.error(e.toString());
			}
		}

		return null;
	}

	// Header Utility Functions
	// Add a telecom element to the telecoms set
	public static void addTelecomPart(SET<TEL> telecoms, String value, TelecommunicationsAddressUse telecomAddressUse, TelecomType telecomType) {
		if(!isNullorEmptyorWhitespace(value)) {
			if(telecomType == Constants.TelecomType.TELEPHONE) {
				telecoms.add(new TEL(Constants.DocumentHeader.TEL_PREFIX + value.replaceAll("-", ""), telecomAddressUse));
			} else if(telecomType == Constants.TelecomType.EMAIL) {
				telecoms.add(new TEL(Constants.DocumentHeader.EMAIL_PREFIX + value, telecomAddressUse));
			}
		}
	}

	// Add a name to the names set
	public static void addNamePart(SET<PN> names, String firstName, String lastName, EntityNameUse entityNameUse) {
		ArrayList<ENXP> name = new ArrayList<ENXP>();
		if(!isNullorEmptyorWhitespace(firstName)) {
			name.add(new ENXP(firstName, EntityNamePartType.Given));
		}
		if(!isNullorEmptyorWhitespace(lastName)) {
			name.add(new ENXP(lastName, EntityNamePartType.Family));
		}
		if(!name.isEmpty()) {
			names.add(new PN(entityNameUse, name));
		}
	}

	// Body Utility Functions
	/**
	 * Find the entryRelationship with the specified oid.
	 *
	 * @param entryRelationships the entryRelationships
	 * @param oid the oid
	 * @return the entryRelationship if found, otherwise null
	 */
	public static EntryRelationship findEntryRelationship(final ArrayList<EntryRelationship> entryRelationships, final String oid) {
		return entryRelationships.stream()
				.filter(e -> EverestUtils.isEntryRelationshipType.test(e, oid))
				.findFirst()
				.orElse(null);
	}

	/**
	 * Creates an observation template entryRelationship.
	 *
	 * @param oid the oid
	 * @return a generic observation template entryRelationship
	 */
	public static EntryRelationship createObservationTemplate(final String oid) {
		CD<String> code = new CD<String>() {{
			setCodeSystem(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID);
			setCodeSystemName(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME);
		}};

		Observation observation = new Observation();
		observation.setCode(code);

		EntryRelationship entryRelationship = new EntryRelationship();
		entryRelationship.setContextConductionInd(true);
		entryRelationship.setTemplateId(Arrays.asList(new II(oid)));
		entryRelationship.setClinicalStatement(observation);
		return entryRelationship;
	}

	/*
	// Create a Date object from dateString
	public static Date stringToDate(String dateString) {
		String[] formatStrings = {"yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd hh:mm", "yyyy-MM-dd"};
		Integer i = 0;
		while(i < formatStrings.length) {
			try {
				return new SimpleDateFormat(formatStrings[i]).parse(dateString);
			} catch (Exception e) {
				i++;
			}
		}

		return null;
	}
	 */

	// Caching Utility Functions
	/**
	 * Find the provider of a given demographicNo.
	 *
	 * @param demographicNo the demographicNo
	 * @return the demographic's providerNo
	 */
	public static String getDemographicProviderNo(final Integer demographicNo) {
		String providerNo = null;
		try {
			if(demographicCache.containsKey(demographicNo)) {
				providerNo =  demographicCache.get(demographicNo).getProviderNo();
			} else {
				DemographicDao demographicDao = CreatePatient.getApplicationContext().getBean(DemographicDao.class);
				Demographic demographic = demographicDao.find(demographicNo);
				demographicCache.put(demographicNo, demographic);
				providerNo = demographic.getProviderNo();
			}
		} catch (Exception e) {
			log.warn("Demographic " + demographicNo + " not found");
		}

		return providerNo;
	}

	/**
	 * Find the provider from the providerNo String.
	 *
	 * @param providerNo the provider no
	 * @return the provider
	 */
	public static Provider getProviderFromString(final String providerNo) {
		Provider provider = null;
		try {
			Integer providerId = Integer.parseInt(providerNo);
			if(providerCache.containsKey(providerId)) {
				provider = providerCache.get(providerId);
			} else {
				ProviderDao providerDao = CreatePatient.getApplicationContext().getBean(ProviderDao.class);
				provider = providerDao.find(providerId);
				providerCache.put(providerId, provider);
			}
		} catch (NumberFormatException e) {
			provider = null;
		}

		return provider;
	}

	// PatientModel Supplemental Functions
	/**
	 * Gets the ICD9 description.
	 * TODO [OSCAR] Replace mock with OSCAR icd9Dao.
	 *
	 * @param code the ICD9 code
	 * @return the ICD9 description
	 */
	public static String getICD9Description(final String code) {
		if(!EverestUtils.isNullorEmptyorWhitespace(code) && Mappings.icd9Map.containsKey(code)) {
			return Mappings.icd9Map.get(code);
		}

		return null;
	}

	/*
	// Find the description of measurement type
	// TODO [OSCAR] Replace mock with OSCAR measurementTypeDao
	public static String getTypeDescription(String type) {
		if(!isNullorEmptyorWhitespace(Mappings.measurementTypeMap.get(type))) {
			return Mappings.measurementTypeMap.get(type);
		}

		return type;
	}
	 */

	/**
	 * Find ATC code of prevention type.
	 *
	 * @param type the type
	 * @return the prevention type
	 */
	public static String getPreventionType(final String type) {
		if(!isNullorEmptyorWhitespace(type) && preventionTypeCodes.containsKey(type)) {
			return preventionTypeCodes.get(type);
		}

		return null;
	}
}
