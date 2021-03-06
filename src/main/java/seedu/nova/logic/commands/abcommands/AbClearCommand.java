package seedu.nova.logic.commands.abcommands;

import static java.util.Objects.requireNonNull;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.model.Model;
import seedu.nova.model.addressbook.AddressBook;

/**
 * Clears the nova book.
 */
public class AbClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.getAddressBookManager().setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
