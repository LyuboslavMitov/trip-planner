package com.lubo.trip.tripplanner.web;


import com.lubo.trip.tripplanner.domain.UsersService;
import com.lubo.trip.tripplanner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


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
    public User getUserById(@PathVariable("id") String userId) {
        return usersService.findById(userId);
    }

    //
//    @PostMapping
//    public ResponseEntity<User> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
//        if (!isLoggedInUserAdmin()) {
//            throw new InvalidEntityException("Only admins can create users");
//        }
//        if (bindingResult.hasFieldErrors()) {
//            String message = bindingResult.getFieldErrors().stream()
//                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
//                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
//                    .reduce("", (acc, errStr) -> acc + errStr);
//            throw new InvalidEntityException(message);
//        }
//        User created = usersService.add(user);
//        return ResponseEntity.created(
//                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
//                .body(created);
//    }
//
    @PutMapping("{id}")
    public User update(@PathVariable String id, @Valid @RequestBody User user) {
        return usersService.update(user);
    }

//
//
//    @DeleteMapping("{id}")
//    public User remove(@PathVariable String id) {
//
//        validatePermissions(id);
//        return usersService.remove(id);
//    }
//

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello from private API controller";
    }
}

