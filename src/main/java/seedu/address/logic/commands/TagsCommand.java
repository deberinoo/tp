    package seedu.address.logic.commands;

    import static java.util.Objects.requireNonNull;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;
    import java.util.stream.Collectors;

    import javafx.collections.ObservableList;
    import seedu.address.model.Model;
    import seedu.address.model.tag.Tag;
    import seedu.address.model.tag.UniqueTagList;

    /**
     * Lists all existing tags in the address book.
     */
    public class TagsCommand extends Command {

        public static final String COMMAND_WORD = "tags";

        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all existing tags in the address book.\n"
                + "Example: " + COMMAND_WORD;

        public static final String MESSAGE_SUCCESS = "Listing all tags: %1$s";

        @Override
        public CommandResult execute(Model model) {
            requireNonNull(model);
            ObservableList<Tag> tags = model.getTagList();

            // Sort the tags alphabetically
            String formattedTags = UniqueTagList.getSortedTags(tags).stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(", "));

            return new CommandResult(String.format(MESSAGE_SUCCESS, formattedTags));
        }
    }