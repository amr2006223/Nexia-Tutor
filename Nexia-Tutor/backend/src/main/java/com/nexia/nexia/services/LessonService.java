package com.nexia.nexia.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexia.nexia.models.Image;
import com.nexia.nexia.models.Keyword;
import com.nexia.nexia.models.Lesson;
import com.nexia.nexia.repositories.LessonRepository;
import com.nexia.nexia.services.iservices.ILessonService;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonService extends CrudOperations<Lesson, String, LessonRepository> implements ILessonService {

    private final String filePath = "Nexia-Tutor\\backend\\src\\main\\resources\\json\\lessons.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LessonService(LessonRepository repository) {
        super(repository);
    }

    @Override
    public Lesson addEntity(Lesson lesson) {
        List<Lesson> oldLessons = getLessons();
        if (oldLessons == null) {
            oldLessons = new ArrayList<>();
            oldLessons.add(lesson);
        } else {
            oldLessons.add(lesson);
        }
        writeLessonsToFile(oldLessons);
        return lesson;
    }

    @Override
    public boolean deleteEntity(String lessonName) {
        List<Lesson> lessons = getLessons();
        if (lessons != null) {
            lessons.removeIf(lesson -> lessonName.equals(lesson.getLessonName()));
            writeLessonsToFile(lessons);
            return true;
        }
        return false;
    }

    @Override
    public Lesson getEntityById(String lessonName) {
        return Optional.ofNullable(getLessons()).flatMap(lessons -> lessons.stream()
                .filter(lesson -> lessonName.equals(lesson.getLessonName()))
                .findFirst()).orElse(null);
    }

    @Override
    public Lesson updateEntity(Lesson updatedLesson) {
        String lessonName = updatedLesson.getLessonName();
        List<Lesson> lessons = getLessons();
        if (lessons == null)
            return null;
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            if (lessonName.equals(lesson.getLessonName())) {
                lessons.set(i, updatedLesson);
                writeLessonsToFile(lessons);
                return updatedLesson;
            }
        }
        System.out.println("Lesson with name " + lessonName + " not found.");
        return null;
    }

    @Override
    public void saveLessons(List<Lesson> lessons) {
        List<Lesson> oldLessons = getLessons();
        if (oldLessons == null) {
            oldLessons = lessons;
        } else {
            oldLessons.addAll(lessons);
        }
        writeLessonsToFile(oldLessons);
    }

    @Override
    public List<Image> getLessonImages(String lessonName, String keyword) {
        Lesson lesson = getEntityById(lessonName);
        if (lesson != null) {
            return lesson.getKeywords().stream()
                    .filter(key -> key.getKeyword_name().equals(keyword))
                    .findFirst()
                    .map(Keyword::getImages)
                    .orElse(null);
        }
        return null;
    }

    @Override
    public List<Lesson> getLessons() {
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<Lesson>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getLessonNames() {
        return Optional.ofNullable(getLessons()).map(lessons -> lessons.stream()
                .map(Lesson::getLessonName)
                .collect(Collectors.toList())).orElse(null);
    }

    private void writeLessonsToFile(List<Lesson> lessons) {
        try {
            objectMapper.writeValue(new File(filePath), lessons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// code before refactoring
// package com.nexia.nexia.services;

// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.nexia.nexia.models.Image;
// import com.nexia.nexia.models.Keyword;
// import com.nexia.nexia.models.LessonJson;

// import org.springframework.stereotype.Service;

// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class LessonJsonService {

// // @Autowired
// // private ImageService imageService;

// private final String filePath =
// "Nexia-Tutor\\backend\\src\\main\\resources\\json\\lessons.json"; // Specify
// the
// // file path

// public void saveLessons(List<LessonJson> lessons, boolean overwrite) {
// ObjectMapper objectMapper = new ObjectMapper();
// List<LessonJson> oldLessons = getLessons();
// if (oldLessons == null || overwrite) {
// oldLessons = lessons;
// } else {
// oldLessons.addAll(lessons);
// }
// try {
// // Write lessons to JSON file
// String allLessons = objectMapper.writeValueAsString(oldLessons);
// File file = new File(filePath);
// try (java.io.FileWriter fileWriter = new java.io.FileWriter(file)) {
// fileWriter.write(allLessons);
// }

// } catch (IOException e) {
// e.printStackTrace();

// }
// }

// public void saveLesson(LessonJson lesson, boolean overwrite) {
// ObjectMapper objectMapper = new ObjectMapper();
// List<LessonJson> oldLessons = getLessons();

// if (oldLessons == null || overwrite) {
// oldLessons = new ArrayList<>();
// oldLessons.add(lesson);
// } else {
// oldLessons.add(lesson);
// }

// try {
// // Write lessons to JSON file
// String allLessons = objectMapper.writeValueAsString(oldLessons);
// File file = new File(filePath);
// try (java.io.FileWriter fileWriter = new java.io.FileWriter(file)) {
// fileWriter.write(allLessons);
// }

// } catch (IOException e) {
// e.printStackTrace();
// }
// }

// // public void saveLessonWithImages(LessonJson lessonJson, List<String>
// // imageUrls) {
// // // Save lesson information
// // saveLesson(lessonJson, false);

// // // Save image URLs to Image table
// // for (String imageUrl : imageUrls) {
// // Image image = new Image();
// // image.setImage_url(imageUrl);
// // imageService.saveImage(image);
// // }
// // }

// public List<Image> getLessonImages(String lessonName, String keyword) {
// LessonJson lesson = getLessonByName(lessonName);
// List<Keyword> keywords = lesson.getKeywords();
// // List<String> imgs= new ArrayList<String>();
// for (Keyword key : keywords) {
// if (key.getKeyword_name().equals(keyword)) {
// return key.getImages();
// }
// }
// return null;

// }

// public List<LessonJson> getLessons() {
// ObjectMapper objectMapper = new ObjectMapper();

// try {
// // Read lessons from JSON file
// return objectMapper.readValue(new File(filePath), new
// TypeReference<List<LessonJson>>() {
// });

// } catch (IOException e) {
// e.printStackTrace();
// System.out
// .println("sfbnvbvufbvusbvusfbvusbfvufuvbsfuvuvfbuvbuvfbuvfbuvfubufvfvufvufvuvfbufvbfbufvbvfufbvu");
// // Handle exception as needed
// return null;
// }
// }

// public List<String> getLessonNames() {

// List<String> lessonNames;

// try {
// List<LessonJson> lessons = getLessons();

// // Extracting lesson names using Java streams
// lessonNames = lessons.stream()
// .map(LessonJson::getLessonName)
// .collect(Collectors.toList());

// return lessonNames;

// } catch (Error e) {
// e.printStackTrace(); // Handle exception properly, log, return error
// response, etc.
// return null;
// }
// }

// public LessonJson getLessonByName(String lessonName) {
// List<LessonJson> lessons = getLessons();
// if (lessons != null) {
// // Find the lesson by name and return its keywords
// return lessons.stream()
// .filter(lesson -> lessonName.equals(lesson.getLessonName()))
// .findFirst()
// .orElse(null);
// }
// return null;
// }

// public void deleteLesson(String lessonName) {
// List<LessonJson> lessons = getLessons();
// if (lessons != null) {
// Optional<LessonJson> lessonOptional = lessons.stream()
// .filter(lesson -> lessonName.equals(lesson.getLessonName()))
// .findFirst();

// lessonOptional.ifPresent(lesson -> {
// lessons.remove(lesson);
// saveLessons(lessons, true);
// });
// }
// }

// public void editLesson(String lessonName, LessonJson updatedLesson) {
// List<LessonJson> lessons = getLessons();
// if (lessons != null) {
// for (int i = 0; i < lessons.size(); i++) {
// LessonJson lesson = lessons.get(i);
// if (lessonName.equals(lesson.getLessonName())) {
// lessons.set(i, updatedLesson); // Update the lesson with the new information
// saveLessons(lessons, true); // Save the updated list to the JSON file
// return;
// }
// }
// }
// // Handle the case where the lesson with the given name is not found
// System.out.println("Lesson with name " + lessonName + " not found.");
// }
// }
