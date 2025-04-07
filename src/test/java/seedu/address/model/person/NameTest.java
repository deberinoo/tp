package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphabetic characters
        assertFalse(Name.isValidName("peter*")); // contains invalid symbol
        assertFalse(Name.isValidName("12345")); // numbers only
        assertFalse(Name.isValidName("peter the 2nd")); // contains numbers
        assertFalse(Name.isValidName("O''Connor")); // consecutive apostrophes
        assertFalse(Name.isValidName("Jean--Luc")); // consecutive hyphens
        assertFalse(Name.isValidName("S//O")); // consecutive slashes
        assertFalse(Name.isValidName(" John")); // leading space
        assertFalse(Name.isValidName("-John")); // leading hyphen
        assertFalse(Name.isValidName("'John")); // leading apostrophe
        assertFalse(Name.isValidName("/John")); // leading slash
        assertFalse(Name.isValidName("John ")); // trailing space
        assertFalse(Name.isValidName("John-")); // trailing hyphen
        assertFalse(Name.isValidName("John'")); // trailing apostrophe
        assertFalse(Name.isValidName("John/")); // trailing slash

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets and space
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("O'Connor")); // with apostrophe
        assertTrue(Name.isValidName("Jean-Luc")); // with hyphen
        assertTrue(Name.isValidName("Ravi S/O Lim")); // with slash
        assertTrue(Name.isValidName("Anne-Marie O'Connor")); // multiple special chars
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr")); // long names
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
