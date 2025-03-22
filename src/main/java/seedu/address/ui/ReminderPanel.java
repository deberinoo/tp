package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.Reminder;
import seedu.address.model.schedule.Session;

/**
 * Panel containing the list of reminders.
 */
public class ReminderPanel extends UiPart<Region> {
    private static final String FXML = "ReminderPanel.fxml";

    @FXML
    private ListView<Reminder> reminderListView;

    @FXML
    private ListView<Session> sessionListView;

    
    @FXML
    private Label sessionHeader; // Label for sessions

    /**
     * Creates a {@code ReminderPanel} with the given list of reminders.
     */
    public ReminderPanel(ObservableList<Reminder> reminderList, ObservableList<Session> sessionList) {
        super(FXML);
        reminderListView.setItems(reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());

        // Display scheduled sessions
        sessionListView.setItems(sessionList);
        sessionListView.setCellFactory(listView -> new SessionListViewCell());

        // if (!sessionList.isEmpty()) {
        //     sessionListView.setItems(sessionList);
        //     sessionListView.setCellFactory(listView -> new SessionListViewCell());
        //     sessionHeader.setText("Scheduled Sessions");
        // } else {
        //     sessionHeader.setText("No Scheduled Sessions");
        // }
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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Session}.
     */
    class SessionListViewCell extends ListCell<Session> {
        @Override
        protected void updateItem(Session session, boolean empty) {
            super.updateItem(session, empty);

            if (empty || session == null) {
                setGraphic(null);
                setText(null);
            } else {
                setText(session.toString());
            }
        }
    }
}
