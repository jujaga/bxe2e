package org.oscarehr.e2e.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.exceptions.ObjectDisposedException;
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.formatters.interfaces.IFormatterParseResult;
import org.marc.everest.formatters.xml.datatypes.r1.DatatypeFormatter;
import org.marc.everest.formatters.xml.datatypes.r1.R1FormatterCompatibilityMode;
import org.marc.everest.formatters.xml.its1.XmlIts1Formatter;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LanguageCommunication;
import org.marc.everest.xml.XMLStateStreamWriter;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.dao.ProviderDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Constants.IdPrefixes;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.extension.ObservationWithConfidentialityCode;
import org.oscarehr.e2e.lens.common.EverestBugLens;
import org.oscarehr.e2e.model.CreatePatient;

public class EverestUtils {
	private static final Logger log = Logger.getLogger(EverestUtils.class.getName());
	private static final String OSCAR_PREVENTIONITEMS_FILE = "/PreventionItems.xml";
	static Map<String, String> preventionTypeCodes = null;

	private static final Map<Integer, Demographic> demographicCache = new ConcurrentHashMap<>();
	private static final Map<Integer, Provider> providerCache = new ConcurrentHashMap<>();

	EverestUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * General Everest Utility Functions
	 */
	// Check String for Null, Empty or Whitespace
	public static Boolean isNullorEmptyorWhitespace(String obj) {
		return obj == null || obj.isEmpty() || obj.trim().isEmpty();
	}

	// Generate Document Function
	public static String generateDocumentToString(ClinicalDocument clinicalDocument, Boolean validation) {
		String output = null;
		if(clinicalDocument == null) {
			return output;
		}

		try {
			StringWriter sw = new StringWriter();
			XmlIts1Formatter fmtr = getFormatter(validation);
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
		} catch (XMLStreamException e) {
			log.error(e.toString());
		}

		return output;
	}

	// Parse Document Function
	public static ClinicalDocument parseDocumentFromString(String document, Boolean validation) {
		ClinicalDocument clinicalDocument = null;
		if(document == null) {
			return clinicalDocument;
		}

		try {
			String input = new EverestBugLens().put(document);
			InputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
			XmlIts1Formatter fmtr = getFormatter(validation);
			IFormatterParseResult result = fmtr.parse(is);
			clinicalDocument = (ClinicalDocument) result.getStructure();

			if(validation) {
				E2EEverestValidator.isValidCDAParse(result);
				E2EXSDValidator.isValidXML(document);
			}
		} catch (ObjectDisposedException e) {
			log.error(e.toString());
		}

		return clinicalDocument;
	}

	// Pretty Print XML
	public static String prettyFormatXML(String input, Integer indent) {
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

	// Standard Formatter Generator
	private static XmlIts1Formatter getFormatter(Boolean validation) {
		XmlIts1Formatter fmtr = new XmlIts1Formatter();
		fmtr.setValidateConformance(validation);
		fmtr.getGraphAides().add(new DatatypeFormatter(R1FormatterCompatibilityMode.ClinicalDocumentArchitecture));
		fmtr.addCachedClass(ClinicalDocument.class);
		fmtr.registerXSITypeName("POCD_MT000040UV.Observation", ObservationWithConfidentialityCode.class);
		return fmtr;
	}

	/**
	 * Header Utility Functions
	 */
	// Add an address to the addrParts list
	public static void addAddressPart(ArrayList<ADXP> addrParts, String value, AddressPartType addressPartType) {
		if(!isNullorEmptyorWhitespace(value)) {
			ADXP addrPart = new ADXP(value, addressPartType);
			addrParts.add(addrPart);
		}
	}

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

	// Add a language to the languages list
	public static void addLanguagePart(ArrayList<LanguageCommunication> languages, String value) {
		if(!isNullorEmptyorWhitespace(value) && Mappings.languageCode.containsKey(value)) {
			LanguageCommunication language = new LanguageCommunication();
			language.setLanguageCode(Mappings.languageCode.get(value));
			languages.add(language);
		}
	}

	/**
	 * Body Utility Functions
	 */
	// Create Prefix-number id object from integer
	public static SET<II> buildUniqueId(IdPrefixes prefix, Integer id) {
		if(id == null) {
			id = 0;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append("-").append(id.toString());

		II ii = new II(Constants.EMR.EMR_OID, sb.toString());
		ii.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
		return new SET<>(ii);
	}

	/*
	// Create Prefix-number id object from long
	public static SET<II> buildUniqueId(IdPrefixes prefix, Long id) {
		if(id == null) {
			id = 0L;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append("-").append(id.toString());

		II ii = new II(Constants.EMR.EMR_OID, sb.toString());
		ii.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
		return new SET<II>(ii);
	}

	// Create a TS object from a Java Date with Day precision
	public static TS buildTSFromDate(Date date) {
		return buildTSFromDate(date, TS.DAY);
	}

	// Create a TS object from a Java Date with specified precision
	public static TS buildTSFromDate(Date date, Integer precision) {
		if(date == null) {
			return null;
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return new TS(calendar, precision);
	}

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

	/**
	 * Caching Utility Functions
	 */
	// Find the provider of a given demographicNo
	public static String getDemographicProviderNo(Integer demographicNo) {
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

	// Find the provider from providerNo String
	public static Provider getProviderFromString(String providerNo) {
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

	// PatientExport Supplemental Functions
	// TODO [OSCAR] Replace mock with OSCAR icd9Dao
	/*
	public static String getICD9Description(String code) {
		if(!EverestUtils.isNullorEmptyorWhitespace(code) && Mappings.icd9Map.containsKey(code)) {
			return Mappings.icd9Map.get(code);
		}

		return null;
	}


	// Find the description of measurement type
	// TODO [OSCAR] Replace mock with OSCAR measurementTypeDao
	public static String getTypeDescription(String type) {
		if(!isNullorEmptyorWhitespace(Mappings.measurementTypeMap.get(type))) {
			return Mappings.measurementTypeMap.get(type);
		}

		return type;
	}
	 */

	// Find ATC code of prevention type
	public static String getPreventionType(String type) {
		if(preventionTypeCodes == null) {
			try (InputStream is = EverestUtils.class.getResourceAsStream(OSCAR_PREVENTIONITEMS_FILE)) {
				Element root = new SAXBuilder().build(is).getRootElement();
				preventionTypeCodes = root.getChildren("item").stream()
						.filter(e -> e.getAttribute("atc") != null && !isNullorEmptyorWhitespace(e.getAttribute("atc").getValue()))
						.collect(Collectors.toConcurrentMap(e -> e.getAttribute("name").getValue(), e -> e.getAttribute("atc").getValue()));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		if(!isNullorEmptyorWhitespace(type) && preventionTypeCodes.containsKey(type)) {
			return preventionTypeCodes.get(type);
		}

		return null;
	}
}
