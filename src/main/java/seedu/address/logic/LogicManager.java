package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ModulePlannerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ModulePlannerParser modulePlannerParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        modulePlannerParser = new ModulePlannerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = modulePlannerParser.parseCommand(commandText);
        commandResult = command.execute(model);
        model.refresh();

        try {
            storage.saveModulePlanner(model.getModulePlanner());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyModulePlanner getModulePlanner() {
        return model.getModulePlanner();
    }

    @Override
    public StudyPlan getActiveStudyPlan() {
        return model.getActiveStudyPlan();
    }

    @Override
    public ObservableList<StudyPlan> getFilteredStudyPlanList() {
        return model.getFilteredStudyPlanList();
    }

    @Override
    public Path getModulePlannerFilePath() {
        return model.getModulePlannerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
