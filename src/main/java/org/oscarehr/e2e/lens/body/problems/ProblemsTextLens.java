package org.oscarehr.e2e.lens.body.problems;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.ED;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class ProblemsTextLens extends AbstractLens<Pair<Dxresearch, Entry>, Pair<Dxresearch, Entry>> {
	public ProblemsTextLens() {
		get = source -> {
			String code = source.getLeft().getDxresearchCode();
			ED text = source.getRight().getClinicalStatementIfObservation().getText();

			if(text == null) {
				String description = EverestUtils.getICD9Description(code);
				if(!EverestUtils.isNullorEmptyorWhitespace(description)) {
					text = new ED(description, Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);
				}
			}

			source.getRight().getClinicalStatementIfObservation().setText(text);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
