package org.oscarehr.e2e.lens.body.problems;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class ProblemsStatusCodeLens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsStatusCodeLens() {
		get = source -> {
			Character status = source.getLeft().getStatus();
			CS<ActStatus> statusCode = source.getRight().getClinicalStatementIfObservation().getStatusCode();

			if(statusCode == null) {
				if(status != null && status.equals('A')) {
					statusCode = new CS<>(ActStatus.Active);
				} else if (status != null && status.equals('C')) {
					statusCode = new CS<>(ActStatus.Completed);
				}
			}

			source.getRight().getClinicalStatementIfObservation().setStatusCode(statusCode);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			Character status = target.getLeft().getStatus();
			CS<ActStatus> statusCode = target.getRight().getClinicalStatementIfObservation().getStatusCode();

			if(status == null) {
				if(statusCode != null) {
					if(statusCode.getCode() == ActStatus.Active) {
						status = 'A';
					} else if (statusCode.getCode() == ActStatus.Completed) {
						status = 'C';
					}
				} else {
					status = 'D';
				}
			}

			target.getLeft().setStatus(status);
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
