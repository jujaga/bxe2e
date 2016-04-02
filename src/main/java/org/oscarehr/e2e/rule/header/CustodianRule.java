package org.oscarehr.e2e.rule.header;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianIdLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianNameLens;
import org.oscarehr.e2e.rule.AbstractRule;

public class CustodianRule extends AbstractRule<Clinic, Custodian> {
	public CustodianRule(Clinic source, Custodian target) {
		super(source, target);
	}

	@Override
	protected AbstractLens<MutablePair<Clinic, Custodian>, MutablePair<Clinic, Custodian>> defineLens() {
		return new CustodianLens()
				.compose(new CustodianIdLens())
				.compose(new CustodianNameLens());
	}
}
