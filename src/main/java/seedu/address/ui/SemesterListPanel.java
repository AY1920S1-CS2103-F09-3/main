package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.semester.Semester;

/**
 * Panel containing the list of semesters.
 */
public class SemesterListPanel extends UiPart<Region> {
    private static final String FXML = "SemesterListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SemesterListPanel.class);

    @FXML
    private ListView<Semester> semesterListView;

    public SemesterListPanel(ObservableList<Semester> semesters) {
        super(FXML);
        semesterListView.setItems(semesters);
        semesterListView.setCellFactory(listView -> new SemesterListViewCell());
    }

    public void refresh() {
        semesterListView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Semester} using a {@code SemesterCard}.
     */
    class SemesterListViewCell extends ListCell<Semester> {
        @Override
        protected void updateItem(Semester semester, boolean empty) {
            super.updateItem(semester, empty);

            if (empty || semester == null) {
                setGraphic(null);
                setText(null);
            } else if (semester.isBlocked()) {
                setGraphic(new BlockedSemesterCard(semester).getRoot());
            } else {
                setGraphic(new SemesterCard(semester).getRoot());
            }
        }
    }

}
