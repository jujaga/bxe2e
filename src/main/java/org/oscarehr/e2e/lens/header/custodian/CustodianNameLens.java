package org.oscarehr.e2e.lens.header.custodian;

import java.util.ArrayList;

import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.ON;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

class CustodianNameLens extends AbstractLens<String, ON> {
	CustodianNameLens() {
		get = value -> {
			ON on = null;
			ArrayList<ENXP> name = new ArrayList<>();
			if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
				name.add(new ENXP(value));
			}
			if(!name.isEmpty()) {
				on = new ON();
				on.setParts(name);
			}

			return on;
		};

		// TODO Put Function
	}
}
