package org.oscarehr.e2e.lens.header.custodian;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class CustodianLens extends AbstractLens<MutablePair<Clinic, Custodian>, MutablePair<Clinic, Custodian>> {
	public CustodianLens() {
		get = source -> {
			Custodian custodian = source.getRight();

			AssignedCustodian assignedCustodian = new AssignedCustodian();
			CustodianOrganization custodianOrganization = new CustodianOrganization();

			custodian.setAssignedCustodian(assignedCustodian);
			assignedCustodian.setRepresentedCustodianOrganization(custodianOrganization);

			source.setRight(custodian);
			return source;
		};

		put = (source, target) -> {
			return source;
		};
	}
}
