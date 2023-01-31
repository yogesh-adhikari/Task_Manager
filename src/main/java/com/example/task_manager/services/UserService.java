package com.example.task_manager.services;


import com.example.task_manager.models.User;
import com.example.task_manager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {



    private final UserRepository userDao;

    @Autowired
    public UserService(UserRepository userDao) {
        this.userDao = userDao;

    }

    public User findById(long id){
        Optional<User> user = userDao.findById(id);
        return user.orElse(null);
    }

    public void saveUser(User user){
        userDao.save(user);
    }




    public List<User> findAll() {
        return  userDao.findAll();
    }

    public void delete(User user) {
        userDao.delete(user);
    }

}
