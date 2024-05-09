package com.example.report_generation.report_generation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.service.JwtService;
import com.example.report_generation.report_generation.service.PDFGeneratorService;
import com.example.report_generation.report_generation.service.UserService;

@RestController
@RequestMapping("/report-generation")
public class UserController {
    @Autowired
    private PDFGeneratorService _pdfGeneratorService;
    @Autowired
    private UserService _userService;
    @Autowired
    private JwtService _JwtService;

    String filePath = "src\\main\\resources\\json\\ImportantUser.json";

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User newUser) throws IOException {
        String token = newUser.getId();
        newUser.setId(_JwtService.extractUUID(token));
        User user = _userService.insertUser(newUser, filePath);

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
        return new ResponseEntity<User>(_userService.deleteUserById(userId, filePath), HttpStatus.OK);
    }

    @PostMapping("/isTested")
    public ResponseEntity<?> isTested(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        Map<String, String> validation = _JwtService.validate(token);
        if (validation.containsKey("error"))
            return new ResponseEntity<>("Error while converting json" + validation.get("error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        if (validation.get("status").equals("invalid"))
            return new ResponseEntity<>("Token is not valid", HttpStatus.BAD_REQUEST);
        boolean hasUserTakenTest = _userService.hasUserTakenTest(token, filePath);
        Map<String, String> response = new HashMap<>();
        response.put("hasUserTakenTest", String.valueOf(hasUserTakenTest));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getMethodName() {
        return new ResponseEntity<String>("test", HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<List<String>> getMetho() {
        List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }

}
