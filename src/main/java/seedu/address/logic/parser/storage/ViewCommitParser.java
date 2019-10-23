//package seedu.address.logic.parser.storage;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//
//import seedu.address.logic.commands.storage.ViewCommitCommand;
//import seedu.address.logic.parser.Parser;
//import seedu.address.logic.parser.exceptions.ParseException;
//
///**
// * Parses input arguments and creates a new ViewCommitCommand object.
// */
//public class ViewCommitParser implements Parser<ViewCommitCommand> {
//    /**
//     * Parses the given {@code String} of arguments in the context of the
//     * ViewCommitCommand and returns an ViewCommitCommand object for
//     * execution.
//     *
//     * @throws ParseException if the user input does not conform the expected format.
//     */
//    public ViewCommitCommand parse(String args) throws ParseException {
//        String[] commitToken = args.trim().split(".");
//        if (commitToken.length != 2 || !commitToken[0].matches("-?\\d+")
//                || !commitToken[1].matches("-?\\d+")) {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommitCommand.MESSAGE_USAGE));
//        }
//        int studyPlanIndex = Integer.parseInt(commitToken[0]);
//        int commitNumber = Integer.parseInt(commitToken[1]);
//        return new ViewCommitCommand(studyPlanIndex, commitNumber);
//    }
//
//}
