package org.oscarehr.e2e.lens.header.recordtarget;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.AbstractLens;

class BirthDateLens extends AbstractLens<Demographic, TS> {
	BirthDateLens() {
		get = demographic -> {
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

			return birthDate;
		};

		// TODO Put Function
	}
}
