package org.oscarehr.e2e.model.export.body;

import java.util.ArrayList;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.ST;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Participant2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ParticipantRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PlayingEntity;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.marc.everest.rmim.uv.cdar2.vocabulary.EntityClassRoot;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ParticipationType;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.AuthorParticipationModel;
import org.oscarehr.e2e.model.export.template.ProviderParticipationModel;
import org.oscarehr.e2e.util.EverestUtils;

public class EncountersModel {
	private CaseManagementNote encounter;

	private SET<II> ids;
	private IVL<TS> effectiveTime;
	private Participant2 encounterLocation;
	private Participant2 encounterProvider;
	private EntryRelationship encounterNote;

	public EncountersModel(CaseManagementNote encounter) {
		if(encounter == null) {
			this.encounter = new CaseManagementNote();
		} else {
			this.encounter = encounter;
		}

		setIds();
		setEffectiveTime();
		setEncounterLocation();
		setEncounterProvider();
		setEncounterNote();
		// Reason Observation not included because icd9 code doesn't fit in Reason Observation template
	}

	public String getTextSummary() {
		StringBuilder sb = new StringBuilder();

		if(encounter.getObservation_date() != null) {
			sb.append(encounter.getObservation_date());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(encounter.getNote())) {
			sb.append(" ".concat(encounter.getNote().replaceAll("\\\\n", "\n")));
		}

		return sb.toString();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		this.ids = EverestUtils.buildUniqueId(Constants.IdPrefixes.Encounters, encounter.getId());
	}

	public IVL<TS> getEffectiveTime() {
		return effectiveTime;
	}

	private void setEffectiveTime() {
		IVL<TS> ivl = null;
		TS startTime = EverestUtils.buildTSFromDate(encounter.getObservation_date());
		if(startTime != null) {
			ivl = new IVL<TS>(startTime, null);
		}

		this.effectiveTime = ivl;
	}

	public Participant2 getEncounterLocation() {
		return encounterLocation;
	}

	private void setEncounterLocation() {
		Participant2 participant = new Participant2(ParticipationType.LOC, ContextControl.OverridingPropagating);
		ParticipantRole participantRole = new ParticipantRole(new CD<String>(Constants.RoleClass.SDLOC.toString()));
		PlayingEntity playingEntity = new PlayingEntity(EntityClassRoot.Organization);

		participantRole.setPlayingEntityChoice(playingEntity);
		participant.setParticipantRole(participantRole);

		this.encounterLocation = participant;
	}

	public Participant2 getEncounterProvider() {
		return encounterProvider;
	}

	private void setEncounterProvider() {
		this.encounterProvider = new ProviderParticipationModel(encounter.getProviderNo()).getProvider();
	}

	public EntryRelationship getEncounterNote() {
		return encounterNote;
	}

	private void setEncounterNote() {
		EntryRelationship entryRelationship = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(encounter.getNote())) {
			entryRelationship = new EntryRelationship(x_ActRelationshipEntryRelationship.SUBJ, new BL(true));

			CD<String> code = new CD<String>(Constants.ObservationType.COMMENT.toString());
			code.setCodeSystem(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID);
			code.setCodeSystemName(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME);

			ST value = new ST(encounter.getNote().replaceAll("\\\\n", "\n"));

			ArrayList<Author> authors = new ArrayList<Author>();
			authors.add(new AuthorParticipationModel(encounter.getSigning_provider_no()).getAuthor(encounter.getUpdate_date()));

			Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.setId(getIds());
			observation.setCode(code);
			observation.setEffectiveTime(getEffectiveTime());
			observation.setValue(value);
			observation.setAuthor(authors);

			entryRelationship.setClinicalStatement(observation);
		}

		this.encounterNote = entryRelationship;
	}
}
