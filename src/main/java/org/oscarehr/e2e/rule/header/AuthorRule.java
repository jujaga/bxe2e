package org.oscarehr.e2e.rule.header;

import java.util.ArrayList;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.e2e.lens.header.author.ProviderLens;
import org.oscarehr.e2e.lens.header.author.SystemLens;
import org.oscarehr.e2e.rule.AbstractRule;

public class AuthorRule extends AbstractRule<String, ArrayList<Author>> {
	public AuthorRule(String source, ArrayList<Author> target) {
		super(source, target);
	}

	@Override
	protected void defineLens() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void apply() {
		if(pair.right == null) {
			// Do forward transformation
			ArrayList<Author> authors = new ArrayList<>();
			authors.add(new ProviderLens().get(pair.left));
			authors.add(new SystemLens().get(null));

			pair.right = authors;
		}

		if(pair.left == null) {
			// Do backward transformation
		}
	}
}
