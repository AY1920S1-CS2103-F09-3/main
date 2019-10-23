package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ST2334;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ST2334;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ST2334;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ST2334;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalModule.ST2334;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ModuleBuilder;

/**
 * A test class for {@code Module}.
 */
public class ModuleTest {
    @Test
    public void equals() {
        // same values -> returns true
        Module CS3244_COPY = new ModuleBuilder(CS3244).build();
        assertTrue(CS3244.equals(CS3244_COPY));

        // same object -> returns true
        assertTrue(CS3244.equals(CS3244));

        // null -> returns false
        assertFalse(CS3244.equals(null));

        // different type -> returns false
        assertFalse(CS3244.equals(5));

        // different person -> returns false
        assertFalse(CS3244.equals(ST2334));

        // different name -> returns false
        Module editedCS3244 = new ModuleBuilder(CS3244).withName(VALID_NAME_ST2334).build();
        assertFalse(CS3244.equals(editedCS3244));

        // different phone -> returns false
        editedCS3244 = new ModuleBuilder(CS3244).withPhone(VALID_PHONE_ST2334).build();
        assertFalse(CS3244.equals(editedCS3244));

        // different email -> returns false
        editedCS3244 = new ModuleBuilder(CS3244).withEmail(VALID_EMAIL_ST2334).build();
        assertFalse(CS3244.equals(editedCS3244));

        // different address -> returns false
        editedCS3244 = new ModuleBuilder(CS3244).withAddress(VALID_ADDRESS_ST2334).build();
        assertFalse(CS3244.equals(editedCS3244));

        // different tags -> returns false
        editedCS3244 = new ModuleBuilder(CS3244).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(CS3244.equals(editedCS3244));
    }
}
