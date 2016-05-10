package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.LanguageCommunication;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class LanguageLens extends AbstractLens<Pair<Demographic, RecordTarget>, Pair<Demographic, RecordTarget>> {
	public LanguageLens() {
		get = source -> {
			String value = source.getLeft().getOfficialLanguage();
			ArrayList<LanguageCommunication> languages = source.getRight().getPatientRole().getPatient().getLanguageCommunication();

			if(languages.isEmpty() && !EverestUtils.isNullorEmptyorWhitespace(value) && Mappings.languageCode.containsKey(value)) {
				LanguageCommunication language = new LanguageCommunication();
				language.setLanguageCode(Mappings.languageCode.get(value));
				languages = new ArrayList<>(Arrays.asList(language));
			}

			source.getRight().getPatientRole().getPatient().setLanguageCommunication(languages);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			String value = target.getLeft().getOfficialLanguage();
			ArrayList<LanguageCommunication> languages = target.getRight().getPatientRole().getPatient().getLanguageCommunication();

			if(languages != null && !languages.isEmpty()) {
				LanguageCommunication language = languages.get(0);
				String code = language.getLanguageCode().getCode();
				if(!EverestUtils.isNullorEmptyorWhitespace(code) && Mappings.languageCode.inverseBidiMap().containsKey(code)) {
					value = Mappings.languageCode.inverseBidiMap().get(code);
				}
			}

			target.getLeft().setOfficialLanguage(value);
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
