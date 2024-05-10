package com.nexia.nexia.kafka;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.basedomain.basedomain.dto.UserDTO;
import com.basedomain.basedomain.dto.UserEvent;
import com.nexia.nexia.models.DyslexiaType;
import com.nexia.nexia.models.User;

import com.nexia.nexia.services.UserService;

@Service
public class UserConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);
    @Autowired
    private UserService userService;

    @KafkaListener(topics = "${topics.user}")
    public void Consume(UserEvent userEvent) {
        LOGGER.info(String.format("User event recieved => %s", userEvent.toString()));
        UserDTO user = userEvent.getUser();
        if (user == null)
            return;
        Set<DyslexiaType> userDyslexiaTypes = new HashSet<DyslexiaType>();
        User newUser = new User(user.getId(), user.getUsername(), user.getPassword(),
                user.getBirthDate(), user.getNationality(), user.isGender(),
                userDyslexiaTypes, user.getRole(),
                user.getParentPin());
        switch (userEvent.getStatus()) {
            case ADD:
            case UPDATE:
                userService.addEntity(newUser);;
                break;
            case DELETE:
                userService.deleteEntity(newUser.getToken());
                break;
            default:
                break;
        }
        
    }
}
