package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.NoteType;

public class NoteCommandParserTest {
    private final NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_validArgs_returnsNoteCommand() {
        assertParseSuccess(parser, "1", new NoteCommand(INDEX_FIRST_PERSON, "", NoteType.NONE));
        assertParseSuccess(parser, "1 -a APPEND", new NoteCommand(INDEX_FIRST_PERSON,
                "APPEND", NoteType.APPEND));
        assertParseSuccess(parser, "1 -o OVERWRITE", new NoteCommand(INDEX_FIRST_PERSON,
                "OVERWRITE", NoteType.OVERWRITE));
        assertParseSuccess(parser, "1 -c", new NoteCommand(INDEX_FIRST_PERSON,
                "", NoteType.CLEAR));
    }

    @Test
    public void parse_multipleValidArgs_returnsNoteCommand() {
        assertParseSuccess(parser, "1 -a APPEND -o OVERWRITE", new NoteCommand(INDEX_FIRST_PERSON,
                "APPEND", NoteType.APPEND));
        assertParseSuccess(parser, "1 -o OVERWRITE -c CLEAR", new NoteCommand(INDEX_FIRST_PERSON,
                "OVERWRITE", NoteType.OVERWRITE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }
}
