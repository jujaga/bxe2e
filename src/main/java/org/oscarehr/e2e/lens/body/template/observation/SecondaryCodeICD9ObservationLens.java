package org.oscarehr.e2e.lens.body.template.observation;

import org.marc.everest.datatypes.ANY;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class SecondaryCodeICD9ObservationLens extends AbstractLens<String, EntryRelationship> {
	public SecondaryCodeICD9ObservationLens() {
		final String oid = Constants.ObservationOids.SECONDARY_CODE_ICD9_OBSERVATION_TEMPLATE_ID;

		get = code -> {
			EntryRelationship entryRelationship = EverestUtils.createObservationTemplate(oid);
			entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);

			Observation observation = entryRelationship.getClinicalStatementIfObservation();
			observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.getCode().setCodeEx(Constants.ObservationType.ICD9CODE.toString());

			CD<String> icd9Value = new CD<>();
			if(!EverestUtils.isNullorEmptyorWhitespace(code)) {
				String description = EverestUtils.getICD9Description(code);
				if(!EverestUtils.isNullorEmptyorWhitespace(description)) {
					icd9Value.setDisplayName(description);
				}
				icd9Value.setCodeEx(code);
				icd9Value.setCodeSystem(Constants.CodeSystems.ICD9_OID);
				icd9Value.setCodeSystemName(Constants.CodeSystems.ICD9_NAME);
			} else {
				icd9Value.setNullFlavor(NullFlavor.Unknown);
			}
			observation.setValue(icd9Value);

			return entryRelationship;
		};

		put = (code, entryRelationship) -> {
			if(EverestUtils.isEntryRelationshipType.test(entryRelationship, oid)) {
				ANY value = entryRelationship.getClinicalStatementIfObservation().getValue();
				if(value != null && !value.isNull() && value.getDataType() == CD.class) {
					@SuppressWarnings("unchecked")
					CD<String> icd9Value = (CD<String>) value;
					if(icd9Value.getCodeSystem() != null && icd9Value.getCodeSystem().equals(Constants.CodeSystems.ICD9_OID)) {
						code = icd9Value.getCode();
					}
				}
			}

			return code;
		};
	}
}
