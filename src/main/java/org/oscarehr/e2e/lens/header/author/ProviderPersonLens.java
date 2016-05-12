package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Person;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.AuthorPersonLens;

public class ProviderPersonLens extends AbstractLens<Pair<String, ArrayList<Author>>, Pair<String, ArrayList<Author>>> {
	public ProviderPersonLens() {
		get = source -> {
			Person person = source.getRight().get(0).getAssignedAuthor().getAssignedAuthorChoiceIfAssignedPerson();

			if(person == null) {
				person = new AuthorPersonLens().get(source.getLeft());
			}

			source.getRight().get(0).getAssignedAuthor().setAssignedAuthorChoice(person);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
