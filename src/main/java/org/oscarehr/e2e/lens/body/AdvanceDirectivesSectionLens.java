package org.oscarehr.e2e.lens.body;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.oscarehr.e2e.constant.BodyConstants.AdvanceDirectives;
import org.oscarehr.e2e.model.IModel;

public class AdvanceDirectivesSectionLens extends AbstractSectionLens {
	public AdvanceDirectivesSectionLens() {
		bodyConstants = AdvanceDirectives.getConstants();

		// TODO Figure out what tasks can be packaged into AbstractSectionLens
		get = source -> {
			ArrayList<Component3> components = source.getRight().getComponent().getBodyChoiceIfStructuredBody().getComponent();

			// Find this section if it exists already
			Optional<Component3> oComponent = components.stream()
					.filter(sectionPredicate)
					.findFirst();

			// Handle component creation or modification
			Component3 component = oComponent.orElse(makeSectionComponent());

			// Determine if this section has entries
			hasEntries = findEntries(source);
			if(hasEntries && component.getSection().getEntry() == null) {
				component.getSection().setEntry(new ArrayList<>());
			}

			// TODO Generate summary text

			// Commit section modifications
			if(oComponent.isPresent()) {
				components.set(components.indexOf(component), component);
			} else {
				components.add(component);
			}

			source.getRight().getComponent().getBodyChoiceIfStructuredBody().setComponent(components);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}

	@Override
	List<String> populateText(Pair<IModel, ClinicalDocument> pair) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Boolean findEntries(Pair<IModel, ClinicalDocument> pair) {
		// TODO Auto-generated method stub
		return false;
	}
}
