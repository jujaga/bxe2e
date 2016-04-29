package org.oscarehr.e2e.lens.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.SD;
import org.marc.everest.datatypes.doc.StructDocElementNode;
import org.marc.everest.datatypes.doc.StructDocTextNode;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Section;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.BodyConstants.AbstractBodyConstants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.IModel;

abstract class AbstractSectionLens extends AbstractLens<Pair<IModel, ClinicalDocument>, Pair<IModel, ClinicalDocument>> {
	protected AbstractBodyConstants bodyConstants;
	protected ArrayList<Entry> entries = null;

	abstract List<String> populateText();
	
	protected Component3 makeSectionComponent() {
		List<String> texts = populateText();
		Component3 component = new Component3();
		component.setTypeCode(ActRelationshipHasComponent.HasComponent);
		component.setContextConductionInd(true);

		Section section = new Section();
		section.setCode(new CE<String>(bodyConstants.CODE, bodyConstants.CODE_SYSTEM, bodyConstants.CODE_SYSTEM_NAME, null));

		if(entries == null || entries.isEmpty()) {
			section.setTemplateId(Arrays.asList(new II(bodyConstants.WITHOUT_ENTRIES_TEMPLATE_ID)));
			section.setTitle(bodyConstants.WITHOUT_ENTRIES_TITLE);
		} else {
			section.setTemplateId(Arrays.asList(new II(bodyConstants.WITH_ENTRIES_TEMPLATE_ID)));
			section.setTitle(bodyConstants.WITH_ENTRIES_TITLE);
			section.setConfidentialityCode(x_BasicConfidentialityKind.Normal);
		}

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
