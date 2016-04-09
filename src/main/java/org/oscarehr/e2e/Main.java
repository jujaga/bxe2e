package org.oscarehr.e2e;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
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
		// Define Transformer
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
		// Define Transformer
		E2EConversionTransformer transformer = new E2EConversionTransformer(null, clinicalDocument, Original.TARGET);

		// Populate Patient Model
		PatientModel patientModel = transformer.getModel();

		// Output Patient Model
		if(patientModel.isLoaded()) {
			System.out.println("\n" + ReflectionToStringBuilder.toString(patientModel.getDemographic(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println("\n" + ReflectionToStringBuilder.toString(patientModel.getClinic(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println("\nImported");
		}

		return patientModel;
	}
}
