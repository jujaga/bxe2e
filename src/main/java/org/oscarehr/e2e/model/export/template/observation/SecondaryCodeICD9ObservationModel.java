package org.oscarehr.e2e.model.export.template.observation;

import java.util.Arrays;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.util.EverestUtils;

public class SecondaryCodeICD9ObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String value) {
		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.HasComponent);
		entryRelationship.setContextConductionInd(true);
		entryRelationship.setTemplateId(Arrays.asList(new II(Constants.ObservationOids.SECONDARY_CODE_ICD9_OBSERVATION_TEMPLATE_ID)));

		observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.getCode().setCodeEx(Constants.ObservationType.ICD9CODE.toString());

		CD<String> icd9Value = new CD<String>();
		if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
			String description = EverestUtils.getICD9Description(value);
			if(!EverestUtils.isNullorEmptyorWhitespace(description)) {
				icd9Value.setDisplayName(description);
			}
			icd9Value.setCodeEx(value);
			icd9Value.setCodeSystem(Constants.CodeSystems.ICD9_OID);
			icd9Value.setCodeSystemName(Constants.CodeSystems.ICD9_NAME);
		} else {
			icd9Value.setNullFlavor(NullFlavor.Unknown);
		}
		observation.setValue(icd9Value);

		return entryRelationship;
	}
}
