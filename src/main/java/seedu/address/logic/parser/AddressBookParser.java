package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.TagsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.schedule.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.schedule.ScheduleCancelCommandParser;
import seedu.address.logic.parser.schedule.ScheduleCommandParser;
import seedu.address.logic.parser.schedule.ScheduleEditCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private static final List<String> commandClasses = new ArrayList<>();

    static {
        // Dynamically adding class names of commands
        commandClasses.add(AddCommand.COMMAND_WORD);
        commandClasses.add(ClearCommand.COMMAND_WORD);
        commandClasses.add(DeleteCommand.COMMAND_WORD);
        commandClasses.add(EditCommand.COMMAND_WORD);
        commandClasses.add(ExitCommand.COMMAND_WORD);
        commandClasses.add(FindCommand.COMMAND_WORD);
        commandClasses.add(ListCommand.COMMAND_WORD);
        commandClasses.add(NoteCommand.COMMAND_WORD);
        commandClasses.add(RemindCommand.COMMAND_WORD);
        commandClasses.add(TagsCommand.COMMAND_WORD);
        commandClasses.add(HelpCommand.COMMAND_WORD);
        commandClasses.add(ScheduleCommand.COMMAND_WORD);
        commandClasses.add(UndoCommand.COMMAND_WORD);
        commandClasses.add(RedoCommand.COMMAND_WORD);
        commandClasses.add(SwitchCommand.COMMAND_WORD);
        // Add more as necessary
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Try to find the closest matching command
        String resolvedCommandWord = getFullCommandName(commandWord);

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (resolvedCommandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case NoteCommand.COMMAND_WORD:
            return new NoteCommandParser().parse(arguments);

        case ScheduleCommand.COMMAND_WORD:
            return parseScheduleCommand(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case RemindCommand.COMMAND_WORD:
            return new RemindCommandParser().parse(arguments);

        case TagsCommand.COMMAND_WORD:
            return new TagsCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Returns the full command name based on the given input.
     * This method attempts to find the closest matching command using a basic similarity check.
     */
    private String getFullCommandName(String commandInput) throws ParseException {
        for (String command : commandClasses) {
            if (commandInput.equalsIgnoreCase(command) || isSimilar(commandInput, command)) {
                return command;
            }
        }
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * A simple similarity check (could be enhanced further).
     * This compares the commandInput against full command names with simple rules like truncation.
     */
    private boolean isSimilar(String commandInput, String fullCommand) {
        // Example: Compare "lis" with "list"
        return fullCommand.startsWith(commandInput) || commandInput.startsWith(fullCommand);
    }

    /**
     * Parses subcommands under "schedule".
     */
    private Command parseScheduleCommand(String arguments) throws ParseException {
        String trimmedArgs = arguments.trim();

        if (trimmedArgs.startsWith("edit")) {
            return new ScheduleEditCommandParser().parse(trimmedArgs.substring(4).trim()); // Remove "edit"
        }

        if (trimmedArgs.startsWith("cancel")) {
            return new ScheduleCancelCommandParser().parse(trimmedArgs.substring(6).trim()); // Remove "cancel"
        }

        return new ScheduleCommandParser().parse(arguments);
    }
}
