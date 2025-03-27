package seedu.address.logic.parser;

import static seedu.address.logic.commands.RemindCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemindCommandParserTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EVENT = "Math Exam";
    private static final String VALID_DATE = "2025-06-20";
    private static final String VALID_TIME = "09:00";

    private static final String INVALID_DATE = "2025-02-30"; // Invalid date
    private static final String INVALID_TIME = "25:00";      // Invalid time

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
                PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                expectedCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_failure() {
        // Missing name prefix
        assertParseFailure(parser,
                VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                String.format("Invalid command format! Expected: %s", MESSAGE_USAGE));

        // Missing event prefix
        assertParseFailure(parser,
                PREFIX_NAME + VALID_NAME + " "
                        + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                String.format("Invalid command format! Expected: %s", MESSAGE_USAGE));

        // Missing date prefix
        assertParseFailure(parser,
                PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + VALID_DATE + " "
                        + PREFIX_TIME + VALID_TIME,
                String.format("Invalid command format! Expected: %s", MESSAGE_USAGE));

        // Missing time prefix
        assertParseFailure(parser,
                PREFIX_NAME + VALID_NAME + " "
                        + PREFIX_EVENT + VALID_EVENT + " "
                        + PREFIX_DATE + VALID_DATE,
                String.format("Invalid command format! Expected: %s", MESSAGE_USAGE));
    }
}
