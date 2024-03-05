package com.example.report_generation.report_generation.kafka;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;

import com.example.basedomain.basedomain.dto.DyslexiaTypeEvent;
import com.example.basedomain.basedomain.dto.DyslexiaTypesDTO;
import com.example.basedomain.basedomain.dto.UserEvent;
import com.example.report_generation.report_generation.models.DyslexiaCategory;

@Service
public class DyslexiaTypeProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DyslexiaTypeProducer.class);

    private KafkaTemplate<String, DyslexiaTypeEvent> kafkaTemplate;

    public DyslexiaTypeProducer(KafkaTemplate<String, DyslexiaTypeEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(DyslexiaTypeEvent event, String topicName) {
        LOGGER.info(String.format("Sending Dyslexia Event => %s", event.toString()));
        // create a message
        Message<DyslexiaTypeEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC,
                        topicName)
                .build();
        kafkaTemplate.send(message);
    }

    public boolean broadcastDyslexiaType(List<DyslexiaCategory> userCategories, String message, String userId) {
        try {
            DyslexiaTypeEvent dyslexiaTypeEvent = new DyslexiaTypeEvent();
            List<DyslexiaTypesDTO> dyslexiaTypesDTOs = new ArrayList<DyslexiaTypesDTO>();
            dyslexiaTypeEvent.setStatus(null);
            dyslexiaTypeEvent.setMessage(message);
            dyslexiaTypeEvent.setUserId(userId);
            for (DyslexiaCategory dyslexiaCategory : userCategories) {
                System.out.println("added category to broadcast");
                dyslexiaTypesDTOs.add(new DyslexiaTypesDTO(dyslexiaCategory.getName(), dyslexiaCategory.getId()));
            }
            dyslexiaTypeEvent.setDylsexiaTypes(dyslexiaTypesDTOs);
            sendMessage(dyslexiaTypeEvent, UserEvent.Topics.DYSLEXIATYPE.getValue());
            System.err.println(dyslexiaTypeEvent.toString());
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("couldnt send dyslexia types");
            return false;
        }
    }
}
