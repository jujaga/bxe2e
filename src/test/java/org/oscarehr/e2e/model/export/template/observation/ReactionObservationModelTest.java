package org.oscarehr.e2e.model.export.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.marc.everest.datatypes.ANY;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.observation.ReactionObservationModel;
import org.oscarehr.e2e.util.EverestUtils;

public class ReactionObservationModelTest {
	private static final String test = "test";
	private static final Date time = new Date();

	@Test
	public void reasonObservationMinimalTest() {
		Observation observation = reactionObservationStructureTestHelper(test, null, null, null);

		IVL<TS> ivl = observation.getEffectiveTime();
		assertNull(ivl);

		ArrayList<Author> authors = observation.getAuthor();
		assertNotNull(authors);
		assertTrue(authors.isEmpty());

		ArrayList<EntryRelationship> entryRelationships = observation.getEntryRelationship();
		assertNotNull(entryRelationships);
		assertNotNull(entryRelationships.isEmpty());
	}

	@Test
	public void reasonObservationAllTest() {
		Observation observation = reactionObservationStructureTestHelper(test, time, Constants.Runtime.VALID_PROVIDER.toString(), test);

		IVL<TS> ivl = observation.getEffectiveTime();
		assertNotNull(ivl);
		assertEquals(EverestUtils.buildTSFromDate(time), ivl.getLow());

		ArrayList<Author> authors = observation.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());

		ArrayList<EntryRelationship> entryRelationships = observation.getEntryRelationship();
		assertNotNull(entryRelationships);
		assertEquals(1, entryRelationships.size());
	}

	@Test
	public void reasonObservationNullTest() {
		EntryRelationship entryRelationship = new ReactionObservationModel().getEntryRelationship(null, null, null, null, null);
		assertNull(entryRelationship);
	}

	private Observation reactionObservationStructureTestHelper(String reaction, Date time, String author, String severity) {
		assumeNotNull(reaction);

		EntryRelationship entryRelationship = new ReactionObservationModel().getEntryRelationship(reaction, time, author, time, severity);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.SUBJ, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.ObservationOids.REACTION_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		Observation observation = entryRelationship.getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());

		CD<String> code = observation.getCode();
		assertNotNull(code);
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME, code.getCodeSystemName());
		assertEquals(Constants.ObservationType.REACTOBS.toString(), code.getCode());

		ED text = observation.getText();
		assertNotNull(text);
		assertEquals(reaction, new String(text.getData()));

		ANY value = observation.getValue();
		assertNotNull(value);
		assertEquals(CD.class, value.getClass());
		assertTrue(value.isNull());
		assertEquals(NullFlavor.NoInformation, value.getNullFlavor().getCode());

		return observation;
	}
}
