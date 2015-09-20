package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActClassObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.oscarehr.e2e.constant.BodyConstants.FamilyHistory;

public class FamilyHistoryPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		setupClass(FamilyHistory.getConstants());
	}

	@Test
	public void familyHistoryComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void familyHistoryEntryCountTest() {
		entryCountTest(1);
	}

	@Test
	public void familyHistoryEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void familyHistoryClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVObservation());

		Observation observation = (Observation) clinicalStatement;
		assertEquals(ActClassObservation.OBS, observation.getClassCode().getCode());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertNotNull(observation.getId());
		assertNotNull(observation.getCode());
		assertNotNull(observation.getEffectiveTime());
		assertNotNull(observation.getValue());
		assertNotNull(observation.getSubject());
		assertNotNull(observation.getEntryRelationship());
	}
}
