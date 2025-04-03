package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandWithConfirmation;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBookStateManager;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.Reminder;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Session;
import seedu.address.storage.Storage;
import seedu.address.ui.MainWindow;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private Model model;
    private MainWindow mainWindow;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        AddressBookStateManager.addState(model.getAddressBook().copy());
    }

    /**
     * Sets the main window of the app.
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.model.setMainWindow(mainWindow);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);

        // If the command requires confirmation, execute it with confirmation
        if (command instanceof CommandWithConfirmation) {
            CommandWithConfirmation commandWithConfirmation = (CommandWithConfirmation) command;
            boolean confirmed = commandWithConfirmation.executeWithConfirmation();
            if (!confirmed) {
                return new CommandResult("Command canceled.");
            }
        }

        commandResult = command.execute(model);

        // list, and find commands should not change the addressbook state. undo commands should function outside
        // the addressbook state
        if (!(command instanceof UndoCommand) && !(command instanceof ListCommand)
                && !(command instanceof FindCommand) && !(command instanceof RedoCommand)) {
            AddressBookStateManager.addState(model.getAddressBook().copy());
        }

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return model.getFilteredReminderList();
    }

    @Override
    public ObservableList<Session> getSessionList() {
        return model.getSessionList().sorted(Comparator.comparing(Session::getDateTime));
    }
}
