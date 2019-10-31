package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.StudyPlanBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code DeleteSemesterCommand}.
 */
public class DeleteSemesterCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        UniqueSemesterList semesters = new UniqueSemesterList();
        semesters.add(new Semester(SemesterName.Y1S1));
        StudyPlan studyPlan = new StudyPlanBuilder().withSemesters(semesters).build();
        model.addStudyPlan(studyPlan);
        model.activateStudyPlan(studyPlan.getIndex());
        model.addModule("CS1101S", SemesterName.Y1S1);
    }

    @Test
    public void execute_deleteMainstreamSemester_success() throws CommandException {
        // a mainstream semester is non-special term or non-Year 5
        SemesterName semesterName = SemesterName.Y1S1;

        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        UniqueSemesterList semesters = new UniqueSemesterList();
        semesters.add(new Semester(SemesterName.Y1S1));
        StudyPlan studyPlan = new StudyPlanBuilder().withSemesters(semesters).build();
        expectedModel.addStudyPlan(studyPlan);
        expectedModel.activateStudyPlan(studyPlan.getIndex());
        expectedModel.deleteAllModulesInSemester(semesterName);

        DeleteSemesterCommand command = new DeleteSemesterCommand(semesterName);
        CommandResult expectedResult =
                new CommandResult(String.format(DeleteSemesterCommand.MESSAGE_DELETE_MAINSTREAM_SEMESTER_SUCCESS,
                        semesterName));
        CommandResult actualResult = command.execute(model);

        // check command result
        assertEquals(expectedResult, actualResult);

        // check semesters
        UniqueSemesterList expectedSemesters = expectedModel.getActiveStudyPlan().getSemesters();
        UniqueSemesterList actualSemesters = model.getActiveStudyPlan().getSemesters();
        assertEquals(expectedSemesters, actualSemesters);
    }

}
