package seedu.address.logic.parser.schedule;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.ScheduleEditCommand;
import seedu.address.logic.commands.schedule.ScheduleEditCommand.EditScheduleDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ScheduleEditCommand object.
 */
public class ScheduleEditCommandParser implements Parser<ScheduleEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleEditCommand
     * and returns a ScheduleEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public ScheduleEditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECT,
                                PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION);

        // Extract index from preamble
        String preamble = argMultimap.getPreamble();

        // Check if preamble is empty, indicating no index provided
        if (preamble.isEmpty()) {
            throw new ParseException(ScheduleEditCommand.MESSAGE_USAGE);
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleEditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SUBJECT,
                                PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION);

        // Create the descriptor to store the edited values
        EditScheduleDescriptor editScheduleDescriptor = new EditScheduleDescriptor();

        // Set the individual fields if they are present in the argument map
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editScheduleDescriptor.setName(argMultimap.getValue(PREFIX_NAME).get());
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            editScheduleDescriptor.setSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editScheduleDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editScheduleDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editScheduleDescriptor.setDuration(ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
        }

        // Check if no fields were edited
        if (!editScheduleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ScheduleEditCommand.MESSAGE_NOT_EDITED);
        }

        // Return the ScheduleEditCommand with the parsed index and descriptor
        return new ScheduleEditCommand(index, editScheduleDescriptor);
    }
}
