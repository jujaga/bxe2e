package org.oscarehr.e2e.rule.header;

import java.util.ArrayList;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.oscarehr.e2e.lens.header.InformationRecipientLens;
import org.oscarehr.e2e.rule.AbstractRule;

public class InformationRecipientRule extends AbstractRule<Object, ArrayList<InformationRecipient>> {
	public InformationRecipientRule(Object source, ArrayList<InformationRecipient> target) {
		super(source, target);
	}

	@Override
	protected void defineLens() {
		lens = new InformationRecipientLens();
	}
}
