package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.List;

import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

class NameLens extends AbstractLens<Demographic, SET<PN>> {
	NameLens() {
		get = demographic -> {
			SET<PN> names = null;
			List<ENXP> name = new ArrayList<>();
			if(!EverestUtils.isNullorEmptyorWhitespace(demographic.getFirstName())) {
				name.add(new NamePartLens(EntityNamePartType.Given).get(demographic.getFirstName()));
			}
			if(!EverestUtils.isNullorEmptyorWhitespace(demographic.getLastName())) {
				name.add(new NamePartLens(EntityNamePartType.Family).get(demographic.getLastName()));
			}
			if(!name.isEmpty()) {
				names = new SET<PN>(new PN(EntityNameUse.Legal, name));
			}

			return names;
		};

		put = (demographic, names) -> {
			if(!names.isNull() && !names.isEmpty()) {
				PN name = names.get(0);
				if(!name.isNull()) {
					List<ENXP> nameParts = name.getParts();
					for(ENXP namePart : nameParts) {
						EntityNamePartType entityNamePartType = namePart.getType().getCode();
						String value = new NamePartLens(entityNamePartType).put(namePart);
						if(entityNamePartType == EntityNamePartType.Given) {
							demographic.setFirstName(value);
						} else if(entityNamePartType == EntityNamePartType.Family) {
							demographic.setLastName(value);
						}
					}
				}
			}

			return demographic;
		};
	}
}
