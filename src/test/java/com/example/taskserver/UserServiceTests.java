package com.example.taskserver;

import com.example.taskserver.model.Role;
import com.example.taskserver.model.User;
import com.example.taskserver.repository.TaskRepository;
import com.example.taskserver.repository.UserRepository;
import com.example.taskserver.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;

@SpringBootTest(properties = "application.properties")
@AutoConfigureMockMvc
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @Test
    void findByUserNameTest() {
        User user = new User();
        user.setUsername("Dan");
        user.setPassword("123");
        userRepository.save(user);
        User excpectedUser = userService.findByUserName(user.getUsername());
        Assertions.assertEquals(excpectedUser.getUsername(), user.getUsername());
    }

    @Test
    void addUserTest() {
        User user = new User();
        user.setUsername("Dan");
        user.setPassword("123");
        userService.addUser(user);
        User excpectedUser = userRepository.findByUsername(user.getUsername());
        Assertions.assertEquals(excpectedUser.getUsername(), user.getUsername());
    }

    @Test
    @WithMockUser(roles = "USER", username = "Dan")
    void getAuthUserTest() {
        User user = new User();
        user.setUsername("Dan");
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        User excpectedUser = userService.getAuthUser();
        Assertions.assertEquals(excpectedUser.getUsername(), user.getUsername());
    }
}
