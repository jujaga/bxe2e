package org.oscarehr.e2e.lens;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.populator.EmrExportPopulator;

public class ClinicalDocumentLens implements Lens<PatientModel, ClinicalDocument> {
	@Override
	public ClinicalDocument get(PatientModel patientModel) {
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		EmrExportPopulator emrExportPopulator = new EmrExportPopulator(patientModel, code, templateId);
		emrExportPopulator.populate();

		return emrExportPopulator.getClinicalDocument();
	}

	@Override
	public PatientModel put(ClinicalDocument clinicalDocument) {
		// TODO Auto-generated method stub
		return null;
	}
}
