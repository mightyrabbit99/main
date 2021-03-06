package seedu.nova.model.category;

import static seedu.nova.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.nova.model.Category;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Category.isValidTagName(null));
    }

}
