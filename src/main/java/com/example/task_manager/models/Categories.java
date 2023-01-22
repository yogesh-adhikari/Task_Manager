package com.example.task_manager.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name ="categories")
public class Categories {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "categoryId")
    private Categories categories;

    @Column(length = 30)
    private String categoryName;

    //Constructors

    public Categories() {
    }

    public Categories(Categories categories, String categoryName) {
        this.categories = categories;
        this.categoryName = categoryName;
    }

    public Categories(Long id, Categories categories, String categoryName) {
        this.id = id;
        this.categories = categories;
        this.categoryName = categoryName;
    }
}
