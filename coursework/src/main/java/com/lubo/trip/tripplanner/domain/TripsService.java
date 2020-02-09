package com.lubo.trip.tripplanner.domain;

import com.lubo.trip.tripplanner.model.Trip;
import com.lubo.trip.tripplanner.model.User;

import java.util.List;

public interface TripsService {
    List<Trip> findAllTripsOwnedBy(String userId);

    List<Trip> findAllTripsForParticipant(String userId);

    List<User> findAllParticipantsForTrip(String tripId);

    Trip findById(String tripId);

    Trip add(Trip trip);

    Trip update(Trip trip);

    Trip remove(String tripId);
}
