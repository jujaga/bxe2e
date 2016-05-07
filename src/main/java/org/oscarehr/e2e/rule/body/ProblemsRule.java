package org.oscarehr.e2e.rule.body;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.lens.body.problems.ProblemsLens;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.rule.common.AbstractRule;

public class ProblemsRule extends AbstractRule<Dxresearch, Entry> {
	public ProblemsRule(Dxresearch source, Entry target) {
		super(source, target);
		this.ruleName = ruleName.concat("-").concat(Integer.toHexString(System.identityHashCode(this)));

		if(this.pair.getLeft() == null) {
			pair = new ImmutablePair<>(new Dxresearch(), pair.getRight());
		}
		if(this.pair.getRight() == null) {
			pair = new ImmutablePair<>(pair.getLeft(), new Entry());
		}
	}

	@Override
	protected AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> defineLens() {
		return new ProblemsLens();
	}
}
