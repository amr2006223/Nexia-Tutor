package com.nexia.nexia.controlles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.models.User;
import com.nexia.nexia.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // @PostMapping("/add")
    // public ResponseEntity<User> addNewUser(@RequestBody UserDTO user) {
    //     User addedUser = userService.addUser(user);
    //     return new ResponseEntity<User>(addedUser,HttpStatus.OK);
    // }

    @PutMapping("/update")
    public ResponseEntity<User> putMethodName(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<User> (user,HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> addNewUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<String>("User Got Deleted",HttpStatus.OK);
    }
    
}
