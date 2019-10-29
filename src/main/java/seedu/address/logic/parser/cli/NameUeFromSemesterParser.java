package seedu.address.logic.parser.cli;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEMESTER_PATTERN;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.cli.NameUeFromSemesterCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new NameUEFromSemesterCommand object
 */
public class NameUeFromSemesterParser implements Parser<NameUeFromSemesterCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Pattern... patterns) {
        return Stream.of(patterns).allMatch(pattern -> argumentMultimap.getValue(pattern).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the NameUEFromSemesterCommand
     * and returns an NameUEFromSemesterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NameUeFromSemesterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, SEMESTER_PATTERN);
        String[] tokens = args.trim().split(" ");
        if (!arePrefixesPresent(argMultimap, SEMESTER_PATTERN)
                || tokens.length != 2
                || tokens[1].isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NameUeFromSemesterCommand.MESSAGE_USAGE));
        }
        SemesterName semester = ParserUtil.parseSemester(argMultimap.getValue(SEMESTER_PATTERN).get());
        String ueName = tokens[1].trim();
        return new NameUeFromSemesterCommand(ueName, semester);
    }

}