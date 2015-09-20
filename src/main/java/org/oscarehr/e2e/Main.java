package org.oscarehr.e2e;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.director.E2ECreator;
import org.oscarehr.e2e.util.EverestUtils;

public class Main {
	Main() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		Integer demographicNo = Constants.Runtime.VALID_DEMOGRAPHIC;

		// Populate Clinical Document
		ClinicalDocument clinicalDocument = E2ECreator.createEmrConversionDocument(demographicNo);

		// Output Clinical Document as String
		String output = EverestUtils.generateDocumentToString(clinicalDocument, true);
		if(!EverestUtils.isNullorEmptyorWhitespace(output)) {
			System.out.println(output);
		}
	}
}
