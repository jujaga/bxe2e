package org.oscarehr.e2e.populator.header;

import java.util.GregorianCalendar;
import java.util.UUID;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.vocabulary.BindingRealm;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.populator.AbstractPopulator;

public class HeaderPopulator extends AbstractPopulator {
	private final Demographic demographic;
	private final CE<String> code;
	private final II templateId;

	public HeaderPopulator(PatientModel patientModel, CE<String> code, II templateId) {
		this.demographic = patientModel.getDemographic();
		this.code = code;
		this.templateId = templateId;

		populators.add(new RecordTargetPopulator(patientModel));
		populators.add(new AuthorPopulator(patientModel));
		populators.add(new CustodianPopulator(patientModel));
		populators.add(new InformationRecipientPopulator());
	}

	@Override
	public void populate() {
		// realmCode
		CS<BindingRealm> binding = new CS<BindingRealm>();
		binding.setCodeEx(new BindingRealm(Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE, null));
		clinicalDocument.setRealmCode(new SET<CS<BindingRealm>>(binding));

		// typeId
		clinicalDocument.setTypeId(new II(
				Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID,
				Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION));

		// templateId
		LIST<II> templateIds = new LIST<II>();
		templateIds.add(new II(Constants.DocumentHeader.TEMPLATE_ID));
		templateIds.add(templateId);
		clinicalDocument.setTemplateId(templateIds);

		// id
		clinicalDocument.setId(UUID.randomUUID().toString().toUpperCase(), demographic.getDemographicNo().toString());

		// code
		clinicalDocument.setCode(code);

		// title
		clinicalDocument.setTitle("E2E-DTC Record of ".concat(demographic.getFirstName()).concat(" ").concat(demographic.getLastName()));

		// effectiveTime
		clinicalDocument.setEffectiveTime(new GregorianCalendar(), TS.MINUTE);

		// confidentialityCode
		clinicalDocument.setConfidentialityCode(x_BasicConfidentialityKind.Normal);

		// languageCode
		clinicalDocument.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);
	}
}
