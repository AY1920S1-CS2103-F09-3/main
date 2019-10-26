package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModulesInfo;
import seedu.address.ui.ResultViewType;

public class ViewTaggedCommandTest {

    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewTaggedCommand("exchange", "core", null));
        assertThrows(NullPointerException.class, () -> new ViewTaggedCommand( "core", null));
    }

    @Test
    public void execute_oneInputTagPresentInSomeModules_modulesShownSuccessful() {
        // construct user tag
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct two modules with the validTagOne and another with ValidTagTwo
        Module cs1231 = new ModuleBuilder().withTags(validTagNameOne).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagNameOne).build();
        Module cs3230 = new ModuleBuilder().withModuleCode("CS3230").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231", cs1231);
        moduleHashMap.put("CS2100", cs2100);
        moduleHashMap.put("CS3230", cs3230);

        // construct model containing study plan with the user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne).withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct list of modules that should be shown
        UniqueModuleList expectedList = new UniqueModuleList();
        expectedList.add(cs1231);
        expectedList.add(cs2100);

        CommandResult expectedCommandResult = new CommandResult(ViewTaggedCommand.MESSAGE_SUCCESS,
                ResultViewType.MODULE, expectedList.asUnmodifiableObservableList());

        // construct command to view all modules with the user tag
        ViewTaggedCommand viewTaggedCommand = new ViewTaggedCommand(validTagNameOne);
        assertCommandSuccess(viewTaggedCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_multipleInputsTagsPresentInSomeModules_modulesShownSuccessful() {
        // construct user tag
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct two modules, one with both user tags and the other with only one
        Module cs1231 = new ModuleBuilder().withTags(validTagNameOne, validTagNameTwo).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagNameOne).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231", cs1231);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with the user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // case 1
        // construct list of modules that should be shown
        UniqueModuleList expectedListOne = new UniqueModuleList();
        expectedListOne.add(cs1231);
        ViewTaggedCommand viewTaggedCommandOne = new ViewTaggedCommand(validTagNameOne, validTagNameTwo);
        CommandResult expectedCommandResultOne = new CommandResult(ViewTaggedCommand.MESSAGE_SUCCESS,
                ResultViewType.MODULE, expectedListOne.asUnmodifiableObservableList());
        assertCommandSuccess(viewTaggedCommandOne, model, expectedCommandResultOne, model);

        // case 2
        // construct list of modules that should be shown
        UniqueModuleList expectedListTwo = new UniqueModuleList();
        expectedListTwo.add(cs1231);
        ViewTaggedCommand viewTaggedCommandTwo = new ViewTaggedCommand(validTagNameTwo);
        CommandResult expectedCommandResultTwo = new CommandResult(ViewTaggedCommand.MESSAGE_SUCCESS,
                ResultViewType.MODULE, expectedListTwo.asUnmodifiableObservableList());
        assertCommandSuccess(viewTaggedCommandTwo, model, expectedCommandResultTwo, model);
    }

    @Test
    public void execute_oneInputTagNotPresentInAnyModules_throwsCommandException() {
        // construct user tag
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct two modules with no tags
        Module cs1231 = new ModuleBuilder().build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231", cs1231);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with the user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        ViewTaggedCommand viewTaggedCommand = new ViewTaggedCommand(validTagNameOne);

        assertThrows(CommandException.class, () -> viewTaggedCommand.execute(model),
                ViewTaggedCommand.MESSAGE_NO_MODULES_FOUND);

    }

    @Test
    public void execute_multipleInputsTagsNotPresentInAnyModules_throwsCommandException() {
        // construct user tag
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct two modules with a different tag each
        Module cs1231 = new ModuleBuilder().withTags(validTagNameOne).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagNameTwo).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231", cs1231);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with the user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        ViewTaggedCommand viewTaggedCommand = new ViewTaggedCommand(validTagNameOne, validTagNameTwo);

        assertThrows(CommandException.class, () -> viewTaggedCommand.execute(model),
                ViewTaggedCommand.MESSAGE_NO_MODULES_FOUND);
    }

    @Test
    public void equals() {
        ViewTaggedCommand viewTaggedCommand =
                new ViewTaggedCommand("tagOne", "tagTwo");
        ViewTaggedCommand viewOtherTaggedCommand =
                new ViewTaggedCommand("tagOne", "tagTwo", "tagThree");

        // same object -> returns true
        assertTrue(viewTaggedCommand.equals(viewTaggedCommand));

        // same values -> returns true
        ViewTaggedCommand viewTaggedCommandCopy =
                new ViewTaggedCommand("tagOne", "tagTwo");
        assertTrue(viewTaggedCommand.equals(viewTaggedCommandCopy));

        // same values, different order -> returns true
        ViewTaggedCommand viewTaggedCommandOtherOrder =
                new ViewTaggedCommand("tagTwo", "tagOne");
        assertTrue(viewTaggedCommand.equals(viewTaggedCommandOtherOrder));

        // different types -> returns false
        assertFalse(viewTaggedCommand.equals(1));

        // null -> returns false
        assertFalse(viewTaggedCommand.equals(null));

        // different tags -> returns false
        assertFalse(viewTaggedCommand.equals(viewOtherTaggedCommand));

    }

}
