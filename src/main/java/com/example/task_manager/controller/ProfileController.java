package com.example.task_manager.controller;

import com.example.task_manager.models.Task;
import com.example.task_manager.models.User;
import com.example.task_manager.repositories.UserRepository;
import com.example.task_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController
{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userDao;

    private final UserService userService;




    @Autowired
    public ProfileController(PasswordEncoder passwordEncoder, UserRepository userDao, UserService userService)
    {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.userService = userService;
    }



    /****************PRODUCTION MAPPING CODE****************/
    @RequestMapping(value = {"/userProfile"})

    public String userProfile(Model model, @PathVariable Optional<Long> id) {

        if (id.isPresent()) {

            long userId = id.get();

            User user = userService.findById(userId);

            List<Task> tasks = user.getTasks();

            //Add attributes for page
            model.addAttribute("user", user);
            model.addAttribute("tasks", tasks);
            model.addAttribute("newTask", new Task());
            return "users/userProfile";

        } else {
            //Get logged-in user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getPrincipal() == "anonymousUser")
            {
                return "redirect:/login";
            }
            User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDao.getReferenceById(loggedinUser.getId());

            //Get logged-in user's posts
            List<Task> tasks = user.getTasks();

            //Add attributes for page
            model.addAttribute("user", user);
            model.addAttribute("tasks", tasks);
            model.addAttribute("newTask", new Task());
            return "users/userProfile";
        }
    }

    @GetMapping("/userProfile/{id}")
    public String userProfileOther(@PathVariable long id, Model model){
        //Get user
        User user = userDao.getReferenceById(id);

        //Get Post
        List<Task> posts = user.getTasks();

        model.addAttribute("user",user);
        model.addAttribute("posts", posts);
        model.addAttribute("newTask", new Task());
        return "users/userProfile";
    }

    /****************TEST MAPPING CODE****************/

    @GetMapping("/profile")
    public String profile(Model model)
    {

        User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("updateProfile", user);
        return "users/userProfile";
    }

    @GetMapping("/updateProfile")
    public String updateProfile(Model model)
    {

        User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("user", user);

        return "users/updateProfile";
    }
    @PostMapping("/updateProfile")
    public String updateProfile
            (@ModelAttribute User updatedUser,
             @RequestParam("id") long id,
             @RequestParam("username") String username,
             @RequestParam("firstName") String firstName,
             @RequestParam("lastName") String lastName,
             @RequestParam("email") String email,
             @RequestParam("password") String password)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedinUser = (User) authentication.getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());

        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        if (!password.isEmpty())
        {
            String hash = passwordEncoder.encode(password);
            user.setPassword(hash);
        }

        userDao.save(user);


        return "redirect:/userProfile";
    }

    @PostMapping("/delete")
    public String deleteUser()
    {
        return "redirect:/login";
    }
}
