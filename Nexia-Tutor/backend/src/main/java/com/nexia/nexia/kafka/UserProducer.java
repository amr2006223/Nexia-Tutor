package com.nexia.nexia.kafka;



import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.basedomain.basedomain.dto.UserEvent;



@Service
public class UserProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProducer.class);

    
    private KafkaTemplate<String, UserEvent> kafkaTemplate;
    
    public UserProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    

    public void sendMessage(UserEvent event,String topicName){
        LOGGER.info(String.format("Sending User Event => %s", event.toString()));
        //create a message 
        Message<UserEvent> message = MessageBuilder
            .withPayload(event)
            .setHeader(KafkaHeaders.TOPIC, 
                        topicName)
            .build();
            kafkaTemplate.send(message);
    }
}
