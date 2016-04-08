package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class ProviderIdLens extends AbstractLens<MutablePair<String, ArrayList<Author>>, MutablePair<String, ArrayList<Author>>> {
	public ProviderIdLens() {
		get = source -> {
			Provider provider = EverestUtils.getProviderFromString(source.getLeft());

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

			source.getRight().get(0).getAssignedAuthor().setId(new SET<>(id));
			return source;
		};

		put = (source, target) -> {
			return source;
		};
	}
}
