package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.schedule.ScheduleCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.schedule.ScheduleCommand;
import seedu.address.logic.parser.schedule.ScheduleCommandParser;
import seedu.address.model.schedule.Session;

public class ScheduleCommandParserTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_SUBJECT = "Math";
    private static final String VALID_DATE = LocalDate.now().plusDays(1).toString(); // Tomorrow
    private static final String VALID_TIME = "14:00";
    private static final String VALID_DURATION = "2h";

    private static final String INVALID_DATE = LocalDate.now().minusDays(1).toString(); // Yesterday
    private static final String INVALID_DURATION_ZERO = "0h";
    private static final String INVALID_DURATION_NEGATIVE = "-1h";
    private static final String INVALID_DURATION_LONG = "25h";

    private final ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Session expectedSession = new Session(VALID_NAME, VALID_SUBJECT,
                LocalDate.parse(VALID_DATE), LocalTime.parse(VALID_TIME),
                Duration.ofHours(2));

        assertParseSuccess(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_SUBJECT + VALID_SUBJECT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME + " "
                        + PREFIX_DURATION + VALID_DURATION,
                new ScheduleCommand(expectedSession));
    }

    @Test
    public void parse_missingPrefixes_failure() {
        // Missing all prefixes
        assertParseFailure(parser,
                VALID_NAME + " " + VALID_SUBJECT + " " + VALID_DATE + " " + VALID_TIME + " " + VALID_DURATION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Missing name prefix
        assertParseFailure(parser,
                VALID_NAME + " "
                        + PREFIX_SUBJECT + VALID_SUBJECT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME + " "
                        + PREFIX_DURATION + VALID_DURATION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDuration_failure() {
        // Zero duration
        assertParseFailure(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_SUBJECT + VALID_SUBJECT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME + " "
                        + PREFIX_DURATION + INVALID_DURATION_ZERO,
                "Duration cannot be zero or negative!");

        // Negative duration
        assertParseFailure(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_SUBJECT + VALID_SUBJECT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME + " "
                        + PREFIX_DURATION + INVALID_DURATION_NEGATIVE,
                "Duration cannot be zero or negative!");

        // Duration > 24 hours
        assertParseFailure(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_SUBJECT + VALID_SUBJECT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME + " "
                        + PREFIX_DURATION + INVALID_DURATION_LONG,
                "Duration cannot be more than 24 hours!");
    }

    @Test
    public void parse_pastDate_failure() {
        assertParseFailure(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_SUBJECT + VALID_SUBJECT + " "
                        + PREFIX_DATE + INVALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME + " "
                        + PREFIX_DURATION + VALID_DURATION,
                String.format("Invalid date! Expected: The specified date must be in the future (after today: %s).",
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
    }
}
