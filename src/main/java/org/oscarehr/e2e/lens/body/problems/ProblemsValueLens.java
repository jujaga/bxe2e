package org.oscarehr.e2e.lens.body.problems;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.ANY;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class ProblemsValueLens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsValueLens() {
		get = source -> {
			ANY value = source.getRight().getClinicalStatementIfObservation().getValue();

			if(value == null) {
				value = new CD<String>();
				value.setNullFlavor(NullFlavor.NoInformation);
			}

			source.getRight().getClinicalStatementIfObservation().setValue(value);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
