package org.oscarehr.e2e;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.director.E2ETransformer;
import org.oscarehr.e2e.lens.AbstractLens;
import org.oscarehr.e2e.lens.ClinicalDocumentLens;
import org.oscarehr.e2e.model.CreatePatient;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.util.EverestUtils;

public class Main {
	Main() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		Integer demographicNo = Constants.Runtime.VALID_DEMOGRAPHIC;

		doExport(demographicNo);
		doImport();
	}

	private static void doExport(Integer demographicNo) {
		// Define Model and Lens
		PatientModel patientModel = new CreatePatient(demographicNo).getPatientModel();
		AbstractLens<PatientModel, ClinicalDocument> lens = new ClinicalDocumentLens();

		E2ETransformer e2eTransformer = new E2ETransformer(patientModel, lens);

		// Populate Clinical Document
		ClinicalDocument clinicalDocument = e2eTransformer.doExport();

		// Output Clinical Document as String
		String output = EverestUtils.generateDocumentToString(clinicalDocument, true);
		if(!EverestUtils.isNullorEmptyorWhitespace(output)) {
			System.out.println(output);
		}
	}

	private static void doImport() {
		// TODO Auto-generated method stub
	}
}
