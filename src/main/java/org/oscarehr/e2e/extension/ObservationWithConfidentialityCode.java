package org.oscarehr.e2e.extension;

import org.marc.everest.annotations.ConformanceType;
import org.marc.everest.annotations.Property;
import org.marc.everest.annotations.PropertyType;
import org.marc.everest.annotations.Structure;
import org.marc.everest.annotations.StructureType;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;

@Structure(model = "POCD_MT000040", name = "Observation", structureType = StructureType.MESSAGETYPE)
public class ObservationWithConfidentialityCode extends Observation {
	private CE<x_BasicConfidentialityKind> confidentialityCode;

	public ObservationWithConfidentialityCode(x_ActMoodDocumentObservation eventoccurrence) {
		this.setMoodCode(new CS<x_ActMoodDocumentObservation>(eventoccurrence));
	}

	@Property(conformance = ConformanceType.REQUIRED, name = "confidentialityCode", namespaceUri = "http://standards.pito.bc.ca/E2E-DTC/cda", propertyType = PropertyType.NONSTRUCTURAL)
	public CE<x_BasicConfidentialityKind> getConfidentialityCode() {
		return confidentialityCode;
	}

	public void setConfidentialityCode(CE<x_BasicConfidentialityKind> confidentialityCode) {
		this.confidentialityCode = confidentialityCode;
	}
}
