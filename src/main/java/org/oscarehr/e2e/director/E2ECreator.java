package org.oscarehr.e2e.director;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.CreatePatient;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.populator.EmrExportPopulator;

public class E2ECreator {
	E2ECreator() {
		throw new UnsupportedOperationException();
	}

	public static ClinicalDocument createEmrConversionDocument(Integer demographicNo) {
		PatientModel patientModel = new CreatePatient(demographicNo).getPatientModel();
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		EmrExportPopulator emrExportPopulator = new EmrExportPopulator(patientModel, code, templateId);
		emrExportPopulator.populate();

		return emrExportPopulator.getClinicalDocument();
	}
}
