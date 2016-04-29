package org.oscarehr.e2e.rule;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.lens.E2EConversionLens;
import org.oscarehr.e2e.lens.body.AdvanceDirectivesSectionLens;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.IModel;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.rule.common.AbstractCDARule;

public class E2EConversionRule extends AbstractCDARule {
	public E2EConversionRule(PatientModel source, ClinicalDocument target) {
		super(source, target);
		if(this.pair.getLeft() == null) {
			pair = new ImmutablePair<>(new PatientModel(), pair.getRight());
		}
		if(this.pair.getRight() == null) {
			pair = new ImmutablePair<>(pair.getLeft(), new ClinicalDocument());
		}
	}

	@Override
	protected AbstractLens<Pair<IModel, ClinicalDocument>, Pair<IModel, ClinicalDocument>> defineLens() {
		return super.defineLens().compose(new E2EConversionLens())
				.compose(new AdvanceDirectivesSectionLens());
	}
}
