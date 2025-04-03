package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.schedule.Session;

/**
 * Panel containing the list of reminders.
 */
public class SessionPanel extends UiPart<Region> {
    private static final String FXML = "SessionPanel.fxml";

    @FXML
    private ListView<Session> sessionListView;

    @FXML
    private Label sessionHeader;

    /**
     * Creates a {@code ReminderPanel} with the given list of reminders.
     */
    public SessionPanel(ObservableList<Session> sessionList) {
        super(FXML);

        ObservableList<Session> sortedList = sessionList.sorted(Comparator.comparing(Session::getDateTime));

        // Display scheduled sessions
        sessionListView.setItems(sortedList);
        sessionListView.setCellFactory(listView -> new SessionListViewCell());

        // Set initial session header text
        updateSessionHeader(sortedList);

        // Add a listener to the sessionList to update the header when the list changes
        sessionList.addListener((ListChangeListener<Session>) change -> {
            updateSessionHeader(sortedList);
        });
    }

    private void updateSessionHeader(ObservableList<Session> sessionList) {
        if (!sessionList.isEmpty()) {
            sessionHeader.setText("Scheduled Sessions");
        } else {
            sessionHeader.setText("No Scheduled Sessions");
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
                String displayText = (getIndex() + 1) + ". " + session.toString();
                setText(displayText);
            }
        }
    }

    public void refresh() {
        sessionListView.refresh();
    }
}
