package org.oscarehr.e2e.model.export.template.observation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PQ;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.PIVL;
import org.marc.everest.datatypes.interfaces.ISetComponent;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.oscarehr.common.model.Drug;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.model.export.template.ConsumableModel;
import org.oscarehr.e2e.util.EverestUtils;

public class DoseObservationModel {
	private static Logger log = Logger.getLogger(DoseObservationModel.class.getName());

	private Drug drug;

	public EntryRelationship getEntryRelationship(Drug drug) {
		if(drug == null) {
			this.drug = new Drug();
		} else {
			this.drug = drug;
		}

		EntryRelationship entryRelationship = new EntryRelationship();
		ArrayList<ISetComponent<TS>> doseTimes = new ArrayList<ISetComponent<TS>>();
		SubstanceAdministration substanceAdministration = new SubstanceAdministration(x_DocumentSubstanceMood.RQO, getConsumable());

		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);
		entryRelationship.setContextConductionInd(true);
		entryRelationship.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.DOSE_OBSERVATION_TEMPLATE_ID)));

		doseTimes.add(getDuration());
		doseTimes.add(getFrequency());

		substanceAdministration.setText(getDoseInstructions());
		substanceAdministration.setEffectiveTime(doseTimes);
		substanceAdministration.setRouteCode(getRoute());
		substanceAdministration.setDoseQuantity(getDoseQuantity());
		substanceAdministration.setAdministrationUnitCode(getForm());

		entryRelationship.setClinicalStatement(substanceAdministration);

		return entryRelationship;
	}

	private Consumable getConsumable() {
		return new ConsumableModel().getConsumable(drug);
	}

	private ED getDoseInstructions() {
		StringBuilder sb = new StringBuilder("Take");

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getDosage())) {
			sb.append(" " + drug.getDosage());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getFreqCode())) {
			sb.append(" " + drug.getFreqCode());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getDuration())) {
			sb.append(" " + drug.getDuration());
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getDurUnit())) {
			sb.append(" " + drug.getDurUnit());
		}

		return new ED(sb.toString());
	}

	private ISetComponent<TS> getDuration() {
		IVL<TS> ivl = new IVL<TS>();

		try {
			if(!EverestUtils.isNullorEmptyorWhitespace(drug.getDuration()) && drug.getDuration() != "0" &&
					!EverestUtils.isNullorEmptyorWhitespace(drug.getDurUnit())) {
				BigDecimal duration = new BigDecimal(drug.getDuration());
				ivl.setWidth(new PQ(duration, drug.getDurUnit()));
			} else {
				ivl.setNullFlavor(NullFlavor.Unknown);
			}
		} catch (NumberFormatException e) {
			log.warn("Duration " + drug.getDuration() + " not a number");
			ivl.setNullFlavor(NullFlavor.Unknown);
		}

		return ivl;
	}

	private ISetComponent<TS> getFrequency() {
		ISetComponent<TS> iSetComponent;

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getFreqCode())) {
			if(Mappings.frequencyInterval.containsKey(drug.getFreqCode())) {
				iSetComponent = Mappings.frequencyInterval.get(drug.getFreqCode());
			} else {
				iSetComponent = new PIVL<TS>();
				iSetComponent.setNullFlavor(new CS<NullFlavor>(NullFlavor.Other));
			}
		} else {
			iSetComponent = new PIVL<TS>();
			iSetComponent.setNullFlavor(new CS<NullFlavor>(NullFlavor.Unknown));
		}

		return iSetComponent;
	}

	private CE<String> getRoute() {
		CE<String> code = null;

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getRoute())) {
			code = new CE<String>(drug.getRoute().toUpperCase(), Constants.CodeSystems.ROUTE_OF_ADMINISTRATION_OID);
			code.setCodeSystemName(Constants.CodeSystems.ROUTE_OF_ADMINISTRATION_NAME);
		}

		return code;
	}

	private IVL<PQ> getDoseQuantity() {
		IVL<PQ> dose = new IVL<PQ>();
		PQ low = null;
		PQ high = null;
		String unit = null;

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getUnitName())) {
			unit = drug.getUnitName().replaceAll("\\s", "_");
		}
		if(drug.getTakeMin() != null) {
			low = new PQ(new BigDecimal(drug.getTakeMin().toString()), unit);
		}
		if(drug.getTakeMax() != null) {
			high = new PQ(new BigDecimal(drug.getTakeMax().toString()), unit);
		}

		if(low != null || high != null) {
			dose.setLow(low);
			dose.setHigh(high);
		} else {
			dose.setNullFlavor(NullFlavor.NoInformation);
		}

		return dose;
	}

	private CE<String> getForm() {
		CE<String> code = null;

		if(!EverestUtils.isNullorEmptyorWhitespace(drug.getDrugForm()) &&
				Mappings.formCode.containsKey(drug.getDrugForm())) {
			code = new CE<String>(Mappings.formCode.get(drug.getDrugForm()), Constants.CodeSystems.ADMINISTERABLE_DRUG_FORM_OID);
			code.setCodeSystemName(Constants.CodeSystems.ADMINISTERABLE_DRUG_FORM_NAME);
			code.setDisplayName(drug.getDrugForm());
		}

		return code;
	}
}
