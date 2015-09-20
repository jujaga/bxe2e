package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.oscarehr.e2e.constant.BodyConstants.Immunizations;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.PatientExport.Immunization;
import org.oscarehr.e2e.model.export.body.ImmunizationsModel;

public class ImmunizationsPopulator extends AbstractBodyPopulator<Immunization> {
	private List<Immunization> immunizations = null;

	ImmunizationsPopulator(PatientExport patientExport) {
		bodyConstants = Immunizations.getConstants();
		if(patientExport.isLoaded()) {
			immunizations = patientExport.getImmunizations();
		}
	}

	@Override
	public void populate() {
		if(immunizations != null) {
			for(Immunization immunization : immunizations) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(immunization)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Immunization> list) {
		ImmunizationsModel immunizationsModel = new ImmunizationsModel(list.get(0));
		SubstanceAdministration substanceAdministration = new SubstanceAdministration(x_DocumentSubstanceMood.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		substanceAdministration.setNegationInd(immunizationsModel.getNegationInd());
		substanceAdministration.setId(immunizationsModel.getIds());
		substanceAdministration.setCode(immunizationsModel.getCode());
		substanceAdministration.setEffectiveTime(immunizationsModel.getEffectiveTime());
		substanceAdministration.setRouteCode(immunizationsModel.getRoute());
		substanceAdministration.setConsumable(immunizationsModel.getConsumable());
		substanceAdministration.setAuthor(immunizationsModel.getAuthor());
		substanceAdministration.setParticipant(immunizationsModel.getParticipant());

		entryRelationships.add(immunizationsModel.getAntigenType());
		entryRelationships.add(immunizationsModel.getRefusalReason());
		entryRelationships.add(immunizationsModel.getNextDate());
		entryRelationships.add(immunizationsModel.getComment());

		substanceAdministration.setEntryRelationship(entryRelationships);
		return substanceAdministration;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		ImmunizationsModel immunizationsModel = new ImmunizationsModel(null);
		SubstanceAdministration substanceAdministration = new SubstanceAdministration(x_DocumentSubstanceMood.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		substanceAdministration.setNegationInd(immunizationsModel.getNegationInd());
		substanceAdministration.setId(immunizationsModel.getIds());
		substanceAdministration.setCode(immunizationsModel.getCode());
		substanceAdministration.setEffectiveTime(immunizationsModel.getEffectiveTime());
		substanceAdministration.setRouteCode(immunizationsModel.getRoute());
		substanceAdministration.setConsumable(immunizationsModel.getConsumable());
		substanceAdministration.setAuthor(immunizationsModel.getAuthor());
		substanceAdministration.setParticipant(immunizationsModel.getParticipant());

		entryRelationships.add(immunizationsModel.getAntigenType());
		entryRelationships.add(immunizationsModel.getRefusalReason());
		entryRelationships.add(immunizationsModel.getNextDate());
		entryRelationships.add(immunizationsModel.getComment());

		substanceAdministration.setEntryRelationship(entryRelationships);
		return substanceAdministration;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(immunizations != null) {
			for(Immunization immunization : immunizations) {
				ImmunizationsModel immunizationsModel = new ImmunizationsModel(immunization);
				list.add(immunizationsModel.getTextSummary());
			}
		}

		return list;
	}
}
