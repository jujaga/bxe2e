package org.oscarehr.e2e.populator.header;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.model.export.header.CustodianModel;
import org.oscarehr.e2e.populator.AbstractPopulator;

class CustodianPopulator extends AbstractPopulator {
	private final CustodianModel custodianModel;

	CustodianPopulator(PatientModel patientModel) {
		custodianModel = new CustodianModel(patientModel.getClinic());
	}

	@Override
	public void populate() {
		Custodian custodian = new Custodian();
		AssignedCustodian assignedCustodian = new AssignedCustodian();
		CustodianOrganization custodianOrganization = new CustodianOrganization();

		custodian.setAssignedCustodian(assignedCustodian);
		assignedCustodian.setRepresentedCustodianOrganization(custodianOrganization);
		custodianOrganization.setId(custodianModel.getIds());
		custodianOrganization.setName(custodianModel.getName());

		clinicalDocument.setCustodian(custodian);
	}
}
