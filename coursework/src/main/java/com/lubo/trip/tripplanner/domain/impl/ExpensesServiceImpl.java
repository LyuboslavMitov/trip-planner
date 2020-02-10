package com.lubo.trip.tripplanner.domain.impl;

import com.lubo.trip.tripplanner.dao.ExpensesRepository;
import com.lubo.trip.tripplanner.domain.ExpensesService;
import com.lubo.trip.tripplanner.exception.NonexisitngEntityException;
import com.lubo.trip.tripplanner.model.Expense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

@Service
@Slf4j
public class ExpensesServiceImpl implements ExpensesService {
    @Autowired
    private ExpensesRepository repo;


    //TODO: Modify expense -> ExpenseResponse so that you can diminish which are  modifiable by user (isOwnedByCurrentUser:true)
    @Override
    public List<Expense> findAllExpensesForTrip(String tripId) {
        return repo.findAllByTripId(tripId);
    }

    @Override
    public Map<String,List<Expense>> getExpensesGroupedByUsername(String tripId) {
        return repo.findAllByTripId(tripId).stream().collect(groupingBy(Expense::getUsername));
    }

    @Override
    public List<Expense> findAllExpensesForUserForTrip(String tripId, String username) {
        return repo.findAllByTripIdAndUsername(tripId, username);
    }


    public Expense findById(String expenseId) {
        return repo.findById(expenseId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Expense with ID='%s' does not exist.", expenseId)));
    }

    public Expense add(Expense expense) {
        return repo.insert(expense);
    }

    public Expense update(Expense expense) {
        Optional<Expense> old = repo.findById(expense.getId());
        if (old.isEmpty()) {
            throw new NonexisitngEntityException(
                    String.format("Expense with ID='%s' does not exist.", expense.getId()));
        }
        return repo.save(expense);
    }

    public Expense remove(String expenseId) {
        Optional<Expense> old = repo.findById(expenseId);
        log.info("!!!!!! ExpenseID = " + expenseId);
        if (old.isEmpty()) {
            throw new NonexisitngEntityException(
                    String.format("Expense with ID='%s' does not exist.", expenseId));
        }
        repo.deleteById(expenseId);
        return old.get();
    }
}
