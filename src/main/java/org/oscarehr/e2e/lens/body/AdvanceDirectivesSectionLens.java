package org.oscarehr.e2e.lens.body;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.oscarehr.e2e.constant.BodyConstants.AdvanceDirectives;

public class AdvanceDirectivesSectionLens extends AbstractSectionLens {
	public AdvanceDirectivesSectionLens() {
		bodyConstants = AdvanceDirectives.getConstants();

		get = source -> {
			Component3 component = makeSectionComponent();
			component.getSection().setEntry(entries);

			source.getRight().getComponent().getBodyChoiceIfStructuredBody().getComponent().add(component);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}

	@Override
	List<String> populateText() {
		return null;
	}
}
