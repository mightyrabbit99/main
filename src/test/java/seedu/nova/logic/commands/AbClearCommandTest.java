package seedu.nova.logic.commands;

import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.abcommands.AbClearCommand;
import seedu.nova.model.addressbook.AddressBook;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.schedule.Scheduler;
import seedu.nova.model.userpref.UserPrefs;

public class AbClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new AbClearCommand(), model, AbClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Scheduler(LocalDate.of(2020, 1,
                13),
                LocalDate.of(2020, 5, 3)));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Scheduler(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 5, 3)));
        expectedModel.getAddressBookManager().setAddressBook(new AddressBook());

        assertCommandSuccess(new AbClearCommand(), model, AbClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
