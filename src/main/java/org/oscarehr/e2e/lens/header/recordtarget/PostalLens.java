package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.oscarehr.e2e.lens.Lens;
import org.oscarehr.e2e.util.EverestUtils;

class PostalLens implements Lens<String, ADXP> {
	private static final AddressPartType addressPartType = AddressPartType.PostalCode;

	@Override
	public ADXP get(String postal) {
		ADXP addrPart = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(postal)) {
			addrPart = new ADXP(postal, addressPartType);
		}
		return addrPart;
	}

	@Override
	public String put(ADXP addrPart) {
		String postal = null;
		if(addrPart != null && !addrPart.isNull() && addrPart.getPartType() == addressPartType) {
			postal = addrPart.getValue();
		}
		return postal;
	}
}
