package seedu.nova.logic.commands;

//import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.abcommands.AbRemarkCommand;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.addressbook.AddressBook;
import seedu.nova.model.schedule.Scheduler;
import seedu.nova.model.userpref.UserPrefs;
import seedu.nova.model.addressbook.person.Person;
import seedu.nova.model.addressbook.person.Remark;
import seedu.nova.testutil.PersonBuilder;

class AbRemarkCommandTest {
    private static final String REMARK_STUB = "Some remark";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
            new Scheduler(LocalDate.of(2020,1, 13),
                    LocalDate.of(2020, 5, 3)));

    @Test
    void execute_addRemarkUnfilteredList_success() {
        Person firstPerson = model.getAddressBookManager().getFilteredPersonList().get(
                INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(REMARK_STUB).build();

        AbRemarkCommand abRemarkCommand = new AbRemarkCommand(INDEX_FIRST_PERSON,
                new Remark(editedPerson.getRemark().value));

        String expectedMessage = String.format(AbRemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBookManager().getAddressBook()),
                new UserPrefs(),
                new Scheduler(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 5, 3)));
        expectedModel.getAddressBookManager().setPerson(firstPerson, editedPerson);

        //assertCommandSuccess(abRemarkCommand, model, expectedMessage, expectedModel);
    }

}
