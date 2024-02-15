package com.nexia.nexia.controlles;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import com.nexia.nexia.models.User;
import com.nexia.nexia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        User user = userService.login(body.get("username"), body.get("password"));
        if(user == null){
            return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
        return new ResponseEntity<String>("not implemented", HttpStatus.OK);
    }
}
