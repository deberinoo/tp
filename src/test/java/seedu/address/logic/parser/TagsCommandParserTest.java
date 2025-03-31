package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagsCommand;
import seedu.address.model.tag.Tag;

public class TagsCommandParserTest {
    private final TagsCommandParser parser = new TagsCommandParser();
    private final String friendsTag = " " + PREFIX_TAG + "friends";
    private final String colleaguesTag = " " + PREFIX_TAG + "colleagues";

    @Test
    public void parse_emptyArgs_success() {
        assertParseSuccess(parser, "", new TagsCommand());
        assertParseSuccess(parser, "   ", new TagsCommand());
    }

    @Test
    public void parse_singleTag_success() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friends"));
        assertParseSuccess(parser, friendsTag, new TagsCommand(expectedTags));
    }

    @Test
    public void parse_multipleTags_success() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friends"));
        expectedTags.add(new Tag("colleagues"));
        assertParseSuccess(parser, friendsTag + colleaguesTag, new TagsCommand(expectedTags));
    }

    @Test
    public void parse_duplicateTags_success() {
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friends"));
        assertParseSuccess(parser, friendsTag + friendsTag, new TagsCommand(expectedTags));
    }

    @Test
    public void parse_missingTagValue_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_TAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TAG + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, friendsTag + " " + PREFIX_TAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, "x/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preambleBeforeTag_throwsParseException() {
        assertParseFailure(parser, "something" + friendsTag,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagsCommand.MESSAGE_USAGE));
    }
}
