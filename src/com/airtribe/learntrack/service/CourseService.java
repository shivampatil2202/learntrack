package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;

/**
 * Service class for Course business logic.
 * Handles validation and coordinates with CourseRepository.
 */
public class CourseService {
    private CourseRepository courseRepository;
    
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    /**
     * Adds a new course to the system
     * @param courseName Course name
     * @param description Course description
     * @param durationInWeeks Duration in weeks
     * @return The created course
     * @throws InvalidInputException if validation fails
     */
    public Course addCourse(String courseName, String description, int durationInWeeks) 
            throws InvalidInputException {
        if (!InputValidator.isValidName(courseName)) {
            throw new InvalidInputException("Course name must be between 2-50 characters");
        }
        if (!InputValidator.isValidDuration(durationInWeeks)) {
            throw new InvalidInputException("Duration must be between 1 and 52 weeks");
        }
        
        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks);
        courseRepository.save(course);
        
        return course;
    }
    
    /**
     * Finds a course by ID
     * @param id The course ID
     * @return The course
     * @throws EntityNotFoundException if course not found
     */
    public Course findCourseById(int id) throws EntityNotFoundException {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course with ID " + id + " not found");
        }
        return course;
    }
    
    /**
     * Gets all courses in the system
     * @return List of all courses
     */
    public ArrayList<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    /**
     * Gets all active courses
     * @return List of active courses
     */
    public ArrayList<Course> getActiveCourses() {
        return courseRepository.findAllActive();
    }
    
    /**
     * Searches for courses by name
     * @param name Course name to search for
     * @return List of matching courses
     */
    public ArrayList<Course> searchCoursesByName(String name) {
        return courseRepository.findByName(name);
    }
    
    /**
     * Finds courses by duration
     * @param durationInWeeks The duration to search for
     * @return List of courses with matching duration
     */
    public ArrayList<Course> getCoursesByDuration(int durationInWeeks) {
        return courseRepository.findByDuration(durationInWeeks);
    }
    
    /**
     * Toggles the active status of a course
     * @param id The course ID
     * @throws EntityNotFoundException if course not found
     */
    public void toggleCourseStatus(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(!course.isActive());
        courseRepository.update(course);
    }
    
    /**
     * Deactivates a course
     * @param id The course ID
     * @throws EntityNotFoundException if course not found
     */
    public void deactivateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(false);
        courseRepository.update(course);
    }
    
    /**
     * Activates a course
     * @param id The course ID
     * @throws EntityNotFoundException if course not found
     */
    public void activateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(true);
        courseRepository.update(course);
    }
    
    /**
     * Updates course information
     * @param id Course ID
     * @param courseName New course name (null to keep existing)
     * @param description New description (null to keep existing)
     * @param durationInWeeks New duration (0 to keep existing)
     * @throws EntityNotFoundException if course not found
     * @throws InvalidInputException if validation fails
     */
    public void updateCourse(int id, String courseName, String description, int durationInWeeks) 
            throws EntityNotFoundException, InvalidInputException {
        Course course = findCourseById(id);
        
        if (courseName != null && !courseName.isEmpty()) {
            if (!InputValidator.isValidName(courseName)) {
                throw new InvalidInputException("Course name must be between 2-50 characters");
            }
            course.setCourseName(courseName);
        }
        
        if (description != null && !description.isEmpty()) {
            course.setDescription(description);
        }
        
        if (durationInWeeks > 0) {
            if (!InputValidator.isValidDuration(durationInWeeks)) {
                throw new InvalidInputException("Duration must be between 1 and 52 weeks");
            }
            course.setDurationInWeeks(durationInWeeks);
        }
        
        courseRepository.update(course);
    }
    
    /**
     * Permanently deletes a course from the system
     * @param id The course ID
     * @return true if deleted, false if not found
     */
    public boolean deleteCourse(int id) {
        return courseRepository.delete(id);
    }
    
    /**
     * Gets the total count of courses
     * @return Total course count
     */
    public int getTotalCourseCount() {
        return courseRepository.count();
    }
    
    /**
     * Gets the count of active courses
     * @return Active course count
     */
    public int getActiveCourseCount() {
        return courseRepository.countActive();
    }
}