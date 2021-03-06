package seedu.nova.logic.parser.eventparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.nova.logic.commands.eventcommands.EventAddLessonCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.common.time.duration.WeekDayDuration;
import seedu.nova.model.event.EventDetails;
import seedu.nova.model.plan.AbsoluteTask;

/**
 * Parses input arguments and creates a new EventAddLessonCommand object
 */
public class EventAddLessonCommandParser implements Parser<EventAddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventAddStudyCommand
     * and returns an EventAddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventAddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_DESC, CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_DESC, CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventAddLessonCommand.MESSAGE_USAGE));
        }

        String desc = argMultimap.getValue(CliSyntax.PREFIX_DESC).get();
        String venue = argMultimap.getValue(CliSyntax.PREFIX_VENUE).get();

        String dateTime = argMultimap.getValue(CliSyntax.PREFIX_TIME).get();
        String[] dateTimeArr = dateTime.split(" ");

        if (dateTimeArr.length != 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventAddLessonCommand.MESSAGE_USAGE));
        }

        DayOfWeek day = ParserUtil.parseDay(dateTimeArr[0]);
        LocalTime startTime = ParserUtil.parseTime(dateTimeArr[1]);
        LocalTime endTime = ParserUtil.parseTime(dateTimeArr[2]);

        EventDetails details = new EventDetails(desc, venue);
        WeekDayDuration wdd = new WeekDayDuration(day, startTime, endTime);
        return new EventAddLessonCommand(new AbsoluteTask(details, wdd));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
