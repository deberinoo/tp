package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Lists all existing tags in the address book or filters persons by a specific tag.
 */
public class TagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all existing tags in the address book "
            + "or lists all persons with the specified tag.\n"
            + "Parameters: [" + PREFIX_TAG + "TAG]\n"
            + "Example 1: " + COMMAND_WORD + " (shows all tags)\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_TAG + "friends (lists persons tagged as 'friends')";

    public static final String MESSAGE_SUCCESS = "Listing all tags: %1$s";
    public static final String MESSAGE_PERSONS_WITH_TAG = "Listed all persons with tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag %1$s not found in the address book.";

    private final Tag tagToFilter;

    /**
     * Creates a TagsCommand to list all tags.
     */
    public TagsCommand() {
        this.tagToFilter = null;
    }

    /**
     * Creates a TagsCommand to filter persons by the specified tag.
     */
    public TagsCommand(Tag tag) {
        this.tagToFilter = tag;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (tagToFilter == null) {
            // Displays all tags
            ObservableList<Tag> tags = model.getTagList();
            String formattedTags = UniqueTagList.getSortedTags(tags).stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(", "));
            return new CommandResult(String.format(MESSAGE_SUCCESS, formattedTags));
        } else {
            // Filter person by tags
            model.updateFilteredPersonList(person -> person.getTags().contains(tagToFilter));

            if (model.getFilteredPersonList().isEmpty()) {
                return new CommandResult(String.format(MESSAGE_TAG_NOT_FOUND, tagToFilter));
            }

            return new CommandResult(String.format(MESSAGE_PERSONS_WITH_TAG, tagToFilter));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagsCommand)) {
            return false;
        }

        TagsCommand otherCommand = (TagsCommand) other;
        return (tagToFilter == null && otherCommand.tagToFilter == null)
                || (tagToFilter != null && tagToFilter.equals(otherCommand.tagToFilter));
    }
}
