package org.oscarehr.e2e.model.export.template.observation;

import java.util.Arrays;
import java.util.Date;

import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.TSDateLens;

public class DateObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(Date date) {
		if(date != null) {
			entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
			entryRelationship.setContextConductionInd(true);
			entryRelationship.setTemplateId(Arrays.asList(new II(Constants.ObservationOids.DATE_OBSERVATION_TEMPLATE_ID)));

			observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.getCode().setCodeEx(Constants.ObservationType.DATEOBS.toString());
			observation.setEffectiveTime(new TSDateLens().get(date), null);
		} else {
			entryRelationship = null;
		}

		return entryRelationship;
	}
}
