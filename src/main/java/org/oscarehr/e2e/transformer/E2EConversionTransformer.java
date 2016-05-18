package org.oscarehr.e2e.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.BodyConstants;
import org.oscarehr.e2e.constant.BodyConstants.AbstractBodyConstants;
import org.oscarehr.e2e.constant.BodyConstants.Problems;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.rule.E2EConversionRule;
import org.oscarehr.e2e.rule.body.ProblemsRule;
import org.oscarehr.e2e.rule.common.IRule;
import org.oscarehr.e2e.rule.common.IRule.Original;
import org.oscarehr.e2e.rule.header.AuthorRule;
import org.oscarehr.e2e.rule.header.CustodianRule;
import org.oscarehr.e2e.rule.header.InformationRecipientRule;
import org.oscarehr.e2e.rule.header.RecordTargetRule;

public class E2EConversionTransformer extends AbstractTransformer<PatientModel, ClinicalDocument> {
	private List<IRule<?, ?>> rules;
	private Map<String, Pair<?, ?>> results;
	private String patientUUID;

	public E2EConversionTransformer(PatientModel model) {
		this(model, null, Original.SOURCE);
	}

	public E2EConversionTransformer(ClinicalDocument target) {
		this(null, target, Original.TARGET);
	}

	public E2EConversionTransformer(PatientModel model, ClinicalDocument target, Original original) {
		super(model, target, original);
	}

	public String getPatientUUID() {
		return patientUUID;
	}

	@Override
	protected void transform() {
		if(model == null) {
			model = new PatientModel();
		}
		if(target == null) {
			target = new ClinicalDocument();
		}

		// Map Reduce
		try {
			results = map();
			reduce();
		} catch (Exception e) {
			log.error("Transformer Error: Failed transformation. Contains invalid results", e);
		}

		// Save Patient UUID session
		try {
			patientUUID = target.getId().getRoot();
		} catch (NullPointerException e) {
			patientUUID = null;
		}
	}

	private Map<String, Pair<?, ?>> map() {
		rules = new ArrayList<>();
		rules.add(new E2EConversionRule(model, target));

		Demographic demographic = model.getDemographic();
		RecordTarget recordTarget = null;
		if(target.getRecordTarget() != null && !target.getRecordTarget().isEmpty()) {
			recordTarget = target.getRecordTarget().get(0);
		}
		rules.add(new RecordTargetRule(demographic, recordTarget));

		String providerNo = null;
		if(demographic != null) {
			providerNo = model.getDemographic().getProviderNo();
		}
		rules.add(new AuthorRule(providerNo, target.getAuthor()));
		rules.add(new CustodianRule(model.getClinic(), target.getCustodian()));
		rules.add(new InformationRecipientRule(null, target.getInformationRecipient()));

		ArrayList<Component3> components;
		try {
			components = target.getComponent().getBodyChoiceIfStructuredBody().getComponent();
		} catch (NullPointerException e) {
			components = new ArrayList<>();
		}

		mapProblems(components);

		// Run all rules
		return rules.parallelStream()
				.map(IRule::execute)
				.sequential()
				.collect(Collectors.toConcurrentMap(IRule::getName, IRule::getPair));
	}

	private void mapProblems(ArrayList<Component3> components) {
		if(original == Original.SOURCE) {
			List<Dxresearch> problems = model.getProblems();

			if(problems != null && !problems.isEmpty()) {
				problems.forEach(problem -> rules.add(new ProblemsRule(problem, null)));
			}
		} else if(original == Original.TARGET) {
			Component3 component = findBodySection(components, Problems.getConstants());

			if(component != null) {
				ArrayList<Entry> entries = component.getSection().getEntry();
				if(entries != null && !entries.isEmpty()) {
					entries.forEach(entry -> rules.add(new ProblemsRule(null, entry)));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void reduce() {
		if(original == Original.SOURCE) {
			target = (ClinicalDocument) results.get(E2EConversionRule.class.getSimpleName()).getRight();
			target.setRecordTarget(new ArrayList<>(Arrays.asList((RecordTarget) results.get(RecordTargetRule.class.getSimpleName()).getRight())));
			target.setAuthor((ArrayList<Author>) results.get(AuthorRule.class.getSimpleName()).getRight());
			target.setCustodian((Custodian) results.get(CustodianRule.class.getSimpleName()).getRight());
			target.setInformationRecipient((ArrayList<InformationRecipient>) results.get(InformationRecipientRule.class.getSimpleName()).getRight());

			ArrayList<Component3> components = target.getComponent().getBodyChoiceIfStructuredBody().getComponent();

			reduceProblems(components);

			target.getComponent().getBodyChoiceIfStructuredBody().setComponent(components);
		} else if(original == Original.TARGET) {
			model = (PatientModel) results.get(E2EConversionRule.class.getSimpleName()).getLeft();

			Integer demographicNo = null;
			if(model.getDemographic() != null) {
				demographicNo = model.getDemographic().getDemographicNo();
			}

			model.setDemographic((Demographic) results.get(RecordTargetRule.class.getSimpleName()).getLeft());
			model.getDemographic().setDemographicNo(demographicNo);
			model.getDemographic().setProviderNo((String) results.get(AuthorRule.class.getSimpleName()).getLeft());
			model.setClinic((Clinic) results.get(CustodianRule.class.getSimpleName()).getLeft());

			ArrayList<Component3> components = target.getComponent().getBodyChoiceIfStructuredBody().getComponent();

			reduceProblems(components);
		}
	}

	private void reduceProblems(ArrayList<Component3> components) {
		if(original == Original.SOURCE) {
			Component3 component = findBodySection(components, Problems.getConstants());

			if(component != null) {
				results.entrySet().forEach(e -> {
					if(e.getKey().startsWith(ProblemsRule.class.getSimpleName())) {
						component.getSection().getEntry().add((Entry) e.getValue().getRight());
					}
				});
			}
		} else if(original == Original.TARGET) {
			results.entrySet().forEach(e -> {
				if(e.getKey().startsWith(ProblemsRule.class.getSimpleName())) {
					model.getProblems().add((Dxresearch) e.getValue().getLeft());
				}
			});
		}
	}

	private Component3 findBodySection(ArrayList<Component3> components, AbstractBodyConstants bodyConstants) {
		return components.stream()
				.filter(e -> BodyConstants.filledEntryFilter.test(BodyConstants.componentToII.apply(e), bodyConstants))
				.findFirst()
				.orElse(null);
	}
}
