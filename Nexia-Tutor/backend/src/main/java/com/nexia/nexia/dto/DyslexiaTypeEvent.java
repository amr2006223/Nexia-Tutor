package com.nexia.nexia.dto;

import java.util.List;

import com.nexia.nexia.dto.UserEvent.Status;

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
