package org.oscarehr.e2e.util;

import org.apache.log4j.Logger;
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.interfaces.IResultDetail;
import org.marc.everest.interfaces.ResultDetailType;

public class E2EEverestValidator {
	private static Logger log = Logger.getLogger(E2EEverestValidator.class.getName());

	E2EEverestValidator() {
		throw new UnsupportedOperationException();
	}

	public static Boolean isValidCDA(IFormatterGraphResult details) {
		Boolean result = true;

		for(IResultDetail dtl : details.getDetails()) {
			if(dtl.getType() == ResultDetailType.INFORMATION) {
				log.info(dtl.getMessage());
			} else if (dtl.getType() == ResultDetailType.WARNING) {
				log.warn(dtl.getMessage());
			} else {
				log.error(dtl.getMessage());
			}

			result = false;
		}

		return result;
	}
}
