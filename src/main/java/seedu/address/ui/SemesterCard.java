package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;

/**
 * An UI component that displays information of a {@code Person}.
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

    private ModuleListPanel moduleListPanel;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label totalMcCount;
    @FXML
    private FlowPane tags;
    @FXML
    private StackPane moduleListPanelPlaceholder;

    public SemesterCard(Semester semester, int displayedIndex) {
        super(FXML);
        this.semester = semester;
        name.setText(semester.getSemesterName().name());
        totalMcCount.setText(Integer.toString(semester.getMcCount()));

        ObservableList<Module> modules = semester.getModules().asUnmodifiableObservableList();

        moduleListPanel = new ModuleListPanel(modules);
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());
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
