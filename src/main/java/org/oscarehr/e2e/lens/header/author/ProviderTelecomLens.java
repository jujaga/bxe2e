package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.TelecomPartLens;
import org.oscarehr.e2e.util.EverestUtils;

public class ProviderTelecomLens extends AbstractLens<MutablePair<String, ArrayList<Author>>, MutablePair<String, ArrayList<Author>>> {
	public ProviderTelecomLens() {
		get = source -> {
			Provider provider = EverestUtils.getProviderFromString(source.getLeft());

			SET<TEL> telecoms = null;
			ArrayList<TEL> tels = new ArrayList<>();
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE).get(provider.getPhone()));
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE).get(provider.getWorkPhone()));
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.Home, TelecomType.EMAIL).get(provider.getEmail()));
			tels.removeAll(Collections.singleton(null));

			if(!tels.isEmpty()) {
				telecoms = new SET<>(tels);
			}

			source.getRight().get(0).getAssignedAuthor().setTelecom(telecoms);
			return source;
		};

		put = (source, target) -> {
			return source;
		};
	}
}
