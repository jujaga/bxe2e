package org.oscarehr.e2e.lens.body.problems;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.BodyConstants.AbstractBodyConstants;
import org.oscarehr.e2e.constant.BodyConstants.Problems;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class ProblemsLens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsLens() {
		AbstractBodyConstants bodyConstants = Problems.getConstants();

		get = source -> {
			Entry entry = source.getRight();

			if(entry.getTemplateId() == null || entry.getTemplateId().isNull() || entry.getTemplateId().isEmpty()) {
				entry.setTypeCode(x_ActRelationshipEntry.DRIV);
				entry.setContextConductionInd(true);
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));

				Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);
				observation.setEntryRelationship(new ArrayList<>());
				entry.setClinicalStatement(observation);
			}

			return new ImmutablePair<>(source.getLeft(), entry);
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
