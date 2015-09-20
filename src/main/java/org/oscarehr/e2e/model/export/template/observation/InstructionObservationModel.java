package org.oscarehr.e2e.model.export.template.observation;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Participant2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ParticipantRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PlayingEntity;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.marc.everest.rmim.uv.cdar2.vocabulary.EntityClassRoot;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ParticipationType;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.util.EverestUtils;

public class InstructionObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String value) {
		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
			entryRelationship.setContextConductionInd(true);
			entryRelationship.setTemplateId(Arrays.asList(new II(Constants.ObservationOids.INSTRUCTION_OBSERVATION_TEMPLATE_ID)));

			observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.getCode().setCodeEx(Constants.ObservationType.INSTRUCT.toString());
			observation.setParticipant(getParticipant());
			observation.setText(new ED(value));
		} else {
			entryRelationship = null;
		}

		return entryRelationship;
	}

	private ArrayList<Participant2> getParticipant() {
		Participant2 participant = new Participant2();
		ParticipantRole role = new ParticipantRole(new CS<String>(Constants.RoleClass.ROL.toString()));

		participant.setTypeCode(new CS<ParticipationType>(ParticipationType.PrimaryInformationRecipient));
		participant.setContextControlCode(new CS<ContextControl>(ContextControl.OverridingPropagating));

		role.setCode(Constants.RoleClass.PAT.toString(), Constants.CodeSystems.ROLE_CLASS_OID);
		role.getCode().setCodeSystemName(Constants.CodeSystems.ROLE_CLASS_NAME);
		role.setPlayingEntityChoice(new PlayingEntity(EntityClassRoot.Person));

		participant.setParticipantRole(role);

		return new ArrayList<Participant2>(Arrays.asList(participant));
	}
}
