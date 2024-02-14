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
import com.nexia.nexia.util.topics;
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
        user.setToken(token);
        produceUser(user);
        return token;
    }

    public User login(String username,String password)
    {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
          //  return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
          return null;
        }
        if (!this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        String token = user.getToken();
        if(user.getToken() == null || jwtService.isTokenExpired(user.getToken())){
            token = this.jwtService.generateToken(user.getId());
            user.setToken(token);
        }

        return user;
    }

    public boolean produceUser(User user){
        try{
            UserEvent userEvent = new UserEvent();
            userEvent.setStatus(UserEvent.Status.ADD);
            userEvent.setMessage("Adding this user");
            userEvent.setUser(new UserDTO(user.getId(), user.getUsername()));
            userProducer.sendMessage(userEvent, UserEvent.Topics.USER.getValue());
            System.err.println(user.toString());
            return true;
        }catch(Exception e){
            return false;
        }
    }
    // public User addUser(UserDTO user) {
    //     // add user to database
    //     List<DyslexiaType> types = new ArrayList<>();
    //     Date date = null;
    //     String id = "203";
    //     User addedUser = new User(id, user.getUsername(), "asdasd", date, "asdf", true, types,"USER");
    //     // userRepository.save(user);
    //     UserEvent userEvent = new UserEvent();
    //     userEvent.setStatus("Pending");
    //     userEvent.setMessage("user status is pending");
    //     userEvent.setUser(user);

    //     NewTopic topic = TopicBuilder.name("new-topic").build();
    //     // userProducer.changeTopic(topic);
    //     userProducer.sendMessage(userEvent, "new-topic");
    //     userProducer.sendMessage(userEvent, "user-data");

    //     System.err.println(user.toString());
    //     return addedUser;
    // }

}
