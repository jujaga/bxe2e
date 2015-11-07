package org.oscarehr.e2e;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.director.E2ETransformer;
import org.oscarehr.e2e.lens.ClinicalDocumentLens;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.temp.Test1Lens;
import org.oscarehr.e2e.lens.temp.Test2Lens;
import org.oscarehr.e2e.model.CreatePatient;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.util.EverestUtils;

public class Main {
	Main() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		Integer demographicNo = Constants.Runtime.VALID_DEMOGRAPHIC;

		AbstractLens<String, Boolean> test = AbstractLens.compose(new Test1Lens(), new Test2Lens());
		System.out.println(test.get("1"));
		//System.out.println(test.put(false));
		
		ClinicalDocument clinicalDocument = doExport(demographicNo);
		doImport(clinicalDocument);
	}

	private static ClinicalDocument doExport(Integer demographicNo) {
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
			System.out.println("Exported");
		}

		return clinicalDocument;
	}

	private static PatientModel doImport(ClinicalDocument clinicalDocument) {
		// Define Model and Lens;
		AbstractLens<PatientModel, ClinicalDocument> lens = new ClinicalDocumentLens();
		E2ETransformer e2eTransformer = new E2ETransformer(null, lens);

		// Populate Patient Model
		PatientModel patientModel = e2eTransformer.doImport(clinicalDocument);
		if(patientModel.isLoaded()) {
			System.out.println("Imported");
		}

		return patientModel;
	}
}
