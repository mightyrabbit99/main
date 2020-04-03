package seedu.nova.model.plan;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.nova.model.Schedule;
import seedu.nova.model.schedule.Day;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.WeakEvent;
import seedu.nova.model.util.time.duration.DateTimeDuration;

/**
 * Task which generates definite events
 */
public class WeakTask extends Task {
    private Duration maxDuration;
    private Duration total;

    private WeakTask(TaskDetails details, Duration maxDuration, Duration total) {
        super(details);
        this.maxDuration = maxDuration;
        this.total = total;
    }

    public static WeakTask get(String name, Duration mind, Duration maxd, Duration total) {
        return new WeakTask(new TaskDetails(name, mind, TaskFreq.DAILY), maxd, total);
    }

    public Duration getMaxDuration() {
        return maxDuration;
    }

    @Override
    public boolean generateEventOnDay(LocalDate date, Schedule sc) throws ImpossibleTaskException {
        if (hasEventOn(date) && sc.hasEvent(getEventOn(date))) {
            return false;
        } else {
            Day d = sc.getDay(date);
            List<DateTimeDuration> dtdLst = d.getFreeSlotList().getSlotList(getBaseDuration());
            if (dtdLst.isEmpty()) {
                throw new ImpossibleTaskException();
            } else {
                DateTimeDuration dtd = getBestTimeframe(dtdLst);
                Event newEvent = new WeakEvent(getName(), dtd, this);
                addEvent(newEvent);
                sc.addEvent(newEvent);
                return true;
            }
        }
    }

    private DateTimeDuration getBestTimeframe(List<DateTimeDuration> freeSlots) {
        assert !freeSlots.isEmpty() : "no free slot to choose";
        List<DateTimeDuration> lst = new ArrayList<>(freeSlots);
        Collections.sort(lst);
        DateTimeDuration dtd = lst.get(lst.size() - 1);
        if (dtd.getDuration().compareTo(getMaxDuration()) < 0) {
            return dtd;
        } else {
            return new DateTimeDuration(dtd.getStartDateTime(), dtd.getStartDateTime().plus(getMaxDuration()));
        }
    }
}