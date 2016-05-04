package org.oscarehr.e2e.util;

import org.apache.log4j.Logger;
import org.marc.everest.formatters.interfaces.IFormatterGraphResult;
import org.marc.everest.formatters.interfaces.IFormatterParseResult;
import org.marc.everest.interfaces.IResultDetail;
import org.marc.everest.interfaces.ResultDetailType;

/**
 * The Class E2EEverestValidator.
 */
public class E2EEverestValidator {
	private static final Logger log = Logger.getLogger(E2EEverestValidator.class.getName());

	E2EEverestValidator() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if graph operation on CDA is valid.
	 *
	 * @param details the details
	 * @return true if valid, false otherwise
	 */
	public static Boolean isValidCDAGraph(final IFormatterGraphResult details) {
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

	/**
	 * Checks if parse operation on CDA is valid.
	 *
	 * @param details the details
	 * @return true if valid, false otherwise
	 */
	public static Boolean isValidCDAParse(final IFormatterParseResult details) {
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
