package org.oscarehr.e2e.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.casemgmt.model.CaseManagementNoteExt;
import org.oscarehr.common.model.Allergy;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.Drug;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.common.model.Hl7TextInfo;
import org.oscarehr.common.model.Measurement;
import org.oscarehr.common.model.MeasurementsExt;
import org.oscarehr.common.model.Prevention;
import org.oscarehr.common.model.PreventionExt;
import org.oscarehr.e2e.constant.Constants;

public class PatientModel extends Model {
	private Demographic demographic = null;
	private List<Allergy> allergies = null;
	private List<Measurement> measurements = null;
	private List<CaseManagementNote> alerts = null;
	private List<CaseManagementNote> encounters = null;
	private List<CaseManagementNote> riskFactors = null;
	private List<FamilyHistoryEntry> familyHistory = null;
	private List<Immunization> immunizations = null;
	private List<Lab> labs = null;
	private List<Drug> drugs = null;
	private List<Dxresearch> problems = null;

	// PatientExport Standard Interface
	public Demographic getDemographic() {
		return demographic;
	}

	public void setDemographic(Demographic demographic) {
		this.demographic = demographic;
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}

	public List<CaseManagementNote> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<CaseManagementNote> alerts) {
		this.alerts = alerts;
	}

	public List<CaseManagementNote> getEncounters() {
		return encounters;
	}

	public void setEncounters(List<CaseManagementNote> encounters) {
		this.encounters = encounters;
	}

	public List<CaseManagementNote> getRiskFactors() {
		return riskFactors;
	}

	public void setRiskFactors(List<CaseManagementNote> riskFactors) {
		this.riskFactors = riskFactors;
	}

	public List<FamilyHistoryEntry> getFamilyHistory() {
		return familyHistory;
	}

	public void setFamilyHistory(List<FamilyHistoryEntry> familyHistory) {
		this.familyHistory = familyHistory;
	}

	public List<Immunization> getImmunizations() {
		return immunizations;
	}

	public void setImmunizations(List<Immunization> immunizations) {
		this.immunizations = immunizations;
	}

	public List<Lab> getLabs() {
		return labs;
	}

	public void setLabs(List<Lab> labs) {
		this.labs = labs;
	}

	public List<Drug> getMedications() {
		return drugs;
	}

	public void setMedications(List<Drug> drugs) {
		this.drugs = drugs;
	}

	public List<Dxresearch> getProblems() {
		return problems;
	}

	public void setProblems(List<Dxresearch> problems) {
		this.problems = problems;
	}

	// Supporting Family History Subclass
	public static class FamilyHistoryEntry {
		private CaseManagementNote familyHistory = new CaseManagementNote();
		private Map<String, String> extMap = new HashMap<String, String>();

		public FamilyHistoryEntry(CaseManagementNote familyHistory, List<CaseManagementNoteExt> extMap) {
			if(familyHistory != null) {
				this.familyHistory = familyHistory;
			}
			if(extMap != null) {
				for(CaseManagementNoteExt extElement : extMap) {
					this.extMap.put(extElement.getKeyVal(), extElement.getValue());
				}
			}
		}

		public CaseManagementNote getFamilyHistory() {
			return familyHistory;
		}

		public Map<String, String> getExtMap() {
			return extMap;
		}
	}

	// Supporting Immunization Subclass
	public static class Immunization {
		private Prevention prevention = new Prevention();
		private Map<String, String> preventionMap = new HashMap<String, String>();

		public Immunization(Prevention prevention, List<PreventionExt> preventionExt) {
			if(prevention != null) {
				this.prevention = prevention;
			}
			if(preventionExt != null) {
				for(PreventionExt extElement : preventionExt) {
					this.preventionMap.put(extElement.getKeyVal(), extElement.getVal());
				}
			}
		}

		public Prevention getPrevention() {
			return prevention;
		}

		public Map<String, String> getPreventionMap() {
			return preventionMap;
		}
	}

	// Supporting Lab Grouping Subclasses
	public static class Lab {
		private Hl7TextInfo hl7TextInfo = new Hl7TextInfo();
		private List<LabOrganizer> labOrganizer = new ArrayList<LabOrganizer>();
		private String requestDate = null;

		public Lab(Hl7TextInfo hl7TextInfo) {
			if(hl7TextInfo != null) {
				this.hl7TextInfo = hl7TextInfo;
			}
		}

		public Hl7TextInfo getHl7TextInfo() {
			return hl7TextInfo;
		}

		public List<LabOrganizer> getLabOrganizer() {
			return labOrganizer;
		}

		public String getRequestDate() {
			return requestDate;
		}

		public void setRequestDate(String requestDate) {
			this.requestDate = requestDate;
		}
	}

	public static class LabOrganizer {
		private Integer id = Constants.Runtime.INVALID_VALUE;
		private String reportStatus = null;
		private List<LabComponent> labComponent = new ArrayList<LabComponent>();

		public LabOrganizer(Integer id, String reportStatus) {
			this.id = id;
			this.reportStatus = reportStatus;
		}

		public Integer getGroupId() {
			return id;
		}

		public String getReportStatus() {
			return reportStatus;
		}

		public List<LabComponent> getLabComponent() {
			return labComponent;
		}
	}

	public static class LabComponent {
		private Measurement measurement = new Measurement();
		private Map<String, String> measurementsMap = new HashMap<String, String>();

		public LabComponent(Measurement measurement, List<MeasurementsExt> measurementsExt) {
			if(measurement != null) {
				this.measurement = measurement;
			}
			if(measurementsExt != null) {
				for(MeasurementsExt extElement : measurementsExt) {
					this.measurementsMap.put(extElement.getKeyVal(), extElement.getVal());
				}
			}
		}

		public Measurement getMeasurement() {
			return measurement;
		}

		public Map<String, String> getMeasurementsMap() {
			return measurementsMap;
		}
	}
}
