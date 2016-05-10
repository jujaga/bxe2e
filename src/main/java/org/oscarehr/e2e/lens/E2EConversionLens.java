package org.oscarehr.e2e.lens;

import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.vocabulary.BindingRealm;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.IModel;
import org.oscarehr.e2e.model.PatientModel;

public class E2EConversionLens extends AbstractLens<Pair<IModel, ClinicalDocument>, Pair<IModel, ClinicalDocument>> {
	public E2EConversionLens() {
		get = source -> {
			Demographic demographic = ((PatientModel) source.getLeft()).getDemographic();
			ClinicalDocument clinicalDocument = source.getRight();

			if(source.getLeft().isLoaded() && demographic != null) {
				CS<BindingRealm> binding = new CS<>();
				binding.setCodeEx(new BindingRealm(Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE, null));
				clinicalDocument.setRealmCode(new SET<>(binding));

				clinicalDocument.setTypeId(new II(
						Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID,
						Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION));

				LIST<II> templateIds = new LIST<>();
				templateIds.add(new II(Constants.DocumentHeader.TEMPLATE_ID));
				templateIds.add(new II(Constants.EMRConversionDocument.TEMPLATE_ID));
				clinicalDocument.setTemplateId(templateIds);

				clinicalDocument.setId(UUID.randomUUID().toString().toUpperCase(), demographic.getDemographicNo().toString());
				clinicalDocument.setCode(Constants.EMRConversionDocument.CODE);
				clinicalDocument.setTitle("E2E-DTC Record of ".concat(demographic.getFirstName()).concat(" ").concat(demographic.getLastName()));
				clinicalDocument.setConfidentialityCode(x_BasicConfidentialityKind.Normal);
				clinicalDocument.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);
			}

			return new ImmutablePair<>(source.getLeft(), clinicalDocument);
		};

		put = (source, target) -> {
			PatientModel patientModel = (PatientModel) target.getLeft();

			Demographic demographic = patientModel.getDemographic();
			if(demographic == null) {
				demographic = new Demographic();
			}
			if(demographic.getDemographicNo() == null) {
				demographic.setDemographicNo(Integer.parseInt(target.getRight().getId().getExtension().replaceAll("[\\D]", "")));
			}

			patientModel.setDemographic(demographic);
			patientModel.setLoaded(true);
			return new ImmutablePair<>(patientModel, target.getRight());
		};
	}
}
