package seedu.nova.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.nova.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.nova.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.nova.commons.core.Messages;
import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.abcommands.AbClearCommand;
import seedu.nova.logic.commands.abcommands.AbEditCommand;
import seedu.nova.logic.commands.abcommands.AbEditCommand.EditPersonDescriptor;
import seedu.nova.model.addressbook.AddressBook;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.schedule.Scheduler;
import seedu.nova.model.userpref.UserPrefs;
import seedu.nova.model.addressbook.person.Person;
import seedu.nova.testutil.EditPersonDescriptorBuilder;
import seedu.nova.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AbEditCommand.
 */
public class AbEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Scheduler(LocalDate.of(2020,
            1, 13), LocalDate.of(2020, 5, 3)));

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        AbEditCommand abEditCommand = new AbEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AbEditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBookManager().getAddressBook()),
                new UserPrefs(),
                new Scheduler(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 5, 3)));
        expectedModel.getAddressBookManager().setPerson(model.getAddressBookManager().getFilteredPersonList().get(0),
                editedPerson);

        assertCommandSuccess(abEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getAddressBookManager().getFilteredPersonList().size());
        Person lastPerson = model.getAddressBookManager().getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        AbEditCommand abEditCommand = new AbEditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(AbEditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBookManager().getAddressBook()),
                new UserPrefs(),
                new Scheduler(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 5, 3)));
        expectedModel.getAddressBookManager().setPerson(lastPerson, editedPerson);

        assertCommandSuccess(abEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        AbEditCommand abEditCommand = new AbEditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson =
                model.getAddressBookManager().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(AbEditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBookManager().getAddressBook()),
                new UserPrefs(),
                new Scheduler(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 5, 3)));

        assertCommandSuccess(abEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getAddressBookManager().getFilteredPersonList().get(
                INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        AbEditCommand abEditCommand = new AbEditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(AbEditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBookManager().getAddressBook()),
                new UserPrefs(),
                new Scheduler(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 5, 3)));
        expectedModel.getAddressBookManager().setPerson(model.getAddressBookManager().getFilteredPersonList().get(0),
                editedPerson);

        assertCommandSuccess(abEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getAddressBookManager().getFilteredPersonList().get(
                INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        AbEditCommand abEditCommand = new AbEditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(abEditCommand, model, AbEditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in nova book
        Person personInList = model.getAddressBookManager().getAddressBook().getPersonList().get(
                INDEX_SECOND_PERSON.getZeroBased());
        AbEditCommand abEditCommand = new AbEditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(abEditCommand, model, AbEditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getAddressBookManager().getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        AbEditCommand abEditCommand = new AbEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(abEditCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of nova book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of nova book list
        assertTrue(
                outOfBoundIndex.getZeroBased() < model.getAddressBookManager().getAddressBook().getPersonList().size());

        AbEditCommand abEditCommand = new AbEditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(abEditCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AbEditCommand standardCommand = new AbEditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        AbEditCommand commandWithSameValues = new AbEditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new AbClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AbEditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AbEditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
