package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Act;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActClassDocumentEntryAct;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentActMood;
import org.oscarehr.e2e.constant.BodyConstants.Allergies;

public class AllergiesPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(Allergies.getConstants());
	}

	@Test
	public void allergiesComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void allergiesEntryCountTest() {
		entryCountTest(1);
	}

	@Test
	public void allergiesEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void allergiesClinicalStatementTest() {
		ClinicalStatement clinicalStatement = component.getSection().getEntry().get(0).getClinicalStatement();
		assertNotNull(clinicalStatement);
		assertTrue(clinicalStatement.isPOCD_MT000040UVAct());

		Act act = (Act) clinicalStatement;
		assertEquals(x_ActClassDocumentEntryAct.Act, act.getClassCode().getCode());
		assertEquals(x_DocumentActMood.Eventoccurrence, act.getMoodCode().getCode());
		assertNotNull(act.getId());
		assertNotNull(act.getCode());
		assertNotNull(act.getStatusCode());
		assertNotNull(act.getEffectiveTime());
		assertNotNull(act.getEntryRelationship());
	}
}
