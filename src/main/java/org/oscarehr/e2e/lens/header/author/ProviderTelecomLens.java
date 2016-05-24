package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.TelecomPartLens;
import org.oscarehr.e2e.util.EverestUtils;

public class ProviderTelecomLens extends AbstractLens<Pair<String, ArrayList<Author>>, Pair<String, ArrayList<Author>>> {
	public ProviderTelecomLens() {
		get = source -> {
			Provider provider = EverestUtils.getProviderFromString(source.getLeft());

			SET<TEL> telecoms = null;
			if(provider != null) {
				ArrayList<TEL> tels = new ArrayList<>();
				tels.add(new TelecomPartLens(TelecomType.TELEPHONE, TelecommunicationsAddressUse.Home).get(provider.getPhone()));
				tels.add(new TelecomPartLens(TelecomType.TELEPHONE, TelecommunicationsAddressUse.WorkPlace).get(provider.getWorkPhone()));
				tels.add(new TelecomPartLens(TelecomType.EMAIL, TelecommunicationsAddressUse.Home).get(provider.getEmail()));
				tels.removeAll(Collections.singleton(null));

				if(!tels.isEmpty()) {
					telecoms = new SET<>(tels);
				}
			}

			source.getRight().get(0).getAssignedAuthor().setTelecom(telecoms);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
