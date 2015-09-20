package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.oscarehr.e2e.constant.BodyConstants.Medications;

public class MedicationsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(Medications.getConstants());
	}

	@Test
	public void medicationsComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void medicationsEntryCountTest() {
		entryCountTest(2);
	}

	@Test
	public void medicationsEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void medicationsClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVSubstanceAdministration());

		SubstanceAdministration substanceAdministration = (SubstanceAdministration) clinicalStatement;
		assertEquals(x_DocumentSubstanceMood.Eventoccurrence, substanceAdministration.getMoodCode().getCode());
		assertNotNull(substanceAdministration.getId());
		assertNotNull(substanceAdministration.getCode());
		assertNotNull(substanceAdministration.getStatusCode());
		assertNotNull(substanceAdministration.getConsumable());
	}
}
