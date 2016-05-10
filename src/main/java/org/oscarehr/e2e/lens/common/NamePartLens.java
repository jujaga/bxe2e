package org.oscarehr.e2e.lens.common;

import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.oscarehr.e2e.util.EverestUtils;

public class NamePartLens extends AbstractLens<String, ENXP> {
	public NamePartLens(final EntityNamePartType entityNamePartType) {
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
