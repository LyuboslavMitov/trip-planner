package com.lubo.trip.tripplanner.domain;


import com.lubo.trip.tripplanner.model.User;

import java.util.List;

public interface UsersService {
    List<User> findAll();

    User findById(String userId);

    User findByUsername(String username);

    User add(User user);

    User update(User user);

    User remove(String userId);

    long count();
}
