package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class AuthorLens extends AbstractLens<Pair<String, ArrayList<Author>>, Pair<String, ArrayList<Author>>> {
	public AuthorLens() {
		get = source -> {
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
