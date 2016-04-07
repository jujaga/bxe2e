package org.oscarehr.e2e.transformer;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.model.Model;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.rule.E2EConversionRule;
import org.oscarehr.e2e.rule.common.IRule;
import org.oscarehr.e2e.rule.common.IRule.Original;
import org.oscarehr.e2e.rule.header.AuthorRule;
import org.oscarehr.e2e.rule.header.CustodianRule;
import org.oscarehr.e2e.rule.header.InformationRecipientRule;
import org.oscarehr.e2e.rule.header.RecordTargetRule;

public class E2EConversionTransformer extends AbstractTransformer<PatientModel, ClinicalDocument> {
	public E2EConversionTransformer(PatientModel model, ClinicalDocument target, Original original) {
		super(model, target, original);
		if(this.model == null) {
			this.model = new PatientModel();
		}
		if(this.target == null) {
			this.target = new ClinicalDocument();
		}

		transform();
	}

	// TODO Design cleaner transformation solution
	@Override
	protected void transform() {
		// Instantiate and map all rules
		IRule<Model, ClinicalDocument> e2eConversionRule = new E2EConversionRule(model, target, original);
		/*List<IRule<?, ?>> rules = new ArrayList<>();
		rules.add(e2eConversionRule);*/

		String providerNo = null;
		try {
			providerNo = model.getDemographic().getProviderNo();
		} catch (NullPointerException e) {
			providerNo = new String();
		}
		ArrayList<Author> authors = null;
		try {
			authors = target.getAuthor();
		} catch (Exception e) {
			authors = new ArrayList<>();
		}
		IRule<String, ArrayList<Author>> authorRule = new AuthorRule(providerNo, authors, original);

		Clinic clinic = null;
		try {
			clinic = model.getClinic();
			if(clinic == null) {
				clinic = new Clinic();
			}
		} catch (Exception e) {
			clinic = new Clinic();
		}
		Custodian custodian = null;
		try {
			custodian = target.getCustodian();
			if(custodian == null) {
				custodian = new Custodian();
			}
		} catch (Exception e) {
			custodian = null;
		}
		IRule<Clinic, Custodian> custodianRule = new CustodianRule(clinic, custodian, original);

		ArrayList<InformationRecipient> informationRecipient = null;
		try {
			informationRecipient = target.getInformationRecipient();
		} catch (Exception e) {
			informationRecipient = null;
		}
		IRule<?, ArrayList<InformationRecipient>> informationRecipientRule = new InformationRecipientRule(model, informationRecipient, original);

		Demographic demographic = null;
		try {
			demographic = model.getDemographic();
			if(demographic == null) {
				demographic = new Demographic();
			}
		} catch (Exception e) {
			demographic = new Demographic();
		}
		RecordTarget recordTarget = null;
		try {
			recordTarget = target.getRecordTarget().get(0);
			if(recordTarget == null) {
				recordTarget = new RecordTarget();
			}
		} catch (Exception e) {
			recordTarget = new RecordTarget();
		}
		IRule<Demographic, RecordTarget> recordTargetRule = new RecordTargetRule(demographic, recordTarget, original);

		// Run all rules

		// Grab contents from rules and reduce
		if(original == Original.SOURCE) {
			target = e2eConversionRule.getTarget();
			target.setRecordTarget(new ArrayList<>(Arrays.asList(recordTargetRule.getTarget())));
			target.setAuthor(authorRule.getTarget());
			target.setCustodian(custodianRule.getTarget());
			target.setInformationRecipient(informationRecipientRule.getTarget());
		} else {
			model = (PatientModel) e2eConversionRule.getSource();
			//model.setDemographic(recordTargetRule.getSource());
			//model.getDemographic().setProviderNo(authorRule.getSource());
			//model.setClinic(custodianRule.getSource());
		}

		// Consider separate aggregation functions?
	}
}
