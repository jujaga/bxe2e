package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.oscarehr.e2e.lens.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

class DelimiterLens extends AbstractLens<String, ADXP> {
	private static final AddressPartType addressPartType = AddressPartType.Delimiter;

	DelimiterLens() {
		get = s -> {
			ADXP addrPart = null;
			if(!EverestUtils.isNullorEmptyorWhitespace(s)) {
				addrPart = new ADXP(s, addressPartType);
			}
			return addrPart;
		};

		put = (s, t) -> {
			if(t != null && !t.isNull() && t.getPartType() == addressPartType) {
				s = t.getValue();
			}
			return s;
		};
	}
}
