package com.lubo.trip.tripplanner.domain.impl;

import com.lubo.trip.tripplanner.dao.ScheduleItemsRepository;
import com.lubo.trip.tripplanner.domain.ScheduleItemsService;
import com.lubo.trip.tripplanner.domain.ScheduleItemsService;
import com.lubo.trip.tripplanner.exception.NonexisitngEntityException;
import com.lubo.trip.tripplanner.model.ScheduleItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScheduleItemsServiceImpl implements ScheduleItemsService {
    @Autowired
    private ScheduleItemsRepository repo;

    @Override
    public List<ScheduleItem> findAllScheduleItemsForTrip(String tripId) {
        return repo.findAllByTripId(tripId);
    }

    public ScheduleItem findById(String scheduleItemId) {
        return repo.findById(scheduleItemId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("ScheduleItem with ID='%s' does not exist.", scheduleItemId)));
    }

    public ScheduleItem add(ScheduleItem scheduleItem) {
        return repo.insert(scheduleItem);
    }

    public ScheduleItem update(ScheduleItem scheduleItem) {
        Optional<ScheduleItem> old = repo.findById(scheduleItem.getId());
        if (old.isEmpty()) {
            throw new NonexisitngEntityException(
                    String.format("ScheduleItem with ID='%s' does not exist.", scheduleItem.getId()));
        }
        return repo.save(scheduleItem);
    }

    public ScheduleItem remove(String scheduleItemId) {
        Optional<ScheduleItem> old = repo.findById(scheduleItemId);
        log.info("!!!!!! ScheduleItemID = " + scheduleItemId);
        if (old.isEmpty()) {
            throw new NonexisitngEntityException(
                    String.format("ScheduleItem with ID='%s' does not exist.", scheduleItemId));
        }
        repo.deleteById(scheduleItemId);
        return old.get();
    }
}