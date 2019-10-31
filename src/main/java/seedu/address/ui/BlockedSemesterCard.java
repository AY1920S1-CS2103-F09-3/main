package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.semester.Semester;

/**
 * An UI component that displays information of a {@code Semester}.
 */
public class BlockedSemesterCard extends UiPart<Region> {
    private static final String FXML = "BlockedSemesterListCard.fxml";

        public final Semester semester;
        private final Logger logger = LogsCenter.getLogger(getClass());
        @FXML
        private HBox semesterCardPane;
        @FXML
        private Label name;
        @FXML
        private Label id;
        @FXML
        private Label totalMcCount;
        @FXML
        private Label reasonForBlocking;

    public BlockedSemesterCard(Semester semester) {
            super(FXML);
        this.semester = semester;
        name.setText(semester.getSemesterName().name());
        totalMcCount.setText("(0)");
        reasonForBlocking.setText(semester.getReasonForBlocked().isEmpty() ? "Semester blocked" :
                "Semester blocked: " + semester.getReasonForBlocked());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BlockedSemesterCard)) {
            return false;
        }

        // state check
        BlockedSemesterCard card = (BlockedSemesterCard) other;
        return id.getText().equals(card.id.getText())
                && semester.equals(card.semester);
    }
}
