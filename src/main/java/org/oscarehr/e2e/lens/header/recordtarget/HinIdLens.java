package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class HinIdLens extends AbstractLens<String, SET<II>> {
	public HinIdLens() {
		get = hin -> {
			II id = new II();
			if(!EverestUtils.isNullorEmptyorWhitespace(hin)) {
				id.setRoot(Constants.DocumentHeader.BC_PHN_OID);
				id.setAssigningAuthorityName(Constants.DocumentHeader.BC_PHN_OID_ASSIGNING_AUTHORITY_NAME);
				id.setExtension(hin);
			} else {
				id.setNullFlavor(NullFlavor.NoInformation);
			}
			return new SET<>(id);
		};

		put = (hin, id) -> {
			if(!id.isNull() && !id.isEmpty()) {
				II ii = id.get(0);
				if(!ii.isNull()) {
					hin = ii.getExtension();
				}
			}
			return hin;
		};
	}
}
