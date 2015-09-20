package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Organizer;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActMoodEventOccurrence;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActClassDocumentEntryOrganizer;
import org.oscarehr.e2e.constant.BodyConstants.RiskFactors;

public class RiskFactorsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(RiskFactors.getConstants());
	}

	@Test
	public void riskFactorsComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void riskFactorsEntryCountTest() {
		entryCountTest(1);
	}

	@Test
	public void riskFactorsEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void riskFactorsClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVOrganizer());

		Organizer organizer = (Organizer) clinicalStatement;
		assertEquals(x_ActClassDocumentEntryOrganizer.CLUSTER, organizer.getClassCode().getCode());
		assertEquals(ActMoodEventOccurrence.Eventoccurrence, organizer.getMoodCode().getCode());
		assertNotNull(organizer.getId());
		assertNotNull(organizer.getCode());
		assertNotNull(organizer.getStatusCode());
		assertNotNull(organizer.getComponent());
	}
}
