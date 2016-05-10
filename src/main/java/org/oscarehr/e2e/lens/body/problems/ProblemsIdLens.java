package org.oscarehr.e2e.lens.body.problems;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.EntryIdLens;

public class ProblemsIdLens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsIdLens() {
		get = source -> {
			Integer problemId = source.getLeft().getDxresearchNo();
			SET<II> id = source.getRight().getClinicalStatementIfObservation().getId();

			if(id == null) {
				id = new EntryIdLens(Constants.IdPrefixes.ProblemList).get(problemId);
			}

			source.getRight().getClinicalStatementIfObservation().setId(id);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			Integer problemId = target.getLeft().getDxresearchNo();
			SET<II> id = source.getRight().getClinicalStatementIfObservation().getId();

			if(problemId == null) {
				problemId = new EntryIdLens(Constants.IdPrefixes.ProblemList).put(id);
			}

			target.getLeft().setDxresearchNo(problemId);
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
