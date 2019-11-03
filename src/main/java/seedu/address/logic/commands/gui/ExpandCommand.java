package seedu.address.logic.commands.gui;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

/**
 * Expands a given semester.
 */
public class ExpandCommand extends Command {
    public static final String COMMAND_WORD = "expand";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Expanding the indicated semester";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the given semester to be expanded.\n"
            + "Parameters: "
            + "SEMESTER\n";

    public static final String MESSAGE_SUCCESS = "Semester %1$s has been expanded.";

    private final SemesterName sem;

    public ExpandCommand(SemesterName sem) {
        requireNonNull(sem);
        this.sem = sem;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.getSemester(sem).setExpanded(true);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sem), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpandCommand // instanceof handles nulls
                && sem.equals(((ExpandCommand) other).sem));
    }
}
