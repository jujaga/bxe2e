package org.oscarehr.e2e.model.export.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.observation.SecondaryCodeICD9ObservationModel;

public class SecondaryCodeICD9ObservationModelTest {
	@Test
	public void validSecondaryCodeICD9ObservationTest() {
		String validString = "250";
		CD<String> value = commonSecondaryCodeICD9ObservationHelper(validString);
		assertNotNull(value.getDisplayName());
		assertEquals(Constants.CodeSystems.ICD9_OID, value.getCodeSystem());
		assertEquals(Constants.CodeSystems.ICD9_NAME, value.getCodeSystemName());
		assertEquals(validString, value.getCode());
	}

	@Test
	public void nullFlavorSecondaryCodeICD9ObservationTest() {
		CD<String> value = commonSecondaryCodeICD9ObservationHelper(null);
		assertTrue(value.isNull());
		assertEquals(NullFlavor.Unknown, value.getNullFlavor().getCode());
	}

	@SuppressWarnings("unchecked")
	private CD<String> commonSecondaryCodeICD9ObservationHelper(String icd9Value) {
		EntryRelationship entryRelationship = new SecondaryCodeICD9ObservationModel().getEntryRelationship(icd9Value);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.ObservationOids.SECONDARY_CODE_ICD9_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, code.getCodeSystemName());
		assertEquals(Constants.ObservationType.ICD9CODE.toString(), code.getCode());

		assertEquals(CD.class, observation.getValue().getDataType());
		CD<String> value = (CD<String>) observation.getValue();
		assertNotNull(value);
		return value;
	}
}
