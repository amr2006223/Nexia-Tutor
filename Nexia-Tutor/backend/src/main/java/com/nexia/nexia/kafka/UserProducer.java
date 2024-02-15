package com.nexia.nexia.kafka;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.basedomain.basedomain.dto.UserDTO;
import com.example.basedomain.basedomain.dto.UserEvent;
import com.nexia.nexia.models.User;



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

    public boolean broadcastUser(User user,UserEvent.Status status,String message){
        try{
            UserEvent userEvent = new UserEvent();
            userEvent.setStatus(status);
            userEvent.setMessage(message);
            userEvent.setUser(new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getBirthDate(), user.getNationality(), user.isGender(), user.getToken()));
            sendMessage(userEvent, UserEvent.Topics.USER.getValue());
            System.err.println(user.toString());
            return true;
        }catch(Exception e){
            System.out.println(e.toString());
            return false;
        }
    }
}
