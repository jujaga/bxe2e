package org.oscarehr.e2e.lens.header.custodian;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class CustodianIdLens extends AbstractLens<Pair<Clinic, Custodian>, Pair<Clinic, Custodian>> {
	public CustodianIdLens() {
		get = source -> {
			Integer clinicId = source.getLeft().getId();
			SET<II> ids = source.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().getId();

			if(ids == null) {
				II id = new II();
				if(clinicId != null && !EverestUtils.isNullorEmptyorWhitespace(clinicId.toString())) {
					id.setRoot(Constants.EMR.EMR_OID);
					id.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
					id.setExtension(clinicId.toString());
				} else {
					id.setNullFlavor(NullFlavor.NoInformation);
				}

				source.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().setId(new SET<>(id));
			}

			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			Integer clinicId = target.getLeft().getId();
			SET<II> ids = target.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().getId();

			if(ids != null && !ids.isNull() && !ids.isEmpty()) {
				II id = ids.get(0);
				if(id != null && !id.isNull() && id.getRoot().equals(Constants.EMR.EMR_OID)) {
					try {
						clinicId = Integer.parseInt(id.getExtension());
					} catch (NumberFormatException e) {
						clinicId = null;
					}
					target.getLeft().setId(clinicId);
				}
			}

			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
