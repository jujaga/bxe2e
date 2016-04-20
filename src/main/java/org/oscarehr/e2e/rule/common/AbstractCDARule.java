package org.oscarehr.e2e.rule.common;

import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.lens.CDALens;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.Model;

public abstract class AbstractCDARule extends AbstractRule<Model, ClinicalDocument> {
	protected AbstractCDARule(Model source, ClinicalDocument target) {
		super(source, target);
	}

	@Override
	protected AbstractLens<Pair<Model, ClinicalDocument>, Pair<Model, ClinicalDocument>> defineLens() {
		return new CDALens();
	}
}
