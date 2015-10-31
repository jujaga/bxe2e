package org.oscarehr.e2e.lens;

import org.apache.log4j.Logger;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.header.HeaderLens;
import org.oscarehr.e2e.model.PatientModel;

// TODO Refactor this lens to allow multiple E2E use cases
public class ClinicalDocumentLens extends AbstractLens<PatientModel, ClinicalDocument> {
	public ClinicalDocumentLens() {
		log = Logger.getLogger(ClinicalDocumentLens.class.getName());

		get = patientModel -> {
			CE<String> code = Constants.EMRConversionDocument.CODE;
			II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

			ClinicalDocument clinicalDocument = new HeaderLens(code, templateId).get(patientModel);
			return clinicalDocument;

			/*EmrExportPopulator emrExportPopulator = new EmrExportPopulator(patientModel, code, templateId);
			emrExportPopulator.populate();

			return emrExportPopulator.getClinicalDocument();*/
		};

		// TODO Put Function
	}
}
