package com.basedomain.basedomain.dto;

import java.util.List;

import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DyslexiaTypeEvent {

    private String userId;
    private String Message;

    private Constants.Status status;
    private List<DyslexiaTypesDTO> DylsexiaTypes;
    

}
