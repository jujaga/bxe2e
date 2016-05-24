package org.oscarehr.e2e.lens.common;

import org.apache.commons.lang3.Validate;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Constants.IdPrefixes;

public class EntryIdLens extends AbstractLens<Integer, SET<II>> {
	public EntryIdLens(final IdPrefixes prefix) {
		Validate.notNull(prefix);

		get = value -> {
			if(value == null) {
				value = 0;
			}

			StringBuilder sb = new StringBuilder();
			sb.append(prefix).append("-").append(value.toString());

			II ii = new II(Constants.EMR.EMR_OID, sb.toString());
			ii.setAssigningAuthorityName(Constants.EMR.EMR_VERSION);
			return new SET<>(ii);
		};

		put = (value, id) -> {
			if(value == null) {
				try {
					II ii = id.get(0);
					if(ii != null && !ii.isNull() && ii.getRoot().equals(Constants.EMR.EMR_OID)
							&& ii.getExtension().startsWith(prefix.toString())) {
						value = Integer.parseInt(ii.getExtension().replaceAll("[\\D]", ""));
					} else {
						value = 0;
					}
				} catch (Exception e) {
					value = 0;
				}
			}

			return value;
		};
	}
}
