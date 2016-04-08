package org.oscarehr.e2e.lens.header.recordtarget;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.NamePartLens;
import org.oscarehr.e2e.util.EverestUtils;

public class NameLens extends AbstractLens<MutablePair<Demographic, RecordTarget>, MutablePair<Demographic, RecordTarget>> {
	public NameLens() {
		get = source -> {
			Demographic demographic = source.getLeft();

			SET<PN> names = null;
			List<ENXP> name = new ArrayList<>();
			if(!EverestUtils.isNullorEmptyorWhitespace(demographic.getFirstName())) {
				name.add(new NamePartLens(EntityNamePartType.Given).get(demographic.getFirstName()));
			}
			if(!EverestUtils.isNullorEmptyorWhitespace(demographic.getLastName())) {
				name.add(new NamePartLens(EntityNamePartType.Family).get(demographic.getLastName()));
			}
			if(!name.isEmpty()) {
				names = new SET<>(new PN(EntityNameUse.Legal, name));
			}

			source.getRight().getPatientRole().getPatient().setName(names);
			return source;
		};

		put = (source, target) -> {
			Demographic demographic = source.getLeft();
			SET<PN> names = target.getRight().getPatientRole().getPatient().getName();

			if(names != null && !names.isNull() && !names.isEmpty()) {
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

			source.setLeft(demographic);
			return source;
		};
	}
}
