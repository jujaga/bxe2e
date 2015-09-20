package org.oscarehr.e2e.model.export.template.observation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.AuthorParticipationModel;
import org.oscarehr.e2e.util.EverestUtils;

public class CommentObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String comment, Date time, String author) {
		if(!EverestUtils.isNullorEmptyorWhitespace(comment)) {
			entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
			entryRelationship.setContextConductionInd(true);
			entryRelationship.setTemplateId(Arrays.asList(new II(Constants.ObservationOids.COMMENT_OBSERVATION_TEMPLATE_ID)));

			observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.getCode().setCodeEx(Constants.ObservationType.COMMENT.toString());
			observation.setText(new ED(comment));
			if(time != null) {
				observation.setEffectiveTime(EverestUtils.buildTSFromDate(time), null);
			}
			if(!EverestUtils.isNullorEmptyorWhitespace(author)) {
				observation.setAuthor(new ArrayList<Author>(Arrays.asList(new AuthorParticipationModel(author).getAuthor(time))));
			}
		} else {
			entryRelationship = null;
		}

		return entryRelationship;
	}
}
