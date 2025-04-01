package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Session;
import seedu.address.model.tag.Tag;
import seedu.address.ui.MainWindow;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final ObservableList<Session> sessions;
    private final ObservableList<Reminder> reminders;
    private final FilteredList<Reminder> filteredReminders;
    private MainWindow mainWindow;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.reminders = FXCollections.observableArrayList();
        this.filteredReminders = new FilteredList<>(this.reminders);
        this.sessions = FXCollections.observableArrayList();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * Creates a copy of the given ModelManager.
     */
    @Override
    public Model copy() {
        return new ModelManager(addressBook.copy(), userPrefs);
    }

    /**
     * Sets the main window of the app.
     */
    @Override
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public MainWindow getMainWindow() {
        return this.mainWindow;
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return addressBook.getTagList();
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== Sessions ================================================================================
    @Override
    public ObservableList<Session> getSessionList() {
        return sessions;
    }

    @Override
    public void addSession(Session session) {
        requireNonNull(session);
        sessions.add(session);
    }

    @Override
    public void updateSession(Session target, Session editedSession) {
        requireNonNull(target);
        requireNonNull(editedSession);

        if (!target.equals(editedSession)) {
            sessions.set(sessions.indexOf(target), editedSession);
        }
    }

    @Override
    public void deleteSession(Session session) {
        sessions.remove(session);
    }

    @Override
    public boolean hasSession(Session session) {
        return sessions.contains(session);
    }
    //=========== Reminders ================================================================================
    /**
     * Adds a reminder to the reminders list.
     */
    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return filteredReminders;
    }
}
