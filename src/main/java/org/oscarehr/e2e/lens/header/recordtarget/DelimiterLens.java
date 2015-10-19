package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.oscarehr.e2e.lens.Get;
import org.oscarehr.e2e.lens.Lens;
import org.oscarehr.e2e.util.EverestUtils;

class DelimiterLens implements Lens<String, ADXP> {
	private static final AddressPartType addressPartType = AddressPartType.Delimiter;

	private Get<String, ADXP> get = s -> {
		ADXP addrPart = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(s)) {
			addrPart = new ADXP(s, addressPartType);
		}
		return addrPart;
	};

	@Override
	public ADXP get(String delimiter) {
		return get.get(delimiter);
	}

	@Override
	public String put(String delimiter, ADXP addrPart) {
		if(addrPart != null && !addrPart.isNull() && addrPart.getPartType() == addressPartType) {
			delimiter = addrPart.getValue();
		}
		return delimiter;
	}
}
