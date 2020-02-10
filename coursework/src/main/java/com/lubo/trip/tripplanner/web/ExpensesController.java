package com.lubo.trip.tripplanner.web;

import com.lubo.trip.tripplanner.annotations.IsExpenseOwner;
import com.lubo.trip.tripplanner.annotations.IsTripParticipant;
import com.lubo.trip.tripplanner.domain.ExpensesService;
import com.lubo.trip.tripplanner.domain.UsersService;
import com.lubo.trip.tripplanner.exception.InvalidEntityException;
import com.lubo.trip.tripplanner.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses/{tripId}")
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;
    @Autowired
    private UsersService usersService;

    @GetMapping
    @IsTripParticipant
    public Map<String,List<Expense>> getExpensesForTrip(@PathVariable("tripId") String tripId, Principal principal) {
        return expensesService.getExpensesGroupedByUsername(tripId);
    }

    @GetMapping("{id}")
    @IsTripParticipant
    public List<Expense> getExpenseForUser(@PathVariable("id") String userId, @PathVariable("tripId") String tripId, Principal principal) {
        return expensesService.findAllExpensesForUserForTrip(tripId, principal.getName());
    }

    @PostMapping
    @IsTripParticipant
    public ResponseEntity<Expense> addExpense(@PathVariable("tripId") String tripId, @Valid @RequestBody Expense expense, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasFieldErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr);
            throw new InvalidEntityException(message);
        }
        expense.setTripId(tripId);
        expense.setUsername(principal.getName());
        Expense created = expensesService.add(expense);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    @IsExpenseOwner
    public Expense update(@PathVariable String id, @Valid @RequestBody Expense expense) {
        if (!id.equals(expense.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", expense.getId(), id));
        }
        return expensesService.update(expense);
    }

    @IsExpenseOwner
    @DeleteMapping("{id}")
    public Expense remove(@PathVariable String id, Principal principal) {
        return expensesService.remove(id);
    }
}
