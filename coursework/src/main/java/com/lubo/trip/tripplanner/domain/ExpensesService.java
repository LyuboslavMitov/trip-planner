package com.lubo.trip.tripplanner.domain;


import com.lubo.trip.tripplanner.model.Expense;

import java.util.List;

public interface ExpensesService {
    List<Expense> findAllExpensesForTrip(String tripId);

    List<Expense> findAllExpensesForUserForTrip(String tripId, String username);

    Expense findById(String expenseId);

    Expense add(Expense expense);

    Expense update(Expense expense);

    Expense remove(String expenseId);
}
