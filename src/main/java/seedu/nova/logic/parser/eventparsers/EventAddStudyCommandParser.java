package seedu.nova.logic.parser.eventparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.nova.logic.commands.eventcommands.EventAddStudyCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.common.time.duration.DateTimeDuration;
import seedu.nova.model.event.Event;
import seedu.nova.model.event.EventDetails;

/**
 * Parses input arguments and creates a new EventAddStudyCommand object
 */
public class EventAddStudyCommandParser implements Parser<EventAddStudyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventAddStudyCommand
     * and returns an EventAddStudyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventAddStudyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_DESC, CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_DESC, CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventAddStudyCommand.MESSAGE_USAGE));
        }

        String desc = argMultimap.getValue(CliSyntax.PREFIX_DESC).get();
        String venue = argMultimap.getValue(CliSyntax.PREFIX_VENUE).get();

        String dateTime = argMultimap.getValue(CliSyntax.PREFIX_TIME).get();
        String[] dateTimeArr = dateTime.split(" ");

        if (dateTimeArr.length != 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventAddStudyCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(dateTimeArr[0]);
        LocalTime startTime = ParserUtil.parseTime(dateTimeArr[1]);
        LocalTime endTime = ParserUtil.parseTime(dateTimeArr[2]);

        EventDetails details = new EventDetails(desc, venue);
        DateTimeDuration dtd = new DateTimeDuration(date, startTime, endTime);
        return new EventAddStudyCommand(new Event(details, dtd));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
