package com.nexia.nexia.controlles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.models.User;
import com.nexia.nexia.services.UserService;
import com.nexia.nexia.services.jwtService;

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
    @Autowired
    private jwtService jwtService;
    @PutMapping("/update/{token}")
    public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable String token) {
        String id = jwtService.extractUUID(token);
        user.setId(id);
        userService.updateEntity(user);
        return new ResponseEntity<User> (user,HttpStatus.OK);
    }

    @PostMapping("/delete/{token}")
    public ResponseEntity<String> deleteUser(@PathVariable String token) {
        userService.deleteEntity(token);
        return new ResponseEntity<String>("User Got Deleted",HttpStatus.OK);
    }

    @GetMapping("/get/{token}")
    public ResponseEntity<User> getUser(@PathVariable String token) {
        User user = userService.getEntityById(token);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
}
