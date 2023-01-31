//package com.example.task_manager.repositories;
//
//import com.example.task_manager.models.Categories;
//import com.example.task_manager.models.Task;
//import com.example.task_manager.models.User;
//import jdk.jfr.Category;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface TaskRepository extends JpaRepository<Task, Long>
//{
//    Task findById(long id);
//
//
//    List<Task> findByUser(User user);
//
//    List<Task> findByCategoriesAndUser(Categories categories, User user);
//
//    List<Task> findByIsScheduledAndUser(boolean scheduled, User user);
//
//    List<Task> findByIsCompletedAndUser(boolean completed, User user);
//
//    List<Task> findByIsCompletedAndIsScheduledAndUser(boolean completed, boolean scheduled, User user);
//}
