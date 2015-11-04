package org.oscarehr.e2e.lens.header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.BindingRealm;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.header.author.AuthorLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianLens;
import org.oscarehr.e2e.lens.header.recordtarget.RecordTargetLens;
import org.oscarehr.e2e.model.PatientModel;

public class HeaderLens extends AbstractLens<PatientModel, ClinicalDocument> {
	public HeaderLens(CE<String> code, II templateId) {
		get = patientModel -> {
			ClinicalDocument clinicalDocument = new ClinicalDocument();

			// Headers
			CS<BindingRealm> binding = new CS<>();
			binding.setCodeEx(new BindingRealm(Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE, null));
			clinicalDocument.setRealmCode(new SET<>(binding));

			clinicalDocument.setTypeId(new II(
					Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID,
					Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION));

			LIST<II> templateIds = new LIST<>();
			templateIds.add(new II(Constants.DocumentHeader.TEMPLATE_ID));
			templateIds.add(templateId);
			clinicalDocument.setTemplateId(templateIds);

			clinicalDocument.setId(UUID.randomUUID().toString().toUpperCase(), patientModel.getDemographic().getDemographicNo().toString());
			clinicalDocument.setCode(code);
			clinicalDocument.setTitle("E2E-DTC Record of ".concat(patientModel.getDemographic().getFirstName()).concat(" ").concat(patientModel.getDemographic().getLastName()));
			clinicalDocument.setEffectiveTime(new GregorianCalendar(), TS.MINUTE);
			clinicalDocument.setConfidentialityCode(x_BasicConfidentialityKind.Normal);
			clinicalDocument.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);

			// Record Target
			RecordTarget recordTarget = new RecordTargetLens().get(patientModel.getDemographic());
			clinicalDocument.setRecordTarget(new ArrayList<>(Arrays.asList(recordTarget)));

			// Author
			clinicalDocument.setAuthor(new AuthorLens().get(patientModel.getDemographic().getProviderNo()));

			// Custodian
			clinicalDocument.setCustodian(new CustodianLens().get(patientModel.getClinic()));

			// Information Recipient
			clinicalDocument.setInformationRecipient(new InformationRecipientLens().get(null));

			return clinicalDocument;
		};

		// TODO Put Function
	}
}
