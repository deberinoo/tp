package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SwitchCommandParser implements Parser<SwitchCommand> {

    @Override
    public SwitchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        } else if (!SwitchCommand.panelNames.contains(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }
        return new SwitchCommand(trimmedArgs);
    }
}