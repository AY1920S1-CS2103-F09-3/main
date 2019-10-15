package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Commits current active study plan with a commit message.
 */
public class CommitStudyPlanEditCommand extends Command {
    public static final String COMMAND_WORD = "commit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits the current active studyPlan."
            + "Parameters: "
            + "commit message \n"
            + "Example: " + COMMAND_WORD + " "
            + "NOC halfyear";

    public static final String MESSAGE_SUCCESS = "study plan commited: %1$s";
    public static final String MESSAGE_DUPLICATE_COMMIT = "This commit already exists in the commit list";

    private final String commitMessage;

    /**
     * Creates a CommitStudyPlanEditCommand to commit with the specified {@code commitMessage}
     */
    public CommitStudyPlanEditCommand(String commitMessage) {
        requireNonNull(commitMessage);
        this.commitMessage = commitMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        //        if (model.hasStudyPlan(toAdd)) {
        //            throw new CommandException(MESSAGE_DUPLICATE_STUDYPLAN);
        //        }

        model.commitActiveStudyPlan(commitMessage);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commitMessage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommitStudyPlanEditCommand // instanceof handles nulls
                && commitMessage.equals(((CommitStudyPlanEditCommand) other).commitMessage));
    }
}
