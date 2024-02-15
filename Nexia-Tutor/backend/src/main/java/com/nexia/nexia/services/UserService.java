package com.nexia.nexia.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.basedomain.basedomain.dto.UserEvent;
import com.nexia.nexia.kafka.UserProducer;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.UserRepository;


@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private jwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProducer userProducer;

    // TODO: get user by userID
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User signUp(User user){
        try{

            String passwordHashed = this.bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(passwordHashed);
            String token = this.jwtService.generateToken(user.getId());
            user.setToken(token);
            User addedUser = this.userRepository.save(user);
            if(userProducer.broadcastUser(user,UserEvent.Status.ADD,"Adding user")){
                System.out.println("error user couldnt be added in other microservices");
            }
            return addedUser;
        }catch(Exception e){
            return null;
        }
    }

    
    public User login(String username,String password)
    {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;
        if (!this.bCryptPasswordEncoder.matches(password, user.getPassword())) return null;
        String token = user.getToken();
        if(token == null || jwtService.isTokenExpired(token)){
            token = this.jwtService.generateToken(user.getId());
            user.setToken(token);
            userRepository.save(user);
        }
        return user;
    }
    public User updateUser(User updatedUser){
        User user = userRepository.findById(updatedUser.getId()).orElse(null);
        if(user == null) return null;
        String passwordHashed = this.bCryptPasswordEncoder.encode(updatedUser.getPassword());
        updatedUser.setPassword(passwordHashed);
        userRepository.save(updatedUser);
        userProducer.broadcastUser(user, UserEvent.Status.UPDATE, "Update User");
        return updatedUser;
    }
    public void deleteUser(String id){
        User user = getUserById(id);
        userRepository.delete(user);
        if(!userProducer.broadcastUser(user, UserEvent.Status.DELETE,"Deleting user")) 
            System.out.println("error user couldnt be deleted in other microservices");
        return;
    }


    


}
