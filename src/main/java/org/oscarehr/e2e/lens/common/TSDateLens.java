package org.oscarehr.e2e.lens.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.marc.everest.datatypes.TS;

public class TSDateLens extends AbstractLens<Date, TS> {
	public TSDateLens() {
		this(TS.DAY);
	}

	public TSDateLens(final Integer precision) {
		get = date -> {
			if(date == null) {
				return null;
			}

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			return new TS(calendar, precision);
		};

		put = (date, ts) -> {
			if(ts == null) {
				return null;
			}

			return ts.getDateValue().getTime();
		};
	}
}
