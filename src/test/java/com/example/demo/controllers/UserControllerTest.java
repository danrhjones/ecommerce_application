package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);

    private CartController cartController;
    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void before() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController,  "cartRepository", cartRepository);
        TestUtils.injectObjects(userController,  "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void test_create_user() {
        when(encoder.encode("password")).thenReturn("thisIsHashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("password");
        createUserRequest.setConfirmPassword("password");

        final ResponseEntity<User> createUserResponse = userController.createUser(createUserRequest);
        assertNotNull(createUserResponse);
        assertEquals(200, createUserResponse.getStatusCodeValue());

        User user = createUserResponse.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("thisIsHashed", user.getPassword());
    }

    @Test
    public void test_finding_user_by_username() {
        createUser("test", "password");
        when(userRepository.findByUsername("test")).thenReturn(setUser());
        final ResponseEntity<User> response = userController.findByUserName("test");
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertEquals("test", user.getUsername());
    }

    @Test
    public void test_finding_user_by_id() {
        createUser("test", "password");
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(setUser()));
        final ResponseEntity<User> response = userController.findById(0L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertEquals(0, user.getId());
    }

    private ResponseEntity<User> createUser(String username, String password) {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(username);
        createUserRequest.setPassword(password);
        createUserRequest.setConfirmPassword(password);
        return userController.createUser(createUserRequest);
    }

    private User setUser() {
        User user = new User();
        user.setId(0);
        user.setUsername("test");
        user.setPassword("testPassword");
        return user;
    }
}
