package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;
import static seedu.address.model.util.SampleDataUtil.getSamplePersons;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {
    @Test
    public void testGetSamplePersons() {
        Person[] persons = getSamplePersons();

        // Ensure that the array has 6 persons
        assertEquals(6, persons.length);

        // Check the first person (Alex Yeoh)
        Person alex = persons[0];
        assertEquals("Alex Yeoh", alex.getName().toString());
        assertEquals("87438807", alex.getPhone().toString());
        assertEquals("alexyeoh@example.com", alex.getEmail().toString());
        assertTrue(alex.getTags().contains(new Tag("P3")));

        // Check the second person (Bernice Yu)
        Person bernice = persons[1];
        assertEquals("Bernice Yu", bernice.getName().toString());
        assertEquals("99272758", bernice.getPhone().toString());
        assertEquals("99272759", bernice.getParentPhone().toString());
        assertEquals("berniceyu@example.com", bernice.getEmail().toString());
        assertTrue(bernice.getTags().contains(new Tag("Math")));
        assertTrue(bernice.getTags().contains(new Tag("P4")));
    }

    @Test
    public void testGetSampleAddressBook() {
        ReadOnlyAddressBook addressBook = getSampleAddressBook();

        // Ensure that the address book has 6 persons
        assertEquals(6, addressBook.getPersonList().size());

        // Check the first person (Alex Yeoh)
        Person alex = addressBook.getPersonList().get(0);
        assertEquals("Alex Yeoh", alex.getName().toString());
        assertEquals("87438807", alex.getPhone().toString());
        assertEquals("alexyeoh@example.com", alex.getEmail().toString());
        assertTrue(alex.getTags().contains(new Tag("P3")));

        // Check the second person (Bernice Yu)
        Person bernice = addressBook.getPersonList().get(1);
        assertEquals("Bernice Yu", bernice.getName().toString());
        assertEquals("99272758", bernice.getPhone().toString());
        assertEquals("99272759", bernice.getParentPhone().toString());
        assertEquals("berniceyu@example.com", bernice.getEmail().toString());
        assertTrue(bernice.getTags().contains(new Tag("Math")));
        assertTrue(bernice.getTags().contains(new Tag("P4")));
    }
}
