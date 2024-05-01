package com.nexia.nexia.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.UserRepository;

@Service
public class UserService extends CrudOperations<User, String, UserRepository>  {

    @Autowired
    private JwtService jwtService;

    public UserService(UserRepository repository) {
        super(repository);
    }

    @Override
    public boolean deleteEntity(String token) {
        User user = getEntityById(token);
        return super.deleteEntity(user.getId());
    }

    @Override
    public User getEntityById(String token){
        String userId = jwtService.extractUUID(token);
        return super.getEntityById(userId);
    }

}
