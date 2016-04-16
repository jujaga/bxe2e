package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class ProviderLens extends AbstractLens<Pair<String, ArrayList<Author>>, Pair<String, ArrayList<Author>>> {
	public ProviderLens() {
		get = source -> {
			Author author = new Author();
			AssignedAuthor assignedAuthor = new AssignedAuthor();

			author.setContextControlCode(ContextControl.OverridingPropagating);
			author.setTime(new GregorianCalendar(), TS.DAY);
			author.setAssignedAuthor(assignedAuthor);

			source.getRight().add(author);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
