package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagsCommandTest {
    private Model model;
    private Model expectedModel;
    private Person alice;
    private Person bob;
    private Person charlie;
    private Person dave;

    @BeforeEach
    public void setUp() {
        // Create test persons with known tags
        alice = new PersonBuilder().withName("Alice")
                .withTags("friends").build();
        bob = new PersonBuilder().withName("Bob")
                .withTags("friends", "colleagues").build();
        charlie = new PersonBuilder().withName("Charlie")
                .withTags("colleagues").build();
        dave = new PersonBuilder().withName("Dave").build();

        // Create model with test data
        model = new ModelManager();
        expectedModel = new ModelManager();
        model.addPerson(alice);
        model.addPerson(bob);
        model.addPerson(charlie);
        model.addPerson(dave);
        expectedModel.addPerson(alice);
        expectedModel.addPerson(bob);
        expectedModel.addPerson(charlie);
        expectedModel.addPerson(dave);
    }

    @Test
    public void execute_listAllTags_success() {
        String expectedMessage = String.format(TagsCommand.MESSAGE_SUCCESS, "colleagues, friends");
        TagsCommand command = new TagsCommand();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filterBySingleTag_personsFound() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friends"));
        String expectedMessage = String.format(TagsCommand.MESSAGE_PERSONS_WITH_TAGS, "friends");
        TagsCommand command = new TagsCommand(tags);

        expectedModel.updateFilteredPersonList(person -> person.getTags().containsAll(tags));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Makes sure the filter person list is correct
        List<Person> filteredPersons = model.getFilteredPersonList();
        assertEquals(2, filteredPersons.size());
        assertTrue(filteredPersons.contains(alice));
        assertTrue(filteredPersons.contains(bob));
    }

    @Test
    public void execute_filterByMultipleTags_personsFound() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friends"));
        tags.add(new Tag("colleagues"));
        String expectedMessage = String.format(TagsCommand.MESSAGE_PERSONS_WITH_TAGS, "colleagues, friends");
        TagsCommand command = new TagsCommand(tags);

        expectedModel.updateFilteredPersonList(person -> person.getTags().containsAll(tags));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Makes sure the filter person list is correct
        List<Person> filteredPersons = model.getFilteredPersonList();
        assertEquals(1, filteredPersons.size());
        assertTrue(filteredPersons.contains(bob));
    }

    @Test
    public void execute_filterByTag_noPersonFound() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("unknown"));
        String expectedMessage = String.format(TagsCommand.MESSAGE_NO_TAGS_FOUND, "unknown");
        TagsCommand command = new TagsCommand(tags);

        expectedModel.updateFilteredPersonList(person -> person.getTags().containsAll(tags));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(0, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_filterByMultipleTags_noPersonFound() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friends"));
        tags.add(new Tag("nonexistent"));
        String expectedMessage = String.format(TagsCommand.MESSAGE_NO_TAGS_FOUND, "friends, nonexistent");
        TagsCommand command = new TagsCommand(tags);

        expectedModel.updateFilteredPersonList(person -> person.getTags().containsAll(tags));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(0, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_noTagsInAddressBook_showsNoTagsMessage() {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        TagsCommand command = new TagsCommand();
        assertEquals(
                new CommandResult(TagsCommand.MESSAGE_NO_TAGS_EXIST),
                command.execute(emptyModel)
        );
    }

    @Test
    public void equals() {
        Set<Tag> friendsTag = new HashSet<>(Set.of(new Tag("friends")));
        Set<Tag> colleaguesTag = new HashSet<>(Set.of(new Tag("colleagues")));
        Set<Tag> multipleTags = new HashSet<>(Set.of(new Tag("friends"), new Tag("colleagues")));

        TagsCommand listAllTagsCommand = new TagsCommand();
        TagsCommand filterFriendsCommand = new TagsCommand(friendsTag);
        TagsCommand filterColleaguesCommand = new TagsCommand(colleaguesTag);
        TagsCommand filterMultipleTagsCommand = new TagsCommand(multipleTags);

        // same object -> returns true
        assertEquals(listAllTagsCommand, listAllTagsCommand);
        assertEquals(filterFriendsCommand, filterFriendsCommand);

        // same values -> returns true
        TagsCommand listAllTagsCommandCopy = new TagsCommand();
        assertEquals(listAllTagsCommand, listAllTagsCommandCopy);
        TagsCommand filterFriendsCommandCopy = new TagsCommand(friendsTag);
        assertEquals(filterFriendsCommand, filterFriendsCommandCopy);

        // different types -> returns false
        assertNotEquals(1, listAllTagsCommand);
        assertNotEquals(1, filterFriendsCommand);

        // null -> returns false
        assertNotEquals(null, listAllTagsCommand);
        assertNotEquals(null, filterFriendsCommand);

        // different commands -> returns false
        assertNotEquals(listAllTagsCommand, filterFriendsCommand);
        assertNotEquals(filterFriendsCommand, filterColleaguesCommand);
        assertNotEquals(filterFriendsCommand, filterMultipleTagsCommand);
    }
}
