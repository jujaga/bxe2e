package org.oscarehr.e2e.lens.common;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.util.EverestUtils;

public class AuthorIdLens extends AbstractLens<String, SET<II>> {
	public AuthorIdLens() {
		get = providerNo -> {
			Provider provider = EverestUtils.getProviderFromString(providerNo);

			II id = new II();
			if(provider != null) {
				if(!EverestUtils.isNullorEmptyorWhitespace(provider.getPractitionerNo())) {
					id.setRoot(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_ID_OID);
					id.setAssigningAuthorityName(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_NAME);
					id.setExtension(provider.getPractitionerNo());
				} else if (!EverestUtils.isNullorEmptyorWhitespace(provider.getOhipNo())) {
					id.setRoot(Constants.DocumentHeader.MEDICAL_SERVICES_PLAN_BILLING_NUMBER_OID);
					id.setAssigningAuthorityName(Constants.DocumentHeader.MEDICAL_SERVICES_PLAN_BILLING_NUMBER_NAME);
					id.setExtension(provider.getOhipNo());
				} else if (provider.getProviderNo() != null) {
					id.setRoot(Constants.DocumentHeader.LOCALLY_ASSIGNED_IDENTIFIER_OID);
					id.setAssigningAuthorityName(Constants.DocumentHeader.LOCALLY_ASSIGNED_IDENTIFIER_NAME);
					id.setExtension(provider.getProviderNo().toString());
				} else {
					id.setNullFlavor(NullFlavor.NoInformation);
				}
			}

			return new SET<>(id);
		};

		put = (providerNo, ids) -> {
			return providerNo;
		};
	}
}
