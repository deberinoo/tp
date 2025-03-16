package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OVERWRITE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Edit the notes of an existing person in the address book.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_APPEND + " APPEND] "
            + "[" + PREFIX_OVERWRITE + " OVERWRITE] "
            + "[" + PREFIX_CLEAR + " CLEAR]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPEND + " Gave qns 2 as homework ";

    public static final String MESSAGE_NOTE_PERSON_SUCCESS = "Note updated";
    private final Index index;
    private final String note;
    private final NoteType noteType;

    /**
     * @param index of the person in the filtered person list to note
     */
    public NoteCommand(Index index, String note, NoteType noteType) {
        requireNonNull(index);

        this.index = index;
        this.note = note;
        this.noteType = noteType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToNote = lastShownList.get(index.getZeroBased());
        updateNoteForPerson(personToNote, note, noteType);
        model.updateFilteredPersonList(person -> person.equals(personToNote));
        return new CommandResult(MESSAGE_NOTE_PERSON_SUCCESS);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToNote}
     * updated with {@code note} based on {@code noteType}.
     */
    private static void updateNoteForPerson(Person personToNote, String note, NoteType noteType) {
        assert personToNote != null;
        if (noteType.equals(NoteType.APPEND)) {
            if (personToNote.getNote().isEmpty()) {
                personToNote.setNote(note);
            } else {
                personToNote.setNote(personToNote.getNote() + "\n" + note);
            }
        } else if (noteType.equals(NoteType.OVERWRITE)) {
            personToNote.setNote(note);
        } else if (noteType.equals(NoteType.CLEAR)) {
            personToNote.setNote("");
        } else {
            personToNote.setNote(personToNote.getNote());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand otherNoteCommand = (NoteCommand) other;
        return index.equals(otherNoteCommand.index)
                && note.equals(otherNoteCommand.note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("note", note)
                .toString();
    }
}
