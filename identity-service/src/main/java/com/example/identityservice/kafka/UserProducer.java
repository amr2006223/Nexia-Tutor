package com.example.identityservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.basedomain.basedomain.dto.Constants;
import com.basedomain.basedomain.dto.UserDTO;
import com.basedomain.basedomain.dto.UserEvent;

@Service
public class UserProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProducer.class);

    private KafkaTemplate<String, UserEvent> kafkaTemplate;
    @Value("${topics.user}")
    private String usertopic;

    public UserProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserEvent event) {
        LOGGER.info(String.format("Sending User Event => %s", event.toString()));
        // Create a message
        Message<UserEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC,
                        usertopic)
                .build();
        kafkaTemplate.send(message);
    }

    public boolean broadcastUser(UserDTO user, Constants.Status status, String message) {
        try {
            UserEvent userEvent = new UserEvent();
            userEvent.setStatus(status);
            userEvent.setMessage(message);
            userEvent.setUser(user);
            sendMessage(userEvent);
            System.err.println(user.toString());
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
