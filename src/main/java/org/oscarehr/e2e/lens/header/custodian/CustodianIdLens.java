package org.oscarehr.e2e.lens.header.custodian;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class CustodianIdLens extends AbstractLens<Integer, SET<II>> {
	CustodianIdLens() {
		get = clinicId -> {
			II id = new II();
			if(clinicId != null && !EverestUtils.isNullorEmptyorWhitespace(clinicId.toString())) {
				id.setRoot(Constants.EMR.EMR_OID);
				id.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
				id.setExtension(clinicId.toString());
			} else {
				id.setNullFlavor(NullFlavor.NoInformation);
			}

			return new SET<>(id);
		};

		// TODO Put Function
	}
}
