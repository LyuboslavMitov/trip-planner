package com.lubo.trip.tripplanner.web;

import com.lubo.trip.tripplanner.annotations.IsTripOwner;
import com.lubo.trip.tripplanner.domain.TripsService;
import com.lubo.trip.tripplanner.domain.UsersService;
import com.lubo.trip.tripplanner.exception.InvalidEntityException;
import com.lubo.trip.tripplanner.model.Trip;
import com.lubo.trip.tripplanner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripsService tripsService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<Trip> getTrips(Principal principal) {
        return tripsService.findAllTripsOwnedBy(getPrincipalId(principal));
    }

    @GetMapping("/participant")
    public List<Trip> getParticipantTrips(Principal principal) {
        return tripsService.findAllTripsForParticipant(getPrincipalId(principal));
    }

    @GetMapping("{tripId}/participants")
    public List<User> getTripParticipants(@PathVariable("tripId") String tripId) {

        return tripsService.findAllParticipantsForTrip(tripId);
    }

    @GetMapping("{id}")
    public Trip getTripById(@PathVariable("id") String tripId) {
        return tripsService.findById(tripId);
    }

    @PostMapping
    public ResponseEntity<Trip> addTrip(@Valid @RequestBody Trip trip, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasFieldErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr);
            throw new InvalidEntityException(message);
        }

        String currentUserId = getPrincipalId(principal);
        trip.setOwnerId(currentUserId);
        trip.getParticipantsNames().add(principal.getName());
        trip.setParticipantsNames(trip.getParticipantsNames());

        trip.getParticipantsId().add(currentUserId);
        trip.setParticipantsId(trip.getParticipantsId());

        Trip created = tripsService.add(trip);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    @IsTripOwner
    public Trip update(@PathVariable String id, @Valid @RequestBody Trip trip,Principal principal) {
        if (!id.equals(trip.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", trip.getId(), id));
        }
        if(trip.getParticipantsNames()==null || trip.getParticipantsNames().size()==0){
            trip.setParticipantsNames(Collections.singletonList(principal.getName()));
        }
        if(trip.getParticipantsId()==null || trip.getParticipantsId().size()==0){
            trip.setParticipantsId(Collections.singletonList(usersService.findByUsername(principal.getName()).getId()));
        }
        return tripsService.update(trip);
    }

    @DeleteMapping("{id}")
    @IsTripOwner
    public Trip remove(@PathVariable String id) {
        return tripsService.remove(id);
    }

    private String getPrincipalId(Principal principal) {
        return usersService.findByUsername(principal.getName()).getId();
    }
}

