package com.example.report_generation.report_generation.kafka;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.basedomain.basedomain.dto.UserDTO;
import com.example.basedomain.basedomain.dto.UserEvent;
import com.example.report_generation.report_generation.models.User;
@Service
public class UserConsumer {
     private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);
     @Autowired
     UserRepository userRepository;
     @KafkaListener(topics = "${topics.user}" ,groupId = "${group.id}")
     public void Consume(UserEvent userEvent){
        LOGGER.info(String.format("User event recieved => %s" , userEvent.toString()));
        //save user in database
        UserDTO userDTO = userEvent.getUser();
        List<UserData> userData = new ArrayList<UserData>();
        User addedUser = new User(userDTO.getId(), userDTO.getUsername(), userData);
        switch (userEvent.getStatus()) {
         case ADD:
         case UPDATE:
            userRepository.save(addedUser);
            break;
         case DELETE:
            userRepository.delete(addedUser);
            break;
         default:
            break;
        } 
     }
     
   //   @KafkaListener(topics = "USER", groupId = "${spring.kafka.consumer.group-id}")
   //   public void Show(UserEvent userEvent) {
   //      LOGGER.info(String.format("Show event recieved from new-topic => %s", userEvent.toString()));
   //      // save user in database

   //   }
}
