package com.example.report_generation.report_generation.dto;

import java.util.List;


import com.example.report_generation.report_generation.dto.UserEvent.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DyslexiaTypeEvent {
    private String Message;
    private String userId;
    private Status status;
    private List<DyslexiaTypesDTO> DylsexiaTypes;

}
