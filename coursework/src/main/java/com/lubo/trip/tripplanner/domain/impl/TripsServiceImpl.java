package com.lubo.trip.tripplanner.domain.impl;

import com.lubo.trip.tripplanner.dao.TripsRepository;
import com.lubo.trip.tripplanner.domain.TripsService;
import com.lubo.trip.tripplanner.exception.NonexisitngEntityException;
import com.lubo.trip.tripplanner.model.Trip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TripsServiceImpl implements TripsService {
    @Autowired
    private TripsRepository repo;

    public List<Trip> findAllTripsOwnedBy(String userId) {
        return repo.findAllByOwnerId(userId);
    }

    public Trip findById(String tripId) {
        return repo.findById(tripId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Trip with ID='%s' does not exist.", tripId)));
    }

    public Trip add(Trip trip) {
        return repo.insert(trip);
    }

    public Trip update(Trip trip) {
        Optional<Trip> old = repo.findById(trip.getId());
        if (old.isEmpty()) {
            throw new NonexisitngEntityException(
                    String.format("Trip with ID='%s' does not exist.", trip.getId()));
        }
        return repo.save(trip);
    }

    public Trip remove(String tripId) {
        Optional<Trip> old = repo.findById(tripId);
        log.info("!!!!!! TripID = " + tripId);
        if (old.isEmpty()) {
            throw new NonexisitngEntityException(
                    String.format("Trip with ID='%s' does not exist.", tripId));
        }
        repo.deleteById(tripId);
        return old.get();
    }
}