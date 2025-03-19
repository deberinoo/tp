package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Session;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

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

        // Tokenize arguments using ArgumentTokenizer
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECT, PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION);

        // Validate presence of required prefixes and values
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                || argMultimap.getValue(PREFIX_SUBJECT).isEmpty()
                || argMultimap.getValue(PREFIX_DATE).isEmpty()
                || argMultimap.getValue(PREFIX_TIME).isEmpty()
                || argMultimap.getValue(PREFIX_DURATION).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        // Extract values from ArgumentMultimap
        String studentName = argMultimap.getValue(PREFIX_NAME).get();
        String subject = argMultimap.getValue(PREFIX_SUBJECT).get();
        String dateStr = argMultimap.getValue(PREFIX_DATE).get();
        String timeStr = argMultimap.getValue(PREFIX_TIME).get();
        String durationStr = argMultimap.getValue(PREFIX_DURATION).get();

        // Parse date, time, and duration using ParserUtil
        LocalDate date = ParserUtil.parseDate(dateStr);
        LocalTime time = ParserUtil.parseTime(timeStr);
        Duration duration = ParserUtil.parseDuration(durationStr);  // Assuming you have a parseDuration method

        // Create a new Session (representing the schedule)
        Session session = new Session(studentName, subject, date, time, duration);

        // Return the ScheduleCommand with the created Session
        return new ScheduleCommand(session);
    }
}
