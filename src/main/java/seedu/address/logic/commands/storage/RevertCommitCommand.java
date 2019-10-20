package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a command for the user to revert to the version of the current active study plan for a particular commit.
 * This discards all the commits after the specified commit.
 */
public class RevertCommitCommand extends Command {

    public static final String COMMAND_WORD = "revert";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverts the study plan to the commit identified by the index used in the displayed commit list.\n"
            + "Parameters: PLAN_INDEX.COMMIT_NUMBER (both must be non-negative integers)\n";
    public static final String MESSAGE_SUCCESS = "Here's your study plan for this commit. All later commits discarded";
    public static final String MESSAGE_NOT_ACTIVE_STUDY_PLAN = "The study plan index must be the active one!";
    public static final String MESSAGE_NO_SUCH_COMMIT = "The commit index you've entered is invalid!";
    public static final String MESSAGE_NO_MORE_STUDYPLAN = "You don't have any study plan currently. Create now!";

    private int studyPlanIndex;
    private int commitNumber;

    public RevertCommitCommand(int studyPlanIndex, int commitNumber) {
        this.studyPlanIndex = studyPlanIndex;
        this.commitNumber = commitNumber;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();

        // if the current active study plan is null
        if (activeStudyPlan == null) {
            return new CommandResult(MESSAGE_NO_MORE_STUDYPLAN);
        }

        // if the index entered is not the current active study plan
        if (studyPlanIndex != activeStudyPlan.getIndex()) {
            return new CommandResult(MESSAGE_NOT_ACTIVE_STUDY_PLAN);
        }

        try {
            // else, activate the commit and make it the current active study plan.
            model.revertToCommit(studyPlanIndex, commitNumber);
            return new CommandResult(MESSAGE_SUCCESS, true, false);
        } catch (IndexOutOfBoundsException e) {
            return new CommandResult(MESSAGE_NO_SUCH_COMMIT);
        }
    }
}
