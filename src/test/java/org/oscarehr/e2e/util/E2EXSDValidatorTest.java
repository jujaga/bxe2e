package org.oscarehr.e2e.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.e2e.util.E2EXSDValidator;

public class E2EXSDValidatorTest {
	private static String s;

	@SuppressWarnings("resource")
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		s = new Scanner(E2EXSDValidatorTest.class.getResourceAsStream("/e2e/validatorTest.xml"), "UTF-8").useDelimiter("\\Z").next();
		assertNotNull(s);
		assertFalse(s.isEmpty());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void instantiationTest() {
		new E2EXSDValidator();
	}

	@Test
	public void isWellFormedXMLTest() {
		assertTrue(E2EXSDValidator.isWellFormedXML(s));
	}

	@Test
	public void isWellFormedXMLOnNonWellFormedDocumentTest() {
		assertFalse(E2EXSDValidator.isWellFormedXML(s.replace("</ClinicalDocument>", "</clinicalDocument>")));
	}

	@Test
	public void testIsValidXML() {
		assertTrue(E2EXSDValidator.isValidXML(s));
	}

	@Test
	public void testIsValidXMLOnNonValidDocument() {
		assertFalse(E2EXSDValidator.isValidXML(s.replace("DOCSECT", "DOXSECT")));
	}
}
