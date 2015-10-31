package org.oscarehr.e2e.lens.header.recordtarget;

import org.apache.log4j.Logger;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.oscarehr.e2e.lens.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

class NamePartLens extends AbstractLens<String, ENXP> {
	NamePartLens(EntityNamePartType entityNamePartType) {
		log = Logger.getLogger(NamePartLens.class.getName());

		get = name -> {
			ENXP namePart = null;
			if(!EverestUtils.isNullorEmptyorWhitespace(name)) {
				namePart = new ENXP(name, entityNamePartType);
			}
			return namePart;
		};

		put = (name, namePart) -> {
			if(namePart != null && !namePart.isNull() && namePart.getType().getCode() == entityNamePartType) {
				name = namePart.getValue();
			}
			return name;
		};
	}
}
