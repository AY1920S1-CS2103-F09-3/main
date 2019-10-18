package seedu.address.logic.commands.verification;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

/**
 * Finds a list of all valid modules that can be taken in a semester, after checking for prerequisites.
 */
public class ValidModsCommand extends Command {

    public static final String COMMAND_WORD = "validmods";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all valid modules that can be taken in a given semester.\n"
            + "Parameters: SEMESTER (must be a valid semester)\n"
            + "Example: " + COMMAND_WORD + " y1s1";

    private final SemesterName semName;

    public ValidModsCommand(SemesterName semName) {
        this.semName = semName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<String> validMods = model.getValidMods(this.semName);
        return new CommandResult(String.join("\n", validMods));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ValidModsCommand)) {
            return false;
        }

        // state check
        ValidModsCommand e = (ValidModsCommand) other;
        return semName.equals(e.semName);
    }
}
