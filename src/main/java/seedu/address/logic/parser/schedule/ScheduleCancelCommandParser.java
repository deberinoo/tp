package seedu.address.logic.parser.schedule;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.ScheduleCancelCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ScheduleCancelCommand object
 */
public class ScheduleCancelCommandParser implements Parser<ScheduleCancelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCancelCommand
     * and returns a ScheduleCancelCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ScheduleCancelCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ScheduleCancelCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCancelCommand.MESSAGE_USAGE), pe);
        }
    }
}
