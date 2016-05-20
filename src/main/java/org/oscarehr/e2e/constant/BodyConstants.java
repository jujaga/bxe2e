package org.oscarehr.e2e.constant;

/**
 * The Class BodyConstants. Contains Section heading constants used in E2E Body Sections.
 */
public class BodyConstants {
	BodyConstants() {
		throw new UnsupportedOperationException();
	}

	// Enumerations
	public static enum SectionPriority {
		MAY, SHALL, SHOULD
	}

	// Body Constants
	public abstract static class AbstractBodyConstants {
		public SectionPriority EMR_CONVERSION_SECTION_PRIORITY;
		public String WITH_ENTRIES_TITLE;
		public String WITHOUT_ENTRIES_TITLE;
		public String WITH_ENTRIES_TEMPLATE_ID;
		public String WITHOUT_ENTRIES_TEMPLATE_ID;
		public String CODE;
		public String CODE_SYSTEM;
		public String CODE_SYSTEM_NAME;
		public String ENTRY_TEMPLATE_ID;
		public String ENTRY_NO_TEXT;
	}

	public static class AdvanceDirectives extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private AdvanceDirectives() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Advance Directives Section [with entries]";
			WITHOUT_ENTRIES_TITLE = "Advance Directives Section [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.2.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.2";
			CODE = "42348-3";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.ADVANCE_DIRECTIVES_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_NOT_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new AdvanceDirectives();
			}
			return bodyConstants;
		}
	}

	/*
	public static class Alerts extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private Alerts() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Alerts [with entries]";
			WITHOUT_ENTRIES_TITLE = "Alerts [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.3.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.3";
			CODE = "ALERTS";
			CODE_SYSTEM = Constants.CodeSystems.SECTIONTYPE_CA_PENDING_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.SECTIONTYPE_CA_PENDING_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.ALERTS_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Alerts();
			}
			return bodyConstants;
		}
	}

	public static class Allergies extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private Allergies() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Allergies and Intolerances (Reaction List) [with entries]";
			WITHOUT_ENTRIES_TITLE = "Allergies and Intolerances (Reaction List) [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.4.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.4";
			CODE = "48765-2";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.ALLERGY_INTOLERANCE_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Allergies();
			}
			return bodyConstants;
		}
	}

	public static class ClinicallyMeasuredObservations extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		public static final String BLOOD_PRESSURE_CODE = "BP";
		public static final String DIASTOLIC_CODE = "SYST";
		public static final String SYSTOLIC_CODE = "DIAS";

		private ClinicallyMeasuredObservations() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHOULD;
			WITH_ENTRIES_TITLE = "Clinical Measured Observations [with entries]";
			WITHOUT_ENTRIES_TITLE = "Clinical Measured Observations [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.8.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.8";
			CODE = "CLINOBS";
			CODE_SYSTEM = Constants.CodeSystems.SECTIONTYPE_CA_PENDING_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.SECTIONTYPE_CA_PENDING_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.CLINICALLY_MEASURED_OBSERVATIONS_ORGANIZER_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_NOT_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new ClinicallyMeasuredObservations();
			}
			return bodyConstants;
		}
	}

	public static class Encounters extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private Encounters() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Encounter History & Notes [with entries]";
			WITHOUT_ENTRIES_TITLE = "Encounter History & Notes [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.12.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.12";
			CODE = "46240-8";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.ENCOUNTER_EVENT_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Encounters();
			}
			return bodyConstants;
		}
	}

	public static class FamilyHistory extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private FamilyHistory() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Family History [with entries]";
			WITHOUT_ENTRIES_TITLE = "Family History [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.13.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.13";
			CODE = "10157-6";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.FAMILY_HISTORY_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new FamilyHistory();
			}
			return bodyConstants;
		}
	}

	public static class Immunizations extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private Immunizations() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Immunizations List [with entries]";
			WITHOUT_ENTRIES_TITLE = "Immunizations List [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.14.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.14";
			CODE = "11369-6";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.IMMUNIZATION_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Immunizations();
			}
			return bodyConstants;
		}
	}

	public static class Labs extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		public static final String ABNORMAL = "Abnormal";
		public static final String ABNORMAL_CODE = "A";
		public static final String NORMAL = "Normal";
		public static final String NORMAL_CODE = "N";

		private Labs() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Laboratory Results and Reports [with entries]";
			WITHOUT_ENTRIES_TITLE = "Laboratory Results and Reports [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.16.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.16";
			CODE = "30954-2";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.LABS_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Labs();
			}
			return bodyConstants;
		}
	}

	public static class Medications extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		public static final String DRUG_THERAPY_ACT_NAME = "Drug Therapy";
		public static final String LONG_TERM = "Long Term";
		public static final String SHORT_TERM = "Short Term";

		private Medications() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Medications and Prescriptions - Medication List [with entries]";
			WITHOUT_ENTRIES_TITLE = "Medications and Prescriptions - Medication List [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.19.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.19";
			CODE = "10160-0";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.MEDICATION_EVENT_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Medications();
			}
			return bodyConstants;
		}
	}

	public static class OrdersAndRequests extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private OrdersAndRequests() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Orders and Requests [with entries]";
			WITHOUT_ENTRIES_TITLE = "Orders and Requests [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.20.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.20";
			CODE = "REQS";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.ORDER_EVENT_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_NOT_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new OrdersAndRequests();
			}
			return bodyConstants;
		}
	}
	 */

	public static class Problems extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private Problems() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Problems and Conditions - Problem List [with entries]";
			WITHOUT_ENTRIES_TITLE = "Problems and Conditions - Problem List [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.21.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.21";
			CODE = "11450-4";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.PROBLEMS_OBSERVATION_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new Problems();
			}
			return bodyConstants;
		}
	}

	/*
	public static class RiskFactors extends AbstractBodyConstants {
		protected static AbstractBodyConstants bodyConstants = null;

		private RiskFactors() {
			EMR_CONVERSION_SECTION_PRIORITY = SectionPriority.SHALL;
			WITH_ENTRIES_TITLE = "Risk Factors [with entries]";
			WITHOUT_ENTRIES_TITLE = "Risk Factors [without entries]";
			WITH_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.24.1";
			WITHOUT_ENTRIES_TEMPLATE_ID = "2.16.840.1.113883.3.1818.10.2.24";
			CODE = "46467-7";
			CODE_SYSTEM = Constants.CodeSystems.LOINC_OID;
			CODE_SYSTEM_NAME = Constants.CodeSystems.LOINC_NAME;
			ENTRY_TEMPLATE_ID = Constants.TemplateOids.RISK_FACTORS_ORGANIZER_TEMPLATE_ID;
			ENTRY_NO_TEXT = Constants.SectionSupport.SECTION_SUPPORTED_NO_DATA;
		}

		public static AbstractBodyConstants getConstants() {
			if(bodyConstants == null) {
				bodyConstants = new RiskFactors();
			}
			return bodyConstants;
		}
	}
	 */
}
