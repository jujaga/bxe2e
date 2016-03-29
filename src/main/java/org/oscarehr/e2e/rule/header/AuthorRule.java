package org.oscarehr.e2e.rule.header;

import java.util.ArrayList;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.e2e.lens.header.author.AuthorLens;
import org.oscarehr.e2e.lens.header.author.ProviderIdLens;
import org.oscarehr.e2e.lens.header.author.ProviderLens;
import org.oscarehr.e2e.lens.header.author.ProviderPersonLens;
import org.oscarehr.e2e.lens.header.author.ProviderTelecomLens;
import org.oscarehr.e2e.lens.header.author.SystemLens;
import org.oscarehr.e2e.rule.AbstractRule;

public class AuthorRule extends AbstractRule<String, ArrayList<Author>> {
	public AuthorRule(String source, ArrayList<Author> target) {
		super(source, target);
	}

	@Override
	protected void defineLens() {
		lens = new AuthorLens()
				.compose(new ProviderLens())
				.compose(new ProviderIdLens())
				.compose(new ProviderTelecomLens())
				.compose(new ProviderPersonLens())
				.compose(new SystemLens());
	}
}
