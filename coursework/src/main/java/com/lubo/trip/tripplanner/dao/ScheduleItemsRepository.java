package com.lubo.trip.tripplanner.dao;


import com.lubo.trip.tripplanner.model.ScheduleItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ScheduleItemsRepository extends MongoRepository<ScheduleItem, String> {
    List<ScheduleItem> findAllByTripId(String tripId);
}
