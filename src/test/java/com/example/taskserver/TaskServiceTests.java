package com.example.taskserver;

import com.example.taskserver.model.Task;
import com.example.taskserver.repository.TaskRepository;
import com.example.taskserver.services.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class TaskServiceTests {
    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @AfterEach
    void cleanup() {
        taskRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER",username = "Dan")
    void addTask(){
        Task task = new Task("name", "text");

        Task taskFromDb = taskService.addTask(task);
        Task taskExcpected = taskRepository.findById(taskFromDb.getId()).get();

        Assertions.assertEquals(taskFromDb.getName(), taskExcpected.getName());

    }
}
