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
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.AuthorParticipationModel;
import org.oscarehr.e2e.util.EverestUtils;

public class ReactionObservationModel extends AbstractObservationModel {
	public EntryRelationship getEntryRelationship(String reaction, Date time, String author, Date authorDate, String severity) {
		if(!EverestUtils.isNullorEmptyorWhitespace(reaction)) {
			ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

			entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
			entryRelationship.setContextConductionInd(true);
			entryRelationship.setTemplateId(Arrays.asList(new II(Constants.ObservationOids.REACTION_OBSERVATION_TEMPLATE_ID)));

			observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
			observation.getCode().setCodeEx(Constants.ObservationType.REACTOBS.toString());
			observation.setText(new ED(reaction));

			if(time != null) {
				observation.setEffectiveTime(new IVL<TS>(EverestUtils.buildTSFromDate(time), null));
			}

			CD<String> value = new CD<String>();
			value.setNullFlavor(NullFlavor.NoInformation);
			observation.setValue(value);

			if(!EverestUtils.isNullorEmptyorWhitespace(author)) {
				observation.setAuthor(new ArrayList<Author>(Arrays.asList(new AuthorParticipationModel(author).getAuthor(authorDate))));
			}

			entryRelationships.add(new SeverityObservationModel().getEntryRelationship(severity));
			observation.setEntryRelationship(entryRelationships);
		} else {
			entryRelationship = null;
		}

		return entryRelationship;
	}
}
