package com.example.taskserver.services.Impl;

import com.example.taskserver.model.Task;
import com.example.taskserver.model.TaskStatus;
import com.example.taskserver.model.User;
import com.example.taskserver.repository.TaskRepository;
import com.example.taskserver.services.TaskService;
import com.example.taskserver.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Log4j2
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    @Override
    public Task addTask(Task task) {
        log.info("Start task service, method addTask, input parametr {}", task);
        Date date = new Date();
        User authUser = userService.getAuthUser();
        task.setDateCreate(date.getTime());
        task.setHistory(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " " + TaskStatus.RENDERING);
        task.setStatus(TaskStatus.RENDERING);
        task.setUser(authUser);
        task.setTimeForComplite(getRandomTime());
        Task taskFromDb = taskRepository.save(task);
        log.info("task saved in DB {}", taskFromDb);
        return taskFromDb;
    }

    private long getRandomTime() {
        return (long) (Math.random() * ((300000 - 60000) + 1)) + 60000;
    }
}
