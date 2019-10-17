package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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
 * Adds a tag to a module.
 */
public class TagModuleCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Adds the specified tag to the specified module. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE "
            + PREFIX_TAG + "TAG_NAME \n"
            + "Example: "
            + "tag CS3230 t/exchange";

    public static final String MESSAGE_SUCCESS_TAG_ADDED = "New tag created: %1$s \n" + "Tag added to module \n%2$s";
    public static final String MESSAGE_SUCCESS = "Tag added to module \n%1$s";
    public static final String MESSAGE_EXISTING_TAG = "This tag is already attached to this module";

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

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        UniqueTagList uniqueTagList = activeStudyPlan.getTags();

        Tag toAdd = getTagToAdd(uniqueTagList);
        Module module = activeStudyPlan.getModules().get(moduleCode);

        if (moduleContainsTag(module, toAdd)) {
            throw new CommandException(MESSAGE_EXISTING_TAG);
        }

        module.addTag(toAdd);
        model.addToHistory();

        if (newTagCreated) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_TAG_ADDED, toAdd, module), true, false);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, module), true, false);
    }

    private boolean moduleContainsTag(Module module, Tag tag) {
        return module.getTags().contains(tag);
    }

    private Tag getTagToAdd(UniqueTagList uniqueTagList) {
        boolean tagExists = uniqueTagList.containsTagWithName(tagName);
        Tag toAdd;
        if (!tagExists) {
            toAdd = createNewTag(tagName, uniqueTagList);
        } else {
            Tag existingTag = uniqueTagList.getTag(tagName);
            toAdd = existingTag;
        }
        return toAdd;
    }

    /**
     * Creates a new tag with the given tag name and adds it to the {@code UniqueTaglist}
     *
     * @param tagName       The name of the tag.
     * @param uniqueTagList The list that the tag is to be added to.
     * @return The tag that was created.
     */
    private UserTag createNewTag(String tagName, UniqueTagList uniqueTagList) {
        UserTag toCreate = new UserTag(tagName);
        uniqueTagList.addTag(toCreate);
        newTagCreated = true;
        return toCreate;
    }

}
