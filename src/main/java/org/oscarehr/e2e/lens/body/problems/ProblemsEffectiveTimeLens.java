package org.oscarehr.e2e.lens.body.problems;

import java.util.Date;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.TSDateLens;

public class ProblemsEffectiveTimeLens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsEffectiveTimeLens() {
		get = source -> {
			Date date = source.getLeft().getStartDate();
			IVL<TS> effectiveTime = source.getRight().getClinicalStatementIfObservation().getEffectiveTime();

			if(effectiveTime == null) {
				TS startTime = new TSDateLens().get(date);
				if(startTime != null) {
					effectiveTime = new IVL<>(startTime, null);
				}
			}

			source.getRight().getClinicalStatementIfObservation().setEffectiveTime(effectiveTime);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			Date date = target.getLeft().getStartDate();
			IVL<TS> effectiveTime = target.getRight().getClinicalStatementIfObservation().getEffectiveTime();

			if(date == null && effectiveTime != null && !effectiveTime.isNull()) {
				date = new TSDateLens().put(effectiveTime.getLow());
			}

			target.getLeft().setStartDate(date);
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
