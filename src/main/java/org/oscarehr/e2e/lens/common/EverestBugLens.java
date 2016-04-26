package org.oscarehr.e2e.lens.common;

public class EverestBugLens extends AbstractLens<String, String> {
	public EverestBugLens() {
		get = source -> {
			String result = source.replaceAll("xsi:nil=\"true\" ", "");
			return result.replaceAll("delimeter", "delimiter");
		};

		put = (source, target) -> {
			return target.replaceAll("delimiter", "delimeter");
		};
	}
}
