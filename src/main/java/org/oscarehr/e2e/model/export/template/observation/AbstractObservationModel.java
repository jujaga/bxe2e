package org.oscarehr.e2e.model.export.template.observation;

import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.oscarehr.e2e.constant.Constants;

abstract class AbstractObservationModel {
	protected EntryRelationship entryRelationship;
	protected Observation observation;

	protected AbstractObservationModel() {
		this.entryRelationship = new EntryRelationship();
		this.observation = new Observation();

		CD<String> code = new CD<String>();
		code.setCodeSystem(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_OID);
		code.setCodeSystemName(Constants.CodeSystems.OBSERVATIONTYPE_CA_PENDING_NAME);
		observation.setCode(code);

		entryRelationship.setClinicalStatement(observation);
	}
}
