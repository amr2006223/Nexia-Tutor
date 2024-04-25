package com.example.report_generation.report_generation.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private Date birthDate;
    private String nationality;
    private boolean gender;
    private String token;
}
