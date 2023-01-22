package com.example.task_manager.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table (name ="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "categoryId")
    private Categories categories;

    @Column(length = 512)
    private String title;

    @Column(length = 2048)
    private String description;

    @Column(updatable = false)
    private Date CreatedAt;

    @Column(updatable = true)
    private Date DueDate;

    //Constructors


    public Task(long id, User user, Categories categories, String title, String description, Date createdAt, Date dueDate) {
        this.id = id;
        this.user = user;
        this.categories = categories;
        this.title = title;
        this.description = description;
        CreatedAt = createdAt;
        DueDate = dueDate;
    }

    public Task() {
    }

}
