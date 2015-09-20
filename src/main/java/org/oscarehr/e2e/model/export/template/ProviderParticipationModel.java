package org.oscarehr.e2e.model.export.template;

import java.util.Arrays;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Participant2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ParticipantRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PlayingEntity;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.marc.everest.rmim.uv.cdar2.vocabulary.EntityClassRoot;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ParticipationType;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.header.AuthorModel;

public class ProviderParticipationModel extends AuthorModel {
	public ProviderParticipationModel(String providerNo) {
		super(providerNo);
	}

	public Participant2 getProvider() {
		Participant2 participant = new Participant2(ParticipationType.PrimaryPerformer, ContextControl.OverridingPropagating);
		ParticipantRole participantRole = new ParticipantRole(new CD<String>(Constants.RoleClass.PROV.toString()));
		PlayingEntity playingEntity = new PlayingEntity(EntityClassRoot.Person);

		playingEntity.setName(person.getName());
		playingEntity.setDesc(new ED("Provider"));

		participantRole.setPlayingEntityChoice(playingEntity);
		participantRole.setId(ids);

		participant.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.PROVIDER_PARTICIPATION_TEMPLATE_ID)));
		participant.setParticipantRole(participantRole);

		return participant;
	}
}
