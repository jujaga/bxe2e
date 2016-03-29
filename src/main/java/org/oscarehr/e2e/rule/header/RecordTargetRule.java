package org.oscarehr.e2e.rule.header;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.header.recordtarget.AddressLens;
import org.oscarehr.e2e.lens.header.recordtarget.BirthDateLens;
import org.oscarehr.e2e.lens.header.recordtarget.GenderLens;
import org.oscarehr.e2e.lens.header.recordtarget.HinIdLens;
import org.oscarehr.e2e.lens.header.recordtarget.LanguageLens;
import org.oscarehr.e2e.lens.header.recordtarget.NameLens;
import org.oscarehr.e2e.lens.header.recordtarget.RecordTargetLens;
import org.oscarehr.e2e.lens.header.recordtarget.TelecomLens;
import org.oscarehr.e2e.rule.AbstractRule;

public class RecordTargetRule extends AbstractRule<Demographic, RecordTarget> {
	public RecordTargetRule(Demographic source, RecordTarget target) {
		super(source, target);
	}

	@Override
	protected void defineLens() {
		lens = new RecordTargetLens()
				.compose(new HinIdLens())
				.compose(new AddressLens())
				.compose(new TelecomLens())
				.compose(new NameLens())
				.compose(new GenderLens())
				.compose(new BirthDateLens())
				.compose(new LanguageLens());
	}
}
