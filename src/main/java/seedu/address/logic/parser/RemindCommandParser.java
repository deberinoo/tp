package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.RemindCommand;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_EVENT, PREFIX_DATE, PREFIX_TIME);

        // Validate presence of required prefixes and values
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                || argMultimap.getValue(PREFIX_EVENT).isEmpty()
                || argMultimap.getValue(PREFIX_DATE).isEmpty()
                || argMultimap.getValue(PREFIX_TIME).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("Invalid command format! Expected: %s",
                    RemindCommand.MESSAGE_USAGE));
        }

        // Extract values from ArgumentMultimap
        String name = argMultimap.getValue(PREFIX_NAME).get();
        String event = argMultimap.getValue(PREFIX_EVENT).get();
        String dateStr = argMultimap.getValue(PREFIX_DATE).get();
        String timeStr = argMultimap.getValue(PREFIX_TIME).get();

        // Parsing date and time strings
        LocalDate date = ParserUtil.parseDate(dateStr);
        LocalTime time = ParserUtil.parseTime(timeStr);

        // Validate that the date is in the future
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedToday = today.format(formatter);
        String errorMessage = String.format(
                "Invalid date! Expected: "
                        + "The specified date must be in the future (after today: %s).",
                formattedToday
        );
        if (!date.isAfter(today)) {
            throw new ParseException(errorMessage);
        }
        return new RemindCommand(name, event, date, time);
    }
}

