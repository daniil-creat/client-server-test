package com.example.taskserver.controllers;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(RuntimeException ex) {
        ModelAndView model = new ModelAndView();
        String message = ex.getMessage();
       model.addObject("messages", message);
       model.setViewName("error");
        return model;
    }

    @ExceptionHandler(BindException.class)
    public ModelAndView handleException(BindException ex) {
       ModelAndView model = new ModelAndView();
       List<FieldError> errors = ex.getFieldErrors();
       List<String> messages = errors.stream()
               .map(error -> error.getField() + " " + error.getDefaultMessage())
               .collect(Collectors.toList());
       model.addObject("messages",messages);
       model.setViewName("error");
        return model;
    }
}
