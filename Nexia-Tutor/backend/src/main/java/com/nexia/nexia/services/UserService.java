package com.nexia.nexia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.basedomain.basedomain.dto.UserEvent;
import com.nexia.nexia.kafka.UserProducer;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.UserRepository;

@Service
public class UserService extends CrudOperations<User, String, UserRepository> {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProducer userProducer;

    public UserService(UserRepository repository) {
        super(repository);
    }

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

    public void logout() {
        throw new UnsupportedOperationException("Method logout() is not implemented yet");
    }

    @Override
    public User addEntity(User user) {
        String passwordHashed = this.bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwordHashed);
        String token = this.jwtService.generateToken(user.getId());
        System.out.println(token);
        user.setToken(token);
        User addedUser = this.userRepository.save(user);
        if (userProducer.broadcastUser(user, UserEvent.Status.ADD, "Adding user")) {
            System.out.println("error user couldnt be added in other microservices");
        }
        return addedUser;
    }

    @Override
    public boolean deleteEntity(String userId) {
        User user = getEntityById(userId);
        super.deleteEntity(userId);
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
}
