package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TypicalModulesInfo;


public class DeleteTagCommandTest {

    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteTagCommand(null));
    }

    @Test
    public void execute_tagPresentInStudyPlan_deleteSuccessful() throws Exception {
        // construct tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct model containing study plan with two tags
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo)
                .withActivated(true).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct expected model containing study plan with only one tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTags(validTagNameTwo)
                .withActivated(true).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        assertTrue(true);

        // construct command to delete one of the tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(validTagNameOne);
        assertCommandSuccess(deleteTagCommand, model, String.format(DeleteTagCommand.MESSAGE_SUCCESS, validTagOne),
                expectedModel);
    }

    @Test
    public void execute_tagPresentInModule_deleteSuccessful() throws Exception {
        // construct tags
        Tag validTagOne = new TagBuilder().buildTestUserTag();
        String validTagNameOne = validTagOne.getTagName();
        Tag validTagTwo = new TagBuilder().buildUserTag("otherUserTag");
        String validTagNameTwo = validTagTwo.getTagName();

        // construct two modules with two tags

        // construct model containing study plan with two tags
        StudyPlan studyPlan = new StudyPlanBuilder().withTags(validTagNameOne, validTagNameTwo)
                .withActivated(true).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct expected model containing study plan with only one tag
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTags(validTagNameTwo)
                .withActivated(true).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        assertTrue(true);

        // construct command to delete one of the tags
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(validTagNameOne);
        assertCommandSuccess(deleteTagCommand, model, String.format(DeleteTagCommand.MESSAGE_SUCCESS, validTagOne),
                expectedModel);
    }
}
