package com.lubo.trip.tripplanner.dao;


import com.lubo.trip.tripplanner.model.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TripsRepository extends MongoRepository<Trip, String> {
    List<Trip> findAllByOwnerId(String userId);
    List<Trip> findAllByParticipantsIdContaining(String userId);
    List<Trip> findAllByParticipantsNamesContaining(String username);

}
