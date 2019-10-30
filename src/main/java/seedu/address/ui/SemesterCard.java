package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.semester.Semester;

import static java.util.Objects.requireNonNull;

/**
 * An UI component that displays information of a {@code Semester}.
 */
public class SemesterCard extends UiPart<Region> {

    private static final String FXML = "SemesterListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Semester semester;

    @FXML
    private HBox semesterCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label totalMcCount;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox moduleListPanelPlaceholder;
    private final Logger logger = LogsCenter.getLogger(getClass());

    public SemesterCard(Semester semester) {
        super(FXML);
        requireNonNull(semester);
        this.semester = semester;
        name.setText(semester.getSemesterName().name());
        totalMcCount.setText("(" + semester.getMcCount() + ")");

        semester.getModules().asUnmodifiableObservableList().stream()
                .sorted(Comparator.comparing(module -> module.getModuleCode().toString()))
                .forEach(module -> {
                    ModuleCard moduleCard = new ModuleCard(module);
                    moduleListPanelPlaceholder.getChildren().add(moduleCard.getRoot());
                });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SemesterCard)) {
            return false;
        }

        // state check
        SemesterCard card = (SemesterCard) other;
        return id.getText().equals(card.id.getText())
                && semester.equals(card.semester);
    }
}
