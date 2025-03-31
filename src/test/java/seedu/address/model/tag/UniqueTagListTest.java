package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniqueTagListTest {
    private final UniqueTagList uniqueTagList = new UniqueTagList();
    private final Tag friendsTag = new Tag("friends");
    private final Tag colleaguesTag = new Tag("colleagues");

    @Test
    public void createOrGetTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.createOrGetTag(null));
    }

    @Test
    public void createOrGetTag_newTag_addsSuccessfully() {
        Tag result = uniqueTagList.createOrGetTag(friendsTag);
        assertEquals(friendsTag, result);
        assertTrue(uniqueTagList.asUnmodifiableObservableList().contains(friendsTag));
    }

    @Test
    public void createOrGetTag_existingTag_returnsExisting() {
        uniqueTagList.createOrGetTag(friendsTag);
        Tag duplicateTag = new Tag("friends");
        Tag result = uniqueTagList.createOrGetTag(duplicateTag);
        assertEquals(friendsTag, result);
        assertEquals(1, uniqueTagList.asUnmodifiableObservableList().size());
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        ObservableList<Tag> unmodifiableList = uniqueTagList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(new Tag("test")));
    }

    @Test
    public void getSortedTags_emptyList_returnsEmptyList() {
        ObservableList<Tag> emptyList = FXCollections.observableArrayList();
        List<Tag> sortedTags = UniqueTagList.getSortedTags(emptyList);
        assertTrue(sortedTags.isEmpty());
    }

    @Test
    public void getSortedTags_unsortedList_returnsSortedList() {
        Tag tagA = new Tag("a");
        Tag tagB = new Tag("b");
        Tag tagC = new Tag("c");

        ObservableList<Tag> unsortedList = FXCollections.observableArrayList(
                tagC, tagA, tagB
        );

        List<Tag> sortedTags = UniqueTagList.getSortedTags(unsortedList);
        assertEquals(Arrays.asList(tagA, tagB, tagC), sortedTags);
    }

    @Test
    public void equals() {
        UniqueTagList differentUniqueTagList = new UniqueTagList();
        differentUniqueTagList.createOrGetTag(colleaguesTag);

        // same object -> returns true
        assertEquals(uniqueTagList, uniqueTagList);

        // same values -> returns true
        UniqueTagList uniqueTagListCopy = new UniqueTagList();
        assertEquals(uniqueTagList, uniqueTagListCopy);

        // different types -> returns false
        assertNotEquals(1, uniqueTagList);

        // null -> returns false
        assertNotEquals(null, uniqueTagList);

        // different tags -> returns false
        assertNotEquals(uniqueTagList, differentUniqueTagList);
    }

    @Test
    public void toString_emptyList_returnsEmptyStringRepresentation() {
        assertEquals("[]", uniqueTagList.toString());
    }

    @Test
    public void toString_nonEmptyList_returnsCorrectStringRepresentation() {
        uniqueTagList.createOrGetTag(friendsTag);
        uniqueTagList.createOrGetTag(colleaguesTag);
        assertEquals("[friends, colleagues]", uniqueTagList.toString());
    }

    @Test
    public void clear_emptyList_remainsEmpty() {
        uniqueTagList.clear();
        assertTrue(uniqueTagList.asUnmodifiableObservableList().isEmpty());
    }

    @Test
    public void clear_nonEmptyList_clearsAllTags() {
        uniqueTagList.createOrGetTag(friendsTag);
        uniqueTagList.clear();
        assertTrue(uniqueTagList.asUnmodifiableObservableList().isEmpty());
    }
}