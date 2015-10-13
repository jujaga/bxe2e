package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.oscarehr.e2e.lens.Lens;
import org.oscarehr.e2e.util.EverestUtils;

class CityLens implements Lens<String, ADXP> {
	private static final AddressPartType addressPartType = AddressPartType.City;

	@Override
	public ADXP get(String city) {
		ADXP addrPart = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(city)) {
			addrPart = new ADXP(city, addressPartType);
		}
		return addrPart;
	}

	@Override
	public String put(ADXP addrPart) {
		String city = null;
		if(addrPart != null && !addrPart.isNull() && addrPart.getPartType() == addressPartType) {
			city = addrPart.getValue();
		}
		return city;
	}
}
