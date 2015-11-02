package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.Collections;

import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.lens.AbstractLens;

class TelecomLens extends AbstractLens<Demographic, SET<TEL>> {
	TelecomLens() {
		get = demographic -> {
			SET<TEL> telecoms = null;
			ArrayList<TEL> tels = new ArrayList<TEL>();
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE).get(demographic.getPhone()));
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE).get(demographic.getPhone2()));
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.Home, TelecomType.EMAIL).get(demographic.getEmail()));
			tels.removeAll(Collections.singleton(null));

			if(!tels.isEmpty()) {
				telecoms = new SET<TEL>(tels);
			}
			return telecoms;
		};

		// TODO Put Function
	}
}
