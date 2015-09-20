package org.oscarehr.e2e.model.export.template.observation;

import java.util.Arrays;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Mappings;

public class LifestageObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String lifeStage) {
		if(Mappings.lifeStageCode.containsKey(lifeStage)) {
			entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
			entryRelationship.setContextConductionInd(true);
			entryRelationship.setTemplateId(Arrays.asList(new II(Constants.ObservationOids.LIFESTAGE_OBSERVATION_TEMPLATE_ID)));

			CD<String> value = new CD<String>(Mappings.lifeStageCode.get(lifeStage), Constants.CodeSystems.SNOMED_CT_OID);
			value.setCodeSystemName(Constants.CodeSystems.SNOMED_CT_NAME);
			value.setDisplayName(Mappings.lifeStageName.get(lifeStage));

			observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.getCode().setCodeEx(Constants.ObservationType.LIFEOBS.toString());
			observation.setValue(value);
		} else {
			entryRelationship = null;
		}

		return entryRelationship;
	}
}
