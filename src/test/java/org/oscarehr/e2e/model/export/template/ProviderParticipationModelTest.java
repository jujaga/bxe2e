package org.oscarehr.e2e.model.export.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Participant2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ParticipantRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PlayingEntity;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ParticipationType;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.ProviderParticipationModel;

public class ProviderParticipationModelTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test
	public void providerParticipationStructureTest() {
		Participant2 provider = new ProviderParticipationModel(Constants.Runtime.VALID_PROVIDER.toString()).getProvider();
		assertNotNull(provider);
		assertEquals(ParticipationType.PrimaryPerformer, provider.getTypeCode().getCode());
		assertEquals(ContextControl.OverridingPropagating, provider.getContextControlCode().getCode());
		assertTrue(provider.getTemplateId().contains(new II(Constants.TemplateOids.PROVIDER_PARTICIPATION_TEMPLATE_ID)));

		ParticipantRole participantRole = provider.getParticipantRole();
		assertNotNull(participantRole);
		assertNotNull(participantRole.getId());

		PlayingEntity playingEntity = participantRole.getPlayingEntityChoiceIfPlayingEntity();
		assertNotNull(playingEntity);
		assertNotNull(playingEntity.getDesc());

		SET<PN> names = playingEntity.getName();
		assertNotNull(names);
		assertFalse(names.isNull());
		assertFalse(names.isEmpty());
		assertNotNull(names.get(0));
	}

	@Test
	public void providerParticipationNullTest() {
		Participant2 provider = new ProviderParticipationModel(null).getProvider();
		assertNotNull(provider);
		assertEquals(ParticipationType.PrimaryPerformer, provider.getTypeCode().getCode());
		assertEquals(ContextControl.OverridingPropagating, provider.getContextControlCode().getCode());
		assertTrue(provider.getTemplateId().contains(new II(Constants.TemplateOids.PROVIDER_PARTICIPATION_TEMPLATE_ID)));

		ParticipantRole participantRole = provider.getParticipantRole();
		assertNotNull(participantRole);
		assertNotNull(participantRole.getId());

		PlayingEntity playingEntity = participantRole.getPlayingEntityChoiceIfPlayingEntity();
		assertNotNull(playingEntity);
		assertNotNull(playingEntity.getDesc());

		SET<PN> names = playingEntity.getName();
		assertNotNull(names);
		assertFalse(names.isNull());
		assertFalse(names.isEmpty());
		assertNotNull(names.get(0));
		assertTrue(names.get(0).isNull());
		assertEquals(NullFlavor.NoInformation, names.get(0).getNullFlavor().getCode());
	}
}
