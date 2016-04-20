package org.oscarehr.e2e.rule.header;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.header.InformationRecipientLens;
import org.oscarehr.e2e.rule.common.AbstractRule;

public class InformationRecipientRule extends AbstractRule<Object, ArrayList<InformationRecipient>> {
	public InformationRecipientRule(Object source, ArrayList<InformationRecipient> target) {
		super(source, target);
		if(this.pair.getLeft() == null) {
			pair = new ImmutablePair<>(new Object(), pair.getRight());
		}
		if(this.pair.getRight() == null) {
			pair = new ImmutablePair<>(pair.getLeft(), new ArrayList<>());
		}
	}

	@Override
	protected AbstractLens<Pair<Object, ArrayList<InformationRecipient>>, Pair<Object, ArrayList<InformationRecipient>>> defineLens() {
		return new InformationRecipientLens();
	}
}
