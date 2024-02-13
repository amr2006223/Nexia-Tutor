package com.example.report_generation.report_generation.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.basedomain.basedomain.dto.UserEvent;
@Service
public class UserConsumer {
     private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);
     @KafkaListener(topics = "${spring.kafka.template.default-topic}",groupId = "${spring.kafka.consumer.group-id}")
     public void Consume(UserEvent userEvent){
        LOGGER.info(String.format("User event recieved => %s" , userEvent.toString()));
        //save user in database

     }
     
     @KafkaListener(topics = "new-topic", groupId = "${spring.kafka.consumer.group-id}")
     public void Show(UserEvent userEvent) {
        LOGGER.info(String.format("Show event recieved from new-topic => %s", userEvent.toString()));
        // save user in database

     }
}
