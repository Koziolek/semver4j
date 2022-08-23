package org.semver4j.internal;

import org.semver4j.SemverException;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.regex.Pattern.compile;
import static org.semver4j.internal.Tokenizers.STRICT;

public class StrictParser {
    private static final Pattern pattern = compile(STRICT);

    public Version parse(String version) {
        Matcher matcher = pattern.matcher(version);

        if (!matcher.matches()) {
            throw new SemverException(format("Version [%s] is not valid semver.", version));
        }

        BigInteger major = new BigInteger(matcher.group(1));
        BigInteger minor = new BigInteger(matcher.group(2));
        BigInteger patch = new BigInteger(matcher.group(3));
        List<String> preRelease = convertToList(matcher.group(4));
        List<String> build = convertToList(matcher.group(5));

        return new Version(major, minor, patch, preRelease, build);
    }

    private List<String> convertToList(String toList) {
        return toList == null ? emptyList() : asList(toList.split("\\."));
    }

    public static class Version {
        private final BigInteger major;
        private final BigInteger minor;
        private final BigInteger patch;
        private final List<String> preRelease;
        private final List<String> build;

        Version(BigInteger major, BigInteger minor, BigInteger patch, List<String> preRelease, List<String> build) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
            this.preRelease = preRelease;
            this.build = build;
        }

        Version(BigInteger major, BigInteger minor, BigInteger patch) {
            this(major, minor, patch, emptyList(), emptyList());
        }

        public BigInteger getMajor() {
            return major;
        }

        public BigInteger getMinor() {
            return minor;
        }

        public BigInteger getPatch() {
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
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Version that = (Version) o;
            return Objects.equals(major, that.major) &&
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
}
