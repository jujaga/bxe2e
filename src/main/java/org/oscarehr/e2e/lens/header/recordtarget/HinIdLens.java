package org.oscarehr.e2e.lens.header.recordtarget;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class HinIdLens extends AbstractLens<Pair<Demographic, RecordTarget>, Pair<Demographic, RecordTarget>> {
	public HinIdLens() {
		get = source -> {
			String hin = source.getLeft().getHin();

			II id = new II();
			if(!EverestUtils.isNullorEmptyorWhitespace(hin)) {
				id.setRoot(Constants.DocumentHeader.BC_PHN_OID);
				id.setAssigningAuthorityName(Constants.DocumentHeader.BC_PHN_OID_ASSIGNING_AUTHORITY_NAME);
				id.setExtension(hin);
			} else {
				id.setNullFlavor(NullFlavor.NoInformation);
			}

			source.getRight().getPatientRole().setId(new SET<>(id));
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			String hin = target.getLeft().getHin();
			SET<II> id = target.getRight().getPatientRole().getId();

			if(id != null && !id.isNull() && !id.isEmpty()) {
				II ii = id.get(0);
				if(!ii.isNull()) {
					hin = ii.getExtension();
				}
			}

			target.getLeft().setHin(hin);
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
