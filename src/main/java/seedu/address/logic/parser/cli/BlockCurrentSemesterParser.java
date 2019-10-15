package seedu.address.logic.parser.cli;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new BlockCurrentSemesterCommand object
 */
public class BlockCurrentSemesterParser implements Parser<BlockCurrentSemesterCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the BlockCurrentSemesterCommand
     * and returns an BlockCurrentSemesterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BlockCurrentSemesterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEMESTER, PREFIX_MODULE_CODE);
        String[] tokens = args.trim().split(" ");
        String reason = tokens.length >= 3 ? String.join(" ",
                Arrays.copyOfRange(tokens, 3, tokens.length)) : "";
        if (!arePrefixesPresent(argMultimap, PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BlockCurrentSemesterCommand.MESSAGE_USAGE));
        }
        SemesterName semesterName = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());

        return new BlockCurrentSemesterCommand(semesterName, reason);
    }
}
