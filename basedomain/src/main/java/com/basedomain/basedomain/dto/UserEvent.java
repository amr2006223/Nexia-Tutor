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
    private Status status;
    private UserDTO user;

    public static enum Status {
        ADD,
        UPDATE,
        DELETE,

    }

    public static enum Topics {
        USER("USER"),
        DYSLEXIATYPE("DYSLEXIATYPE"),
        LESSON("LESSON");

        private final String value;

        Topics(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
