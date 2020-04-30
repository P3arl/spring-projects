package com.learning.springbootapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private Map<String, User> users = new HashMap<>(Map.of(
            "1", User.builder().id("1").name("user1").build(),
            "2", User.builder().id("2").name("user2").build()
    ));

    @GetMapping
    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(Integer.toString(users.size() + 1));
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        if (users.containsKey(id)) {
            user.setId(id);
            users.put(id, user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return new ResponseEntity(USER_DELETED_SUCCESSFULLY, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
