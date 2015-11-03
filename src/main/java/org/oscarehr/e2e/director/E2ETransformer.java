package org.oscarehr.e2e.director;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.PatientModel;

public class E2ETransformer extends CDATransformer<PatientModel> {
	public E2ETransformer(PatientModel model, AbstractLens<PatientModel, ClinicalDocument> lens) {
		super(model, lens);
	}
}
