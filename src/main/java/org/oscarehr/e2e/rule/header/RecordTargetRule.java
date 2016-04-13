package org.oscarehr.e2e.rule.header;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.header.recordtarget.AddressLens;
import org.oscarehr.e2e.lens.header.recordtarget.BirthDateLens;
import org.oscarehr.e2e.lens.header.recordtarget.GenderLens;
import org.oscarehr.e2e.lens.header.recordtarget.HinIdLens;
import org.oscarehr.e2e.lens.header.recordtarget.LanguageLens;
import org.oscarehr.e2e.lens.header.recordtarget.NameLens;
import org.oscarehr.e2e.lens.header.recordtarget.RecordTargetLens;
import org.oscarehr.e2e.lens.header.recordtarget.TelecomLens;
import org.oscarehr.e2e.rule.common.AbstractRule;

public class RecordTargetRule extends AbstractRule<Demographic, RecordTarget> {
	public RecordTargetRule(Demographic source, RecordTarget target, Original original) {
		super(source, target, original);
		if(this.pair.getLeft() == null) {
			pair = new ImmutablePair<>(new Demographic(), pair.getRight());
		}
		if(this.pair.getRight() == null) {
			pair = new ImmutablePair<>(pair.getLeft(), new RecordTarget());
		}
	}

	@Override
	protected AbstractLens<Pair<Demographic, RecordTarget>, Pair<Demographic, RecordTarget>> defineLens() {
		return new RecordTargetLens()
				.compose(new HinIdLens())
				.compose(new AddressLens())
				.compose(new TelecomLens())
				.compose(new NameLens())
				.compose(new GenderLens())
				.compose(new BirthDateLens())
				.compose(new LanguageLens());
	}
}
