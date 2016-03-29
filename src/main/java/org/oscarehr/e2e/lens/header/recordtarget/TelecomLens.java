package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.TelecomPartLens;

public class TelecomLens extends AbstractLens<MutablePair<Demographic, RecordTarget>, MutablePair<Demographic, RecordTarget>> {
	public TelecomLens() {
		get = source -> {
			Demographic demographic = source.getLeft();

			SET<TEL> telecoms = null;
			ArrayList<TEL> tels = new ArrayList<>();
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.Home, TelecomType.TELEPHONE).get(demographic.getPhone()));
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.WorkPlace, TelecomType.TELEPHONE).get(demographic.getPhone2()));
			tels.add(new TelecomPartLens(TelecommunicationsAddressUse.Home, TelecomType.EMAIL).get(demographic.getEmail()));
			tels.removeAll(Collections.singleton(null));

			if(!tels.isEmpty()) {
				telecoms = new SET<>(tels);
			}

			source.getRight().getPatientRole().setTelecom(telecoms);
			return source;
		};

		put = (source, target) -> {
			Demographic demographic = source.getLeft();
			SET<TEL> telecoms = target.getRight().getPatientRole().getTelecom();

			if(!telecoms.isNull() && telecoms.isEmpty()) {
				for(TEL tel : telecoms) {
					if(TEL.isValidPhoneFlavor(tel)) {
						String value = new TelecomPartLens(null, TelecomType.TELEPHONE).put(tel);
						if(tel.getUse().get(0).getCode() == TelecommunicationsAddressUse.Home) {
							demographic.setPhone(value);
						} else if(tel.getUse().get(0).getCode() == TelecommunicationsAddressUse.WorkPlace) {
							demographic.setPhone2(value);
						}
					} else if(TEL.isValidEMailFlavor(tel)) {
						String value = new TelecomPartLens(null, TelecomType.EMAIL).put(tel);
						if(tel.getUse().get(0).getCode() == TelecommunicationsAddressUse.Home) {
							demographic.setEmail(value);
						}
					}
				}
			}

			source.setLeft(demographic);
			return source;
		};
	}
}
