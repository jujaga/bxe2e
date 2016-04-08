package org.oscarehr.e2e.lens.header.recordtarget;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class BirthDateLens extends AbstractLens<MutablePair<Demographic, RecordTarget>, MutablePair<Demographic, RecordTarget>> {
	public BirthDateLens() {
		get = source -> {
			Demographic demographic = source.getLeft();

			TS birthDate = new TS();
			if(demographic.getYearOfBirth() != null && demographic.getMonthOfBirth() != null) {
				try {
					if(Integer.parseInt(demographic.getYearOfBirth()) >= 0 &&
							Integer.parseInt(demographic.getMonthOfBirth()) >= 1 &&
							Integer.parseInt(demographic.getMonthOfBirth()) <= 12) {
						Calendar cal = Calendar.getInstance();

						if(demographic.getDateOfBirth() != null &&
								Integer.parseInt(demographic.getDateOfBirth()) >= 1 &&
								Integer.parseInt(demographic.getDateOfBirth()) <= 31) {
							String dob = demographic.getYearOfBirth() + demographic.getMonthOfBirth() + demographic.getDateOfBirth();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							cal.setTime(sdf.parse(dob));
							birthDate.setDateValuePrecision(TS.DAY);
						} else {
							String mob = demographic.getYearOfBirth() + demographic.getMonthOfBirth();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
							cal.setTime(sdf.parse(mob));
							birthDate.setDateValuePrecision(TS.MONTH);
						}

						birthDate.setDateValue(cal);
					} else {
						throw new NumberFormatException();
					}
				} catch (Exception e) {
					birthDate.setNullFlavor(NullFlavor.Other);
				}
			} else {
				birthDate.setNullFlavor(NullFlavor.NoInformation);
			}

			source.getRight().getPatientRole().getPatient().setBirthTime(birthDate);
			return source;
		};

		put = (source, target) -> {
			Demographic demographic = source.getLeft();
			TS birthDate = target.getRight().getPatientRole().getPatient().getBirthTime();

			if(birthDate != null && !birthDate.isNull() && !birthDate.isInvalidDate()) {
				Calendar date = birthDate.getDateValue();

				demographic.setYearOfBirth(Integer.toString(date.get(Calendar.YEAR)));
				if(birthDate.getDateValuePrecision() > TS.YEAR) {
					demographic.setMonthOfBirth(Integer.toString(date.get(Calendar.MONTH)));
					if(birthDate.getDateValuePrecision() > TS.MONTH) {
						demographic.setDateOfBirth(Integer.toString(date.get(Calendar.DATE)));
					}
				}
			}

			source.setLeft(demographic);
			return source;
		};
	}
}
