package seedu.address.model.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS3244;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalSemester.EMPTY_SEMESTER;
import static seedu.address.testutil.TypicalSemester.FULL_UNBLOCKED_SEMESTER_1;
import static seedu.address.testutil.TypicalSemester.FULL_UNBLOCKED_SEMESTER_2;

import org.junit.jupiter.api.Test;

/**
 * A test class for {@code Semester}.
 */
public class SemesterTest {

    @Test
    public void equals() throws CloneNotSupportedException {
        // same values -> returns true
        Semester FULL_UNBLOCKED_SEMESTER_1_COPY = FULL_UNBLOCKED_SEMESTER_1.clone();

        // same object -> returns true
        assertTrue(FULL_UNBLOCKED_SEMESTER_1.equals(FULL_UNBLOCKED_SEMESTER_1));

        // null -> returns false
        assertFalse(FULL_UNBLOCKED_SEMESTER_1.equals(null));

        // different type -> returns false
        assertFalse(FULL_UNBLOCKED_SEMESTER_1.equals(5));

        assertEquals(FULL_UNBLOCKED_SEMESTER_1, FULL_UNBLOCKED_SEMESTER_1_COPY);
        assertEquals(FULL_UNBLOCKED_SEMESTER_1.getMcCount(), FULL_UNBLOCKED_SEMESTER_1_COPY.getMcCount());
        assertEquals(FULL_UNBLOCKED_SEMESTER_1.getModules(), FULL_UNBLOCKED_SEMESTER_1_COPY.getModules());
        assertEquals(FULL_UNBLOCKED_SEMESTER_1.getSemesterName(), FULL_UNBLOCKED_SEMESTER_1_COPY.getSemesterName());
        assertEquals(FULL_UNBLOCKED_SEMESTER_1.getReasonForBlocked(),
                FULL_UNBLOCKED_SEMESTER_1_COPY.getReasonForBlocked());
        assertEquals(FULL_UNBLOCKED_SEMESTER_1.getMcCount(), FULL_UNBLOCKED_SEMESTER_1_COPY.getMcCount());
        assertNotSame(FULL_UNBLOCKED_SEMESTER_1_COPY, FULL_UNBLOCKED_SEMESTER_1);
    }

    @Test
    public void toString_valid_success() {

    }

    @Test
    public void constructor_null_throwsException() {
        assertThrows(NullPointerException.class, () -> new Semester(null));
        assertThrows(NullPointerException.class,
            () -> new Semester(null, false, null, null));
    }

    @Test
    public void getters_setters() throws CloneNotSupportedException {
        Semester emptySemester = EMPTY_SEMESTER.clone();
        assertFalse(emptySemester.hasModule(VALID_MODULE_CODE_CS3244));
        emptySemester.addModule(CS3244);
        assertTrue(emptySemester.hasModule(VALID_MODULE_CODE_CS3244));
        assertFalse(emptySemester.isBlocked());
        assertEquals(emptySemester.getMcCount(), 4);
    }

    @Test
    public void toString_valid() {
        System.out.println(FULL_UNBLOCKED_SEMESTER_1.toString());
        System.out.println(FULL_UNBLOCKED_SEMESTER_2.toString());
        assertEquals("Y1S2:\n"
                        + "Probability and Statistics Module code: ST2334 MCs: 4 Prereqs satisfied: false Tags: [Stats]\n"
                        + "Machine Learning Module code: CS3244 MCs: 4 Prereqs satisfied: false Tags: [Cool][AI][ML]\n"
                        + "Programming Methodology Module code: CS1101S MCs: 4 Prereqs satisfied: false Tags: [Hard]\n",
                FULL_UNBLOCKED_SEMESTER_1.toString());
        assertEquals("Y1S2:\n" +
                        "Database Systems Module code: CS2102 MCs: 4 Prereqs satisfied: false Tags: [Database]\n"
                        + "Theory and Algorithms for Machine Learning\n"
                        + " Module code: CS5339 MCs: 4 Prereqs satisfied: false Tags: [SWE]\n"
                        + "Automated Software Validation\n"
                        + " Module code: CS5219 MCs: 4 Prereqs satisfied: false Tags: [SWE]\n",
                FULL_UNBLOCKED_SEMESTER_2.toString());
    }
}
