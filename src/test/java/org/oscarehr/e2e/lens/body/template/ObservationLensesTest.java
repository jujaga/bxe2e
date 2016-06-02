package org.oscarehr.e2e.lens.body.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.body.template.observation.DateObservationLens;
import org.oscarehr.e2e.lens.body.template.observation.SecondaryCodeICD9ObservationLens;
import org.oscarehr.e2e.util.EverestUtils;

public class ObservationLensesTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test
	public void dateObservationLensGetTest() {
		DateObservationLens lens = new DateObservationLens();
		assertNotNull(lens);
		assertNull(lens.get(null));

		EntryRelationship entryRelationship = lens.get(new Date());
		assertNotNull(entryRelationship);
		assertTrue(entryRelationship.getContextConductionInd().getValue());
		assertEquals(Constants.ObservationOids.DATE_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertNotNull(observation.getCode());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, observation.getCode().getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, observation.getCode().getCodeSystemName());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertEquals(Constants.ObservationType.DATEOBS.toString(), observation.getCode().getCode());
		assertNotNull(observation.getEffectiveTime());
		assertFalse(observation.getEffectiveTime().isNull());
		assertFalse(observation.getEffectiveTime().getLow().isNull());
		assertFalse(observation.getEffectiveTime().getLow().isInvalidDate());
	}

	@Test
	public void dateObservationLensPutTest() {
		DateObservationLens lens = new DateObservationLens();
		assertNotNull(lens);

		assertNull(lens.put(null));
		assertNull(lens.put(lens.get(null)));
		assertNotNull(lens.put(lens.get(new Date())));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void secondaryCodeICD9ObservationLensGetTest() {
		final String test = "491";

		SecondaryCodeICD9ObservationLens lens = new SecondaryCodeICD9ObservationLens();
		assertNotNull(lens);

		EntryRelationship nullEntryRelationship = lens.get(null);
		assertNotNull(nullEntryRelationship);
		assertTrue(nullEntryRelationship.getContextConductionInd().getValue());
		assertEquals(Constants.ObservationOids.SECONDARY_CODE_ICD9_OBSERVATION_TEMPLATE_ID, nullEntryRelationship.getTemplateId().get(0).getRoot());
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, nullEntryRelationship.getTypeCode().getCode());

		Observation nullObservation = nullEntryRelationship.getClinicalStatementIfObservation();
		assertNotNull(nullObservation);
		assertNotNull(nullObservation.getCode());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, nullObservation.getCode().getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, nullObservation.getCode().getCodeSystemName());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, nullObservation.getMoodCode().getCode());
		assertEquals(Constants.ObservationType.ICD9CODE.toString(), nullObservation.getCode().getCode());
		assertNotNull(nullObservation.getValue());
		assertTrue(nullObservation.getValue().isNull());
		assertEquals(NullFlavor.Unknown, nullObservation.getValue().getNullFlavor().getCode());

		EntryRelationship entryRelationship = lens.get(test);
		assertNotNull(entryRelationship);
		assertTrue(entryRelationship.getContextConductionInd().getValue());
		assertEquals(Constants.ObservationOids.SECONDARY_CODE_ICD9_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertNotNull(observation.getCode());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, observation.getCode().getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, observation.getCode().getCodeSystemName());
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertEquals(Constants.ObservationType.ICD9CODE.toString(), observation.getCode().getCode());
		assertNotNull(observation.getValue());
		assertEquals(CD.class, observation.getValue().getClass());

		CD<String> icd9Value = (CD<String>) observation.getValue();
		assertEquals(test, icd9Value.getCode());
		assertEquals(Constants.CodeSystems.ICD9_OID, icd9Value.getCodeSystem());
		assertEquals(Constants.CodeSystems.ICD9_NAME, icd9Value.getCodeSystemName());
		assertEquals(EverestUtils.getICD9Description(test), icd9Value.getDisplayName());
	}

	@Test
	public void secondaryCodeICD9ObservationLensPutTest() {
		final String test = "491";

		SecondaryCodeICD9ObservationLens lens = new SecondaryCodeICD9ObservationLens();
		assertNotNull(lens);

		assertNull(lens.put(null));
		assertNull(lens.put(lens.get(null)));
		assertEquals(test, lens.put(lens.get(test)));
	}
}
