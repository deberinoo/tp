package seedu.address.logic.parser;

import static seedu.address.logic.commands.RemindCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemindCommandParserTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EVENT = "Math Exam";
    private static final String VALID_DATE = "2025-06-20";
    private static final String VALID_TIME = "09:00";

    private static final String INVALID_DATE = "2025-02-30"; // Invalid date
    private static final String INVALID_TIME = "25:00"; // Invalid time

    private static final String PAST_DATE = "2025-02-01";

    private static final String PREFIX_NAME = "n/";
    private static final String PREFIX_EVENT = "e/";
    private static final String PREFIX_DATE = "d/";
    private static final String PREFIX_TIME = "t/";

    private final RemindCommandParser parser = new RemindCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        RemindCommand expectedCommand = new RemindCommand(VALID_NAME, VALID_EVENT,
                LocalDate.parse(VALID_DATE), LocalTime.parse(VALID_TIME));

        // Valid input with all fields present
        assertParseSuccess(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format("Invalid command format! Expected: %s",
                MESSAGE_USAGE);
        // Missing name prefix
        assertParseFailure(parser,
                VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                expectedMessage);

        // Missing event prefix
        assertParseFailure(parser,
                PREFIX_NAME + VALID_NAME + " "
                        + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                expectedMessage);

        // Missing date prefix
        assertParseFailure(parser,
                PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                expectedMessage);

        // Missing time prefix
        assertParseFailure(parser,
                PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid date
        assertParseFailure(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + INVALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                "Invalid date format! Expected format: yyyy-MM-dd");

        // Invalid time
        assertParseFailure(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + INVALID_TIME,
                "Invalid time format! Expected format: HH:mm");

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Format: 02 April 2025
        String formattedToday = today.format(formatter);

        // Construct the error message
        String errorMessage = String.format(
                "Invalid date! Expected: The specified date must be in the future (after today: %s).",
                formattedToday
        );

        // Past date
        assertParseFailure(parser,
                " " + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + PAST_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                errorMessage);
        // Non-empty preamble
        assertParseFailure(parser,
                "randomPreambleText "
                        + PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                String.format("Invalid command format! Expected: %s", MESSAGE_USAGE));
    }
}
