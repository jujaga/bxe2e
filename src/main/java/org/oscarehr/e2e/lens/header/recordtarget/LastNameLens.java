package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.oscarehr.e2e.lens.Lens;
import org.oscarehr.e2e.util.EverestUtils;

class LastNameLens implements Lens<String, ENXP> {
	private static final EntityNamePartType entityNamePartType = EntityNamePartType.Family;

	@Override
	public ENXP get(String name) {
		ENXP namePart = null;
		if(!EverestUtils.isNullorEmptyorWhitespace(name)) {
			namePart = new ENXP(name, entityNamePartType);
		}
		return namePart;
	}

	@Override
	public String put(String name, ENXP namePart) {
		if(namePart != null && !namePart.isNull() && namePart.getType().getCode() == entityNamePartType) {
			name = namePart.getValue();
		}
		return name;
	}
}
