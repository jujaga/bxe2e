package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.oscarehr.e2e.lens.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

class AddressPartLens extends AbstractLens<String, ADXP> {
	AddressPartLens(AddressPartType addressPartType) {
		get = value -> {
			ADXP addrPart = null;
			if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
				addrPart = new ADXP(value, addressPartType);
			}
			return addrPart;
		};

		put = (value, addrPart) -> {
			if(addrPart != null && !addrPart.isNull() && addrPart.getPartType() == addressPartType) {
				value = addrPart.getValue();
			}
			return value;
		};
	}
}
