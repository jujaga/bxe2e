package org.oscarehr.e2e.model.export.template.observation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.AuthorParticipationModel;
import org.oscarehr.e2e.util.EverestUtils;

public class ReasonObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String reason, Date time, String author) {
		if(!EverestUtils.isNullorEmptyorWhitespace(reason)) {
			entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
			entryRelationship.setContextConductionInd(true);
			entryRelationship.setTemplateId(Arrays.asList(new II(Constants.ObservationOids.REASON_OBSERVATION_TEMPLATE_ID)));

			observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.getCode().setCodeEx(Constants.ObservationType.REASON.toString());
			observation.setText(new ED(reason));

			SET<II> id = new SET<II>();
			id.setNullFlavor(NullFlavor.NoInformation);
			observation.setId(id);

			IVL<TS> ivl = new IVL<TS>();
			if(time != null) {
				ivl.setLow(EverestUtils.buildTSFromDate(time));
			} else {
				ivl.setNullFlavor(NullFlavor.NoInformation);
			}
			observation.setEffectiveTime(ivl);

			CD<String> value = new CD<String>();
			value.setNullFlavor(NullFlavor.NoInformation);
			observation.setValue(value);

			if(!EverestUtils.isNullorEmptyorWhitespace(author)) {
				observation.setAuthor(new ArrayList<Author>(Arrays.asList(new AuthorParticipationModel(author).getAuthor(time))));
			}
		} else {
			entryRelationship = null;
		}

		return entryRelationship;
	}
}
