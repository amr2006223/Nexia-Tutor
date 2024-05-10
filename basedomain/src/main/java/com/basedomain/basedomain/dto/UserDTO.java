package com.basedomain.basedomain.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Indicates that the class is a Spring component.
 * Spring will automatically detect and instantiate this class during component
 * scanning.
 */
@Component
// annotation that automatically generates getters and setters for all fields,
@Data
// annotation that generates a constructor with all arguments.
@AllArgsConstructor
// annotation that generates a default constructor with no arguments.
@NoArgsConstructor

public class UserDTO {
    private String id;
    private String username;
    private String password;
    private Date birthDate;
    private String nationality;
    private boolean gender;
    private String token;
    private String role;
    private String parentPin;
}
