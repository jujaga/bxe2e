package org.oscarehr.e2e.populator.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Organizer;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActMoodEventOccurrence;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActClassDocumentEntryOrganizer;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.BodyConstants.ClinicallyMeasuredObservations;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.populator.body.ClinicallyMeasuredObservationsPopulator;

public class ClinicallyMeasuredObservationsPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		setupClass(ClinicallyMeasuredObservations.getConstants());
	}

	@Test
	public void clinicallyMeasuredObservationsComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void clinicallyMeasuredObservationsEntryCountTest() {
		entryCountTest(6);
	}

	@Test
	public void clinicallyMeasuredObservationsEntryStructureTest() {
		entryStructureTest();
	}

	@Test
	public void clinicallyMeasuredObservationsClinicalStatementTest() {
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
		assertTrue(organizer.getComponent().size() > 0);
	}

	@Test
	public void clinicallyMeasuredObservationsNullFlavorTest() {
		ClinicallyMeasuredObservationsPopulator cmoPopulator = new ClinicallyMeasuredObservationsPopulator(new PatientExport(Constants.Runtime.INVALID_VALUE));
		assertNull(cmoPopulator.populateNullFlavorClinicalStatement());
	}
}
