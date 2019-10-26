package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.DefaultTagType;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModule;
import seedu.address.testutil.TypicalModuleHashMap;
import seedu.address.testutil.TypicalModulesInfo;
import seedu.address.ui.ResultViewType;

public class ViewModuleTagsCommandTest {

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewModuleTagsCommand(null));
    }

    @Test
    public void execute_userTagsPresentInModule_allModuleTagsShownSuccessful() {
        // construct user tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct module with one user tag
        Module cs1231s = new ModuleBuilder().withModuleCode("CS1231S").withTags(validTagNameOne).build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo)
                .withModules(moduleHashMap).build();
        // assign default tags to the module
        studyPlan.assignDefaultTags(TypicalModulesInfo.CS1231S);
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct list of tags that should be shown
        UniqueTagList expectedList = new UniqueTagList();
        expectedList.addTag(validTagOne);
        expectedList.addTag(new TagBuilder().buildDefaultTag(DefaultTagType.SU));
        expectedList.addTag(new TagBuilder().buildDefaultCoreTag());

        CommandResult expectedCommandResult = new CommandResult(ViewModuleTagsCommand.MESSAGE_SUCCESS,
                ResultViewType.TAG, expectedList.asUnmodifiableObservableList());

        // construct command to show all tags for the module
        ViewModuleTagsCommand viewModuleTagsCommand = new ViewModuleTagsCommand("CS1231S");
        assertCommandSuccess(viewModuleTagsCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_noUserTagsPresentInModule_allModuleTagsShownSuccessful() {
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();

        // construct module with no user tags
        Module cs1231s = new ModuleBuilder().withModuleCode("CS1231S").build();
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1231S", cs1231s);

        // construct model containing study plan with two user tags
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne)
                .withModules(moduleHashMap).build();
        // assign default tags to the module
        studyPlan.assignDefaultTags(TypicalModulesInfo.CS1231S);
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct list of tags that should be shown
        UniqueTagList expectedList = new UniqueTagList();
        expectedList.addTag(new TagBuilder().buildDefaultTag(DefaultTagType.SU));
        expectedList.addTag(new TagBuilder().buildDefaultCoreTag());

        CommandResult expectedCommandResult = new CommandResult(ViewModuleTagsCommand.MESSAGE_SUCCESS,
                ResultViewType.TAG, expectedList.asUnmodifiableObservableList());

        // construct command to show all tags for the module
        ViewModuleTagsCommand viewModuleTagsCommand = new ViewModuleTagsCommand("CS1231S");
        assertCommandSuccess(viewModuleTagsCommand, model, expectedCommandResult, model);
    }

    @Test
    public void equals() {
        ViewModuleTagsCommand viewModuleTagsCommand = new ViewModuleTagsCommand("CS1231");
        ViewModuleTagsCommand viewOtherModuleTagsCommand = new ViewModuleTagsCommand("CS3230");

        // same object -> returns true
        assertTrue(viewModuleTagsCommand.equals(viewModuleTagsCommand));

        // same values -> returns true
        ViewModuleTagsCommand viewModuleTagsCommandCopy = new ViewModuleTagsCommand("CS1231");
        assertTrue(viewModuleTagsCommand.equals(viewModuleTagsCommandCopy));

        // different types -> returns false
        assertFalse(viewModuleTagsCommand.equals(1));

        // null -> returns false
        assertFalse(viewModuleTagsCommand.equals(null));

        // different module code -> returns false
        assertFalse(viewModuleTagsCommand.equals(viewOtherModuleTagsCommand));
    }
    
}
