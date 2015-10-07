package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.StructuredBody;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.populator.AbstractPopulator;

public class DocumentBodyPopulator extends AbstractPopulator {
	public DocumentBodyPopulator(PatientModel patientModel) {
		patientModel.isLoaded(); // Avoid PMD Failure
		populators.add(new AdvanceDirectivesPopulator());
	}

	@Override
	public void populate() {
		Component2 bodyComponent = new Component2();
		bodyComponent.setTypeCode(ActRelationshipHasComponent.HasComponent);
		bodyComponent.setContextConductionInd(true);
		bodyComponent.setBodyChoice(getStructuredBody());

		clinicalDocument.setComponent(bodyComponent);
	}

	public StructuredBody getStructuredBody() {
		StructuredBody body = new StructuredBody();
		ArrayList<Component3> sectionComponents = new ArrayList<Component3>();

		body.setConfidentialityCode(x_BasicConfidentialityKind.Normal);
		body.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);
		body.setComponent(sectionComponents);
		return body;
	}
}
