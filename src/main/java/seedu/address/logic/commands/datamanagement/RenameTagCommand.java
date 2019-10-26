package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UserTag;

/**
 * Renames a tag
 */
public class RenameTagCommand extends Command {

    // not in parser yet

    public static final String COMMAND_WORD = "renametag";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Renaming an existing tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Renames the tag with the specified original name "
            + "with the specified new name. "
            + "Parameters: "
            + "ORIGINAL_TAG_NAME "
            + "NEW_TAG_NAME \n"
            + "Example: "
            + "rename t/exchange t/SEP";

    public static final String MESSAGE_SUCCESS = "Tag [%1$s] renamed to %2$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "There is no [%1$s] tag in this study plan";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be renamed";

    private final String originalTagName;
    private final String newTagName;

    /**
     * Creates an {@code RenameTagCommand} to rename a tag with the given original name to the given new name.
     *
     * @param originalTagName The original name of the tag.
     * @param newTagName      The new name of the tag.
     */
    public RenameTagCommand(String originalTagName, String newTagName) {
        requireAllNonNull(originalTagName, newTagName);
        this.originalTagName = originalTagName;
        this.newTagName = newTagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean tagExists = model.activeSpContainsTag(originalTagName);
        if (!tagExists) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, originalTagName));
        }

        Tag targetTag = model.getTagFromActiveSp(originalTagName);
        if (targetTag.isDefault()) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        UserTag toRename = (UserTag) targetTag;

        toRename.rename(newTagName);
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, originalTagName, toRename));
    }

}
