package org.oscarehr.e2e.lens.header.custodian;

import java.util.Arrays;

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
			ON on = source.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().getName();

			if(on == null) {
				if(!EverestUtils.isNullorEmptyorWhitespace(value)) {
					on = new ON();
					on.setParts(Arrays.asList(new ENXP(value)));
				}
			}

			source.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().setName(on);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			String value = target.getLeft().getClinicName();
			ON on = target.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().getName();

			if(value == null && on != null && !on.isNull()) {
				value = on.getPart(0).getValue();
				target.getLeft().setClinicName(value);
			}

			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
