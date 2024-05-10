package com.basedomain.basedomain.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserEvent {
    private String message;
    private Constants.Status status;
    private UserDTO user;

}
