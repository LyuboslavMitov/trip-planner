package com.lubo.trip.tripplanner.dao;


import com.lubo.trip.tripplanner.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
}
