package org.semver4j.internal;

import org.semver4j.Semver;
import org.semver4j.Semver.VersionDiff;

import java.util.Objects;

import static org.semver4j.Semver.VersionDiff.*;

public class Differ {
    private final Semver version;

    public Differ(Semver version) {
        this.version = version;
    }

    public VersionDiff diff(Semver other) {
        if (!version.getMajor().equals(other.getMajor())) {
            return MAJOR;
        }
        if (!Objects.equals(version.getMinor(), other.getMinor())) {
            return MINOR;
        }
        if (!Objects.equals(version.getPatch(), other.getPatch())) {
            return PATCH;
        }
        if (!version.getPreRelease().equals(other.getPreRelease())) {
            return SUFFIX;
        }
        if (!version.getBuild().equals(other.getBuild())) {
            return BUILD;
        }
        return NONE;
    }
}
