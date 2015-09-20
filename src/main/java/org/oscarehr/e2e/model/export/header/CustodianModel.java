package org.oscarehr.e2e.model.export.header;

import java.util.ArrayList;

import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.ON;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.util.EverestUtils;

public class CustodianModel {
	private final Clinic clinic;

	private SET<II> ids;
	private ON name;

	public CustodianModel(Clinic clinic) {
		if(clinic == null) {
			this.clinic = new Clinic();
		} else {
			this.clinic = clinic;
		}

		setIds();
		setName();
	}

	public SET<II> getIds() {
		return ids;
	}

	private void setIds() {
		II id = new II();
		if(clinic.getId() != null && !EverestUtils.isNullorEmptyorWhitespace(clinic.getId().toString())) {
			id.setRoot(Constants.EMR.EMR_OID);
			id.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
			id.setExtension(clinic.getId().toString());
		} else {
			id.setNullFlavor(NullFlavor.NoInformation);
		}
		this.ids = new SET<II>(id);
	}

	public ON getName() {
		return name;
	}

	private void setName() {
		ArrayList<ENXP> name = new ArrayList<ENXP>();
		if(!EverestUtils.isNullorEmptyorWhitespace(clinic.getClinicName())) {
			name.add(new ENXP(clinic.getClinicName()));
		}
		if(!name.isEmpty()) {
			ON on = new ON();
			on.setParts(name);
			this.name = on;
		}
		else {
			this.name = null;
		}
	}
}
