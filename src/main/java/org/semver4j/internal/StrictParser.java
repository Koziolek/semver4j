package org.semver4j.internal;

import org.semver4j.SemverException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.regex.Pattern.compile;
import static org.semver4j.internal.Tokenizers.STRICT;

class StrictParser implements VersionParser {
    private static final Pattern pattern = compile(STRICT);

    public Version parse(String version) {
        Matcher matcher = pattern.matcher(version);

        if (!matcher.matches()) {
            throw new SemverException(format("Version [%s] is not valid semver.", version));
        }

        int major = parseInt(matcher.group(1));
        int minor = parseInt(matcher.group(2));
        int patch = parseInt(matcher.group(3));
        List<String> preRelease = convertToList(matcher.group(4));
        List<String> build = convertToList(matcher.group(5));

        return new Version(major, minor, patch, preRelease, build);
    }

    private List<String> convertToList(String toList) {
        return toList == null ? emptyList() : asList(toList.split("\\."));
    }

}
