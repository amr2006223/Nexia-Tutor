package com.nexia.nexia.services.iservices;

import com.nexia.nexia.models.User;

public interface IUserService {

    User login(String username, String password);

}
