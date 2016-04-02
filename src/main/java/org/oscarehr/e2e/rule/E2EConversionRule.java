package org.oscarehr.e2e.rule;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.lens.E2EConversionLens;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.Model;
import org.oscarehr.e2e.model.PatientModel;

public class E2EConversionRule extends AbstractCDARule {
	public E2EConversionRule(PatientModel source, ClinicalDocument target) {
		super(source, target);
	}

	@Override
	protected AbstractLens<MutablePair<Model, ClinicalDocument>, MutablePair<Model, ClinicalDocument>> defineLens() {
		return super.defineLens().compose(new E2EConversionLens());
	}
}
