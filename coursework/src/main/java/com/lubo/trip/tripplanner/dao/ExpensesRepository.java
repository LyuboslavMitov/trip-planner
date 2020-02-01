package com.lubo.trip.tripplanner.dao;


import com.lubo.trip.tripplanner.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ExpensesRepository extends MongoRepository<Expense, String> {
    List<Expense> findAllByTripId(String tripId);
    List<Expense> findAllByTripIdAndUsername(String tripId, String username);
}
