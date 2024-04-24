package com.nexia.nexia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    private String message;
    private Status status;
    private UserDTO user;
    public static enum Status{
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

