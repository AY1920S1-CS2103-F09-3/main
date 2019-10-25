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

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    private static final ModulesInfo modulesInfo = TypicalModulesInfo.getTypicalModulesInfo();
    private static SemesterName typicalCurrentSemester = SemesterName.Y1S1;

    // typical study plans
    public static final StudyPlan SP_1 = new StudyPlan(new Title("first study plan"), modulesInfo,
            typicalCurrentSemester);
    public static final StudyPlan SP_2 = new StudyPlan(new Title("second study plan"), modulesInfo,
            typicalCurrentSemester);
    public static final StudyPlan SP_3 = new StudyPlan(new Title("third study plan"), modulesInfo,
            typicalCurrentSemester);
    // more study plans that are not included in the Typical Module Planner
    public static final StudyPlan SP_4 = new StudyPlan(new Title("fourth study plan"), modulesInfo,
            typicalCurrentSemester);
    public static final StudyPlan SP_5 = new StudyPlan(new Title("fifth study plan"), modulesInfo,
            typicalCurrentSemester);

    private TypicalStudyPlans() {
        SP_1.addModuleToSemester(new ModuleCode("CS1101S"), SemesterName.Y1S1);
        SP_1.addModuleToSemester(new ModuleCode("CS2030"), SemesterName.Y1S2);
        SP_1.addModuleToSemester(new ModuleCode("CS2040S"), SemesterName.Y2S1);

        SP_2.addModuleToSemester(new ModuleCode("CS3230"), SemesterName.Y2S1);
        SP_2.addModuleToSemester(new ModuleCode("CS2100"), SemesterName.Y2S1);
        SP_2.addModuleToSemester(new ModuleCode("CS2103T"), SemesterName.Y2S1);

        SP_3.addModuleToSemester(new ModuleCode("MA1521"), SemesterName.Y1S1);

        /////
        SP_4.addModuleToSemester(new ModuleCode("MA1521"), SemesterName.Y1S1);
        SP_4.addModuleToSemester(new ModuleCode("IS1103X"), SemesterName.Y2S1);

        SP_5.addModuleToSemester(new ModuleCode("CS1101S"), SemesterName.Y1S1);
        SP_5.addModuleToSemester(new ModuleCode("CS1231S"), SemesterName.Y1S1);
    } // prevents instantiation

    /**
     * Returns an {@code ModulePlanner} with all the typical studyPlans.
     */
    public static ModulePlanner getTypicalModulePlanner() {
        ModulePlanner ab = new ModulePlanner(modulesInfo);
        for (StudyPlan studyPlan : getTypicalStudyPlans()) {
            ab.addStudyPlan(studyPlan);
            studyPlan.setActivated(true);
        }
        ab.activateStudyPlan(SP_1.getIndex());
        return ab;
    }

    public static List<StudyPlan> getTypicalStudyPlans() {
        return new ArrayList<>(Arrays.asList(SP_1, SP_2, SP_3));
    }
}
