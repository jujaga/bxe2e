package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Encounter;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentEncounterMood;
import org.oscarehr.e2e.constant.BodyConstants.Encounters;

public class EncounterPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(Encounters.getConstants());
	}

	@Test
	public void encountersComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void encountersEntryCountTest() {
		entryCountTest(6);
	}

	@Test
	public void encountersEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void encountersClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVEncounter());

		Encounter encounter = (Encounter) clinicalStatement;
		assertEquals(x_DocumentEncounterMood.Eventoccurrence, encounter.getMoodCode().getCode());
		assertNotNull(encounter.getId());
		assertNotNull(encounter.getParticipant());
	}
}
