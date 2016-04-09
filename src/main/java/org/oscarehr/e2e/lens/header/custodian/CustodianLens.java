package org.oscarehr.e2e.lens.header.custodian;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class CustodianLens extends AbstractLens<Pair<Clinic, Custodian>, Pair<Clinic, Custodian>> {
	public CustodianLens() {
		get = source -> {
			Custodian custodian = source.getRight();

			AssignedCustodian assignedCustodian = new AssignedCustodian();
			CustodianOrganization custodianOrganization = new CustodianOrganization();

			custodian.setAssignedCustodian(assignedCustodian);
			assignedCustodian.setRepresentedCustodianOrganization(custodianOrganization);

			return new ImmutablePair<>(source.getLeft(), custodian);
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
