package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_SEMESTER = "Semester is not valid.";
    public static final String MESSAGE_INVALID_MODULE = "Module specified is not valid.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code semester} into an {@code SemesterName} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if specified semester is not valid.
     */
    public static SemesterName parseSemester(String semester) throws ParseException {
        String trimmedSemester = semester.trim().toUpperCase();
        if (!trimmedSemester.matches("Y\\dS\\w?\\d")) {
            throw new ParseException(MESSAGE_INVALID_SEMESTER);
        }
        int year = Character.getNumericValue(trimmedSemester.charAt(1));
        int sem = Character.getNumericValue(trimmedSemester.charAt(trimmedSemester.length() - 1));
        if (trimmedSemester.length() == 4) {
            return SemesterName.getEnum(year, sem);
        } else {
            return SemesterName.getSpecialTermEnum(year, sem);
        }
    }

    /**
     * Checks whether or not the module is valid.
     *
     * @throws ParseException if specified module is not valid.
     */
    public static String parseModule(String module) throws ParseException {
        // TODO: Check module against megaList to ensure that it is valid
        return module.toUpperCase().trim();
    }

    /**
     * Parses a raw {@code String tagName} into a formatted tagName.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tagName} is invalid.
     */
    public static String parseTag(String tagName) throws ParseException {
        requireNonNull(tagName);
        String trimmedTagName = tagName.trim();
        if (!Tag.isValidTagName(trimmedTagName)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return trimmedTagName;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<String> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<String> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
