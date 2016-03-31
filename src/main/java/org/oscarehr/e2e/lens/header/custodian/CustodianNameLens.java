package org.oscarehr.e2e.lens.header.custodian;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.ON;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class CustodianNameLens extends AbstractLens<MutablePair<Clinic, Custodian>, MutablePair<Clinic, Custodian>> {
	public CustodianNameLens() {
		get = source -> {
			String value = source.getLeft().getClinicName();

			ON on = null;
			List<ENXP> name = new ArrayList<>();
			if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
				name.add(new ENXP(value));
			}
			if(!name.isEmpty()) {
				on = new ON();
				on.setParts(name);
			}

			source.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().setName(on);
			return source;
		};

		put = (source, target) -> {
			return source;
		};
	}
}
