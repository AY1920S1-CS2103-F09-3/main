package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;

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
            + "remove m/CS3230 t/exchange";

    public static final String MESSAGE_SUCCESS = "Tag %1$s removed from %2$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "The module %1$s does not have the tag %2$s";
    public static final String MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION = "Default tags cannot be removed";

    private final String tagName;
    private final String moduleCode;

    /**
     * <<<<<<< HEAD:src/main/java/seedu/address/logic/commands/datamanagement/RemoveTagFromModuleCommand.java
     * Creates an {@code RemoveTagFromModuleCommand} to move a tag with the given name from the specified module.
     *
     * @param tagName    The name of the tag.
     *                   =======
     *                   Creates an {@code RemoveTagCommand} to move a tag with the given name from the specified module.
     * @param tagName    The name of the tag.
     *                   >>>>>>> master:src/main/java/seedu/address/logic/commands/datamanagement/RemoveTagCommand.java
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

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        HashMap<String, Module> moduleHashMap = activeStudyPlan.getModules();
        Module targetModule = moduleHashMap.get(moduleCode);

        boolean matches = checkMatch(targetModule, tagName);
        if (!matches) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, targetModule, "[" + tagName + "]"));
        }

        UniqueTagList uniqueTagList = activeStudyPlan.getTags();
        Tag toDelete = uniqueTagList.getTag(tagName);
        if (toDelete.isDefault()) {
            throw new CommandException(MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
        }

        targetModule.getTags().remove(toDelete);
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete, targetModule));
    }

    /**
     * Checks if there are any tags attached to the current module that has the given tag name.
     *
     * @param currentModule The module with an existing list of tags.
     * @param tagName       The name of the tag that is to be checked.
     * @return True if the module has a tag with the given name.
     */
    private boolean checkMatch(Module currentModule, String tagName) {
        UniqueTagList tags = currentModule.getTags();
        for (Tag tag : tags) {
            boolean match = tag.getTagName().equals(tagName);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
