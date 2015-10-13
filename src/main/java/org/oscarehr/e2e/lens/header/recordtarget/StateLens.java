package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.oscarehr.e2e.lens.Lens;
import org.oscarehr.e2e.util.EverestUtils;

class StateLens implements Lens<String, ADXP> {
	private static final AddressPartType addressPartType = AddressPartType.State;

	@Override
	public ADXP get(String state) {
		ADXP addrPart = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(state)) {
			addrPart = new ADXP(state, addressPartType);
		}
		return addrPart;
	}

	@Override
	public String put(ADXP addrPart) {
		String state = null;
		if(addrPart != null && !addrPart.isNull() && addrPart.getPartType() == addressPartType) {
			state = addrPart.getValue();
		}
		return state;
	}
}
