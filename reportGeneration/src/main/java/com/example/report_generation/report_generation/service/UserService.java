package com.example.report_generation.report_generation.service;

import com.example.report_generation.report_generation.kafka.DyslexiaTypeProducer;
import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.repository.DyslexiaCategoryRepository;
import com.example.report_generation.report_generation.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    public User insertUser(User newUser, String filePath) throws IOException {
        User user = userRepository.findById(newUser.getId()).orElse(null);
        if(user == null) return null;
        newUser.setUsername(user.getUsername());
        List<User> userList = readUserDataFromFile(filePath);

        if (!isUserInList(userList, newUser)) {
            userList.add(newUser);
            saveUserDataToFile(filePath, userList);
            return newUser;
        }

        return null;
    }

    public User getUserById(String userId, String filePath) {
        List<User> userList = readUserDataFromFile(filePath);

        return userList.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public User deleteUserById(String userId, String filePath) throws IOException {
        List<User> userList = readUserDataFromFile(filePath);
        User userToDelete = getUserById(userId, filePath);

        if (userToDelete == null) {
            System.err.println("User not found");
            return null;
        }

        userList.remove(userToDelete);
        saveUserDataToFile(filePath, userList);

        return userToDelete;
    }

    public UserData getLatestRecord(User user) {
        List<UserData> data = user.getData();

        return data.stream()
                .max(Comparator.comparing(UserData::getDate))
                .orElse(null);
    }


    public boolean hasUserTakenTest(String token, String filePath) {
        String userId = jwtService.extractUUID(token);
        User user = getUserById(userId, filePath);

        return user != null && user.getData() != null;
    }


    private List<User> readUserDataFromFile(String filePath) {
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                return new ArrayList<>();
            }

            return objectMapper.readValue(file, new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            System.err.println("Failed to read user data from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    private void saveUserDataToFile(String filePath, List<User> userList) {
        try {
            File file = new File(filePath);
            objectMapper.writeValue(file, userList);
        } catch (IOException e) {
            System.err.println("Failed to save user data to file: " + e.getMessage());
        }
    }


    private boolean isUserInList(List<User> userList, User newUser) {
        for (User user : userList) {
            if (user.getId().equals(newUser.getId())) {
                List<UserData> oldUserData = user.getData();
                List<UserData> newUserData = newUser.getData();

                oldUserData.addAll(newUserData);
                user.setData(oldUserData);
                return true;
            }
        }

        return false;
    }
}
