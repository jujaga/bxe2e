package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.oscarehr.e2e.constant.BodyConstants.Immunizations;

public class ImmunizationsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(Immunizations.getConstants());
	}

	@Test
	public void immunizationsComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void immunizationsEntryCountTest() {
		entryCountTest(3);
	}

	@Test
	public void immunizationsEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void immunizationsClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVSubstanceAdministration());

		SubstanceAdministration substanceAdministration = (SubstanceAdministration) clinicalStatement;
		assertEquals(x_DocumentSubstanceMood.Eventoccurrence, substanceAdministration.getMoodCode().getCode());
		assertNotNull(substanceAdministration.getId());
		assertNotNull(substanceAdministration.getEffectiveTime());
		assertNotNull(substanceAdministration.getConsumable());
		assertNotNull(substanceAdministration.getAuthor());
		assertNotNull(substanceAdministration.getParticipant());
	}
}
