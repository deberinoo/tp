package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a list of unique tags that does not allow duplicate tags.
 * Tags are considered duplicates if they are equal according to their {@code equals()} method.
 *
 * <p>The class provides functionality to add tags if they don't already exist
 * and retrieve an unmodifiable view of the internal list of tags. This ensures
 * that the integrity of the list is maintained while allowing controlled access.
 *
 * <p>Use cases:
 * - To manage a central, unique list of tags that can be associated with other entities.
 * - To prevent duplicate tags from being created in the system.
 *
 * <p>Thread Safety:
 * This class is not thread-safe as it uses {@code ObservableList}, which
 * is not inherently synchronized.
 */
public class UniqueTagList implements Iterable<Tag> {
    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tag> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Ensures a tag exists in the list. If it doesn't, adds it to the list.
     * Returns the existing tag if found, or the newly added tag.
     *
     * @param toAdd The tag to be added or retrieved. Must not be null.
     * @return The existing tag if found, otherwise the newly added tag.
     */
    public Tag createOrGetTag(Tag toAdd) {
        requireNonNull(toAdd);
        for (Tag tag : internalList) {
            if (tag.equals(toAdd)) {
                return tag;
            }
        }
        internalList.add(toAdd);
        return toAdd;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     * This prevents external modification of the internal list.
     *
     * @return An unmodifiable view of the list of tags.
     */
    public ObservableList<Tag> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueTagList)) {
            return false;
        }

        UniqueTagList otherUniqueTagList = (UniqueTagList) other;
        return internalList.equals(otherUniqueTagList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    public void clear() {
        internalList.clear();
    }
}
