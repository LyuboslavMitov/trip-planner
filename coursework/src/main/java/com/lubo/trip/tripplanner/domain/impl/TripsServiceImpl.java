package com.lubo.trip.tripplanner.domain.impl;

import com.lubo.trip.tripplanner.dao.TripsRepository;
import com.lubo.trip.tripplanner.dao.UsersRepository;
import com.lubo.trip.tripplanner.domain.TripsService;
import com.lubo.trip.tripplanner.exception.NonexisitngEntityException;
import com.lubo.trip.tripplanner.model.Trip;
import com.lubo.trip.tripplanner.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TripsServiceImpl implements TripsService {
    @Autowired
    private TripsRepository repo;

    @Autowired
    private UsersRepository usersRepository;

    public List<Trip> findAllTripsOwnedBy(String userId) {
        return repo.findAllByOwnerId(userId);
    }

    @Override
    public List<Trip> findAllTripsForParticipant(String username) {
//        return repo.findAllByParticipantsIdContaining(userId);
        return repo.findAllByParticipantsNamesContaining(username);
    }

    @Override
    public List<User> findAllParticipantsForTrip(String tripId) {
//        Trip trip = repo.findById(tripId).orElseThrow(() -> new NonexisitngEntityException(
//                String.format("Trip with ID='%s' does not exist.", tripId)));
//        return trip.getParticipantsId().stream().map(t -> {
//            return usersRepository.findById(t).orElseThrow(() -> new NonexisitngEntityException("Participant not found"));
//        }).collect(Collectors.toList());
        Trip trip = repo.findById(tripId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Trip with ID='%s' does not exist.", tripId)));
        return trip.getParticipantsNames().stream().map(t -> {
            return usersRepository.findByUsername(t).orElseThrow(() -> new NonexisitngEntityException("Participant not found"));
        }).collect(Collectors.toList());
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
        trip.setOwnerId(old.get().getOwnerId());
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
