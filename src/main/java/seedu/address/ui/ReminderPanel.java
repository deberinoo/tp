package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.Reminder;

/**
 * Panel containing the list of reminders.
 */
public class ReminderPanel extends UiPart<Region> {
    private static final String FXML = "ReminderPanel.fxml";

    @FXML
    private ListView<Reminder> reminderListView;

    /**
     * Creates a {@code ReminderPanel} with the given list of reminders.
     */
    public ReminderPanel(ObservableList<Reminder> reminderList) {
        super(FXML);
        reminderListView.setItems(reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminder}.
     */
    class ReminderListViewCell extends ListCell<Reminder> {
        @Override
        protected void updateItem(Reminder reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setText(reminder.toString());
            }
        }
    }
}
