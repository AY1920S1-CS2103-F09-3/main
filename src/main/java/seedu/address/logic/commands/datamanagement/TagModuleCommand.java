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
 * Adds a tag to a module.
 */
public class TagModuleCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Adds the specified tag to the specified module. "
            + "Parameters: "
            + "MODULE CODE "
            + "TAG_NAME \n"
            + "Example: "
            + "tag CS3230 exchange";

    public static final String MESSAGE_SUCCESS_TAG_ADDED = "A new tag %1$s has been created and added to module %2$s";
    public static final String MESSAGE_SUCCESS = "Tag %1$s has been added to module %2$s";
    public static final String MESSAGE_EXISTING_TAG = "Tag %1$s is already attached to %2$s";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be added";

    private final String tagName;
    private final String moduleCode;
    private boolean newTagCreated = false;

    /**
     * Creates an {@code TagModuleCommand} to add a tag with the given name to the module of the given module code.
     *
     * @param tagName
     */
    public TagModuleCommand(String tagName, String moduleCode) {
        requireAllNonNull(tagName, moduleCode);
        this.tagName = tagName;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Tag toAdd;
        if (!model.activeSpContainsTag(tagName)) {
            toAdd = createNewTag(tagName);
        } else {
            toAdd = model.getTagFromActiveSp(tagName);
            if (toAdd.isDefault()) {
                throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
            }
        }

        boolean added = model.addTagToActiveSp((UserTag) toAdd, moduleCode);

        if (!added) {
            throw new CommandException(String.format(MESSAGE_EXISTING_TAG, toAdd, moduleCode));
        }

        if (newTagCreated) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_TAG_ADDED, toAdd, moduleCode));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, moduleCode));
    }

    /**
     * Creates a new tag with the given tag name and adds it to the {@code UniqueTaglist}
     * @param tagName The name of the tag.
     * @return The tag that was created.
     */
    private UserTag createNewTag(String tagName) {
        UserTag toCreate = new UserTag(tagName);
        newTagCreated = true;
        return toCreate;
    }

}
