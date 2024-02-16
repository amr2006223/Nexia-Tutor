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
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateEntity(user);
        return new ResponseEntity<User> (user,HttpStatus.OK);
    }

    @PostMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteEntity(userId);
        return new ResponseEntity<String>("User Got Deleted",HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getEntityById(userId);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    
    
}
