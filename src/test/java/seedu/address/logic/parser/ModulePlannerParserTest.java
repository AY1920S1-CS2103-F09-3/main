package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.RedoCommand;
import seedu.address.logic.commands.cli.UndoCommand;
import seedu.address.model.module.Module;
import seedu.address.model.module.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditModuleDescriptorBuilder;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModuleUtil;

public class ModulePlannerParserTest {

    @Test
    public void parse_add() throws Exception {
        Module module = new ModuleBuilder().build();
        AddModuleCommand command = (AddModuleCommand) Parser.parse(ModuleUtil.getAddModuleCommand(module));
        assertEquals(new AddModuleCommand(module), command);
    }

    @Test
    public void parse_clear() throws Exception {
        assertTrue(Parser.parse(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(Parser.parse(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parse_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) Parser.parse(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parse_edit() throws Exception {
        Module module = new ModuleBuilder().build();
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(module).build();
        EditCommand command = (EditCommand) Parser.parse(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + ModuleUtil.getEditModuleDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parse_exit() throws Exception {
        assertTrue(Parser.parse(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(Parser.parse(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parse_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) Parser.parse(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parse_help() throws Exception {
        assertTrue(Parser.parse(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(Parser.parse(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parse_history() throws Exception {
        assertTrue(Parser.parse(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(Parser.parse(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            Parser.parse("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parse_list() throws Exception {
        assertTrue(Parser.parse(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(Parser.parse(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parse_select() throws Exception {
        SelectCommand command = (SelectCommand) Parser.parse(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parse_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(Parser.parse(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(Parser.parse("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parse_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(Parser.parse(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(Parser.parse("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> Parser.parse(""));
    }

    @Test
    public void parse_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> Parser.parse("unknownCommand"));
    }
}
