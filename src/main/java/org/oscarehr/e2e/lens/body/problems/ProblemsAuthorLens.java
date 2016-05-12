package org.oscarehr.e2e.lens.body.problems;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.lens.body.template.AuthorParticipationLens;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class ProblemsAuthorLens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsAuthorLens() {
		get = source -> {
			Dxresearch problem = source.getLeft();
			ArrayList<Author> authors = source.getRight().getClinicalStatementIfObservation().getAuthor();

			if(authors == null || authors.isEmpty()) {
				authors = new AuthorParticipationLens(problem.getProviderNo()).get(problem.getUpdateDate());
			}

			source.getRight().getClinicalStatementIfObservation().setAuthor(authors);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			Dxresearch problem = target.getLeft();
			ArrayList<Author> authors = target.getRight().getClinicalStatementIfObservation().getAuthor();

			if(problem.getUpdateDate() == null) {
				problem.setUpdateDate(new AuthorParticipationLens(problem.getProviderNo()).put(authors));
			}

			return new ImmutablePair<>(problem, target.getRight());
		};
	}
}
