package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.List;

import org.marc.everest.datatypes.AD;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.PostalAddressUse;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.ZipperLens;

class AddressLens implements ZipperLens<Demographic, SET<AD>> {
	@Override
	public SET<AD> get(Demographic demographic) {
		SET<AD> addresses = null;
		List<ADXP> addrParts = new ArrayList<>();

		addrParts.add(new DelimiterLens().get(demographic.getAddress()));
		addrParts.add(new CityLens().get(demographic.getCity()));
		addrParts.add(new StateLens().get(demographic.getProvince()));
		addrParts.add(new PostalLens().get(demographic.getPostal()));
		if(!addrParts.isEmpty()) {
			CS<PostalAddressUse> use = new CS<PostalAddressUse>(PostalAddressUse.HomeAddress);
			AD addr = new AD(use, addrParts);
			addresses = new SET<AD>(addr);
		}

		return addresses;
	}

	@Override
	public void put(Demographic demographic, SET<AD> addresses) {
		if(!addresses.isNull() && !addresses.isEmpty()) {
			AD addr = addresses.get(0);
			if(!addr.isNull()) {
				List<ADXP> addrParts = addr.getPart();
				for(ADXP addrPart : addrParts) {
					AddressPartType addressPartType = addrPart.getPartType();
					if(addressPartType == AddressPartType.Delimiter) {
						demographic.setAddress(new DelimiterLens().put(addrPart));
					} else if(addressPartType == AddressPartType.City) {
						demographic.setCity(new CityLens().put(addrPart));
					} else if(addressPartType == AddressPartType.State) {
						demographic.setProvince(new StateLens().put(addrPart));
					} else if(addressPartType == AddressPartType.PostalCode) {
						demographic.setPostal(new PostalLens().put(addrPart));
					}
				}
			}
		}
	}
}
