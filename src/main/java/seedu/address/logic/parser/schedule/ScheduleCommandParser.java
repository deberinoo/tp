package seedu.address.logic.parser.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.schedule.ScheduleCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Session;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns a ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize arguments
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECT,
                PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION);

        // Validate prefixes
        validatePrefixes(argMultimap);

        // **Check for duplicate prefixes**
        verifyNoDuplicatePrefixes(argMultimap, PREFIX_NAME, PREFIX_SUBJECT, PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION);

        // Extract and validate values
        String studentName = getValidatedValue(argMultimap, PREFIX_NAME, "Student name cannot be empty!");
        String subject = getValidatedValue(argMultimap, PREFIX_SUBJECT, "Subject cannot be empty!");
        LocalDate date = ParserUtil.parseDate(getValidatedValue(argMultimap, PREFIX_DATE, "Date is required!"));
        LocalTime time = ParserUtil.parseTime(getValidatedValue(argMultimap, PREFIX_TIME, "Time is required!"));
        Duration duration = ParserUtil.parseDuration(getValidatedValue(argMultimap, PREFIX_DURATION,
                                                        "Duration is required!"));

        validateDate(date);
        validateDuration(duration);

        // Create a new Session (representing the schedule)
        Session session = new Session(studentName, subject, date, time, duration);

        // Return the ScheduleCommand with the created Session
        return new ScheduleCommand(session);
    }

    /** Validates that required prefixes are present. */
    private void validatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        // Validate presence of required prefixes and values
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                || argMultimap.getValue(PREFIX_SUBJECT).isEmpty()
                || argMultimap.getValue(PREFIX_DATE).isEmpty()
                || argMultimap.getValue(PREFIX_TIME).isEmpty()
                || argMultimap.getValue(PREFIX_DURATION).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
    }

    /** Throws an error if any prefix appears more than once. */
    private void verifyNoDuplicatePrefixes(ArgumentMultimap argMultimap, Prefix... prefixes) throws ParseException {
        for (Prefix prefix : prefixes) {
            if (argMultimap.getAllValues(prefix).size() > 1) {
                throw new ParseException("Duplicate prefix detected: " + prefix.getPrefix());
            }
        }
    }

    /** Extracts and trims a value, throwing an error if empty. */
    private String getValidatedValue(ArgumentMultimap argMultimap, Prefix prefix, String errorMessage)
            throws ParseException {
        return argMultimap.getValue(prefix)
            .map(String::trim)
            .filter(val -> !val.isEmpty())
            .orElseThrow(() -> new ParseException(errorMessage));
    }

    /** Validates that the session date is in the future. */
    private void validateDate(LocalDate date) throws ParseException {
        if (!date.isAfter(LocalDate.now())) {
            throw new ParseException("Invalid date! The specified date must be after today.");
        }
    }

    /** Validates that the session duration is within an acceptable range. */
    private void validateDuration(Duration duration) throws ParseException {
        if (duration.isZero() || duration.isNegative()) {
            throw new ParseException("Duration must be greater than zero!");
        }
        if (duration.toHours() > 24) {
            throw new ParseException("Duration cannot exceed 24 hours!");
        }
    }
}
