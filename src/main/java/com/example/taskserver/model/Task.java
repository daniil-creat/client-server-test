package com.example.taskserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
public class Task {

    public Task(String name, String text) {
        this.name = name;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 30, message = "must be from 2 to 30 characters")
    private String name;

    @Size(min = 3, max = 30, message = "must be from 2 to 30 characters")
    private String text;

    private TaskStatus status;

    private String history;

    private Long dateCreate;

    private Long timeForComplite;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private User user;
}
