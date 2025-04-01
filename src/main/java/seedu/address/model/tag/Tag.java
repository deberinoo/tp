package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be a single alphanumeric combination.\n"
            + "Valid examples: 'Tag123', 'HelloWorld', 'Alpha123Beta', 'Test1234'.\n"
            + "Invalid examples: 'Tag 123' (contains a space), 'Hello-World' (contains a hyphen), "
            + "'Alpha_123' (contains an underscore), '#Tag123' (contains a special character).";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;
    private final String normalizedTagName; // For case-insensitive comparison

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        this.normalizedTagName = tagName.toLowerCase(); // Store lowercase version for comparison
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag otherTag)) {
            return false;
        }

        return normalizedTagName.equals(otherTag.normalizedTagName); // Case-insensitive comparison
    }

    @Override
    public int hashCode() {
        return normalizedTagName.hashCode(); // Consistent with equals
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return tagName; // Preserve original case for display
    }
}
