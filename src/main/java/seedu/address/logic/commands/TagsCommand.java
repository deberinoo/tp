package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Lists all existing tags in the address book or filters persons by specified tags.
 */
public class TagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all existing tags in the address book "
            + "or lists all persons with the specified tags.\n"
            + "Parameters: [" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORD + " (shows all tags)\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_TAG + "friends " + PREFIX_TAG + "colleagues";

    public static final String MESSAGE_SUCCESS = "Listing all tags: %1$s";
    public static final String MESSAGE_PERSONS_WITH_TAGS = "Listed all persons with tags: %1$s";
    public static final String MESSAGE_NO_TAGS_FOUND = "No persons found with all specified tags: %1$s";

    private final Set<Tag> tagsToFilter;

    /**
     * Creates a TagsCommand to list all tags.
     */
    public TagsCommand() {
        this.tagsToFilter = null;
    }

    /**
     * Creates a TagsCommand to filter persons by the specified tags.
     */
    public TagsCommand(Set<Tag> tags) {
        this.tagsToFilter = new HashSet<>(tags);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (tagsToFilter == null || tagsToFilter.isEmpty()) {
            // Show all tags case
            ObservableList<Tag> tags = model.getTagList();
            String formattedTags = UniqueTagList.getSortedTags(tags).stream().map(Tag::toString)
                    .collect(Collectors.joining(", "));
            return new CommandResult(String.format(MESSAGE_SUCCESS, formattedTags));
        } else {
            // Filter by tags case
            model.updateFilteredPersonList(person ->
                    person.getTags().containsAll(tagsToFilter));

            if (model.getFilteredPersonList().isEmpty()) {
                String tagsString = tagsToFilter.stream()
                        .map(Tag::toString)
                        .collect(Collectors.joining(", "));
                return new CommandResult(String.format(MESSAGE_NO_TAGS_FOUND, tagsString));
            }

            String tagsString = tagsToFilter.stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(", "));

            return new CommandResult(String.format(MESSAGE_PERSONS_WITH_TAGS, tagsString));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagsCommand otherCommand)) {
            return false;
        }

        return (tagsToFilter == null && otherCommand.tagsToFilter == null)
                || (tagsToFilter != null && tagsToFilter.equals(otherCommand.tagsToFilter));
    }
}
