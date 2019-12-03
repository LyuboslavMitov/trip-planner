package com.blog.springhomework1;

import com.blog.springhomework1.domain.UsersService;
import com.blog.springhomework1.model.User;
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
        if(usersService.count()==0) {
            User adminUser = new User("admin","admin", "Admin","Adminov","ROLE_ADMIN",true);
            User bloggerUser = new User("blogger","blogger", "Blogger","Bloggerov","ROLE_BLOGGER",true);
            usersService.add(adminUser);
            usersService.add(bloggerUser);
            log.info("Initializing admin user....");
        }
    }
}
