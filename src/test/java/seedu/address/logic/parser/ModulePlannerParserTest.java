package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.RedoCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UndoCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.commands.storage.ViewCommitHistoryCommand;
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

public class ModulePlannerParserTest {

    private static final String VALID_MODULE_CODE = "CS3244";
    private static final String VALID_SEMESTER = "Y1S1";
    private static final SemesterName VALID_SEMESTER_NAME = SemesterName.Y1S1;
    private static final String VALID_TAG = "cool";
    private final ModulePlannerParser parser = new ModulePlannerParser();

    // =================== CLI ===================

    @Test
    public void parseCommand_add() throws Exception {
        AddModuleCommand command = (AddModuleCommand) parser.parseCommand(
                AddModuleCommand.COMMAND_WORD + " " + VALID_MODULE_CODE + " " + VALID_SEMESTER);
        assertEquals(new AddModuleCommand(VALID_MODULE_CODE, VALID_SEMESTER_NAME), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteModuleCommand command = (DeleteModuleCommand) parser.parseCommand(
                DeleteModuleCommand.COMMAND_WORD + " " + VALID_SEMESTER + " " + VALID_MODULE_CODE);
        assertEquals(new DeleteModuleCommand(VALID_MODULE_CODE, VALID_SEMESTER_NAME), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_setCurrentSemester() throws Exception {
        SetCurrentSemesterCommand command = new SetCurrentSemesterCommand(VALID_SEMESTER_NAME);
        assertEquals((SetCurrentSemesterCommand)
                parser.parseCommand(SetCurrentSemesterCommand.COMMAND_WORD + " " + VALID_SEMESTER), command);
    }

    // =================== TAG ===================


    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    // =================== GUI ===================

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    // =================== VERIFICATION ===================
    @Test
    public void parseCommand_description() throws Exception {
        DescriptionCommand command = new DescriptionCommand(VALID_MODULE_CODE);
        assertEquals(parser.parseCommand(DescriptionCommand.COMMAND_WORD + " " + VALID_MODULE_CODE), command);
    }

    @Test
    public void parseCommand_validMods() throws Exception {
        ValidModsCommand command = new ValidModsCommand(VALID_SEMESTER_NAME);
        assertEquals(parser.parseCommand(ValidModsCommand.COMMAND_WORD + " " + VALID_SEMESTER), command);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(ViewCommitHistoryCommand.COMMAND_WORD) instanceof ViewCommitHistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListAllStudyPlansCommand.COMMAND_WORD) instanceof ListAllStudyPlansCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
