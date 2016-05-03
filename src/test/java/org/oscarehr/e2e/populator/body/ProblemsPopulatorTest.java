package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.oscarehr.e2e.constant.BodyConstants.Problems;

public class ProblemsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(Problems.getConstants());
	}

	@Test
	public void problemsComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void problemsEntryCountTest() {
		entryCountTest(2);
	}

	@Test
	public void problemsEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void problemsClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVObservation());

		Observation observation = (Observation) clinicalStatement;
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertNotNull(observation.getId());
		assertNotNull(observation.getCode());
		assertNotNull(observation.getStatusCode());
	}
}
