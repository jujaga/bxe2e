package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Encounter;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Participant2;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentEncounterMood;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.e2e.constant.BodyConstants.Encounters;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.body.EncountersModel;

public class EncountersPopulator extends AbstractBodyPopulator<CaseManagementNote> {
	private List<CaseManagementNote> encounters = null;

	EncountersPopulator(PatientExport patientExport) {
		bodyConstants = Encounters.getConstants();
		if(patientExport.isLoaded()) {
			encounters = patientExport.getEncounters();
		}
	}

	@Override
	public void populate() {
		if(encounters != null) {
			for(CaseManagementNote encounter : encounters) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(encounter)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<CaseManagementNote> list) {
		EncountersModel encountersModel = new EncountersModel(list.get(0));
		Encounter encounter = new Encounter(x_DocumentEncounterMood.Eventoccurrence);
		ArrayList<Participant2> participants = new ArrayList<Participant2>();
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		encounter.setId(encountersModel.getIds());
		encounter.setEffectiveTime(encountersModel.getEffectiveTime());
		participants.add(encountersModel.getEncounterLocation());
		participants.add(encountersModel.getEncounterProvider());
		entryRelationships.add(encountersModel.getEncounterNote());

		encounter.setParticipant(participants);
		encounter.setEntryRelationship(entryRelationships);
		return encounter;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		EncountersModel encountersModel = new EncountersModel(null);
		Encounter encounter = new Encounter(x_DocumentEncounterMood.Eventoccurrence);
		ArrayList<Participant2> participants = new ArrayList<Participant2>();
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		encounter.setId(encountersModel.getIds());
		encounter.setEffectiveTime(encountersModel.getEffectiveTime());
		participants.add(encountersModel.getEncounterLocation());
		participants.add(encountersModel.getEncounterProvider());
		entryRelationships.add(encountersModel.getEncounterNote());

		encounter.setParticipant(participants);
		encounter.setEntryRelationship(entryRelationships);
		return encounter;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(encounters != null) {
			for(CaseManagementNote encounter : encounters) {
				EncountersModel encountersModel = new EncountersModel(encounter);
				list.add(encountersModel.getTextSummary());
			}
		}

		return list;
	}
}
