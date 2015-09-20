package org.oscarehr.e2e.model.export.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.model.export.template.observation.LifestageObservationModel;

public class LifestageObservationModelTest {
	@Test
	public void lifestageObservationTest() {
		String lifeStage = "N";

		EntryRelationship entryRelationship = new LifestageObservationModel().getEntryRelationship(lifeStage);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.ObservationOids.LIFESTAGE_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, code.getCodeSystemName());
		assertEquals(Constants.ObservationType.LIFEOBS.toString(), code.getCode());

		assertNotNull(observation.getValue());
		assertEquals(CD.class, observation.getValue().getClass());

		@SuppressWarnings("unchecked")
		CD<String> value = (CD<String>) observation.getValue();
		assertNotNull(value);
		assertEquals(Constants.CodeSystems.SNOMED_CT_OID, value.getCodeSystem());
		assertEquals(Constants.CodeSystems.SNOMED_CT_NAME, value.getCodeSystemName());
		assertEquals(Mappings.lifeStageCode.get(lifeStage), value.getCode());
		assertEquals(Mappings.lifeStageName.get(lifeStage), value.getDisplayName());
	}

	@Test
	public void nullFlavorLifestageObservationTest() {
		EntryRelationship entryRelationship = new LifestageObservationModel().getEntryRelationship(null);
		assertNull(entryRelationship);
	}
}
