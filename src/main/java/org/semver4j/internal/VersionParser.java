package org.semver4j.internal;

import org.semver4j.Semver;

public interface VersionParser {

	static VersionParser getImplementation(Semver.SemverType type) {
		switch(type) {
			default:
				return new StrictParser();
		}
	}

	Version parse(String version);
}
