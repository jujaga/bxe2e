package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.List;

import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.Lens;
import org.oscarehr.e2e.util.EverestUtils;

class NameLens implements Lens<Demographic, SET<PN>> {
	@Override
	public SET<PN> get(Demographic demographic) {
		SET<PN> names = null;
		List<ENXP> name = new ArrayList<>();
		if(!EverestUtils.isNullorEmptyorWhitespace(demographic.getFirstName())) {
			name.add(new FirstNameLens().get(demographic.getFirstName()));
		}
		if(!EverestUtils.isNullorEmptyorWhitespace(demographic.getLastName())) {
			name.add(new FirstNameLens().get(demographic.getLastName()));
		}
		if(!name.isEmpty()) {
			names = new SET<PN>(new PN(EntityNameUse.Legal, name));
		}

		return names;
	}

	@Override
	public Demographic put(Demographic demographic, SET<PN> names) {
		if(!names.isNull() && !names.isEmpty()) {
			PN name = names.get(0);
			if(!name.isNull()) {
				List<ENXP> nameParts = name.getParts();
				for(ENXP namePart : nameParts) {
					EntityNamePartType entityNamePartType = namePart.getType().getCode();
					if(entityNamePartType == EntityNamePartType.Given) {
						demographic.setFirstName(new FirstNameLens().put(null, namePart));
					} else if(entityNamePartType == EntityNamePartType.Family) {
						demographic.setLastName(new LastNameLens().put(null, namePart));
					}
				}
			}
		}

		return demographic;
	}
}
