//package com.example.task_manager.services;
//
//
//import com.example.task_manager.models.Categories;
//import com.example.task_manager.models.Task;
//import com.example.task_manager.models.User;
//import com.example.task_manager.repositories.TaskRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@AllArgsConstructor
//@Service
//public class TaskService {
//    private final TaskRepository taskDao;
//
//    public List<Task> findByUser(User user) {
//        return taskDao.findByUser(user);
//    }
//
//    public void save(Task task) {
//        taskDao.save(task);
//    }
//
//    public Task findById(long id) {
//        return taskDao.findById(id);
//    }
//
//    public void delete(Task task) {
//        taskDao.delete(task);
//    }
//
//
//    public List<Task> findByCategoriesName(Categories categories,User user) {
//        return taskDao.findByCategoriesAndUser(categories,user);
//    }
//
//    public List<Task>findByScheduled(boolean scheduled, User user){
//        return taskDao.findByIsScheduledAndUser(scheduled, user);
//    }
//
//    public List<Task> findByCompleted(boolean completed, User user) {
//        return taskDao.findByIsCompletedAndUser(completed, user);
//    }
//
//
//    public   List<Task> findByCompletedAndScheduled(boolean completed, boolean scheduled,User user){
//        return taskDao.findByIsCompletedAndIsScheduledAndUser(completed,scheduled,user);
//    }
//
//}