package org.oscarehr.e2e.rule.header;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.header.author.AuthorLens;
import org.oscarehr.e2e.lens.header.author.ProviderIdLens;
import org.oscarehr.e2e.lens.header.author.ProviderLens;
import org.oscarehr.e2e.lens.header.author.ProviderPersonLens;
import org.oscarehr.e2e.lens.header.author.ProviderTelecomLens;
import org.oscarehr.e2e.lens.header.author.SystemLens;
import org.oscarehr.e2e.rule.common.AbstractRule;

public class AuthorRule extends AbstractRule<String, ArrayList<Author>> {
	public AuthorRule(String source, ArrayList<Author> target) {
		super(source, target);
		// Source is allowed to be null
		if(this.pair.getRight() == null) {
			pair = new ImmutablePair<>(pair.getLeft(), new ArrayList<>());
		}
	}

	@Override
	protected AbstractLens<Pair<String, ArrayList<Author>>, Pair<String, ArrayList<Author>>> defineLens() {
		return new AuthorLens()
				.compose(new ProviderLens())
				.compose(new ProviderIdLens())
				.compose(new ProviderTelecomLens())
				.compose(new ProviderPersonLens())
				.compose(new SystemLens());
	}
}
