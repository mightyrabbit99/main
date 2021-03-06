package seedu.nova.logic.commands.abcommands;

import static java.util.Objects.requireNonNull;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.model.Model;
import seedu.nova.model.addressbook.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class AbFindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD n\\[MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n\\alice";

    private final NameContainsKeywordsPredicate predicate;

    public AbFindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.getAddressBookManager().updateFilteredPersonList(predicate);
        String listOfPeople = "Found the following: " + "\n";
        for (int i = 0; i < model.getAddressBookManager().getFilteredPersonList().size(); i++) {
            listOfPeople =
                    listOfPeople + (i + 1) + ". " + model.getAddressBookManager().getFilteredPersonList().get(i) + "\n";
        }

        return new CommandResult(listOfPeople);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AbFindCommand // instanceof handles nulls
                && predicate.equals(((AbFindCommand) other).predicate)); // state check
    }
}
