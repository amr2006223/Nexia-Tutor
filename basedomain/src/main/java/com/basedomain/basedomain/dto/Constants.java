package com.basedomain.basedomain.dto;

public class Constants {
    public static enum Status {
        ADD,
        UPDATE,
        DELETE
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
