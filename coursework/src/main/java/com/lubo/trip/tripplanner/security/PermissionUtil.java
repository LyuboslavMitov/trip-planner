package com.lubo.trip.tripplanner.security;

import com.lubo.trip.tripplanner.domain.ExpensesService;
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
    ExpensesService expensesService;
    @Autowired
    UsersService usersService;

    public boolean isTripParticipant(String tripId, String principal) {
        return tripsService.findById(tripId).getParticipantsId().stream().anyMatch(participantId -> Objects.equals(participantId, usersService.findByUsername(principal).getId()));
    }

    public boolean isTripOwner(String tripId, String principal) {
        return Objects.equals(tripsService.findById(tripId).getOwnerId(), usersService.findByUsername(principal).getId());
    }
    public boolean isTripOwnerUsingParticipantNames(String tripId, String principal) {
        return tripsService.findById(tripId).getParticipantsNames().contains(principal);
    }
    public boolean isCurrentUser(String userId, String principal) {
        return Objects.equals(usersService.findById(userId), usersService.findByUsername(principal));
    }

    public boolean isExpenseOwner(String expenseId, String principal) {
        return Objects.equals(expensesService.findById(expenseId).getUsername(), principal);
    }
}
