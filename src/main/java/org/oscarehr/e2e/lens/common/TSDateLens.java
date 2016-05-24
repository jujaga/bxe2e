package org.oscarehr.e2e.lens.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.Validate;
import org.marc.everest.datatypes.TS;

public class TSDateLens extends AbstractLens<Date, TS> {
	public TSDateLens() {
		this(TS.DAY);
	}

	public TSDateLens(final Integer precision) {
		Validate.notNull(precision);

		get = date -> {
			TS ts = null;
			if(date != null) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				ts = new TS(calendar, precision);
			}

			return ts;
		};

		put = (date, ts) -> {
			if(ts != null && !ts.isNull() && !ts.isInvalidDate()) {
				date = ts.getDateValue().getTime();
			}

			return date;
		};
	}
}
