package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModulesInfo;

public class RemoveTagFromAllCommandTest {

    private Tag validTagOne;
    private String validTagNameOne;

    @BeforeEach
    public void setUp() {
        // construct user tag
        validTagOne = new TagBuilder().buildTestUserTag();
        validTagNameOne = validTagOne.getTagName();
    }

    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveTagFromAllCommand(null));
    }

    @Test
    public void execute_tagPresentInStudyPlanAndModules_removeSuccessful() {
        // construct two modules with the user tag and one module without
        Module cs1231s = new ModuleBuilder().withTags(validTagOne).build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").withTags(validTagOne).build();
        Module cs3230 = new ModuleBuilder().withModuleCode("CS3230").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);
        moduleHashMap.put("CS2100", cs2100);
        moduleHashMap.put("CS3230", cs3230);

        // construct model containing study plan with the user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct three expected modules with no user tags
        Module expectedCS1231S = new ModuleBuilder().build();
        Module expectedCS2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        Module expectedCS3230 = new ModuleBuilder().withModuleCode("CS3230").build();
        HashMap<String, Module> expectedModuleHashMap = new HashMap<String, Module>();
        expectedModuleHashMap.put("CS1231S", expectedCS1231S);
        expectedModuleHashMap.put("CS2100", expectedCS2100);
        expectedModuleHashMap.put("CS3230", expectedCS3230);

        // construct expected model containing study plan with the user tag and expected modules
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withModuleTags(validTagOne)
                .withModules(expectedModuleHashMap).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(studyPlan);
        expectedModel.addStudyPlan(expectedStudyPlan);
        expectedModel.addToHistory();

        // construct command to remove the user tag from all modules
        RemoveTagFromAllCommand removeTagFromAllCommand = new RemoveTagFromAllCommand(validTagNameOne);
        assertCommandSuccess(removeTagFromAllCommand, model,
                String.format(RemoveTagFromAllCommand.MESSAGE_SUCCESS, validTagOne), expectedModel);
    }

    @Test
    public void execute_tagNotPresentInStudyPlan_throwsCommandException() {
        // construct model containing study plan with no user tags
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to remove the user tag from all modules
        RemoveTagFromAllCommand removeTagFromAllCommand = new RemoveTagFromAllCommand(validTagNameOne);
        assertThrows(CommandException.class, () -> removeTagFromAllCommand.execute(model),
                RemoveTagFromAllCommand.MESSAGE_TAG_NOT_FOUND_IN_STUDY_PLAN);
    }

    @Test
    public void execute_tagNotInAnyModule_throwsCommandException() {
        // construct two modules with no user tags
        Module cs1231s = new ModuleBuilder().build();
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);
        moduleHashMap.put("CS2100", cs2100);

        // construct model containing study plan with the user tag
        StudyPlan studyPlan = new StudyPlanBuilder().withModuleTags(validTagOne)
                .withModules(moduleHashMap).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to remove the user tag from all modules
        RemoveTagFromAllCommand removeTagFromAllCommand = new RemoveTagFromAllCommand(validTagNameOne);
        assertThrows(CommandException.class, () -> removeTagFromAllCommand.execute(model),
                RemoveTagFromAllCommand.MESSAGE_TAG_NOT_FOUND_IN_MODULES);
    }

    @Test
    public void execute_removeDefaultTag_throwsCommandException() {
        String validDefaultTag = new TagBuilder().buildDefaultCoreTag().getTagName();

        // construct model containing study plan
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to remove the default tag from all modules
        RemoveTagFromAllCommand removeTagFromAllCommand = new RemoveTagFromAllCommand(validDefaultTag);
        assertThrows(CommandException.class, () -> removeTagFromAllCommand.execute(model),
                RemoveTagFromAllCommand.MESSAGE_INVALID_DEFAULT_TAG_MODIFICATION);
    }

    @Test
    public void equals() {
        RemoveTagFromAllCommand removeTagFromAllCommand = new RemoveTagFromAllCommand("testUserTag");
        RemoveTagFromAllCommand removeOtherTagFromAllCommand = new RemoveTagFromAllCommand("otherUserTag");

        // same object -> returns true
        assertTrue(removeTagFromAllCommand.equals(removeTagFromAllCommand));

        // same values -> returns true
        RemoveTagFromAllCommand removeTagFromAllCommandCopy = new RemoveTagFromAllCommand("testUserTag");
        assertTrue(removeTagFromAllCommand.equals(removeTagFromAllCommandCopy));

        // different types -> returns false
        assertFalse(removeTagFromAllCommand.equals(1));

        // null -> returns false
        assertFalse(removeTagFromAllCommand.equals(null));

        // different user tag -> returns false
        assertFalse(removeTagFromAllCommand.equals(removeOtherTagFromAllCommand));
    }

}