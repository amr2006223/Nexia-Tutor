package com.basedomain.basedomain.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.basedomain.basedomain.dto.UserEvent.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DyslexiaTypeEvent {
    private String Message;
    private String userId;
    private Status status;
    private List<DyslexiaTypesDTO> DylsexiaTypes;

}
