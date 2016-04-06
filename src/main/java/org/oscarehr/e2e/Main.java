package org.oscarehr.e2e;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.director.E2ETransformer;
import org.oscarehr.e2e.lens.ClinicalDocumentLens;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.CreatePatient;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.rule.common.IRule.Original;
import org.oscarehr.e2e.transformer.E2EConversionTransformer;
import org.oscarehr.e2e.util.EverestUtils;

public class Main {
	Main() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		Integer demographicNo = Constants.Runtime.VALID_DEMOGRAPHIC;
		PatientModel patientModel = new CreatePatient(demographicNo).getPatientModel();

		ClinicalDocument clinicalDocument = doExport(patientModel);
		doImport(clinicalDocument);
	}

	private static ClinicalDocument doExport(PatientModel patientModel) {
		// Define Model and Lens
		E2EConversionTransformer transformer = new E2EConversionTransformer(patientModel, null, Original.SOURCE);

		// Populate Clinical Document
		ClinicalDocument clinicalDocument = transformer.getTarget();

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
		//E2EConversionTransformer transformer = new E2EConversionTransformer(null, clinicalDocument, Original.TARGET);

		// Populate Patient Model
		PatientModel patientModel = e2eTransformer.doImport(clinicalDocument);
		if(patientModel.isLoaded()) {
			System.out.println("Imported");
		}

		return patientModel;
	}
}
