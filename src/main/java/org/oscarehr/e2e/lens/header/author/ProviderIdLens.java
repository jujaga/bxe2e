package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.AuthorIdLens;

public class ProviderIdLens extends AbstractLens<Pair<String, ArrayList<Author>>, Pair<String, ArrayList<Author>>> {
	public ProviderIdLens() {
		get = source -> {
			SET<II> ids = source.getRight().get(0).getAssignedAuthor().getId();

			if(ids == null) {
				ids = new AuthorIdLens().get(source.getLeft());
			}

			source.getRight().get(0).getAssignedAuthor().setId(ids);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
