package org.semver4j.internal.range.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;
import static org.semver4j.Range.RangeOperator.GTE;
import static org.semver4j.Range.RangeOperator.LT;
import static org.semver4j.internal.Tokenizers.TILDE;
import static org.semver4j.internal.range.processor.RangesUtils.*;

/**
 * <p>Processor for translate <a href="https://github.com/npm/node-semver#tilde-ranges-123-12-1">tilde ranges</a>
 * into classic range.</p>
 * <br>
 * Translates:
 * <ul>
 *     <li>{@code ~1.2.3} to {@code ≥1.2.3 <1.3.0}</li>
 *     <li>{@code ~1.2} to {@code ≥1.2.0 <1.3.0}</li>
 *     <li>{@code ~1} to {@code ≥1.0.0 <2.0.0}</li>
 *     <li>{@code ~0.2.3} to {@code ≥0.2.3 <0.3.0}</li>
 *     <li>{@code ~0.2} to {@code ≥0.2.0 <0.3.0}</li>
 *     <li>{@code ~0} to {@code ≥0.0.0 <1.0.0}</li>
 * </ul>
 */
public class TildeProcessor implements Processor {
    private static final Pattern pattern = compile(TILDE);

    @Override
    public String process(String range) {
        Matcher matcher = pattern.matcher(range);

        if (!matcher.matches()) {
            return range;
        }

        // Left unused variables for brevity.

        String fullRange = matcher.group(0);

        int major = parseIntWithXSupport(matcher.group(1));
        int minor = parseIntWithXSupport(matcher.group(2));
        int path = parseIntWithXSupport(matcher.group(3));
        String preRelease = matcher.group(4);
        String build = matcher.group(5);

        String from;
        String to;

        if (isX(minor)) {
            from = format("%s%d.0.0", GTE.asString(), major);
            to = format("%s%d.0.0", LT.asString(), (major + 1));
        } else if (isX(path)) {
            from = format("%s%d.%d.0", GTE.asString(), major, minor);
            to = format("%s%d.%d.0", LT.asString(), major, (minor + 1));
        } else if (isNotBlank(preRelease)) {
            from = format("%s%d.%d.%d-%s", GTE.asString(), major, minor, path, preRelease);
            to = format("%s%d.%d.0", LT.asString(), major, (minor + 1));
        } else {
            from = format("%s%d.%d.%d", GTE.asString(), major, minor, path);
            to = format("%s%d.%d.0", LT.asString(), major, (minor + 1));
        }

        return format("%s %s", from, to);
    }
}
