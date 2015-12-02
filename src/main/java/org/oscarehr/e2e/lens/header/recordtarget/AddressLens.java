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
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.AddressPartLens;

public class AddressLens extends AbstractLens<Demographic, SET<AD>> {
	public AddressLens() {
		get = demographic -> {
			SET<AD> addresses = null;
			List<ADXP> addrParts = new ArrayList<>();

			addrParts.add(new AddressPartLens(AddressPartType.Delimiter).get(demographic.getAddress()));
			addrParts.add(new AddressPartLens(AddressPartType.City).get(demographic.getCity()));
			addrParts.add(new AddressPartLens(AddressPartType.State).get(demographic.getProvince()));
			addrParts.add(new AddressPartLens(AddressPartType.PostalCode).get(demographic.getPostal()));
			if(!addrParts.isEmpty()) {
				CS<PostalAddressUse> use = new CS<>(PostalAddressUse.HomeAddress);
				AD addr = new AD(use, addrParts);
				addresses = new SET<>(addr);
			}

			return addresses;
		};

		put = (demographic, addresses) -> {
			if(!addresses.isNull() && !addresses.isEmpty()) {
				AD addr = addresses.get(0);
				if(!addr.isNull()) {
					List<ADXP> addrParts = addr.getPart();
					for(ADXP addrPart : addrParts) {
						AddressPartType addressPartType = addrPart.getPartType();
						String value = new AddressPartLens(addressPartType).put(addrPart);

						if(addressPartType == AddressPartType.Delimiter) {
							demographic.setAddress(value);
						} else if(addressPartType == AddressPartType.City) {
							demographic.setCity(value);
						} else if(addressPartType == AddressPartType.State) {
							demographic.setProvince(value);
						} else if(addressPartType == AddressPartType.PostalCode) {
							demographic.setPostal(value);
						}
					}
				}
			}

			return demographic;
		};
	}
}
