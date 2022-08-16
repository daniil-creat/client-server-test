package com.example.taskserver.controllers;

import com.example.taskserver.model.User;
import com.example.taskserver.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
@Log4j2
public class RegistrationController {

    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Map<String, Object> model) throws BindException {
        log.info("Start registration controller, method post, input user {}", user);
        if (bindingResult.hasErrors()) {
            log.info("Validation error");
            throw new BindException(bindingResult);
        }
        User userFromDb = userService.findByUserName(user.getUsername());
        log.info("User from DB {}", userFromDb);
        if (userFromDb != null) {
            model.put("message", "User exists!");
            log.info("User exists {}", user);
            return "registration";
        }
        userService.addUser(user);
        log.info("User saved in DB {}", user);
        return "redirect:/login";
    }
}
