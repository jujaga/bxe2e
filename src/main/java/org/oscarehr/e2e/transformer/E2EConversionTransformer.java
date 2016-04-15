package org.oscarehr.e2e.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.common.model.Demographic;
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
	@SuppressWarnings("unchecked")
	@Override
	protected void transform() {
		// Instantiate and map all rules
		List<IRule<?, ?>> rules = new ArrayList<>();
		rules.add(new E2EConversionRule(model, target, original));

		Demographic demographic = model.getDemographic();
		RecordTarget recordTarget = null;
		if(target.getRecordTarget() != null && !target.getRecordTarget().isEmpty()) {
			recordTarget = target.getRecordTarget().get(0);
		}
		rules.add(new RecordTargetRule(demographic, recordTarget, original));

		String providerNo = null;
		if(demographic != null) {
			providerNo = model.getDemographic().getProviderNo();
		}
		rules.add(new AuthorRule(providerNo, target.getAuthor(), original));
		rules.add(new CustodianRule(model.getClinic(), target.getCustodian(), original));
		rules.add(new InformationRecipientRule(null, target.getInformationRecipient(), original));

		// Run all rules
		rules = rules.stream()
				.map(rule -> {
					rule.apply();
					return rule;
				})
				.collect(Collectors.toList());

		// Grab contents from rules and reduce
		if(original == Original.SOURCE) {
			// TODO Determine if mapped list streaming can be better written and ordered
			rules.stream().filter(rule -> rule.isApplied() && rule.getClass() == E2EConversionRule.class).forEach(rule -> {
				target = (ClinicalDocument) rule.getTarget();
			});

			for(IRule<?, ?> rule : rules) {
				if(rule.getClass() == RecordTargetRule.class) {
					target.setRecordTarget(new ArrayList<>(Arrays.asList((RecordTarget) rule.getTarget())));
				}
				if(rule.getClass() == AuthorRule.class) {
					target.setAuthor((ArrayList<Author>) rule.getTarget());
				}
				if(rule.getClass() == CustodianRule.class) {
					target.setCustodian((Custodian) rule.getTarget());
				}
				if(rule.getClass() == InformationRecipientRule.class) {
					target.setInformationRecipient((ArrayList<InformationRecipient>) rule.getTarget());
				}
			}
		} else {
			for(IRule<?, ?> rule : rules) {
				if(rule.getClass() == E2EConversionRule.class) {
					model = (PatientModel) rule.getSource();
				}
			}
			for(IRule<?, ?> rule : rules) {
				if(rule.getClass() == RecordTargetRule.class) {
					model.setDemographic((Demographic) rule.getSource());
				}
			}
			for(IRule<?, ?> rule : rules) {
				if(rule.getClass() == AuthorRule.class) {
					model.getDemographic().setProviderNo((String) rule.getSource());
				}
				if(rule.getClass() == CustodianRule.class) {
					model.setClinic((Clinic) rule.getSource());
				}
			}
		}

		// Consider separate aggregation functions?
	}
}
