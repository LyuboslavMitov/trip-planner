package com.lubo.trip.tripplanner;


import com.lubo.trip.tripplanner.domain.UsersService;
import com.lubo.trip.tripplanner.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UsersService usersService;

    @Override
    public void run(ApplicationArguments args) {
        if (usersService.count() == 0) {
            User travelerUser = new User("methodman", "method123", "Method", "Man", "ROLE_OWNER", true);
            User companyUser = new User("redman", "redman123", "Red", "Man", "ROLE_PARTICIPANT", true);
            usersService.add(travelerUser);
            usersService.add(companyUser);

            log.info("Initialized users....");
        }
    }
}
