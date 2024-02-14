package com.nexia.nexia.services;

import java.util.Optional;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

import com.example.basedomain.basedomain.dto.UserDTO;
import com.example.basedomain.basedomain.dto.UserEvent;
import com.nexia.nexia.kafka.UserProducer;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.DyslexiaTypeRepository;
import com.nexia.nexia.repositories.UserRepository;
import com.nexia.nexia.models.DyslexiaType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DyslexiaTypeRepository dyslexiaTypeRepository;
    @Autowired
    private UserProducer userProducer;

    // TODO: get user by userID
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User addUser(UserDTO user) {
        // add user to database
        List<DyslexiaType> types = new ArrayList<>();
        Date date = null;
        String id = "203";
        User addedUser = new User(id, user.getUsername(), "asdasd", date, "asdf", true, types,"USER");
        // userRepository.save(user);
        UserEvent userEvent = new UserEvent();
        userEvent.setStatus("Pending");
        userEvent.setMessage("user status is pending");
        userEvent.setUser(user);

        NewTopic topic = TopicBuilder.name("new-topic").build();
        // userProducer.changeTopic(topic);
        userProducer.sendMessage(userEvent, "new-topic");
        userProducer.sendMessage(userEvent, "user-data");

        System.err.println(user.toString());
        return addedUser;
    }

}
