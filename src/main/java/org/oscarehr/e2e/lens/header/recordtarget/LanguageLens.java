package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LanguageCommunication;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

class LanguageLens extends AbstractLens<String, ArrayList<LanguageCommunication>> {
	LanguageLens() {
		get = value -> {
			ArrayList<LanguageCommunication> languages = null;
			if(!EverestUtils.isNullorEmptyorWhitespace(value) && Mappings.languageCode.containsKey(value)) {
				LanguageCommunication language = new LanguageCommunication();
				language.setLanguageCode(Mappings.languageCode.get(value));
				languages = new ArrayList<LanguageCommunication>(Arrays.asList(language));
			}

			return languages;
		};

		// TODO Put Function
	}
}
