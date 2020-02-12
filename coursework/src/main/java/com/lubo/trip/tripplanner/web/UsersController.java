package com.lubo.trip.tripplanner.web;


import com.lubo.trip.tripplanner.annotations.IsCurrentUser;
import com.lubo.trip.tripplanner.domain.UsersService;
import com.lubo.trip.tripplanner.exception.InvalidEntityException;
import com.lubo.trip.tripplanner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<User> getUsers() {

        return usersService.findAll();
    }

    @GetMapping("{id}")
    @IsCurrentUser
    public User getUserById(@PathVariable("id") String id) {
        return usersService.findById(id);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            String message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr);
            throw new InvalidEntityException(message);
        }
        User created = usersService.add(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    public User update(@PathVariable String id, @Valid @RequestBody User user) {
        return usersService.update(user);
    }

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello from private API controller";
    }
}

