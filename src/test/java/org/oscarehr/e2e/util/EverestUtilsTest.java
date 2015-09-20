package org.oscarehr.e2e.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.util.EverestUtils;

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
	public void prettyFormatXMLTest() {
		assertNotNull(EverestUtils.prettyFormatXML("<test/>", Constants.XML.INDENT));
		assertNull(EverestUtils.prettyFormatXML(null, Constants.XML.INDENT));
	}

	@Test
	public void getPreventionTypeTest() {
		assertNull(EverestUtils.getPreventionType(null));
		assertNotNull(EverestUtils.preventionTypeCodes);
		assertEquals(38, EverestUtils.preventionTypeCodes.size());
		assertEquals("J07CA02", EverestUtils.getPreventionType("DTaP-HBV-IPV-Hib"));
	}

	@Test
	public void getDemographicProviderNoTest() {
		assertNull(EverestUtils.getDemographicProviderNo(Constants.Runtime.INVALID_VALUE));
		assertNotNull(EverestUtils.getDemographicProviderNo(Constants.Runtime.VALID_DEMOGRAPHIC));
		// Test Caching
		assertNotNull(EverestUtils.getDemographicProviderNo(Constants.Runtime.VALID_DEMOGRAPHIC));
	}
}
