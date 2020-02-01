package com.lubo.trip.tripplanner.domain;

import com.lubo.trip.tripplanner.model.Trip;

import java.util.List;

public interface TripsService {
    List<Trip> findAllTripsOwnedBy(String userId);

    Trip findById(String tripId);

    Trip add(Trip trip);

    Trip update(Trip trip);

    Trip remove(String tripId);
}
