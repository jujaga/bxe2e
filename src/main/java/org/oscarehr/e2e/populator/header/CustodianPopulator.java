package org.oscarehr.e2e.populator.header;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;
import org.oscarehr.common.dao.ClinicDao;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.header.CustodianModel;
import org.oscarehr.e2e.populator.AbstractPopulator;

class CustodianPopulator extends AbstractPopulator {
	private final CustodianModel custodianModel;

	CustodianPopulator(PatientExport patientExport) {
		ClinicDao clinicDao = patientExport.getApplicationContext().getBean(ClinicDao.class);
		Clinic clinic = clinicDao.find(Constants.Runtime.VALID_CLINIC);
		custodianModel = new CustodianModel(clinic);
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
