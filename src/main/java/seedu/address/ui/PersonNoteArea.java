package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * TextArea containing notes of a Person.
 */
public class PersonNoteArea extends UiPart<Region> {
    private static final String FXML = "PersonNoteArea.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonNoteArea.class);
    @FXML
    private TextArea noteTextArea;
    @FXML
    private StackPane placeHolder;

    /**
     * Creates a {@code PersonNoteArea} with the given {@code ObservableList}.
     */
    public PersonNoteArea(ObservableList<Person> personList) {
        super(FXML);
        // Add listener to update the TextArea when the list changes
        personList.addListener((ListChangeListener<Person>) change -> updateNoteArea(personList));

        // Initialize the TextArea visibility
        updateNoteArea(personList);
    }

    /**
     * Updates the TextArea based on the list size.
     */
    private void updateNoteArea(ObservableList<Person> personList) {
        if (personList.size() == 1) {
            Person singlePerson = personList.get(0);
            noteTextArea.setText(singlePerson.getNote());
            noteTextArea.setVisible(true);
            placeHolder.setVisible(true);
        } else {
            noteTextArea.setText("");
            noteTextArea.setVisible(false);
            placeHolder.setVisible(false);
        }
    }
}
