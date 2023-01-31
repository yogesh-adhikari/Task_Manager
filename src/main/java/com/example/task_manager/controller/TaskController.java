//package com.example.task_manager.controller;
//
//import com.example.task_manager.models.Categories;
//import com.example.task_manager.models.Task;
//import com.example.task_manager.models.User;
//import com.example.task_manager.services.CategoriesService;
//import com.example.task_manager.services.TaskService;
//import com.example.task_manager.services.UserService;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//
//@Controller
//public class TaskController {
//
//    private final TaskService taskService;
//
//    private final UserService userService;
//    private final CategoriesService categoriesService;
//
//    public TaskController(TaskService taskService, UserService userService, CategoriesService categoriesService) {
//        this.taskService = taskService;
//        this.userService = userService;
//        this.categoriesService = categoriesService;
//    }
//
//    public User loggedInUser() {
//
//        User user1 =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userService.findById(user1.getId());
//    }
//
//
//    //view all task controller mapping
//
//    @GetMapping("/task")
//    public String showTasks(Model model) {
//        User user = loggedInUser();
//
//        List<Task> tasks = taskService.findByUser(user);
//
//
//        model.addAttribute("tasks", tasks);
//        model.addAttribute("user", user);
//
//
//        return "tasks/readTask";
//    }
//
//
//    // create a task controller mapping
//    @GetMapping("/showTaskForm")
//    public String ShowCreate() {
//
//        return "createTask";
//    }
//
//
//    @PostMapping("/createTask")
//    public String create(
//            @RequestParam("title") String title,
//            @RequestParam("description") String description,
//            @RequestParam("categories") String categories) {
//
//        User user = loggedInUser();
//        Categories categories1 = categoriesService.findByCategoriesName(categories);
//        LocalDate createdDate = LocalDate.now();
//
//        Task task = new Task(title, description, false, false, categories1, createdDate, user);
//        taskService.save(task);
//
//        return "redirect:/task";
//    }
//
//
//    //Task delete controller mapping
//
//    @GetMapping("/deleteTask/{id}")
//    public String deleteTask(@PathVariable("id") long id) {
//
//        Task task = taskService.findById(id);
//        taskService.delete(task);
//        return "redirect:/task";
//    }
//
//
//    //Task complete controller mapping
//
//    @GetMapping("/completeTask/{id}")
//    public String completeTask(@PathVariable("id") long id) {
//        Task task = taskService.findById(id);
//        if(task.isScheduled()){
//            task.setScheduled(false);
//            task.setDueDate(null);
//        }
//        task.setCompleted(true);
//        taskService.save(task);
//        return "redirect:/task";
//    }
//    //Task scheduling mapping
//    @GetMapping("/scheduleTask/{id}")
//    public String showScheduleForm(@PathVariable("id") long id, Model model) {
//        Task task = taskService.findById(id);
//        model.addAttribute("task", task);
//
//        return "scheduleTask";
//    }
//
//
//    @PostMapping("/scheduleTask")
//    public String schedule(
//            @RequestParam("dueDate") LocalDate dueDate,
//            @RequestParam("id") long id,
//            Model model) {
//
//        Task task = taskService.findById(id);
//        if(dueDate.isBefore(LocalDate.now())){
//            model.addAttribute("task", task);
//            model.addAttribute("pastDate","Due date cannot be in the past.");
//            return "scheduleTask";
//        }
//
//        task.setDueDate(dueDate);
//        task.setScheduled(true);
//        taskService.save(task);
//
//        return "redirect:/task";
//    }
//
//
//
//
//    //update task comtroller mapping
//
//    @GetMapping("/updateTask/{id}")
//    public String showUpdateTaskForm(@PathVariable("id") long id, Model model) {
//        Task task = taskService.findById(id);
//        model.addAttribute("task", task);
//        return "updateTask";
//    }
//
//
//    @PostMapping("/updateTask")
//    public String updateTask(@RequestParam("title") String title,
//                             @RequestParam("description") String description,
//                             @RequestParam("categories") String categories,
//                             @RequestParam(name ="dueDate",required=false) Optional<LocalDate> dueDate,
//                             @RequestParam("id") long id,
//                             Model model) {
//
//
//        Task task = taskService.findById(id);
//
//
//
//        Categories categories1 = categoriesService.findByCategoriesName(categories);
//        task.setName(categories);
//        task.setDescription(description);
//        task.setCategories(categories1);
//        dueDate.ifPresent(task::setDueDate);
//        taskService.save(task);
//
//        return "redirect:/task";
//    }
//
//
//
//    // filtering by category mapping
//
////    @GetMapping("/category/{categoriesName}")
////    public String taskCategory(@PathVariable("categoriesName")String categoriesName,
////                               Model model){
////        User user = loggedInUser();
////        Categories categories1 = categoriesService.findByCategoriesName(categoriesName);
////        List<Task> tasks = taskService.findByCategoriesName(categories1,user);
////        model.addAttribute("tasks",tasks);
////        model.addAttribute("user",user);
////        if (tasks.size()==0){
////            model.addAttribute("noTask","You don't have any task in this category.");
////        }
////
////        return "tasks";
////    }
////
////
////
////    @GetMapping("/status/{status}")
////    public String taskStatus(@PathVariable("status")String status,
////                             Model model){
////        User user = loggedInUser();
////        model.addAttribute("user",user);
////
////        if(status.equals("scheduled")) {
////            List<Task> tasks = taskService.findByScheduled(true,user);
////            model.addAttribute("tasks",tasks);
////
////            if (tasks.size()==0){
////                model.addAttribute("notask","Status: No task...");
////            }
////
////
////        }
////
////        if(status.equals("completed")) {
////            List<Task> tasks = taskService.findByCompleted(true,user);
////            model.addAttribute("tasks",tasks);
////
////            if (tasks.size()==0){
////                model.addAttribute("notask","Status: No task...");
////            }
////
////        }
////
////        if(status.equals("pending")) {
////            List<Task> tasks = taskService.findByCompletedAndScheduled(false, false, user);
////            model.addAttribute("tasks", tasks);
////
////            if (tasks.size() == 0) {
////                model.addAttribute("notask", "Status: No task...");
////            }
////        }
////
////
////
////        return "tasks";
////    }
////
//
//
//
//}