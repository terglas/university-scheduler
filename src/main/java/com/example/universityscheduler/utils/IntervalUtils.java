package com.example.universityscheduler.utils;

import com.example.universityscheduler.domain.Schedule;
import com.example.universityscheduler.model.TimeInterval;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class IntervalUtils {

    public boolean doesIntervalFit(TimeInterval newInterval, List<TimeInterval> timeIntervals) {
        for (TimeInterval interval : timeIntervals) {
            val newStart = newInterval.getStartTime();
            val newEnd = newInterval.getEndTime();
            val intervalStart = interval.getStartTime();
            val intervalEnd = interval.getEndTime();
            if ((newStart.isAfter(intervalStart) && newStart.isBefore(intervalEnd)) ||
                    (newEnd.isAfter(intervalStart) && newEnd.isBefore(intervalEnd)) ||
                    (newStart.equals(intervalStart) || newEnd.equals(intervalEnd))) {
                return false;
            }
        }
        return true;
    }

    public List<TimeInterval> formInterval(List<Schedule> schedules) {
        return schedules.stream().map(s -> new TimeInterval()
                .startTime(s.getStartTime()
                        .toLocalTime())
                .endTime(s.getEndTime()
                        .toLocalTime())).collect(Collectors.toList());
    }
}
