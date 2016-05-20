package org.oscarehr.e2e.lens.body.template.observation;

import java.util.Date;

import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.TSDateLens;
import org.oscarehr.e2e.util.EverestUtils;

public class DateObservationLens extends AbstractLens<Date, EntryRelationship> {
	public DateObservationLens() {
		final String oid = Constants.ObservationOids.DATE_OBSERVATION_TEMPLATE_ID;

		get = date -> {
			EntryRelationship entryRelationship = null;

			if(date != null) {
				entryRelationship = EverestUtils.createObservationTemplate(oid);
				entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);

				Observation observation = entryRelationship.getClinicalStatementIfObservation();
				observation.setMoodCode(x_ActMoodDocumentObservation.Eventoccurrence);
				observation.getCode().setCodeEx(Constants.ObservationType.DATEOBS.toString());
				observation.setEffectiveTime(new TSDateLens().get(date), null);
			}

			return entryRelationship;
		};

		put = (date, entryRelationship) -> {
			if(date == null && EverestUtils.isEntryRelationshipType.test(entryRelationship, oid)) {
				IVL<TS> ivl = entryRelationship.getClinicalStatementIfObservation().getEffectiveTime();
				if(ivl != null && !ivl.isNull()) {
					date = new TSDateLens().put(ivl.getLow());
				}
			}

			return date;
		};
	}
}
