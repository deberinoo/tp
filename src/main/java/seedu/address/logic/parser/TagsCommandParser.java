package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.TagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagsCommand object
 */
public class TagsCommandParser implements Parser<TagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagsCommand
     * and returns a TagsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagsCommand.MESSAGE_USAGE));
        }

        Set<Tag> tags = new HashSet<>();
        for (String tagName : argMultimap.getAllValues(PREFIX_TAG)) {
            if (tagName.trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagsCommand.MESSAGE_USAGE));
            }
            try {
                // The Tag constructor will handle case normalization
                tags.add(ParserUtil.parseTag(tagName));
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagsCommand.MESSAGE_USAGE), pe);
            }
        }

        return tags.isEmpty() ? new TagsCommand() : new TagsCommand(tags);
    }
}
