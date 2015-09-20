package org.oscarehr.e2e.populator.header;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;
import org.oscarehr.e2e.populator.AbstractPopulatorTest;

public class CustodianPopulatorTest extends AbstractPopulatorTest {
	@Test
	public void custodianTest() {
		Custodian custodian = clinicalDocument.getCustodian();
		assertNotNull(custodian);
	}

	@Test
	public void assignedCustodianTest() {
		AssignedCustodian assignedCustodian = clinicalDocument.getCustodian().getAssignedCustodian();
		assertNotNull(assignedCustodian);
	}

	@Test
	public void custodianOrganizationTest() {
		AssignedCustodian assignedCustodian = clinicalDocument.getCustodian().getAssignedCustodian();
		CustodianOrganization custodianOrganization = assignedCustodian.getRepresentedCustodianOrganization();
		assertNotNull(custodianOrganization);
		assertNotNull(custodianOrganization.getId());
	}
}
