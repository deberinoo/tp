package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.Reminder;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Session;
import seedu.address.model.tag.Tag;
import seedu.address.ui.MainWindow;

/**
 * Test class for RemindCommand.
 */
public class RemindCommandTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EVENT = "Math Exam";
    private static final LocalDate VALID_DATE = LocalDate.of(2025, 6, 20);
    private static final LocalTime VALID_TIME = LocalTime.of(9, 0);

    private static final String ANOTHER_NAME = "Jane Smith";
    private static final String ANOTHER_EVENT = "Science Test";
    private static final LocalDate ANOTHER_DATE = LocalDate.of(2025, 6, 25);
    private static final LocalTime ANOTHER_TIME = LocalTime.of(14, 30);

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemindCommand(null, VALID_EVENT, VALID_DATE, VALID_TIME));
        assertThrows(NullPointerException.class, () -> new RemindCommand(VALID_NAME, null, VALID_DATE, VALID_TIME));
        assertThrows(NullPointerException.class, () -> new RemindCommand(VALID_NAME, VALID_EVENT, null, VALID_TIME));
        assertThrows(NullPointerException.class, () -> new RemindCommand(VALID_NAME, VALID_EVENT, VALID_DATE, null));
    }

    @Test
    public void execute_validReminder_addSuccessful() throws Exception {
        Model model = new ModelStubAcceptingReminderAdded();
        RemindCommand remindCommand = new RemindCommand(VALID_NAME, VALID_EVENT, VALID_DATE, VALID_TIME);

        CommandResult commandResult = remindCommand.execute(model);

        Reminder expectedReminder = new Reminder(VALID_NAME, VALID_EVENT, VALID_DATE, VALID_TIME);
        ObservableList<Reminder> reminders = model.getFilteredReminderList();

        assertEquals(String.format("New reminder added:\n%s", expectedReminder), commandResult.getFeedbackToUser());
        assertEquals(1, reminders.size());
        assertEquals(expectedReminder, reminders.get(0));
    }


    // method execute_duplicateReminder_throwsCommandException() to be added{

    @Test
    public void equals() {
        RemindCommand remindCommand1 = new RemindCommand(VALID_NAME, VALID_EVENT, VALID_DATE, VALID_TIME);
        RemindCommand remindCommand2 = new RemindCommand(ANOTHER_NAME, ANOTHER_EVENT, ANOTHER_DATE, ANOTHER_TIME);

        // same object -> returns true
        assertEquals(remindCommand1, remindCommand1);

        // same values -> returns true
        RemindCommand remindCommandCopy = new RemindCommand(VALID_NAME, VALID_EVENT, VALID_DATE, VALID_TIME);
        assertEquals(remindCommand1, remindCommandCopy);

        // different types -> returns false
        assertNotEquals(1, remindCommand1);

        // null -> returns false
        assertNotEquals(null, remindCommand1);

        // different reminders -> returns false
        assertNotEquals(remindCommand1, remindCommand2);
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getTagList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSession(Session target, Session editedSession) {

        }

        @Override
        public ObservableList<Session> getSessionList() {
            // Return a mock list or an empty list
            return FXCollections.observableArrayList();
        }

        @Override
        public void deleteSession(Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSession(Session session) {
            return false;
        }

        @Override
        public Model copy() {
            return null;
        }

        @Override
        public MainWindow getMainWindow() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMainWindow(MainWindow mainWindow) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accepts the reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public void addReminder(Reminder reminder) {
            requireNonNull(reminder);
            remindersAdded.add(reminder);
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            return FXCollections.observableArrayList(remindersAdded);
        }
    }

}
