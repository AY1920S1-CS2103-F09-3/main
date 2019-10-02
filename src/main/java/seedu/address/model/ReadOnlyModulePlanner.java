package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyModulePlanner {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<StudyPlan> getStudyPlanList();

}
