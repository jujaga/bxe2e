package org.oscarehr.e2e.lens.header.recordtarget;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.AdministrativeGender;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class GenderLens extends AbstractLens<Pair<Demographic, RecordTarget>, Pair<Demographic, RecordTarget>> {
	public GenderLens() {
		get = source -> {
			String sex = source.getLeft().getSex();
			CE<AdministrativeGender> gender = source.getRight().getPatientRole().getPatient().getAdministrativeGenderCode();

			if(gender == null) {
				gender = new CE<>();
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
			}

			source.getRight().getPatientRole().getPatient().setAdministrativeGenderCode(gender);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			String sex = target.getLeft().getSex();
			CE<AdministrativeGender> gender = target.getRight().getPatientRole().getPatient().getAdministrativeGenderCode();

			if(gender != null && !gender.isNull()) {
				AdministrativeGender administrativeGender = gender.getCode();
				if(Mappings.genderCode.inverseBidiMap().containsKey(administrativeGender)) {
					sex = Mappings.genderCode.inverseBidiMap().get(administrativeGender).toUpperCase().replace("UN", "U");
				}
			}

			target.getLeft().setSex(sex);
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
