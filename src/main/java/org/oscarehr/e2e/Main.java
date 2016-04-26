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
	private static Boolean validation = true;
	private static String exportString = null;

	Main() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		Integer demographicNo = Constants.Runtime.VALID_DEMOGRAPHIC;
		PatientModel patientModel = new CreatePatient(demographicNo).getPatientModel();

		doExport(patientModel);
		doImport(exportString);
	}

	private static ClinicalDocument doExport(PatientModel patientModel) {
		// Define Transformer
		E2EConversionTransformer transformer = new E2EConversionTransformer(patientModel, null, Original.SOURCE);

		// Populate Clinical Document
		ClinicalDocument clinicalDocument = transformer.getTarget();

		// Output Clinical Document as String
		exportString = EverestUtils.generateDocumentToString(clinicalDocument, validation);
		if(!EverestUtils.isNullorEmptyorWhitespace(exportString)) {
			System.out.println(exportString);
			System.out.println("Exported\n");
		}

		return clinicalDocument;
	}

	private static PatientModel doImport(String document) {
		return doImport(EverestUtils.parseDocumentFromString(document, validation));
	}

	private static PatientModel doImport(ClinicalDocument clinicalDocument) {
		// Define Transformer
		E2EConversionTransformer transformer = new E2EConversionTransformer(null, clinicalDocument, Original.TARGET);

		// Populate Patient Model
		PatientModel patientModel = transformer.getModel();

		// Output Patient Model
		if(patientModel.isLoaded()) {
			System.out.println(ReflectionToStringBuilder.toString(patientModel.getDemographic(), ToStringStyle.SIMPLE_STYLE));
			System.out.println(ReflectionToStringBuilder.toString(patientModel.getClinic(), ToStringStyle.SIMPLE_STYLE));
			System.out.println("\nImported\n");
		}

		return patientModel;
	}
}
