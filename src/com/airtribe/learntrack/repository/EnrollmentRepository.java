package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import java.util.ArrayList;

/**
 * Repository for managing Enrollment data storage and retrieval.
 * Uses ArrayList for in-memory storage.
 */
public class EnrollmentRepository {
    private ArrayList<Enrollment> enrollments;
    
    public EnrollmentRepository() {
        this.enrollments = new ArrayList<>();
    }
    
    /**
     * Saves a new enrollment to the repository
     * @param enrollment The enrollment to save
     */
    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }
    
    /**
     * Finds an enrollment by its ID
     * @param id The enrollment ID to search for
     * @return The enrollment if found, null otherwise
     */
    public Enrollment findById(int id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        return null;
    }
    
    /**
     * Returns all enrollments in the repository
     * @return A copy of the enrollments list
     */
    public ArrayList<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }
    
    /**
     * Finds all enrollments for a specific student
     * @param studentId The student ID
     * @return List of enrollments for the student
     */
    public ArrayList<Enrollment> findByStudentId(int studentId) {
        ArrayList<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }
    
    /**
     * Finds all enrollments for a specific course
     * @param courseId The course ID
     * @return List of enrollments for the course
     */
    public ArrayList<Enrollment> findByCourseId(int courseId) {
        ArrayList<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == courseId) {
                result.add(enrollment);
            }
        }
        return result;
    }
    
    /**
     * Finds all active enrollments for a student
     * @param studentId The student ID
     * @return List of active enrollments
     */
    public ArrayList<Enrollment> findActiveByStudentId(int studentId) {
        ArrayList<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId && 
                "ACTIVE".equals(enrollment.getStatus())) {
                result.add(enrollment);
            }
        }
        return result;
    }
    
    /**
     * Finds enrollment by student and course IDs
     * @param studentId The student ID
     * @param courseId The course ID
     * @return The enrollment if found, null otherwise
     */
    public Enrollment findByStudentAndCourse(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId && 
                enrollment.getCourseId() == courseId) {
                return enrollment;
            }
        }
        return null;
    }
    
    /**
     * Finds all enrollments with a specific status
     * @param status The status to filter by (ACTIVE, COMPLETED, CANCELLED)
     * @return List of enrollments with the given status
     */
    public ArrayList<Enrollment> findByStatus(String status) {
        ArrayList<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (status.equals(enrollment.getStatus())) {
                result.add(enrollment);
            }
        }
        return result;
    }
    
    /**
     * Deletes an enrollment by ID
     * @param id The ID of the enrollment to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(int id) {
        Enrollment enrollment = findById(id);
        if (enrollment != null) {
            enrollments.remove(enrollment);
            return true;
        }
        return false;
    }
    
    /**
     * Updates an existing enrollment
     * @param enrollment The enrollment with updated information
     */
    public void update(Enrollment enrollment) {
        Enrollment existing = findById(enrollment.getId());
        if (existing != null) {
            int index = enrollments.indexOf(existing);
            enrollments.set(index, enrollment);
        }
    }
    
    /**
     * Returns the total number of enrollments
     * @return Count of enrollments
     */
    public int count() {
        return enrollments.size();
    }
    
    /**
     * Returns the count of active enrollments
     * @return Count of active enrollments
     */
    public int countActive() {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if ("ACTIVE".equals(enrollment.getStatus())) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Returns the count of completed enrollments
     * @return Count of completed enrollments
     */
    public int countCompleted() {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if ("COMPLETED".equals(enrollment.getStatus())) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Checks if a student is already enrolled in a course with active status
     * @param studentId The student ID
     * @param courseId The course ID
     * @return true if actively enrolled, false otherwise
     */
    public boolean isActivelyEnrolled(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId && 
                enrollment.getCourseId() == courseId &&
                "ACTIVE".equals(enrollment.getStatus())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if an enrollment with the given ID exists
     * @param id The enrollment ID
     * @return true if exists, false otherwise
     */
    public boolean exists(int id) {
        return findById(id) != null;
    }
}
