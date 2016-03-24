package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.vocabulary.AdministrativeGender;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class GenderLens extends AbstractLens<String, CE<AdministrativeGender>> {
	public GenderLens() {
		get = sex -> {
			CE<AdministrativeGender> gender = new CE<>();
			if(EverestUtils.isNullorEmptyorWhitespace(sex)) {
				gender.setNullFlavor(NullFlavor.NoInformation);
			}
			else {
				String sexCode = sex.toUpperCase().replace("U", "UN");
				if(Mappings.genderCode.containsKey(sexCode)) {
					gender.setCodeEx(Mappings.genderCode.get(sexCode));
					gender.setDisplayName(Mappings.genderDescription.get(sexCode));
				}
				else {
					gender.setNullFlavor(NullFlavor.NoInformation);
				}
			}
			return gender;
		};

		put = (sex, gender) -> {
			if(!gender.isNull()) {
				AdministrativeGender administrativeGender = gender.getCode();
				if(Mappings.genderCode.inverseBidiMap().containsKey(administrativeGender)) {
					sex = Mappings.genderCode.inverseBidiMap().get(administrativeGender).toUpperCase().replace("UN", "U");
				}
			}
			return sex;
		};
	}
}
