package com.jeevankumar.service;

import com.jeevankumar.dto.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final List<Course> courses = new ArrayList<>();
    private int nextId = 1;

    public synchronized List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public synchronized Course getCourseById(int id) {
        return courses.stream()
                .filter(course -> course.getId() != null && course.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    public synchronized Course createCourse(Course course) {
        course.setId(nextId++);
        courses.add(course);
        return course;
    }

    public synchronized Course updateCourse(int id, Course updatedCourse) {
        int index = findCourseIndex(id);
        updatedCourse.setId(id);
        courses.set(index, updatedCourse);
        return updatedCourse;
    }

    public synchronized void deleteCourse(int id) {
        courses.remove(findCourseIndex(id));
    }

    private int findCourseIndex(int id) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() != null && courses.get(i).getId() == id) {
                return i;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
    }
}
