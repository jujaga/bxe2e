package org.oscarehr.e2e.lens.header.custodian;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class CustodianLens extends AbstractLens<Clinic, Custodian> {
	public CustodianLens() {
		get = clinic -> {
			Custodian custodian = new Custodian();
			AssignedCustodian assignedCustodian = new AssignedCustodian();
			CustodianOrganization custodianOrganization = new CustodianOrganization();

			custodian.setAssignedCustodian(assignedCustodian);
			assignedCustodian.setRepresentedCustodianOrganization(custodianOrganization);
			custodianOrganization.setId(new CustodianIdLens().get(clinic.getId()));
			custodianOrganization.setName(new CustodianNameLens().get(clinic.getClinicName()));

			return custodian;
		};

		// TODO Put Function
	}
}
