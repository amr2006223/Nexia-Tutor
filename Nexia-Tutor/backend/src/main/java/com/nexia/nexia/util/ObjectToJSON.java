package com.nexia.nexia.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJSON <T> {
    
    public String ObjectToString(T entity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String entityJsonString = objectMapper.writeValueAsString(entity);
        return entityJsonString;
    }
}
