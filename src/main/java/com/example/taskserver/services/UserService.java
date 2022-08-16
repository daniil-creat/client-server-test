package com.example.taskserver.services;

import com.example.taskserver.model.Task;
import com.example.taskserver.model.User;

import java.util.Set;

public interface UserService {

    void addUser(User user);

    User findByUserName(String name);

    Set<Task> getTasksAuthUser();

    User getAuthUser();
}
