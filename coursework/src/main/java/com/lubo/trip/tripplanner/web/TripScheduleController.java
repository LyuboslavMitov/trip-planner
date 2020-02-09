package com.lubo.trip.tripplanner.web;

import com.lubo.trip.tripplanner.annotations.IsTripParticipant;
import com.lubo.trip.tripplanner.domain.ScheduleItemsService;
import com.lubo.trip.tripplanner.domain.UsersService;
import com.lubo.trip.tripplanner.exception.InvalidEntityException;
import com.lubo.trip.tripplanner.model.ScheduleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/scheduleItems/{tripId}")
public class TripScheduleController {
    @Autowired
    private ScheduleItemsService scheduleItemsService;
    @Autowired
    private UsersService usersService;

    @GetMapping
    @IsTripParticipant
    public List<ScheduleItem> getScheduleItemsForTrip(@PathVariable("tripId") String tripId, Principal principal) {
        return scheduleItemsService.findAllScheduleItemsForTrip(tripId);
    }

    @PostMapping
    @IsTripParticipant
    public ResponseEntity<ScheduleItem> addScheduleItem(@PathVariable("tripId") String tripId, @Valid @RequestBody ScheduleItem scheduleItem, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasFieldErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr);
            throw new InvalidEntityException(message);
        }
        scheduleItem.setTripId(tripId);
        ScheduleItem created = scheduleItemsService.add(scheduleItem);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    @IsTripParticipant
    public ScheduleItem update(@PathVariable String id, @Valid @RequestBody ScheduleItem scheduleItem) {
        if (!id.equals(scheduleItem.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", scheduleItem.getId(), id));
        }
        return scheduleItemsService.update(scheduleItem);
    }

    @IsTripParticipant
    @DeleteMapping("{id}")
    public ScheduleItem remove(@PathVariable String id, Principal principal) {
        return scheduleItemsService.remove(id);
    }
}

