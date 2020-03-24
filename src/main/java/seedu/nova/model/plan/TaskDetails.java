package seedu.nova.model.plan;

import java.time.Duration;

/**
 * Task details
 */
public class TaskDetails {
    private final String name;
    private final Duration duration;
    private final TaskFreq freq;

    public TaskDetails(String name, Duration duration, TaskFreq freq) {
        this.name = name;
        this.duration = duration;
        this.freq = freq;
    }

    String getName() {
        return name;
    }

    Duration getDuration() {
        return duration;
    }

    TaskFreq getTaskFreq() {
        return freq;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskDetails) {
            return name.equals(((TaskDetails) obj).name)
                    && freq.equals(((TaskDetails) obj).freq);
        } else {
            return super.equals(obj);
        }
    }
}