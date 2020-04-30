package com.learning.springbootapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    private UserController userController;
    private Map<String, User> users;

    @BeforeEach
    void setUp() {
        userController = new UserController();
        users = new HashMap<>(Map.of(
                "1", User.builder().id("1").name("user1").build(),
                "2", User.builder().id("2").name("user2").build()
        ));
    }

    @AfterEach
    void tearDown() {
        userController = null;
        users = null;
    }

    @Test
    void getUsersTest() {
        final List<User> savedUsers = userController.getUsers();
        assertEquals(users.size(), savedUsers.size(),
                () -> "size should be " + users.size());
    }

    @Test
    void createUserTest() {
        final User user = User.builder().id("3").name("random name").build();
        final User createdUser = userController.createUser(user);
        assertEquals(user.getId(), createdUser.getId(),
                () -> "id should be " + user.getId());
        assertEquals(user.getName(), createdUser.getName(),
                () -> "name should be " + user.getName());
    }

    @Test
    void updateUserTest() {
        final User userToUpdate = userController.getUsers().get(0);
        userToUpdate.setName("updated random name");
        final ResponseEntity<User> userResponseEntity = userController.updateUser(userToUpdate.getId(), userToUpdate);

        assertEquals(HttpStatus.OK, userResponseEntity.getStatusCode(),
                () -> "HttpStatus should be 200");

        final User updatedUser = userResponseEntity.getBody();
        assertEquals(userToUpdate.getName(), updatedUser.getName(),
                () -> "Updated name should be " + userToUpdate.getName());
    }

    @Test
    void deleteUserTest() {
        final ResponseEntity<User> userResponseEntity = userController.deleteUser("1");

        assertEquals(HttpStatus.OK, userResponseEntity.getStatusCode());
        String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
        assertEquals(USER_DELETED_SUCCESSFULLY,
                userResponseEntity.getBody());
    }
}