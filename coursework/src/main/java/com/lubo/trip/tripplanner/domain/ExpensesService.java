package com.lubo.trip.tripplanner.domain;


import com.lubo.trip.tripplanner.model.Expense;

import java.util.List;
import java.util.Map;

public interface ExpensesService {
    List<Expense> findAllExpensesForTrip(String tripId);

    List<Expense> findAllExpensesForUserForTrip(String tripId, String username);

    Map<String,List<Expense>> getExpensesGroupedByUsername(String tripId);

    Expense findById(String expenseId);

    Expense add(Expense expense);

    Expense update(Expense expense);

    Expense remove(String expenseId);
}
