package org.oscarehr.e2e.lens.header.custodian;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class CustodianIdLens extends AbstractLens<MutablePair<Clinic, Custodian>, MutablePair<Clinic, Custodian>> {
	public CustodianIdLens() {
		get = source -> {
			Integer clinicId = source.getLeft().getId();

			II id = new II();
			if(clinicId != null && !EverestUtils.isNullorEmptyorWhitespace(clinicId.toString())) {
				id.setRoot(Constants.EMR.EMR_OID);
				id.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
				id.setExtension(clinicId.toString());
			} else {
				id.setNullFlavor(NullFlavor.NoInformation);
			}

			source.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().setId(new SET<>(id));
			return source;
		};

		put = (source, target) -> {
			return source;
		};
	}
}
