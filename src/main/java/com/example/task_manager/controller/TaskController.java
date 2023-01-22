package com.example.task_manager.controller;

import com.example.task_manager.models.Task;
import com.example.task_manager.repositories.TaskRepository;
import com.example.task_manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private TaskRepository taskDao;

    @Autowired
    private UserRepository userDao;

    /********************COMPLETE ROUTES********************/
    @GetMapping("/tasks")
    public String userPost(Model model){
        //Get All Post
        List<Task> tasks = taskDao.findAll();
        Collections.reverse(tasks);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:/login";
        }
        com.example.task_manager.models.User loggedinUser =(com.example.task_manager.models.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.example.task_manager.models.User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("user",user);
        model.addAttribute("tasks",tasks);
        model.addAttribute("newTask",new Task());

        return "feed";
    }


    @PostMapping("/tasks/create")
    public String testTask1(@ModelAttribute("newTask") Task newTask){
        //Get UserId from logged-in user to create new post
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        com.example.task_manager.models.User loggedinUser =(com.example.task_manager.models.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.example.task_manager.models.User user = userDao.getReferenceById(loggedinUser.getId());

        //Check if user logged-in
        System.out.println(user.getId());

        //Save new task to database
        user.getTasks().add(newTask);
        newTask.setUser(user);
        taskDao.save(newTask);
        return "redirect:/posts";
    }





    @PostMapping("/posts/create/user")
    public String testTask2(@ModelAttribute("newTask") Task newTask){
        //Get UserId from logged-in user to create new post
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        com.example.task_manager.models.User loggedinUser =(com.example.task_manager.models.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.example.task_manager.models.User user = userDao.getReferenceById(loggedinUser.getId());

        //Check if user logged-in
        System.out.println(user.getId());

        //Save new post to database
        user.getTasks().add(newTask);
        newTask.setUser(user);
        taskDao.save(newTask);
        return "redirect:/userProfile";
    }


    /********************TEST ROUTES********************/
    @GetMapping("/createTask")
    public String testPost(Model model){
        model.addAttribute("task", new Task());
        return "TestTemplates/CreateTask";
    }

    @PostMapping("/search")
    public String taskSearch(@RequestParam(value = "searchValue")String searchValue) {
        System.out.println(searchValue);
        return "redirect:profile";
    }

}


