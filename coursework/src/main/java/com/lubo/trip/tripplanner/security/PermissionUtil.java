package com.lubo.trip.tripplanner.security;

import com.lubo.trip.tripplanner.domain.TripsService;
import com.lubo.trip.tripplanner.domain.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PermissionUtil {
    @Autowired
    TripsService tripsService;

    @Autowired
    UsersService usersService;

    public boolean isTripParticipant(String tripId, String principal) {
        return tripsService.findById(tripId).getParticipantsId().stream().anyMatch(participantId -> Objects.equals(participantId, usersService.findByUsername(principal).getId()));
    }

    public boolean isTripOwner(String tripId, String principal) {
        return Objects.equals(tripsService.findById(tripId).getOwnerId(), usersService.findByUsername(principal).getId());
    }

    public boolean isCurrentUser(String userId, String principal) {
        return Objects.equals(usersService.findById(userId), usersService.findByUsername(principal));
    }
}