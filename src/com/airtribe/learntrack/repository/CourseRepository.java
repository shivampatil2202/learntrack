package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.ArrayList;

/**
 * Repository for managing Course data storage and retrieval.
 * Uses ArrayList for in-memory storage.
 */
public class CourseRepository {
    private ArrayList<Course> courses;
    
    public CourseRepository() {
        this.courses = new ArrayList<>();
    }
    
    /**
     * Saves a new course to the repository
     * @param course The course to save
     */
    public void save(Course course) {
        courses.add(course);
    }
    
    /**
     * Finds a course by its ID
     * @param id The course ID to search for
     * @return The course if found, null otherwise
     */
    public Course findById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }
    
    /**
     * Returns all courses in the repository
     * @return A copy of the courses list
     */
    public ArrayList<Course> findAll() {
        return new ArrayList<>(courses);
    }
    
    /**
     * Finds all active courses
     * @return List of active courses
     */
    public ArrayList<Course> findAllActive() {
        ArrayList<Course> activeCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.isActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }
    
    /**
     * Finds courses by name (case-insensitive partial match)
     * @param name The course name to search for
     * @return List of matching courses
     */
    public ArrayList<Course> findByName(String name) {
        ArrayList<Course> result = new ArrayList<>();
        String searchTerm = name.toLowerCase();
        
        for (Course course : courses) {
            if (course.getCourseName().toLowerCase().contains(searchTerm)) {
                result.add(course);
            }
        }
        return result;
    }
    
    /**
     * Finds courses by duration
     * @param durationInWeeks The duration to search for
     * @return List of courses with matching duration
     */
    public ArrayList<Course> findByDuration(int durationInWeeks) {
        ArrayList<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getDurationInWeeks() == durationInWeeks) {
                result.add(course);
            }
        }
        return result;
    }
    
    /**
     * Deletes a course by ID
     * @param id The ID of the course to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(int id) {
        Course course = findById(id);
        if (course != null) {
            courses.remove(course);
            return true;
        }
        return false;
    }
    
    /**
     * Updates an existing course
     * @param course The course with updated information
     */
    public void update(Course course) {
        Course existing = findById(course.getId());
        if (existing != null) {
            int index = courses.indexOf(existing);
            courses.set(index, course);
        }
    }
    
    /**
     * Returns the total number of courses
     * @return Count of courses
     */
    public int count() {
        return courses.size();
    }
    
    /**
     * Returns the count of active courses
     * @return Count of active courses
     */
    public int countActive() {
        int count = 0;
        for (Course course : courses) {
            if (course.isActive()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Checks if a course with the given ID exists
     * @param id The course ID
     * @return true if exists, false otherwise
     */
    public boolean exists(int id) {
        return findById(id) != null;
    }
}

