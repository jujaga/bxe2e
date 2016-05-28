package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.AD;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.PostalAddressUse;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.AddressPartLens;

public class AddressLens extends AbstractLens<Pair<Demographic, RecordTarget>, Pair<Demographic, RecordTarget>> {
	public AddressLens() {
		get = source -> {
			Demographic demographic = source.getLeft();
			SET<AD> addresses = source.getRight().getPatientRole().getAddr();

			if(addresses == null) {
				List<ADXP> addrParts = new ArrayList<>();
				addrParts.add(new AddressPartLens(AddressPartType.Delimiter).get(demographic.getAddress()));
				addrParts.add(new AddressPartLens(AddressPartType.City).get(demographic.getCity()));
				addrParts.add(new AddressPartLens(AddressPartType.State).get(demographic.getProvince()));
				addrParts.add(new AddressPartLens(AddressPartType.PostalCode).get(demographic.getPostal()));

				if(!addrParts.isEmpty()) {
					addresses = new SET<>(new AD(new CS<>(PostalAddressUse.HomeAddress), addrParts));
				}
			}

			source.getRight().getPatientRole().setAddr(addresses);
			return new ImmutablePair<>(demographic, source.getRight());
		};

		put = (source, target) -> {
			Demographic demographic = target.getLeft();
			SET<AD> addresses = target.getRight().getPatientRole().getAddr();

			if(addresses != null && !addresses.isNull() && !addresses.isEmpty()) {
				AD addr = addresses.get(0);
				if(!addr.isNull()) {
					addr.getPart().forEach(addrPart -> {
						AddressPartType addressPartType = addrPart.getPartType();
						if(addressPartType == AddressPartType.Delimiter) {
							demographic.setAddress(new AddressPartLens(addressPartType).put(demographic.getAddress(), addrPart));
						} else if(addressPartType == AddressPartType.City) {
							demographic.setCity(new AddressPartLens(addressPartType).put(demographic.getCity(), addrPart));
						} else if(addressPartType == AddressPartType.State) {
							demographic.setProvince(new AddressPartLens(addressPartType).put(demographic.getProvince(), addrPart));
						} else if(addressPartType == AddressPartType.PostalCode) {
							demographic.setPostal(new AddressPartLens(addressPartType).put(demographic.getPostal(), addrPart));
						}
					});
				}
			}

			return new ImmutablePair<>(demographic, target.getRight());
		};
	}
}
