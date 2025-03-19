package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERWRITE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.NoteType;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NoteCommand object
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NoteCommand
     * and returns an NoteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPEND, PREFIX_OVERWRITE, PREFIX_CLEAR);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_APPEND, PREFIX_OVERWRITE, PREFIX_CLEAR);

        if (argMultimap.getValue(PREFIX_APPEND).isPresent()) {
            return new NoteCommand(index, argMultimap.getValue(PREFIX_APPEND).get(), NoteType.APPEND);
        } else if (argMultimap.getValue(PREFIX_OVERWRITE).isPresent()) {
            return new NoteCommand(index, argMultimap.getValue(PREFIX_OVERWRITE).get(), NoteType.OVERWRITE);
        } else if (argMultimap.getValue(PREFIX_CLEAR).isPresent()) {
            return new NoteCommand(index, "", NoteType.CLEAR);
        }
        return new NoteCommand(index, "", NoteType.NONE);
    }

}
