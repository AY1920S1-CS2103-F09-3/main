package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.UserTag;

/**
 * Deletes a tag completely from the study plan.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Deletes the tag with the specified tag name "
        + "Parameters: "
        + PREFIX_TAG + "TAG_NAME \n"
        + "Example: "
        + "delete t/exchange";

    public static final String MESSAGE_SUCCESS = "Tag %1$s has been deleted";
    public static final String MESSAGE_TAG_NOT_FOUND = "There is no [%1$s] tag in this study plan";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be deleted";

    private final String tagName;

    /**
     * Creates an {@code DeleteTagCommand} to delete the tag with the given name.
     * @param tagName
     */
    public DeleteTagCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!UserTag.isValidTagName(tagName)) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        if (!model.activeSpContainsTag(tagName)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagName));
        }

        UserTag toDelete = (UserTag) model.getTagFromActiveSp(tagName);

        model.deleteTagFromActiveSp(toDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

}
