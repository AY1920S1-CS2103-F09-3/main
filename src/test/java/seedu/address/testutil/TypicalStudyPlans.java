package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ModulePlanner;
import seedu.address.model.ModulesInfo;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;

/**
 * A utility class containing a list of {@code StudyPlan} objects to be used in tests.
 */
public class TypicalStudyPlans {
    public static final Title SP_1_TITLE = new Title("first study plan");
    public static final Title SP_2_TITLE = new Title("second study plan");
    public static SemesterName SP_1_SEMESTER_NAME = SemesterName.Y1S1;
    public static SemesterName SP_2_SEMESTER_NAME = SemesterName.Y1S2;

    private static final ModulesInfo modulesInfo = TypicalModulesInfo.getTypicalModulesInfo();

    // typical study plans
    public static final StudyPlan SP_1 = new StudyPlan(SP_1_TITLE, modulesInfo, SP_1_SEMESTER_NAME);
    public static final StudyPlan SP_2 = new StudyPlan(SP_2_TITLE, modulesInfo, SP_2_SEMESTER_NAME);
    public static final StudyPlan SP_3 = new StudyPlan(new Title("third study plan"), modulesInfo,
            SP_1_SEMESTER_NAME);

    /**
     * Returns an {@code ModulePlanner} with all the typical studyPlans.
     */
    public static ModulePlanner getTypicalModulePlanner() {
        ModulePlanner ab = new ModulePlanner(modulesInfo);
        for (StudyPlan studyPlan : getTypicalStudyPlans()) {
            ab.addStudyPlan(studyPlan);
        }
        return ab;
    }

    public static List<StudyPlan> getTypicalStudyPlans() {
        return new ArrayList<>(Arrays.asList(SP_1, SP_2, SP_3));
    }
}
