package com.blog.springhomework1.domain;

import com.blog.springhomework1.model.User;
import org.springframework.security.core.userdetails.UserDetails;

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
