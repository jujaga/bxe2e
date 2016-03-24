package org.oscarehr.e2e.constant;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.bidimap.UnmodifiableBidiMap;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PQ;
import org.marc.everest.datatypes.SetOperator;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.DomainTimingEvent;
import org.marc.everest.datatypes.generic.EIVL;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.PIVL;
import org.marc.everest.datatypes.interfaces.ISetComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.AdministrativeGender;
import org.oscarehr.e2e.constant.Constants.IssueCodes;

public class Mappings {
	Mappings() {
		throw new UnsupportedOperationException();
	}

	public static final BidiMap<String, AdministrativeGender> genderCode;
	static {
		BidiMap<String, AdministrativeGender> map = new DualHashBidiMap<>();
		map.put(Constants.DocumentHeader.MALE_ADMINISTRATIVE_GENDER_CODE, AdministrativeGender.Male);
		map.put(Constants.DocumentHeader.FEMALE_ADMINISTRATIVE_GENDER_CODE, AdministrativeGender.Female);
		map.put(Constants.DocumentHeader.UNDIFFERENTIATED_ADMINISTRATIVE_GENDER_CODE, AdministrativeGender.Undifferentiated);
		genderCode = UnmodifiableBidiMap.unmodifiableBidiMap(map);
	}

	public static final Map<String, String> genderDescription;
	static {
		Map<String, String> map = new HashMap<>();
		map.put(Constants.DocumentHeader.MALE_ADMINISTRATIVE_GENDER_CODE, Constants.DocumentHeader.MALE_ADMINISTRATIVE_GENDER_DESCRIPTION);
		map.put(Constants.DocumentHeader.FEMALE_ADMINISTRATIVE_GENDER_CODE, Constants.DocumentHeader.FEMALE_ADMINISTRATIVE_GENDER_DESCRIPTION);
		map.put(Constants.DocumentHeader.UNDIFFERENTIATED_ADMINISTRATIVE_GENDER_CODE, Constants.DocumentHeader.UNDIFFERENTIATED_ADMINISTRATIVE_GENDER_DESCRIPTION);
		genderDescription = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> languageCode;
	static {
		Map<String, String> map = new HashMap<>();
		map.put(Constants.DocumentHeader.HUMANLANGUAGE_ENGLISH_DESCRIPTION, Constants.DocumentHeader.HUMANLANGUAGE_ENGLISH_CODE);
		map.put(Constants.DocumentHeader.HUMANLANGUAGE_FRENCH_DESCRIPTION, Constants.DocumentHeader.HUMANLANGUAGE_FRENCH_CODE);
		languageCode = Collections.unmodifiableMap(map);
	}

	public static final Map<Integer, String> reactionTypeCode;
	static {
		Map<Integer, String> map = new HashMap<>();
		map.put(8, Constants.ReactionTypeCode.DALG.toString());
		map.put(10, Constants.ReactionTypeCode.DALG.toString());
		map.put(11, Constants.ReactionTypeCode.DALG.toString());
		map.put(12, Constants.ReactionTypeCode.ALG.toString());
		map.put(13, Constants.ReactionTypeCode.DALG.toString());
		map.put(14, Constants.ReactionTypeCode.ALG.toString());
		reactionTypeCode = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> lifeStageCode;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("N", "133933007");
		map.put("I", "133931009");
		map.put("C", "410601007");
		map.put("T", "133937008");
		map.put("A", "133936004");
		lifeStageCode = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> lifeStageName;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("N", "Newborn");
		map.put("I", "Infant");
		map.put("C", "Child");
		map.put("T", "Adolescent");
		map.put("A", "Adult");
		lifeStageName = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> allergyTestValue;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("1", "A2");
		map.put("2", "A3");
		map.put("3", "A4");
		allergyTestValue = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> allergyTestName;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("1", "mild reaction");
		map.put("2", "moderate reaction");
		map.put("3", "severe reaction");
		allergyTestName = Collections.unmodifiableMap(map);
	}

	// Mock issueId mapping
	public static final Map<IssueCodes, Long> issueId;
	static {
		Map<IssueCodes, Long> map = new HashMap<>();
		map.put(Constants.IssueCodes.OMeds, 65L);
		map.put(Constants.IssueCodes.SocHistory, 66L);
		map.put(Constants.IssueCodes.MedHistory, 67L);
		map.put(Constants.IssueCodes.Concerns, 68L);
		map.put(Constants.IssueCodes.Reminders, 69L);
		map.put(Constants.IssueCodes.FamHistory, 70L);
		map.put(Constants.IssueCodes.RiskFactors, 71L);
		issueId = Collections.unmodifiableMap(map);
	}

	// Mock icd9 description mapping
	public static final Map<String, String> icd9Map;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("428", "HEART FAILURE*");
		map.put("401", "ESSENTIAL HYPERTENSION*");
		map.put("250", "DIABETES MELLITUS*");
		map.put("491", "CHRONIC BRONCHITIS*");
		icd9Map = Collections.unmodifiableMap(map);
	}

	public static final Map <String, String> formCode;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("AEROSOL", Constants.FormCodes.AER.toString());
		map.put("AEROSOL, FOAM", Constants.FormCodes.FOAM.toString());
		map.put("AEROSOL, METERED DOSE", Constants.FormCodes.AER.toString());
		map.put("BAR (CHEWABLE)", Constants.FormCodes.BAR.toString());
		map.put("CAPSULE", Constants.FormCodes.CAP.toString());
		map.put("CAPSULE (CONTROLLED-DELIVERY)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (DELAYED RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (ENTERIC-COATED)", Constants.FormCodes.ENTCAP.toString());
		map.put("CAPSULE (EXTENDED RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (IMMEDIATE AND DELAYED RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (IMMEDIATE AND EXTENDED RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (IMMEDIATE RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CAPSULE (SUSTAINED-RELEASE)", Constants.FormCodes.ERCAP.toString());
		map.put("CREAM", Constants.FormCodes.CRM.toString());
		map.put("DISC", Constants.FormCodes.DISK.toString());
		map.put("DISC (EXTENDED-RELEASE)", Constants.FormCodes.DISK.toString());
		map.put("DOUCHE", Constants.FormCodes.DOUCHE.toString());
		map.put("DROPS", Constants.FormCodes.DROP.toString());
		map.put("ELIXIR", Constants.FormCodes.ELIXIR.toString());
		map.put("ENEMA", Constants.FormCodes.ENEMA.toString());
		map.put("FILM, SOLUBLE", Constants.FormCodes.PATCH.toString());
		map.put("GAS", Constants.FormCodes.GASINHL.toString());
		map.put("GEL", Constants.FormCodes.GEL.toString());
		map.put("GEL (CONTROLLED-RELEASE)", Constants.FormCodes.GELAPL.toString());
		map.put("GRANULES", Constants.FormCodes.GRAN.toString());
		map.put("GRANULES FOR SOLUTION", Constants.FormCodes.GRAN.toString());
		map.put("GRANULES FOR SUSPENSION", Constants.FormCodes.GRAN.toString());
		map.put("GRANULES FOR SUSPENSION, DELAYED RELEASE", Constants.FormCodes.GRAN.toString());
		map.put("GRANULES FOR SUSPENSION,EXTENDED RELEASE", Constants.FormCodes.GRAN.toString());
		map.put("GUM", Constants.FormCodes.GUM.toString());
		map.put("LOTION", Constants.FormCodes.LTN.toString());
		map.put("LOZENGE", Constants.FormCodes.ORTROCHE.toString());
		map.put("METERED-DOSE AEROSOL", Constants.FormCodes.MDINHL.toString());
		map.put("METERED-DOSE PUMP", Constants.FormCodes.MDINHL.toString());
		map.put("MOUTHWASH/GARGLE", Constants.FormCodes.RINSE.toString());
		map.put("OINTMENT", Constants.FormCodes.OINT.toString());
		map.put("PAD", Constants.FormCodes.PAD.toString());
		map.put("PASTE", Constants.FormCodes.PASTE.toString());
		map.put("PATCH", Constants.FormCodes.PATCH.toString());
		map.put("PATCH (EXTENDED RELEASE)", Constants.FormCodes.PATCH.toString());
		map.put("PELLET", Constants.FormCodes.PELLET.toString());
		map.put("PELLET (DENTAL)", Constants.FormCodes.PELLET.toString());
		map.put("PILL", Constants.FormCodes.PILL.toString());
		map.put("POWDER", Constants.FormCodes.POWD.toString());
		map.put("POWDER (EFFERVESCENT)", Constants.FormCodes.POWD.toString());
		map.put("POWDER (ENTERIC-COATED)", Constants.FormCodes.POWD.toString());
		map.put("POWDER (EXTENDED RELEASE)", Constants.FormCodes.POWD.toString());
		map.put("POWDER (METERED DOSE)", Constants.FormCodes.POWD.toString());
		map.put("POWDER FOR SOLUTION", Constants.FormCodes.POWD.toString());
		map.put("POWDER FOR SUSPENSION", Constants.FormCodes.POWD.toString());
		map.put("POWDER FOR SUSPENSION, SUSTAINED-RELEASE", Constants.FormCodes.POWD.toString());
		map.put("RING (SLOW-RELEASE)", Constants.FormCodes.VAGSUPP.toString());
		map.put("SHAMPOO", Constants.FormCodes.SHMP.toString());
		map.put("SOAP BAR", Constants.FormCodes.BARSOAP.toString());
		map.put("SOLUTION", Constants.FormCodes.SOL.toString());
		map.put("SOLUTION (EXTENDED RELEASE)", Constants.FormCodes.SOL.toString());
		map.put("SOLUTION (LONG-ACTING)", Constants.FormCodes.SOL.toString());
		map.put("SPRAY", Constants.FormCodes.SPRYADAPT.toString());
		map.put("SPRAY, BAG-ON-VALVE", Constants.FormCodes.SPRYADAPT.toString());
		map.put("SPRAY, METERED DOSE", Constants.FormCodes.SPRYADAPT.toString());
		map.put("SUPPOSITORY", Constants.FormCodes.SUPP.toString());
		map.put("SUPPOSITORY (SUSTAINED-RELEASE)", Constants.FormCodes.SUPP.toString());
		map.put("SUSPENSION", Constants.FormCodes.SUSP.toString());
		map.put("SUSPENSION (EXTENDED-RELEASE)", Constants.FormCodes.ERSUSP.toString());
		map.put("SUSPENSION (LENTE)", Constants.FormCodes.ERSUSP.toString());
		map.put("SUSPENSION (ULTRA-LENTE)", Constants.FormCodes.ERSUSP.toString());
		map.put("SWAB", Constants.FormCodes.SWAB.toString());
		map.put("SYRUP", Constants.FormCodes.SYRUP.toString());
		map.put("SYRUP (EXTENDED-RELEASE)", Constants.FormCodes.SYRUP.toString());
		map.put("TABLET", Constants.FormCodes.TAB.toString());
		map.put("TABLET (CHEWABLE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (COMBINED RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (DELAYED AND EXTENDED RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (DELAYED-RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (EFFERVESCENT)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (ENTERIC-COATED)", Constants.FormCodes.ECTAB.toString());
		map.put("TABLET (EXTENDED-RELEASE)", Constants.FormCodes.ERTAB.toString());
		map.put("TABLET (IMMEDIATE AND DELAYED-RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (IMMEDIATE RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET (ORALLY DISINTEGRATING)", Constants.FormCodes.SLTAB.toString());
		map.put("TABLET (SLOW-RELEASE)", Constants.FormCodes.TAB.toString());
		map.put("TABLET FOR SUSPENSION", Constants.FormCodes.TAB.toString());
		map.put("TINCTURE", Constants.FormCodes.TINC.toString());
		map.put("TOOTHPASTE", Constants.FormCodes.TPASTE.toString());
		map.put("VAGINAL TABLET", Constants.FormCodes.VAGTAB.toString());
		map.put("VAGINAL TABLET, EFFERVESCENT", Constants.FormCodes.VAGTAB.toString());
		map.put("WAFER", Constants.FormCodes.WAFER.toString());
		map.put("WIPE", Constants.FormCodes.SWAB.toString());
		formCode = Collections.unmodifiableMap(map);
	}

	public static final Map<String, ISetComponent<TS>> frequencyInterval;
	static {
		Map<String, ISetComponent<TS>> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

		PIVL<TS> onedt = new PIVL<>(null, new PQ(BigDecimal.ONE, Constants.TimeUnit.d.toString()));
		onedt.setOperator(SetOperator.Intersect);
		onedt.setInstitutionSpecified(true);

		PIVL<TS> onehf = new PIVL<>(null, new PQ(BigDecimal.ONE, Constants.TimeUnit.h.toString()));
		onehf.setOperator(SetOperator.Intersect);
		onehf.setInstitutionSpecified(false);

		PIVL<TS> onemot = new PIVL<>(null, new PQ(BigDecimal.ONE, Constants.TimeUnit.mo.toString()));
		onemot.setOperator(SetOperator.Intersect);
		onemot.setInstitutionSpecified(true);

		PIVL<TS> onewkt = new PIVL<>(null, new PQ(BigDecimal.ONE, Constants.TimeUnit.wk.toString()));
		onewkt.setOperator(SetOperator.Intersect);
		onewkt.setInstitutionSpecified(true);

		PIVL<TS> twodt = new PIVL<>(null, new PQ(new BigDecimal(2), Constants.TimeUnit.d.toString()));
		twodt.setOperator(SetOperator.Intersect);
		twodt.setInstitutionSpecified(true);

		PIVL<TS> twohf = new PIVL<>(null, new PQ(new BigDecimal(2), Constants.TimeUnit.h.toString()));
		twohf.setOperator(SetOperator.Intersect);
		twohf.setInstitutionSpecified(false);

		PIVL<TS> twowkt = new PIVL<>(null, new PQ(new BigDecimal(2), Constants.TimeUnit.wk.toString()));
		twowkt.setOperator(SetOperator.Intersect);
		twowkt.setInstitutionSpecified(true);

		PIVL<TS> threehf = new PIVL<>(null, new PQ(new BigDecimal(3), Constants.TimeUnit.h.toString()));
		threehf.setOperator(SetOperator.Intersect);
		threehf.setInstitutionSpecified(false);

		PIVL<TS> threemot = new PIVL<>(null, new PQ(new BigDecimal(3), Constants.TimeUnit.mo.toString()));
		threemot.setOperator(SetOperator.Intersect);
		threemot.setInstitutionSpecified(true);

		PIVL<TS> fourhf = new PIVL<>(null, new PQ(new BigDecimal(4), Constants.TimeUnit.h.toString()));
		fourhf.setOperator(SetOperator.Intersect);
		fourhf.setInstitutionSpecified(false);

		PIVL<TS> sixht = new PIVL<>(null, new PQ(new BigDecimal(6), Constants.TimeUnit.h.toString()));
		sixht.setOperator(SetOperator.Intersect);
		sixht.setInstitutionSpecified(true);

		PIVL<TS> sixhf = new PIVL<>(null, new PQ(new BigDecimal(6), Constants.TimeUnit.h.toString()));
		sixhf.setOperator(SetOperator.Intersect);
		sixhf.setInstitutionSpecified(false);

		PIVL<TS> eightht = new PIVL<>(null, new PQ(new BigDecimal(8), Constants.TimeUnit.h.toString()));
		eightht.setOperator(SetOperator.Intersect);
		eightht.setInstitutionSpecified(true);

		PIVL<TS> eighthf = new PIVL<>(null, new PQ(new BigDecimal(8), Constants.TimeUnit.h.toString()));
		eighthf.setOperator(SetOperator.Intersect);
		eighthf.setInstitutionSpecified(false);

		PIVL<TS> twelveht = new PIVL<>(null, new PQ(new BigDecimal(12), Constants.TimeUnit.h.toString()));
		twelveht.setOperator(SetOperator.Intersect);
		twelveht.setInstitutionSpecified(true);

		PIVL<TS> twelvehf = new PIVL<>(null, new PQ(new BigDecimal(12), Constants.TimeUnit.h.toString()));
		twelvehf.setOperator(SetOperator.Intersect);
		twelvehf.setInstitutionSpecified(false);

		PIVL<TS> twentyfourhf = new PIVL<>(null, new PQ(new BigDecimal(24), Constants.TimeUnit.h.toString()));
		twentyfourhf.setOperator(SetOperator.Intersect);
		twentyfourhf.setInstitutionSpecified(false);

		EIVL<TS> hourOfSleep = new EIVL<>(DomainTimingEvent.HourOfSleep, null, SetOperator.Intersect);
		// TODO [MARC-HI] Notify about error "When the Event property implies before, after or between meals the Offset property must not be populated"
		hourOfSleep.setOffset(new IVL<PQ>() {{setNullFlavor(NullFlavor.NotApplicable);}});

		EIVL<TS> betweenDinnerAndSleep = new EIVL<>(DomainTimingEvent.BetweenDinnerAndSleep, null, SetOperator.Intersect);

		map.put("daily", onedt);
		map.put("once daily", onedt);
		map.put("OD", onedt);
		map.put("QD", onedt);
		map.put("Q1H", onehf);
		map.put("Q1Month", onemot);
		map.put("monthly", onemot);
		map.put("QM", onemot);
		map.put("weekly", onewkt);
		map.put("Q1Week", onewkt);
		map.put("QOD", twodt);
		map.put("Q2H", twohf);
		map.put("Q2Week", twowkt);
		map.put("Q3Month", threemot);
		map.put("Q4H", fourhf);
		map.put("QID", sixht);
		map.put("4x day", sixht);
		map.put("Q6H", sixhf);
		map.put("TID", eightht);
		map.put("3x day", eightht);
		map.put("3x daily", eightht);
		map.put("4x daily", eightht);
		map.put("Q8H", eighthf);
		map.put("BID", twelveht);
		map.put("twice daily", twelveht);
		map.put("Q12H", twelvehf);
		map.put("Q24H", twentyfourhf);

		// TODO [MARC-HI] Wait on answer about IVL based PIVL_TS implementation
		map.put("Q1-2H", onehf);
		map.put("Q3-4H", threehf);
		map.put("Q4-6H", fourhf);

		// TODO [E2E] Resolve QAM to EIVL_TS DomainTimingEvent mapping
		map.put("QAM", onedt);
		map.put("QPM", betweenDinnerAndSleep);
		map.put("QHS", hourOfSleep);
		frequencyInterval = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> measurementCodeMap;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("BMI", "39156-5");
		map.put("BP", "55284-4");
		map.put("DIAS", "8462-4");
		map.put("FEET", "11428-0");
		map.put("HR", "8867-4");
		map.put("HT", "8302-2");
		map.put("PULS", "8867-4");
		map.put("PULSE", "8867-4");
		map.put("SYST", "8480-6");
		map.put("TEMP", "8310-5");
		map.put("WAIS", "56115-9");
		map.put("WT", "3141-9");
		measurementCodeMap = Collections.unmodifiableMap(map);
	}

	// TODO [OSCAR] Replace this mapping with measurementTypeDao
	public static final Map<String, String> measurementTypeMap;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("BMI", "Body Mass Index");
		map.put("BP", "Blood Pressure");
		map.put("DIAS", "Diastolic Blood Pressure");
		map.put("FEET", "Feet Check skin");
		map.put("HR", "Heart Rate");
		map.put("HT", "Height");
		map.put("PULS", "Heart Rate");
		map.put("PULSE", "Heart Rate");
		map.put("SYST", "Systolic Blood Pressure");
		map.put("TEMP", "Temperature");
		map.put("WAIS", "Waist");
		map.put("WT", "Weight");
		measurementTypeMap = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> measurementUnitMap;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("02", "%");
		map.put("02SA", "%");
		map.put("24UA", "mg/24h");
		map.put("24UR", "mg/24h");
		map.put("ACR", "mg/mmol");
		map.put("ALB", "g/l");
		map.put("ALT", "U/L");
		map.put("AST", "U/L");
		map.put("BG", "mmol/L");
		map.put("BILC", "mmol/L");
		map.put("BILT", "mmol/L");
		map.put("BILU", "mmol/L");
		map.put("BMI", "kg/m2");
		map.put("BP", "mm[Hg]");
		map.put("CD4", "x10e9/l");
		map.put("CD4P", "%");
		map.put("Clpl", "mmol/L");
		map.put("COUM", "mg/week");
		map.put("CRCL", "ml/h");
		map.put("DIAS", "mm[Hg]");
		map.put("EGFR", "ml/min");
		map.put("Exer", "min/week");
		map.put("FBPC", "mmol/L");
		map.put("FRAM", "%");
		map.put("FTST", "nmol/L");
		map.put("Hb", "g/L");
		map.put("HDL", "mmol/L");
		map.put("HEAD", "cm");
		map.put("HR", "beats/min");
		map.put("HT", "cm");
		map.put("Kpl", "mmol/L");
		map.put("Napl", "mmol/L");
		map.put("PULS", "beats/min");
		map.put("PULSE", "beats/min");
		map.put("SCR", "umol/L");
		map.put("SYST", "mm[Hg]");
		map.put("TCHL", "mmol/L");
		map.put("TEMP", "C");
		map.put("TRIG", "mmol/L");
		map.put("UA", "mmol/L");
		map.put("UACR", "mg/mmol");
		map.put("UALB", "mg/mmol");
		map.put("VB12", "pmol/L");
		map.put("VLOA", "x10e9/L");
		map.put("WAIS", "cm");
		map.put("WT", "kg");
		measurementUnitMap = Collections.unmodifiableMap(map);
	}

	public static final Map<String, String> personalRelationshipRole;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("aunt", "AUNT");
		map.put("brother", "BRO");
		map.put("brother-in-law", "BROINLAW");
		map.put("adopted child", "CHLDADOPT");
		map.put("foster child", "CHLDFOST");
		map.put("child in-law", "CHLDINLAW");
		map.put("cousin", "COUSN");
		map.put("natural daughter", "DAU");
		map.put("adopted daughter", "DAUADOPT");
		map.put("daughter", "DAUC");
		map.put("foster daughter", "DAUFOST");
		map.put("daughter in-law", "DAUINLAW");
		map.put("emergency contact", "ECON");
		map.put("family member", "FAMMEMB");
		map.put("child", "CHILD");
		map.put("extended family member", "EXT");
		map.put("immediate family", "IMED");
		map.put("parent", "PRN");
		map.put("sibling", "SIB");
		map.put("significant other", "SIGOTHR");
		map.put("domestic partner", "DOMPART");
		map.put("spouse", "SPS");
		map.put("unrelated friend", "FRND");
		map.put("father", "FTH");
		map.put("father-in-law", "FTHINLAW");
		map.put("great grandfather", "GGRFTH");
		map.put("great grandmother", "GGRMTH");
		map.put("great grandparent", "GGRPRN");
		map.put("grandfather", "GRFTH");
		map.put("grandmother", "GRMTH");
		map.put("grandchild", "GRNDCHILD");
		map.put("granddaughter", "GRNDDAU");
		map.put("grandson", "GRNDSON");
		map.put("grandparent", "GRPRN");
		map.put("guardian", "GUARD");
		map.put("half-brother", "HBRO");
		map.put("half-sibling", "HSIB");
		map.put("half-sister", "HSIS");
		map.put("husband", "HUSB");
		map.put("mother", "MTH");
		map.put("mother-in-law", "MTHINLAW");
		map.put("neighbor", "NBOR");
		map.put("natural brother", "NBRO");
		map.put("natural child", "NCHILD");
		map.put("nephew", "NEPHEW");
		map.put("natural father", "NFTH");
		map.put("natural father of fetus", "NFTHF");
		map.put("niece", "NIECE");
		map.put("niece/nephew", "NIENEPH");
		map.put("natural mother", "NMTH");
		map.put("next of kin", "NOK");
		map.put("natural parent", "NPRN");
		map.put("natural sibling", "NSIB");
		map.put("natural sister", "NSIS");
		map.put("power of attorney", "POWATY");
		map.put("power of attorney-personal", "POWATYPR");
		map.put("power of attorney-property", "POWATYPT");
		map.put("parent in-law", "PRNINLAW");
		map.put("roomate", "ROOM");
		map.put("sibling in-law", "SIBINLAW");
		map.put("sister", "SIS");
		map.put("sister-in-law", "SISINLAW");
		map.put("natural son", "SON");
		map.put("adopted son", "SONADOPT");
		map.put("son", "SONC");
		map.put("foster son", "SONFOST");
		map.put("son in-law", "SONINLAW");
		map.put("stepbrother", "STPBRO");
		map.put("step child", "STPCHLD");
		map.put("stepdaughter", "STPDAU");
		map.put("stepfather", "STPFTH");
		map.put("stepmother", "STPMTH");
		map.put("step parent", "STPPRN");
		map.put("step sibling", "STPSIB");
		map.put("stepsister", "STPSIS");
		map.put("stepson", "STPSON");
		map.put("substitute decision maker", "SUBDM");
		map.put("uncle", "UNCLE");
		map.put("wife", "WIFE");
		personalRelationshipRole = Collections.unmodifiableMap(map);
	}
}
