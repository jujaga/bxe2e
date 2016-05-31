package org.oscarehr.e2e.lens.header.recordtarget;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class BirthDateLens extends AbstractLens<Pair<Demographic, RecordTarget>, Pair<Demographic, RecordTarget>> {
	public BirthDateLens() {
		get = source -> {
			Demographic demographic = source.getLeft();
			TS birthDate = source.getRight().getPatientRole().getPatient().getBirthTime();

			if(birthDate == null) {
				birthDate = new TS();
				if(!EverestUtils.isNullorEmptyorWhitespace(demographic.getYearOfBirth()) &&
						!EverestUtils.isNullorEmptyorWhitespace(demographic.getMonthOfBirth())) {
					try {
						if(Integer.parseInt(demographic.getYearOfBirth()) >= 0 &&
								Integer.parseInt(demographic.getMonthOfBirth()) >= 1 &&
								Integer.parseInt(demographic.getMonthOfBirth()) <= 12) {
							Calendar cal = Calendar.getInstance();
							String value = demographic.getYearOfBirth().concat(demographic.getMonthOfBirth());
							String format = "yyyyMM";
							Integer precision = TS.MONTH;

							if(!EverestUtils.isNullorEmptyorWhitespace(demographic.getDateOfBirth()) &&
									Integer.parseInt(demographic.getDateOfBirth()) >= 1 &&
									Integer.parseInt(demographic.getDateOfBirth()) <= 31) {
								value = value.concat(demographic.getDateOfBirth());
								format = "yyyyMMdd";
								precision = TS.DAY;
							}

							cal.setTime(new SimpleDateFormat(format).parse(value));
							birthDate.setDateValue(cal);
							birthDate.setDateValuePrecision(precision);
						} else {
							throw new NumberFormatException();
						}
					} catch (Exception e) {
						birthDate.setNullFlavor(NullFlavor.Other);
					}
				} else {
					birthDate.setNullFlavor(NullFlavor.NoInformation);
				}
			}

			source.getRight().getPatientRole().getPatient().setBirthTime(birthDate);
			return new ImmutablePair<>(demographic, source.getRight());
		};

		put = (source, target) -> {
			Demographic demographic = target.getLeft();
			TS birthDate = target.getRight().getPatientRole().getPatient().getBirthTime();

			if(birthDate != null && !birthDate.isNull() && !birthDate.isInvalidDate()) {
				Calendar date = birthDate.getDateValue();

				demographic.setYearOfBirth(Integer.toString(date.get(Calendar.YEAR)));
				if(birthDate.getDateValuePrecision() > TS.YEAR) {
					String month = StringUtils.leftPad(Integer.toString(date.get(Calendar.MONTH) + 1), 2, '0');
					demographic.setMonthOfBirth(month);
					if(birthDate.getDateValuePrecision() > TS.MONTH) {
						demographic.setDateOfBirth(Integer.toString(date.get(Calendar.DATE)));
					}
				}
			}

			return new ImmutablePair<>(demographic, target.getRight());
		};
	}
}
