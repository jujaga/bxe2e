package org.oscarehr.e2e.rule.header;

import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianIdLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianNameLens;
import org.oscarehr.e2e.rule.common.AbstractRule;

public class CustodianRule extends AbstractRule<Clinic, Custodian> {
	public CustodianRule(Clinic source, Custodian target, Original original) {
		super(source, target, original);
	}

	@Override
	protected AbstractLens<Pair<Clinic, Custodian>, Pair<Clinic, Custodian>> defineLens() {
		return new CustodianLens()
				.compose(new CustodianIdLens())
				.compose(new CustodianNameLens());
	}
}
