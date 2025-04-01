package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals_differentCase_consideredEqual() {
        Tag tag1 = new Tag("Friends");
        Tag tag2 = new Tag("friends");
        Tag tag3 = new Tag("FRIENDS");

        assertEquals(tag1, tag2);
        assertEquals(tag1, tag3);
        assertEquals(tag2, tag3);
    }

    @Test
    public void hashCode_differentCase_sameHashCode() {
        Tag tag1 = new Tag("Friends");
        Tag tag2 = new Tag("friends");

        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void toString_preservesOriginalCase() {
        Tag mixedCaseTag = new Tag("FrIeNdS");
        assertEquals("FrIeNdS", mixedCaseTag.toString());
    }

    @Test
    public void equals_differentTags_consideredDifferent() {
        Tag tag1 = new Tag("Friends");
        Tag tag2 = new Tag("Colleagues");

        assertNotEquals(tag1, tag2);
    }
}
