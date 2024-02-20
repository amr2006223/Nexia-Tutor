package com.example.report_generation.report_generation.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.repository.DyslexiaCategoryRepository;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class UserService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    DyslexiaCategoryRepository _dyslexiaCategoryRepository;

    public User InsertUser(User newUser, String filePath) throws IOException {
        try {

            File jsonFile = new File(filePath);
            List<User> userList = new ArrayList<>();
            if (checkIfJsonFileExist(jsonFile, userList, newUser) != null)
                return newUser;
            // If the file exists, read the data and try to find the user
            userList = objectMapper.readValue(jsonFile,
                    new TypeReference<List<User>>() {
                    });
            // If user doesn't exist, create a new user entry
            if (!checkIfUserInJsonFile(userList, newUser)) {
                userList.add(newUser);
                objectMapper.writeValue(jsonFile, userList);
                return newUser;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(String userId, String filePath) {

        try {
            // Read the JSON file and parse it into a list of User objects
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(filePath);
            List<User> userList = List.of(objectMapper.readValue(file, User[].class));

            // Find the user with the specified ID
            for (User user : userList) {
                if (userId.equals(user.getId())) {
                    return user;
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User deleteUserById(String userId, String filePath) throws IOException {
        User user = getUserById(userId, filePath);
        if (user == null) {
            // User with the specified ID not found
            System.err.println("User not found");
            return null;
        }

        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();

        // Read existing JSON data from the file
        JsonNode rootNode = objectMapper.readTree(file);

        // Check if the root node is an array
        if (rootNode.isArray()) {
            ArrayNode usersArray = (ArrayNode) rootNode;

            // Use Java Stream API for more concise code
            Iterator<JsonNode> userIterator = usersArray.iterator();
            while (userIterator.hasNext()) {
                JsonNode userNode = userIterator.next();

                if (userId.equals(userNode.get("id").asText())) {
                    // Remove the entire userNode
                    userIterator.remove();

                    try {
                        // Write the updated JSON data back to the file
                        objectMapper.writeValue(file, rootNode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Returning the deleted user could be useful for further processing or logging
                    // Replace with the actual deleted user if needed
                    return user;
                }
            }
        }

        // If the code reaches here, the user with the specified ID was not found
        System.err.println("User not found");
        return null;
    }

    public boolean checkIfUserInJsonFile(List<User> userList, User newUser) {
        boolean found = false;
        for (User user : userList) {
            if (user.getId().equals(newUser.getId())) {
                // If user exists, add the new data to their existing record
                List<UserData> oldUserData = user.getData();
                List<UserData> newUserData = newUser.getData();
                oldUserData.addAll(newUserData);
                user.setData(oldUserData);
                found = true;
                break;
            }

        }
        return found;

    }

    public User checkIfJsonFileExist(File jsonFile, List<User> userList, User newUser)
            throws StreamWriteException, DatabindException, IOException {
        if (!jsonFile.exists()) {
            // If the file doesn't exist, create a new list with the provided user data
            userList.add(newUser);
            objectMapper.writeValue(jsonFile, userList);
            return newUser;
        }
        return null;
    }

    public UserData getLatestRecord(User user) {
        List<UserData> data = user.getData();
        if (data == null || data.isEmpty()) {
            return null; // No records available
        }
        UserData latestRecord = data.get(0); // Assume the first record is the latest initially
        for (UserData record : data) {
            if (record.getDate().after(latestRecord.getDate())) {
                latestRecord = record;
            }
        }
        return latestRecord;
    }

    public double getAccuracy(int start, int end, Map<String, String> record) {
        double accuracy = 0;
        int count = 1;
        for (int i = start; i <= end; i++) {
            accuracy += Double.parseDouble(record.get("Accuracy" + i));
            count++;
        }
        return accuracy / count;
    }

    public List<DyslexiaCategory> categoryDetection(User user) {
        // get the newest record of the user
        UserData data = getLatestRecord(user);
        Map<String, String> record = data.getRecord();
        // get the average of the record and what categories that the user had
        List<DyslexiaCategory> userCategories = new ArrayList<DyslexiaCategory>();
        // TO-DO needs refactoring asap database need to have the number or the interval
        // of question fro each dyslexia type
        DyslexiaCategory alpha = _dyslexiaCategoryRepository.findCategoryByName("Alphabetic Awareness");
        DyslexiaCategory phono = _dyslexiaCategoryRepository.findCategoryByName("Phonological Awareness");
        DyslexiaCategory visual = _dyslexiaCategoryRepository.findCategoryByName("Visual Working Memory");
        System.out.println("User Accuracy: " + getAccuracy(5,9, record) + "\n" + "alpha accuracy " + phono.getAverage());
        if (getAccuracy(1, 4, record) <= alpha.getAverage())
            userCategories.add(alpha);
        if (getAccuracy(5, 9, record) <= phono.getAverage())
            userCategories.add(phono);
        if (getAccuracy(18, 21, record) <= visual.getAverage())
            userCategories.add(visual);
        return userCategories;

    }
}
