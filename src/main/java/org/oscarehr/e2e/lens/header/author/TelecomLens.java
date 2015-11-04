package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;
import java.util.Collections;

import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.TelecomPartLens;

class TelecomLens extends AbstractLens<Provider, SET<TEL>> {
	TelecomLens() {
		get = provider -> {
			SET<TEL> telecoms = null;
			ArrayList<TEL> tels = new ArrayList<>();
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE).get(provider.getPhone()));
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE).get(provider.getWorkPhone()));
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.Home, TelecomType.EMAIL).get(provider.getEmail()));
			tels.removeAll(Collections.singleton(null));

			if(!tels.isEmpty()) {
				telecoms = new SET<>(tels);
			}
			return telecoms;
		};

		// TODO Put Function
	}
}
