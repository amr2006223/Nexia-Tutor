package com.example.report_generation.report_generation.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.service.PDFGeneratorService;
import com.example.report_generation.report_generation.service.ScreeningService;
import com.example.report_generation.report_generation.service.UserService;



@RestController
public class UserController {
    @Autowired
    ScreeningService _screeningService;
    @Autowired
    PDFGeneratorService _pdfGeneratorService;
    @Autowired
    UserService _userService;
    
    String filePath = "reportGeneration\\src\\main\\resources\\json\\ImportantUser.json";
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User newUser) throws IOException {
        User user = _userService.InsertUser(newUser, filePath);
        _pdfGeneratorService.generateDocumentInServer(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) throws IOException {
        User user = _userService.getUserById(id, filePath);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<User> deleteUserById(@PathVariable String userId) throws IOException {
        return new ResponseEntity<User>(_userService.deleteUserById(userId,filePath),HttpStatus.OK);
    }

   
 
}
