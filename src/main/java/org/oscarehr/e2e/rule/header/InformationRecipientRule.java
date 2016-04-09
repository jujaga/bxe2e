package org.oscarehr.e2e.rule.header;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.header.InformationRecipientLens;
import org.oscarehr.e2e.rule.common.AbstractRule;

public class InformationRecipientRule extends AbstractRule<Object, ArrayList<InformationRecipient>> {
	public InformationRecipientRule(Object source, ArrayList<InformationRecipient> target, Original original) {
		super(source, target, original);
	}

	@Override
	protected AbstractLens<Pair<Object, ArrayList<InformationRecipient>>, Pair<Object, ArrayList<InformationRecipient>>> defineLens() {
		return new InformationRecipientLens();
	}
}
