package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a command for the user to view all study plans.
 */
public class ListAllStudyPlansCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Here is a list of all the study plans you've created:\n";
    public static final String MESSAGE_NO_STUDYPLAN = "You don't have any study plan yet! Go create one now!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<StudyPlan> studyPlans = model.getFilteredStudyPlanList();

        if (studyPlans == null || studyPlans.size() == 0) {
            return new CommandResult(MESSAGE_NO_STUDYPLAN);
        }

        StringBuilder toReturn = new StringBuilder(MESSAGE_SUCCESS);
        Iterator<StudyPlan> studyPlanIterator = studyPlans.iterator();
        while (studyPlanIterator.hasNext()) {
            StudyPlan studyPlan = studyPlanIterator.next();
            toReturn.append(studyPlan.toString() + "\n");
        }
        return new CommandResult(toReturn.toString());
    }
}
