package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class AuthorLens extends AbstractLens<MutablePair<String, ArrayList<Author>>, MutablePair<String, ArrayList<Author>>> {
	public AuthorLens() {
		get = source -> {
			ArrayList<Author> author = source.getRight();

			if(author == null) {
				author = new ArrayList<Author>();
			}

			source.setRight(author);
			return source;
		};

		put = (source, target) -> {
			return source;
		};
	}
}
