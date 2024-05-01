package com.nexia.nexia.controlles;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.models.User;
import com.nexia.nexia.services.JwtService;
import com.nexia.nexia.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/nexia-tutor/api/auth")

public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User addedUser = userService.addEntity(user);
        return new ResponseEntity<User>(addedUser, HttpStatus.OK);

    }
    @PostMapping("/token/validate")
    public ResponseEntity<Boolean> validate(@RequestBody Map<String,String> body) {
        String token = body.get("token");
        return new ResponseEntity<Boolean>(jwtService.validateToken(token),HttpStatus.OK) ;

    }
    @GetMapping("/genToken/{id}")
    public ResponseEntity<String> getToken(@PathVariable String id) {
        return new ResponseEntity<String>(jwtService.generateToken(id),HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        User user = userService.login(body.get("username"), body.get("password"));
        if(user == null){
            return new ResponseEntity<String>("User Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    //TO-DO logout implementation
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
        return new ResponseEntity<String>("not implemented", HttpStatus.OK);
    }
}
