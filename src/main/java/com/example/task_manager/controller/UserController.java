package com.example.task_manager.controller;

import com.example.task_manager.models.User;
import com.example.task_manager.repositories.UserRepository;
import com.example.task_manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserController {


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userDao;


    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {


        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isValidPassword(String password) {
       return password.length() >= 8 &&
                password.contains("!") ||
                password.contains("#") ||
                password.contains("$") ||
                password.contains("%") ||
                password.contains("&") ||
                password.contains("*") ||
                password.contains("-") ||
                password.contains("_") ||
                password.contains("(") ||
                password.contains("+") ||
                password.contains(")") ||
                password.contains("=") ||
                password.contains("{") ||
                password.contains("}") ||
                password.contains("[") ||
                password.contains("]") ||
                password.contains(":") ||
                password.contains(";") ||
                password.contains("\"") ||
         password.contains("<") ||
          password.contains(">") ||
                password.contains(".") ||
                password.contains("?") ||
               password.contains("@") ||
               password.contains("\\") ||
                password.contains("'");

    }


    User loggedinUser(){
        return   (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/")
    public String showLandingPage(){

        return "landingPage";
    }


    @GetMapping("/sign-up")
    public String showSignupForm(){

        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(
            @RequestParam("firstName")String firstName,
            @RequestParam("lastName")String lastName,
            @RequestParam("username")String username,
            @RequestParam("email")String email,
            @RequestParam("password")String password,
           Model model,RedirectAttributes attributes){

        User user = new User(firstName,lastName,email,password,username);

       if(!isValidPassword(user.getPassword())){
          model.addAttribute("firstname",user.getFirstName());
           model.addAttribute("lastname",user.getLastName());
           model.addAttribute("username",user.getUsername());
           model.addAttribute("email",user.getEmail());

           model.addAttribute("invalid","Password must be 8 characters and above and must contain a special character" );
           return "users/sign-up";
       }

        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userService.saveUser(user);
        attributes.addFlashAttribute("success","You successfully registered! You can now login");
        return "redirect:/login";
    }

    //Upload User Profile Image
    @GetMapping ("/users/imageUpload/{baseImgUrl}/{extensionUrl}/{returnUrl}")
    public String profileImageUpload(@PathVariable String baseImgUrl,
                                     @PathVariable String extensionUrl,
                                     @PathVariable String returnUrl){
        System.out.println("Upload image controller hit");
        System.out.println("base image url: "+baseImgUrl);
        System.out.println("extension url: "+ extensionUrl);
        System.out.println("return url: "+ returnUrl);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        String imgUrl = "https://"+baseImgUrl+"/"+extensionUrl;
        user.setProfilePicture(imgUrl);
        userDao.save(user);
        System.out.println("Save should hit");
        return "redirect:/"+returnUrl;
    }


}
