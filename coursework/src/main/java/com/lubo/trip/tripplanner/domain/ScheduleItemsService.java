package com.lubo.trip.tripplanner.domain;


import com.lubo.trip.tripplanner.model.ScheduleItem;

import java.util.List;

public interface ScheduleItemsService {
    List<ScheduleItem> findAllScheduleItemsForTrip(String tripId);

    ScheduleItem findById(String scheduleItemId);

    ScheduleItem add(ScheduleItem scheduleItem);

    ScheduleItem update(ScheduleItem scheduleItem);

    ScheduleItem remove(String scheduleItemId);
}
