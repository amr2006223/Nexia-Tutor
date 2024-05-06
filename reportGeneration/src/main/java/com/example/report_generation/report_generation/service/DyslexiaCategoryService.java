package com.example.report_generation.report_generation.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.report_generation.report_generation.kafka.DyslexiaTypeProducer;
import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.repository.DyslexiaCategoryRepository;
import java.util.List;
import java.util.Map;
@Service
public class DyslexiaCategoryService {
    @Autowired
    private DyslexiaCategoryRepository _categoryRepository;
    @Autowired
    private DyslexiaTypeProducer dyslexiaTypeProducer;
    @Autowired
    private UserService userService;
    
    public List<DyslexiaCategory> categorizeUser(User user) {
        List<DyslexiaCategory> userCategories = new ArrayList<>();
        UserData latestData = userService.getLatestRecord(user);

        if (latestData != null) {
            Map<String, String> record = latestData.getRecord();
            List<DyslexiaCategory> categories = _categoryRepository.findAll();

            for (DyslexiaCategory category : categories) {
                double averageAccuracy = calculateAverageAccuracy(category.getStart(), category.getEnd(), record);

                if (averageAccuracy <= category.getAverage()) {
                    userCategories.add(category);
                }
            }

            dyslexiaTypeProducer.broadcastDyslexiaType(userCategories, "Categorizing User", user.getId());
        }

        return userCategories;
    }
    
    private double calculateAverageAccuracy(int start, int end, Map<String, String> record) {
        double accuracySum = 0;
        int count = end - start + 1;

        for (int i = start; i <= end; i++) {
            accuracySum += Double.parseDouble(record.get("Accuracy" + i));
        }

        return accuracySum / count;
    }
        public DyslexiaCategory saveCategory(DyslexiaCategory dyslexiaCategory) {
        return _categoryRepository.save(dyslexiaCategory);
    }
    
    public DyslexiaCategory getCategoryByName(String categoryName) {
        return _categoryRepository.findCategoryByName(categoryName);
    }
}
