package org.oscarehr.e2e.lens;

import java.util.GregorianCalendar;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.IModel;

public class CDALens extends AbstractLens<Pair<IModel, ClinicalDocument>, Pair<IModel, ClinicalDocument>> {
	public CDALens() {
		get = source -> {
			ClinicalDocument clinicalDocument = source.getRight();

			clinicalDocument.setEffectiveTime(new GregorianCalendar(), TS.MINUTE);

			return new ImmutablePair<>(source.getLeft(), clinicalDocument);
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
