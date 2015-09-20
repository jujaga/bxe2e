package org.oscarehr.e2e.model.export.template.observation;

import java.util.Arrays;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Mappings;

public class SeverityObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String severity) {
		entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
		entryRelationship.setContextConductionInd(true);
		entryRelationship.setTemplateId(Arrays.asList(new II(Constants.ObservationOids.SEVERITY_OBSERVATION_TEMPLATE_ID)));

		CD<String> value = new CD<String>();
		if(Mappings.allergyTestValue.containsKey(severity)) {
			value.setCodeEx(Mappings.allergyTestValue.get(severity));
			value.setCodeSystem(Constants.CodeSystems.OBSERVATION_VALUE_OID);
			value.setCodeSystemName(Constants.CodeSystems.OBSERVATION_VALUE_NAME);
			value.setDisplayName(Mappings.allergyTestName.get(severity));
		} else {
			value.setNullFlavor(NullFlavor.Unknown);
		}

		observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.getCode().setCodeEx(Constants.ObservationType.SEV.toString());
		observation.setValue(value);

		return entryRelationship;
	}
}
