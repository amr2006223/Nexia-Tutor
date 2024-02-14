package com.nexia.nexia.services;

import java.util.Optional;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private jwtService jwtService;
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

    public String signUp(User user){
        String passwordHashed = this.bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwordHashed);
        this.userRepository.save(user);

        String token = this.jwtService.generateToken(user.getId());
        return token;
    }

    public Map<String, String> login(String username,String password)
    {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
          //  return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
          return null;
        }
        if (!this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        String token = this.jwtService.generateToken(user.getId());
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        String uuid = this.jwtService.extractUUID(token);
        result.put("UUID", uuid);
        return result;
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
