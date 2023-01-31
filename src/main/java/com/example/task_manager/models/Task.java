package com.example.task_manager.models;


import com.example.task_manager.models.Categories;
import com.example.task_manager.models.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Task {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;

    @Column(name = "completed")
    private boolean Completed;

    @Column(name = "scheduled")

    private boolean Scheduled;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Categories category;

    @Column(name = "createdAt")
    private LocalDate dateCreated;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "taskCategoryName")
    private String taskCategoryName;

}
