package org.oscarehr.e2e.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.oscarehr.e2e.constant.Constants;

public class EverestUtilsTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void instantiationTest() {
		new EverestUtils();
	}

	@Test
	public void isNullorEmptyorWhitespaceTest() {
		assertTrue(EverestUtils.isNullorEmptyorWhitespace(null));
		assertTrue(EverestUtils.isNullorEmptyorWhitespace(""));
		assertTrue(EverestUtils.isNullorEmptyorWhitespace(" "));
		assertFalse(EverestUtils.isNullorEmptyorWhitespace("test"));
	}

	@Test
	public void generateDocumentToStringTest() {
		ClinicalDocument clinicalDocument = new ClinicalDocument();
		assertNotNull(EverestUtils.generateDocumentToString(clinicalDocument, true));
		assertNull(EverestUtils.generateDocumentToString(null, true));
	}

	@Test
	public void parseDocumentFromStringTest() {
		String document = EverestUtils.generateDocumentToString(new ClinicalDocument(), true);
		assertNotNull(EverestUtils.parseDocumentFromString(document, true));
		assertNull(EverestUtils.parseDocumentFromString(null, true));
	}

	@Test
	public void prettyFormatXMLTest() {
		assertNotNull(EverestUtils.prettyFormatXML("<test/>", Constants.XML.INDENT));
		assertNull(EverestUtils.prettyFormatXML(null, Constants.XML.INDENT));
	}

	@Test
	public void findEntryRelationshipTest() {
		final String testOid = "testOid";
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<>();
		assertNull(EverestUtils.findEntryRelationship(entryRelationships, testOid));
		entryRelationships.add(EverestUtils.createObservationTemplate(testOid));
		assertNotNull(EverestUtils.findEntryRelationship(entryRelationships, testOid));
	}

	@Test
	public void createObservationTemplateTest() {
		final String testOid = "testOid";
		EntryRelationship entryRelationship = EverestUtils.createObservationTemplate(testOid);
		assertNotNull(entryRelationship);
		assertTrue(entryRelationship.getContextConductionInd().getValue());
		assertEquals(testOid, entryRelationship.getTemplateId().get(0).getRoot());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertNotNull(observation.getCode());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, observation.getCode().getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, observation.getCode().getCodeSystemName());
	}

	@Test
	public void getDemographicProviderNoTest() {
		assertNull(EverestUtils.getDemographicProviderNo(Constants.Runtime.INVALID_VALUE));
		assertEquals(Constants.Runtime.VALID_PROVIDER.toString(), EverestUtils.getDemographicProviderNo(Constants.Runtime.VALID_DEMOGRAPHIC));
		// Test Caching
		assertEquals(Constants.Runtime.VALID_PROVIDER.toString(), EverestUtils.getDemographicProviderNo(Constants.Runtime.VALID_DEMOGRAPHIC));
	}

	@Test
	public void getProviderFromStringTest() {
		assertNotNull(EverestUtils.getProviderFromString(Constants.Runtime.VALID_PROVIDER.toString()));
		assertNull(EverestUtils.getProviderFromString(Constants.Runtime.INVALID_VALUE.toString()));
		assertNull(EverestUtils.getProviderFromString(Constants.Runtime.SPRING_APPLICATION_CONTEXT));
	}

	@Test
	public void getICD9DescriptionTest() {
		assertEquals("HEART FAILURE*", EverestUtils.getICD9Description("428"));
		assertNull(EverestUtils.getICD9Description(Constants.Runtime.INVALID_VALUE.toString()));
		assertNull(EverestUtils.getICD9Description(null));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getPreventionTypeTest() throws Exception {
		Field f = EverestUtils.class.getDeclaredField("preventionTypeCodes");
		f.setAccessible(true);
		Map<String, String> preventionTypeCodes = (Map<String, String>) f.get(EverestUtils.class);

		assertNotNull(preventionTypeCodes);
		assertEquals(38, preventionTypeCodes.size());
		assertNull(EverestUtils.getPreventionType(null));
		assertEquals("J07CA02", EverestUtils.getPreventionType("DTaP-HBV-IPV-Hib"));
	}
}
