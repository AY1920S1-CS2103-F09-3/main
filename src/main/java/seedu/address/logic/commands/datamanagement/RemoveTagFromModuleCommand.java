package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UserTag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Removes a {@code Tag} from a {@code Module}.
 */
public class RemoveTagFromModuleCommand extends Command {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Removes the specified tag from the specified module "
        + "Parameters: "
        + PREFIX_MODULE_CODE + "MODULE_CODE "
        + PREFIX_TAG + "TAG_NAME \n"
        + "Example: "
        + "removetag m/CS3230 t/exchange";

    public static final String MESSAGE_SUCCESS = "Tag %1$s removed from %2$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "The module %1$s does not have the tag [%2$s]";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be removed";

    private final String tagName;
    private final String moduleCode;

    /**
     * Creates an {@code RemoveTagFromModuleCommand} to move a tag with the given name from the specified module.
     * @param tagName The name of the tag.
     * @param moduleCode The module code of the module from which the tag is to be deleted.
     */

    public RemoveTagFromModuleCommand(String moduleCode, String tagName) {
        requireAllNonNull(tagName, moduleCode);
        this.tagName = tagName;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Tag toRemove;
        try {
            toRemove = model.getTagFromActiveSp(tagName);
        } catch (TagNotFoundException exception) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, moduleCode, tagName));
        }

        if (toRemove.isDefault()) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        boolean removed = model.removeTagFromModuleInActiveSp((UserTag) toRemove, moduleCode);

        if (!removed) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, moduleCode, tagName));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove, moduleCode));
    }

}
