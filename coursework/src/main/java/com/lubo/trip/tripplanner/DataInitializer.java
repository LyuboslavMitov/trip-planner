package com.lubo.trip.tripplanner;


import com.lubo.trip.tripplanner.dao.ExpensesRepository;
import com.lubo.trip.tripplanner.dao.ScheduleItemsRepository;
import com.lubo.trip.tripplanner.dao.TripsRepository;
import com.lubo.trip.tripplanner.domain.TripsService;
import com.lubo.trip.tripplanner.domain.UsersService;
import com.lubo.trip.tripplanner.model.Expense;
import com.lubo.trip.tripplanner.model.ScheduleItem;
import com.lubo.trip.tripplanner.model.Trip;
import com.lubo.trip.tripplanner.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;


@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private TripsRepository tripsRepository;

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private ScheduleItemsRepository scheduleItemsRepository;

    @Autowired
    private UsersService usersService;

    @Override
    public void run(ApplicationArguments args) {
        if (usersService.count() == 0) {
            User travelerUser = new User("methodman", "method123", "Method", "Man", "ROLE_REGISTERED", true);
            User companyUser = new User("redman", "redman123", "Red", "Man", "ROLE_ADMIN", true);
            usersService.add(travelerUser);
            usersService.add(companyUser);

            Trip trip = new Trip("Summer vibes", "Amsterdam", LocalDateTime.now(), LocalDateTime.now().plusDays(5),
                    travelerUser.getId(), Collections.singletonList(travelerUser.getId()));
            trip = tripsRepository.save(trip);

            Expense expense = new Expense("Pizza", BigDecimal.valueOf(15), LocalDateTime.now(), trip.getId(), travelerUser.getUsername());
            expensesRepository.save(expense);

            ScheduleItem scheduleItem = new ScheduleItem("Get together", "Amsterdam central station",
                    LocalDateTime.now().plusHours(3), trip.getId(), trip.getParticipantsId());
            scheduleItemsRepository.save(scheduleItem);
        }
    }
}
