package org.oscarehr.e2e.lens.body.problems;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.body.template.observation.DateObservationLens;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class ProblemsDiagnosisDateLens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsDiagnosisDateLens() {
		final String oid = Constants.ObservationOids.DATE_OBSERVATION_TEMPLATE_ID;

		get = source -> {
			Date date = source.getLeft().getUpdateDate();
			ArrayList<EntryRelationship> entryRelationships = source.getRight().getClinicalStatementIfObservation().getEntryRelationship();

			EntryRelationship entryRelationship = EverestUtils.findEntryRelationship(entryRelationships, oid);
			if(entryRelationship == null) {
				entryRelationship = new DateObservationLens().get(date);
				entryRelationships.add(entryRelationship);
			}

			source.getRight().getClinicalStatementIfObservation().setEntryRelationship(entryRelationships);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			Date date = source.getLeft().getUpdateDate();
			ArrayList<EntryRelationship> entryRelationships = target.getRight().getClinicalStatementIfObservation().getEntryRelationship();

			EntryRelationship entryRelationship = EverestUtils.findEntryRelationship(entryRelationships, oid);
			if(entryRelationship != null) {
				date = new DateObservationLens().put(entryRelationship);
			}

			target.getLeft().setUpdateDate(date);
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
