package org.oscarehr.e2e.model.export.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.marc.everest.datatypes.ANY;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.model.export.template.observation.SeverityObservationModel;

public class SeverityObservationModelTest {
	@Test
	public void severityObservationTest() {
		String validString = "3";
		CD<String> value = commonSeverityObservationHelper(validString);
		assertEquals(Mappings.allergyTestValue.get(validString), value.getCode());
		assertEquals(Constants.CodeSystems.OBSERVATION_VALUE_OID, value.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATION_VALUE_NAME, value.getCodeSystemName());
		assertEquals(Mappings.allergyTestName.get(validString), value.getDisplayName());
	}

	@Test
	public void severityNullObservationTest() {
		CD<String> value = commonSeverityObservationHelper(null);
		assertTrue(value.isNull());
		assertEquals(NullFlavor.Unknown, value.getNullFlavor().getCode());
	}

	@SuppressWarnings("unchecked")
	private CD<String> commonSeverityObservationHelper(String severity) {
		EntryRelationship entryRelationship = new SeverityObservationModel().getEntryRelationship(severity);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.ObservationOids.SEVERITY_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, code.getCodeSystemName());
		assertEquals(Constants.ObservationType.SEV.toString(), code.getCode());

		ANY value = observation.getValue();
		assertNotNull(value);
		assertEquals(CD.class, value.getClass());
		return (CD<String>) value;
	}
}
