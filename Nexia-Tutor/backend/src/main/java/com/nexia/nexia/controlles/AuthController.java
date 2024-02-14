package com.nexia.nexia.controlles;

import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.UserRepository;
import com.nexia.nexia.services.UserService;
import com.nexia.nexia.services.jwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String token = userService.signUp(user);
        return new ResponseEntity<String>(token, HttpStatus.OK);

    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String, String> body) {
        Map<String,String> result = userService.login(body.get("username"), body.get("password"));
        if(result == null){
            return new ResponseEntity<Map<String, String>>(new HashMap<String, String>() {
                {
                    put("Error", "Not Found");
                }
            }, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);

    }

}
