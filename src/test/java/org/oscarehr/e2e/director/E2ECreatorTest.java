package org.oscarehr.e2e.director;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.director.E2ECreator;

public class E2ECreatorTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void instantiationTest() {
		new E2ECreator();
	}

	@Test
	public void createEmrConversionDocumentTest() {
		assertNotNull(E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC));
	}

	@Test
	public void emptyCreateEmrConversionDocumentTest() {
		assertNotNull(E2ECreator.createEmrConversionDocument(Constants.Runtime.EMPTY_DEMOGRAPHIC));
	}
}
