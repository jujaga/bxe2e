package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class AuthorLens extends AbstractLens<String, ArrayList<Author>> {
	public AuthorLens() {
		get = providerNo -> {
			ArrayList<Author> authors = new ArrayList<>();
			authors.add(new ProviderLens().get(providerNo));
			authors.add(new SystemLens().get(null));

			return authors;
		};

		// TODO Put Function
	}
}
