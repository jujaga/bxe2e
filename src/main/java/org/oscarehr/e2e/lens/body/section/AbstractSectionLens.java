package org.oscarehr.e2e.lens.body.section;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.SD;
import org.marc.everest.datatypes.doc.StructDocElementNode;
import org.marc.everest.datatypes.doc.StructDocTextNode;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Section;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.BodyConstants.AbstractBodyConstants;
import org.oscarehr.e2e.constant.BodyConstants.SectionPriority;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.IModel;
import org.oscarehr.e2e.model.PatientModel;

abstract class AbstractSectionLens extends AbstractLens<Pair<IModel, ClinicalDocument>, Pair<IModel, ClinicalDocument>> {
	private AbstractBodyConstants bodyConstants;

	protected AbstractSectionLens(AbstractBodyConstants bodyConstants) {
		this.bodyConstants = Validate.notNull(bodyConstants);

		get = source -> {
			PatientModel patientModel = (PatientModel) source.getLeft();
			ArrayList<Component3> components = source.getRight().getComponent().getBodyChoiceIfStructuredBody().getComponent();

			// Check if Model has Entries
			Boolean hasEntries = containsEntries(patientModel);

			// Find Section Component if it exists
			Optional<Component3> oComponent = components.stream()
					.filter(e -> entryFilter.test(componentToII.apply(e), bodyConstants))
					.findFirst();

			// Setup Section Component Structure
			Component3 component = oComponent.orElse(makeSectionComponent(patientModel, hasEntries));
			if(hasEntries && component.getSection().getEntry() == null) {
				component.getSection().setEntry(new ArrayList<>());
			}

			// Commit Section Component Changes
			if(oComponent.isPresent()) {
				components.set(components.indexOf(component), component);
			} else if(bodyConstants.EMR_CONVERSION_SECTION_PRIORITY == SectionPriority.SHALL || hasEntries) {
				components.add(component);
			}

			source.getRight().getComponent().getBodyChoiceIfStructuredBody().setComponent(components);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			PatientModel patientModel = (PatientModel) target.getLeft();
			ArrayList<Component3> components = target.getRight().getComponent().getBodyChoiceIfStructuredBody().getComponent();

			// Find Section Component if it exists
			Optional<Component3> oComponent = components.stream()
					.filter(e -> filledEntryFilter.test(componentToII.apply(e), bodyConstants))
					.findFirst();

			// Setup Model Structure
			if(oComponent.isPresent()) {
				patientModel = createModelList(patientModel);
			}

			return new ImmutablePair<>(patientModel, target.getRight());
		};
	}

	abstract List<String> createSummaryText(PatientModel patientModel);
	abstract Boolean containsEntries(PatientModel patientModel);
	abstract PatientModel createModelList(PatientModel patientModel);

	// TODO Consolidate to Constants
	private Function<Component3, LIST<II>> componentToII = e -> {
		if(e.getSection() != null) {
			return e.getSection().getTemplateId();
		}
		return null;
	};

	// TODO Consolidate to Constants
	private BiPredicate<LIST<II>, AbstractBodyConstants> entryFilter = (e, bc) -> {
		if(e != null && !e.isNull() && !e.isEmpty()) {
			return e.stream().anyMatch(ii -> {
				return ii.getRoot().equals(bc.WITH_ENTRIES_TEMPLATE_ID) ||
						ii.getRoot().equals(bc.WITHOUT_ENTRIES_TEMPLATE_ID);
			});
		}
		return false;
	};

	// TODO Consolidate to Constants
	private BiPredicate<LIST<II>, AbstractBodyConstants> filledEntryFilter = (e, bc) -> {
		if(e != null && !e.isNull() && !e.isEmpty()) {
			return e.stream().anyMatch(ii -> {
				return ii.getRoot().equals(bc.WITH_ENTRIES_TEMPLATE_ID);
			});
		}
		return false;
	};

	private Component3 makeSectionComponent(PatientModel patientModel, Boolean hasEntries) {
		Component3 component = new Component3();
		component.setTypeCode(ActRelationshipHasComponent.HasComponent);
		component.setContextConductionInd(true);

		Section section = new Section();
		section.setCode(new CE<String>(bodyConstants.CODE, bodyConstants.CODE_SYSTEM, bodyConstants.CODE_SYSTEM_NAME, null));

		if(!hasEntries) {
			section.setTemplateId(Arrays.asList(new II(bodyConstants.WITHOUT_ENTRIES_TEMPLATE_ID)));
			section.setTitle(bodyConstants.WITHOUT_ENTRIES_TITLE);
		} else {
			section.setTemplateId(Arrays.asList(new II(bodyConstants.WITH_ENTRIES_TEMPLATE_ID)));
			section.setTitle(bodyConstants.WITH_ENTRIES_TITLE);
			section.setConfidentialityCode(x_BasicConfidentialityKind.Normal);
		}

		List<String> texts = createSummaryText(patientModel);
		if(texts == null || texts.isEmpty()) {
			section.setText(new SD(new StructDocTextNode(bodyConstants.ENTRY_NO_TEXT)));
		} else {
			StructDocElementNode list = new StructDocElementNode("list");
			texts.stream().forEach(e -> list.addElement("item", e));
			section.setText(new SD(list));
		}

		component.setSection(section);
		return component;
	}
}
