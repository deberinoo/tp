package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new {@code RemindCommand} object
 */
public class RemindCommandParser implements Parser<RemindCommand> {
    // Define prefixes for parsing arguments
    private static final Prefix PREFIX_NAME = new Prefix("n/");
    private static final Prefix PREFIX_EVENT = new Prefix("e/");
    private static final Prefix PREFIX_DATE = new Prefix("d/");
    private static final Prefix PREFIX_TIME = new Prefix("t/");

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemindCommand}
     * and returns a {@code RemindCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public RemindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize arguments using ArgumentTokenizer
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EVENT, PREFIX_DATE, PREFIX_TIME);

        // Validate presence of required prefixes and values
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                || argMultimap.getValue(PREFIX_EVENT).isEmpty()
                || argMultimap.getValue(PREFIX_DATE).isEmpty()
                || argMultimap.getValue(PREFIX_TIME).isEmpty()) {
            throw new ParseException(String.format("Invalid command format! Expected: %s", RemindCommand.MESSAGE_USAGE));
        }

        // Extract values from ArgumentMultimap
        String name = argMultimap.getValue(PREFIX_NAME).get();
        String event = argMultimap.getValue(PREFIX_EVENT).get();
        String date = argMultimap.getValue(PREFIX_DATE).get();
        String time = argMultimap.getValue(PREFIX_TIME).get();

        // Validate date and time formats (to be added later)
        // ParserUtil.validateFutureDate(date);
        // ParserUtil.validateTimeFormat(time);
        // try and catch,
        // throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), ive);
        return new RemindCommand(name, event, date, time);
    }
}
