package org.oscarehr.e2e.lens.body.problems;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.body.template.observation.SecondaryCodeICD9ObservationLens;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class ProblemsICD9Lens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsICD9Lens() {
		final String oid = Constants.ObservationOids.SECONDARY_CODE_ICD9_OBSERVATION_TEMPLATE_ID;
		final String icd9 = "icd9";

		get = source -> {
			String code = source.getLeft().getDxresearchCode();
			String codeSystem = source.getLeft().getCodingSystem();
			ArrayList<EntryRelationship> entryRelationships = source.getRight().getClinicalStatementIfObservation().getEntryRelationship();

			EntryRelationship entryRelationship = EverestUtils.findEntryRelationship(entryRelationships, oid);
			if(entryRelationship == null && icd9.equals(codeSystem)) {
				entryRelationship = new SecondaryCodeICD9ObservationLens().get(code);
				entryRelationships.add(entryRelationship);
			}

			source.getRight().getClinicalStatementIfObservation().setEntryRelationship(entryRelationships);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			String code = target.getLeft().getDxresearchCode();
			String codeSystem = target.getLeft().getCodingSystem();
			ArrayList<EntryRelationship> entryRelationships = target.getRight().getClinicalStatementIfObservation().getEntryRelationship();

			EntryRelationship entryRelationship = EverestUtils.findEntryRelationship(entryRelationships, oid);
			if(code == null && codeSystem == null && entryRelationship != null) {
				code = new SecondaryCodeICD9ObservationLens().put(entryRelationship);
				codeSystem = icd9;
			}

			target.getLeft().setDxresearchCode(code);
			target.getLeft().setCodingSystem(codeSystem);
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
