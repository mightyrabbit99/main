package seedu.nova.model.plan;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.nova.model.common.time.duration.DateTimeDuration;
import seedu.nova.model.common.time.duration.WeekDayDuration;
import seedu.nova.model.common.time.slotlist.DateTimeSlotList;
import seedu.nova.model.event.Event;
import seedu.nova.model.event.EventDetails;
import seedu.nova.model.schedule.timeunit.Week;

/**
 * Task which generates definite events
 */
public class AbsoluteTask extends Task {
    private WeekDayDuration wdd;

    public AbsoluteTask(EventDetails details, WeekDayDuration wdd) {
        super(details, wdd.getDuration());
        this.wdd = wdd;
    }

    public WeekDayDuration getWeekDayDuration() {
        return this.wdd;
    }


    public List<DateTimeDuration> getPossibleSlot(DateTimeSlotList dsl) {
        return dsl.intersectWith(this.wdd).stream().filter(x -> x.getDuration().compareTo(this.duration) >= 0).collect(
                Collectors.toList());
    }

    @Override
    public Optional<Event> generateEvent(Week week) {
        Event event = new Event(this.details, this.wdd.toDateTimeDuration(week.getDuration().getStartDate()));
        if (isCompletedOnWeek(week.getDuration().getStartDate()) || week.addEvent(event)) {
            return Optional.empty();
        } else {
            return Optional.of(event);
        }
    }

}
