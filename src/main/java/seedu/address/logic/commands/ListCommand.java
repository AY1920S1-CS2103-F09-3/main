package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDY_PLANS;

import seedu.address.model.Model;

/**
 * Lists all study plans in the module planner to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all study plans";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
