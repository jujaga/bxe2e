package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Section;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.BodyConstants.AbstractBodyConstants;
import org.oscarehr.e2e.director.E2ECreator;

public abstract class AbstractBodyPopulatorTest {
	private static ClinicalDocument clinicalDocument;
	private static ArrayList<Component3> components;

	protected static Component3 component;
	protected static AbstractBodyConstants bodyConstants;

	// This must be called in the BeforeClass or things will break
	protected static void setupClass(AbstractBodyConstants constants) {
		clinicalDocument = E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC);
		components = clinicalDocument.getComponent().getBodyChoiceIfStructuredBody().getComponent();
		bodyConstants = constants;

		for(Component3 value : components) {
			if(value.getSection().getTemplateId().contains(new II(bodyConstants.WITH_ENTRIES_TEMPLATE_ID)) ||
					value.getSection().getTemplateId().contains(new II(bodyConstants.WITHOUT_ENTRIES_TEMPLATE_ID))) {
				component = value;
				break;
			}
		}
	}

	protected void componentSectionTest() {
		assertEquals(ActRelationshipHasComponent.HasComponent, component.getTypeCode().getCode());
		assertTrue(component.getContextConductionInd().toBoolean());

		Section section = component.getSection();
		assertNotNull(section);
		assertTrue(section.getTemplateId().contains(new II(bodyConstants.WITH_ENTRIES_TEMPLATE_ID)) ||
				section.getTemplateId().contains(new II(bodyConstants.WITHOUT_ENTRIES_TEMPLATE_ID)));
		assertEquals(new CE<String>(bodyConstants.CODE, bodyConstants.CODE_SYSTEM, Constants.CodeSystems.LOINC_NAME, null), section.getCode());
		assertTrue(section.getTitle().getValue().equals(bodyConstants.WITH_ENTRIES_TITLE) ||
				section.getTitle().getValue().equals(bodyConstants.WITHOUT_ENTRIES_TITLE));
		assertNotNull(section.getText());
	}

	protected void entryCountTest(Integer count) {
		Section section = component.getSection();
		assertNotNull(section);
		assertEquals(count.intValue(), section.getEntry().size());
	}

	protected void entryStructureTest() {
		Section section = component.getSection();
		assertNotNull(section);
		assertEquals(x_BasicConfidentialityKind.Normal, section.getConfidentialityCode().getCode());

		ArrayList<Entry> entries = section.getEntry();
		assertNotNull(entries);

		Entry entry = entries.get(0);
		assertNotNull(entry);
		assertEquals(x_ActRelationshipEntry.DRIV, entry.getTypeCode().getCode());
		assertTrue(entry.getTemplateId().contains(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
		assertTrue(entry.getContextConductionInd().toBoolean());
	}
}
