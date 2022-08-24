package org.semver4j.internal;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Collections.emptyList;

public class Version {
	private final int major;
	private final int minor;
	private final int patch;
	private final List<String> preRelease;
	private final List<String> build;

	Version(int major, int minor, int patch, List<String> preRelease, List<String> build) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
		this.preRelease = preRelease;
		this.build = build;
	}

	Version(int major, int minor, int patch) {
		this(major, minor, patch, emptyList(), emptyList());
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getPatch() {
		return patch;
	}

	public List<String> getPreRelease() {
		return preRelease;
	}

	public List<String> getBuild() {
		return build;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}

		if(o == null || getClass() != o.getClass()) {
			return false;
		}

		Version that = (Version) o;
		return major == that.major &&
				Objects.equals(minor, that.minor) &&
				Objects.equals(patch, that.patch) &&
				Objects.equals(preRelease, that.preRelease) &&
				Objects.equals(build, that.build);
	}

	@Override
	public int hashCode() {
		return Objects.hash(major, minor, patch, preRelease, build);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Version.class.getSimpleName() + "[", "]")
				.add("major=" + major)
				.add("minor=" + minor)
				.add("patch=" + patch)
				.add("preRelease=" + preRelease)
				.add("build=" + build)
				.toString();
	}
}
