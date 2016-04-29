package org.oscarehr.e2e.lens.body;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.StructuredBody;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.IModel;

public class DocumentBodyLens extends AbstractLens<Pair<IModel, ClinicalDocument>, Pair<IModel, ClinicalDocument>> {
	public DocumentBodyLens() {
		get = source -> {
			Component2 component = source.getRight().getComponent();

			if(component == null) {
				StructuredBody structuredBody = new StructuredBody();
				structuredBody.setConfidentialityCode(x_BasicConfidentialityKind.Normal);
				structuredBody.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);
				structuredBody.setComponent(new ArrayList<>());

				component = new Component2();
				component.setTypeCode(ActRelationshipHasComponent.HasComponent);
				component.setContextConductionInd(true);
				component.setBodyChoice(structuredBody);
			}

			source.getRight().setComponent(component);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
