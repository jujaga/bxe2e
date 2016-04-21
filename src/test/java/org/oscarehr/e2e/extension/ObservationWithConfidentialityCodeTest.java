package org.oscarehr.e2e.extension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;

public class ObservationWithConfidentialityCodeTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test
	public void ObservationBaseTest() {
		Observation observation = new ObservationWithConfidentialityCode(x_ActMoodDocumentObservation.Eventoccurrence);
		assertNotNull(observation);
	}

	@Test
	public void ObservationConfidentialityCodeTest() {
		ObservationWithConfidentialityCode observation = new ObservationWithConfidentialityCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.setConfidentialityCode(new CE<>(x_BasicConfidentialityKind.Normal));
		assertNotNull(observation.getConfidentialityCode());
		assertEquals(x_BasicConfidentialityKind.Normal, observation.getConfidentialityCode().getCode());
	}
}
