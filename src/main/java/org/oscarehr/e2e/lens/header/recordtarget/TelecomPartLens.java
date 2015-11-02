package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.lens.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

class TelecomPartLens extends AbstractLens<String, TEL> {
	TelecomPartLens(TelecommunicationsAddressUse telecomAddressUse, TelecomType telecomType) {
		get = value -> {
			TEL tel = null;
			if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
				if(telecomType == Constants.TelecomType.TELEPHONE) {
					tel = new TEL(Constants.DocumentHeader.TEL_PREFIX + value.replaceAll("-", ""), telecomAddressUse);
				} else if(telecomType == Constants.TelecomType.EMAIL) {
					tel = new TEL(Constants.DocumentHeader.EMAIL_PREFIX + value, telecomAddressUse);
				}
			}

			return tel;
		};

		// TODO Put Function
	}
}
