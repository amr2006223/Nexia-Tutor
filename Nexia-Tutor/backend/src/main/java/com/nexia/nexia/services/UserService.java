package com.nexia.nexia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.basedomain.basedomain.dto.UserEvent;
import com.nexia.nexia.kafka.UserProducer;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.UserRepository;
import com.nexia.nexia.services.iservices.IUserService;

@Service
public class UserService extends CrudOperations<User, String, UserRepository> implements IUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private jwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProducer userProducer;

    public UserService(UserRepository repository) {
        super(repository);
    }
    @Override
    public User login(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null)
            return null;
        if (!this.bCryptPasswordEncoder.matches(password, user.getPassword()))
            return null;
        String token = user.getToken();
        if (token == null || jwtService.isTokenExpired(token)) {
            token = this.jwtService.generateToken(user.getId());
            user.setToken(token);
            userRepository.save(user);
        }
        return user;
    }
    
    // @Override
    // public void logout() {
    //     throw new UnsupportedOperationException("Method logout() is not implemented yet");
    // }

    @Override
    public User addEntity(User user) {
        String passwordHashed = this.bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwordHashed);
        User addedUser = this.userRepository.save(user);
        String token = this.jwtService.generateToken(user.getId());
        // System.out.println(token);
        user.setToken(token);
        if (!userProducer.broadcastUser(user, UserEvent.Status.ADD, "Adding user")) {
            System.out.println("error user couldnt be added in other microservices");
        }
        return addedUser;
    }

    @Override
    public boolean deleteEntity(String token) {
        User user = getEntityById(token);
        super.deleteEntity(user.getId());
        if (!userProducer.broadcastUser(user, UserEvent.Status.DELETE, "Deleting user"))
            return false;
        return true;
    }

    @Override
    public User updateEntity(User updatedUser) {
        try {

            User user = userRepository.findById(updatedUser.getId()).orElse(null);
            if (user == null)
                return null;
            String passwordHashed = this.bCryptPasswordEncoder.encode(updatedUser.getPassword());
            updatedUser.setPassword(passwordHashed);
            super.updateEntity(updatedUser);
            userProducer.broadcastUser(user, UserEvent.Status.UPDATE, "Update User");
            return updatedUser;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public User getEntityById(String token){
        String userId = jwtService.extractUUID(token);
        return super.getEntityById(userId);
    }
}
