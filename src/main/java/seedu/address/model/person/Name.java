package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names must start and end with a letter.\n"
                    + "In addition, only ', -, / are allowed once.\n"
                    + "Numbers and other special characters are not allowed.\n"
                    + "Valid examples: John Doe, O'Connor, Jean-Luc, Ravi S/O Lim\n"
                    + "Invalid examples: O''Connor, -John, Mary-, S//O, 007\n";

    /*
     * The regex enforces:
     * - Starts and ends with a letter
     * - Can contain letters, spaces, apostrophes, hyphens, and slashes
     * - No consecutive special characters
     * - No leading/trailing spaces and special characters
     */
    public static final String VALIDATION_REGEX =
            "^(?!.*[ '-/]$)(?!.*'')(?!.*--)(?!.*//)[A-Za-z]+(?:[\\s'-][A-Za-z]+)*(?:/[A-Za-z]+)?(?:[\\s'-][A-Za-z]+)*$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
