package org.oscarehr.e2e.lens;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.header.HeaderLens;
import org.oscarehr.e2e.model.PatientModel;

// TODO Refactor this lens to allow multiple E2E use cases
// Use Code and TemplateID value passing for template behavior change (header and body)
public class ClinicalDocumentLens extends AbstractLens<PatientModel, ClinicalDocument> {
	public ClinicalDocumentLens() {
		get = patientModel -> {
			CE<String> code = Constants.EMRConversionDocument.CODE;
			II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

			ClinicalDocument clinicalDocument = new HeaderLens(code, templateId).get(patientModel);
			// TODO BodyLens
			return clinicalDocument;
		};

		put = (patientModel, clinicalDocument) -> {
			patientModel = new PatientModel();
			patientModel.setLoaded(true);
			return patientModel;
		};
	}
}
