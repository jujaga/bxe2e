package org.oscarehr.e2e.lens.common;

import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.util.EverestUtils;

public class TelecomPartLens extends AbstractLens<String, TEL> {
	public TelecomPartLens(final TelecommunicationsAddressUse telecomAddressUse, final TelecomType telecomType) {
		get = value -> {
			TEL tel = null;
			if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
				if(telecomType == Constants.TelecomType.TELEPHONE) {
					tel = new TEL(Constants.DocumentHeader.TEL_PREFIX + value.replaceAll("[^0-9]", ""), telecomAddressUse);
				} else if(telecomType == Constants.TelecomType.EMAIL) {
					tel = new TEL(Constants.DocumentHeader.EMAIL_PREFIX + value, telecomAddressUse);
				}
			}

			return tel;
		};

		put = (value, tel) -> {
			if(telecomType == Constants.TelecomType.TELEPHONE && TEL.isValidPhoneFlavor(tel)) {
				// Consider restoring hyphens and parentheses in telephone numbers
				value = tel.getValue().replaceAll(Constants.DocumentHeader.TEL_PREFIX, "");
			} else if(telecomType == Constants.TelecomType.EMAIL && TEL.isValidEMailFlavor(tel)) {
				value = tel.getValue().replaceAll(Constants.DocumentHeader.EMAIL_PREFIX, "");
			}
			return value;
		};
	}
}
