package com.example.taskserver.controllers;


import com.example.taskserver.model.Task;
import com.example.taskserver.services.TaskService;
import com.example.taskserver.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    private final UserService userService;

    private final TaskService taskService;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        log.info("Start main controller, method get");
        Set<Task> tasks = userService.getTasksAuthUser();
        log.info("Tasks auth user {}", tasks);
        model.put("tasks", tasks);
        return "main";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam @Valid String name, @RequestParam @Valid String text, BindingResult bindingResult) throws BindException {
        log.info("Start main controller, method post, input parametr name {}, text {}", name, text);
        if (bindingResult.hasErrors()) {
            log.info("Validation error");
            throw new BindException(bindingResult);
        }
        Task task = new Task(name, text);
        taskService.addTask(task);
        log.info("Task saved in DB");
        return "redirect:/main";
    }

}