package seedu.nova.logic.parser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.nova.logic.commands.AbAddCommand;
import seedu.nova.logic.commands.AbClearCommand;
import seedu.nova.logic.commands.AbEditCommand;
import seedu.nova.logic.commands.AbListCommand;
import seedu.nova.logic.commands.AbRemarkCommand;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.ExitCommand;
import seedu.nova.logic.commands.HelpCommand;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AbAddCommand.COMMAND_WORD:
            return new AbAddCommandParser().parse(arguments);

        case AbEditCommand.COMMAND_WORD:
            return new AbEditCommandParser().parse(arguments);

        case seedu.nova.logic.commands.AbDeleteCommand.COMMAND_WORD:
            return new AbDeleteCommandParser().parse(arguments);

        case AbClearCommand.COMMAND_WORD:
            return new AbClearCommand();

        case seedu.nova.logic.commands.AbFindCommand.COMMAND_WORD:
            return new AbFindCommandParser().parse(arguments);

        case AbListCommand.COMMAND_WORD:
            return new AbListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AbRemarkCommand.COMMAND_WORD:
            return new AbRemarkCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}