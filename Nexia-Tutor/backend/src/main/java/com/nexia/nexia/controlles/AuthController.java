package com.nexia.nexia.controlles;

import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;

import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.UserRepository;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder; // hash of password,comparison of passwords(hashed and
                                                         // plaintext password)
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private jwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {

        String passwordHashed = this.bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwordHashed);
        this.userRepository.save(user);

        String token = this.jwtService.generateToken(user.getId());
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
        String hashedPassword = user.getPassword();
        boolean matched = this.bCryptPasswordEncoder.matches(password, hashedPassword);
        if (matched) {
            String token = this.jwtService.generateToken(user.getId());

            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            String uuid = this.jwtService.extractUUID(token);
            result.put("UUID", uuid);
            return new ResponseEntity<>(result, HttpStatus.OK);

        }
        return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);

    }

}
