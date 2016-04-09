package org.oscarehr.e2e.lens.header.custodian;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.ON;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class CustodianNameLens extends AbstractLens<Pair<Clinic, Custodian>, Pair<Clinic, Custodian>> {
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
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			Clinic clinic = target.getLeft();
			ON on = target.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().getName();

			if(on != null && !on.isNull()) {
				String clinicName = "test";
				clinic.setClinicName(clinicName);
			}

			return new ImmutablePair<>(clinic, target.getRight());
		};
	}
}
