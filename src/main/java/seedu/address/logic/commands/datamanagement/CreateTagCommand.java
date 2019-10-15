package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;

/**
 * Creates a new tag.
 */
public class CreateTagCommand extends Command {

    public static final String COMMAND_WORD = "newtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Creates a new tag type. "
        + "Parameters: "
        + PREFIX_TAG + "TAG_NAME \n"
        + "Example: "
        + "newtag t/exchange";

    public static final String MESSAGE_SUCCESS = "New tag created: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists";
    public static final String MESSAGE_INVALID_TAG_NAME = UserTag.MESSAGE_CONSTRAINTS;

    private final String tagName;

    /**
     * Creates an {@code CreateTagCommand} to create a tag with the given name.
     * @param tagName
     */
    public CreateTagCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!UserTag.isValidTagName(tagName)) {
            throw new CommandException(MESSAGE_INVALID_TAG_NAME);
        }

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        UniqueTagList uniqueTagList = activeStudyPlan.getTags();

        if (uniqueTagList.containsTagWithName(tagName)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        UserTag toCreate = new UserTag(tagName);
        uniqueTagList.addTag(toCreate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toCreate));
    }

}
