package com.nexia.nexia.controlles;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/test")

public class TestController {
    @GetMapping("/me")
    public ResponseEntity<UserDetails> GetMyDetails(@AuthenticationPrincipal UserDetails userdetails) {
        return new ResponseEntity<UserDetails>(userdetails, HttpStatus.OK);
    }

}
