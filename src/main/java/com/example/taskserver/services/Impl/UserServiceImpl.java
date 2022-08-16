package com.example.taskserver.services.Impl;

import com.example.taskserver.model.Role;
import com.example.taskserver.model.Task;
import com.example.taskserver.model.TaskStatus;
import com.example.taskserver.model.User;
import com.example.taskserver.repository.UserRepository;
import com.example.taskserver.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void addUser(User user) {
        log.info("Start user service, method addUser, input parametr {},", user);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        log.info("User saved in DB");
    }

    @Override
    public User findByUserName(String name) {
        log.info("Start user service, method findByUserName, input parametr {}", name);
        User user = userRepository.findByUsername(name);
        log.info("Found user {}", user);
        return user;
    }

    @Override
    public User getAuthUser() {
        log.info("Start user service, method etAuthUser");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        log.info("Founf auth user {}", user);
        return user;
    }

    @Override
    public Set<Task> getTasksAuthUser() {
        log.info("Start user service, method getTasksAuthUser");
        Date date = new Date();
        User user = getAuthUser();
        Set<Task> tasks = user.getTasks().stream()
                .map(task -> {
                    if (date.getTime() - task.getDateCreate() >= task.getTimeForComplite() && task.getStatus().equals(TaskStatus.RENDERING)) {
                        task.setStatus(TaskStatus.COMPLETE);
                        task.setHistory(task.getHistory() + "\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                                " " + TaskStatus.RENDERING + " -> " + TaskStatus.COMPLETE);
                    }
                    return task;
                }).collect(toSet());
        user.setTasks(tasks);
        userRepository.save(user);
        log.info("User updated and saved in DB {}", user);
        log.info("User tasks {}", tasks);
        return tasks;
    }
}
