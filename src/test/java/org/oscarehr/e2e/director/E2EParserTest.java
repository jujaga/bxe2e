package org.oscarehr.e2e.director;

import org.junit.Test;
import org.oscarehr.e2e.director.E2EParser;

public class E2EParserTest {
	@Test(expected=UnsupportedOperationException.class)
	public void instantiationTest() {
		new E2EParser();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void parseEmrConversionDocumentTest() {
		E2EParser.parseEmrConversionDocument();
	}
}
