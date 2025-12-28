package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import java.util.ArrayList;

/**
 * Service class for Enrollment business logic.
 * Handles validation and coordinates with multiple repositories.
 */
public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;
    private StudentService studentService;
    private CourseService courseService;
    
    public EnrollmentService(EnrollmentRepository enrollmentRepository, 
                            StudentService studentService, 
                            CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }
    
    /**
     * Enrolls a student in a course
     * @param studentId The student ID
     * @param courseId The course ID
     * @return The created enrollment
     * @throws EntityNotFoundException if student or course not found
     * @throws InvalidInputException if enrollment rules violated
     */
    public Enrollment enrollStudent(int studentId, int courseId) 
            throws EntityNotFoundException, InvalidInputException {
        // Verify student exists and is active
        Student student = studentService.findStudentById(studentId);
        if (!student.isActive()) {
            throw new InvalidInputException("Cannot enroll inactive student");
        }
        
        // Verify course exists and is active
        Course course = courseService.findCourseById(courseId);
        if (!course.isActive()) {
            throw new InvalidInputException("Cannot enroll in inactive course");
        }
        
        // Check for duplicate enrollment
        if (enrollmentRepository.isActivelyEnrolled(studentId, courseId)) {
            throw new InvalidInputException("Student is already enrolled in this course");
        }
        
        // Create enrollment
        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId);
        enrollmentRepository.save(enrollment);
        
        return enrollment;
    }
    
    /**
     * Gets all enrollments for a specific student
     * @param studentId The student ID
     * @return List of enrollments
     * @throws EntityNotFoundException if student not found
     */
    public ArrayList<Enrollment> getEnrollmentsByStudent(int studentId) 
            throws EntityNotFoundException {
        // Verify student exists
        studentService.findStudentById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }
    
    /**
     * Gets all active enrollments for a student
     * @param studentId The student ID
     * @return List of active enrollments
     * @throws EntityNotFoundException if student not found
     */
    public ArrayList<Enrollment> getActiveEnrollmentsByStudent(int studentId) 
            throws EntityNotFoundException {
        // Verify student exists
        studentService.findStudentById(studentId);
        return enrollmentRepository.findActiveByStudentId(studentId);
    }
    
    /**
     * Gets all enrollments for a specific course
     * @param courseId The course ID
     * @return List of enrollments
     * @throws EntityNotFoundException if course not found
     */
    public ArrayList<Enrollment> getEnrollmentsByCourse(int courseId) 
            throws EntityNotFoundException {
        // Verify course exists
        courseService.findCourseById(courseId);
        return enrollmentRepository.findByCourseId(courseId);
    }
    
    /**
     * Gets all enrollments in the system
     * @return List of all enrollments
     */
    public ArrayList<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    
    /**
     * Gets enrollments by status
     * @param status The status to filter by
     * @return List of enrollments with the given status
     */
    public ArrayList<Enrollment> getEnrollmentsByStatus(String status) {
        return enrollmentRepository.findByStatus(status);
    }
    
    /**
     * Updates the status of an enrollment
     * @param enrollmentId The enrollment ID
     * @param status The new status (ACTIVE, COMPLETED, CANCELLED, DROPPED)
     * @throws EntityNotFoundException if enrollment not found
     * @throws InvalidInputException if status is invalid
     */
    public void updateEnrollmentStatus(int enrollmentId, String status) 
            throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment with ID " + enrollmentId + " not found");
        }
        
        // Validate status
        if (!isValidStatus(status)) {
            throw new InvalidInputException(
                "Invalid status. Valid options: ACTIVE, COMPLETED, CANCELLED, DROPPED");
        }
        
        enrollment.setStatus(status);
        enrollmentRepository.update(enrollment);
    }
    
    /**
     * Marks an enrollment as completed
     * @param enrollmentId The enrollment ID
     * @throws EntityNotFoundException if enrollment not found
     * @throws InvalidInputException if validation fails
     */
    public void completeEnrollment(int enrollmentId) 
            throws EntityNotFoundException, InvalidInputException {
        updateEnrollmentStatus(enrollmentId, "COMPLETED");
    }
    
    /**
     * Cancels an enrollment
     * @param enrollmentId The enrollment ID
     * @throws EntityNotFoundException if enrollment not found
     * @throws InvalidInputException if validation fails
     */
    public void cancelEnrollment(int enrollmentId) 
            throws EntityNotFoundException, InvalidInputException {
        updateEnrollmentStatus(enrollmentId, "CANCELLED");
    }
    
    /**
     * Marks an enrollment as dropped
     * @param enrollmentId The enrollment ID
     * @throws EntityNotFoundException if enrollment not found
     * @throws InvalidInputException if validation fails
     */
    public void dropEnrollment(int enrollmentId) 
            throws EntityNotFoundException, InvalidInputException {
        updateEnrollmentStatus(enrollmentId, "DROPPED");
    }
    
    /**
     * Deletes an enrollment permanently
     * @param enrollmentId The enrollment ID
     * @return true if deleted, false if not found
     */
    public boolean deleteEnrollment(int enrollmentId) {
        return enrollmentRepository.delete(enrollmentId);
    }
    
    /**
     * Gets the total count of enrollments
     * @return Total enrollment count
     */
    public int getTotalEnrollmentCount() {
        return enrollmentRepository.count();
    }
    
    /**
     * Gets the count of active enrollments
     * @return Active enrollment count
     */
    public int getActiveEnrollmentCount() {
        return enrollmentRepository.countActive();
    }
    
    /**
     * Gets the count of completed enrollments
     * @return Completed enrollment count
     */
    public int getCompletedEnrollmentCount() {
        return enrollmentRepository.countCompleted();
    }
    
    /**
     * Gets enrollment statistics for a student
     * @param studentId The student ID
     * @return String with statistics
     * @throws EntityNotFoundException if student not found
     */
    public String getStudentEnrollmentStats(int studentId) 
            throws EntityNotFoundException {
        Student student = studentService.findStudentById(studentId);
        ArrayList<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        
        int active = 0;
        int completed = 0;
        int cancelled = 0;
        
        for (Enrollment e : enrollments) {
            switch (e.getStatus()) {
                case "ACTIVE":
                    active++;
                    break;
                case "COMPLETED":
                    completed++;
                    break;
                case "CANCELLED":
                    cancelled++;
                    break;
            }
        }
        
        return String.format("Student: %s\nTotal Enrollments: %d\nActive: %d\nCompleted: %d\nCancelled: %d",
                           student.getDisplayName(), enrollments.size(), active, completed, cancelled);
    }
    
    /**
     * Gets enrollment statistics for a course
     * @param courseId The course ID
     * @return String with statistics
     * @throws EntityNotFoundException if course not found
     */
    public String getCourseEnrollmentStats(int courseId) 
            throws EntityNotFoundException {
        Course course = courseService.findCourseById(courseId);
        ArrayList<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        
        int active = 0;
        int completed = 0;
        
        for (Enrollment e : enrollments) {
            if ("ACTIVE".equals(e.getStatus())) {
                active++;
            } else if ("COMPLETED".equals(e.getStatus())) {
                completed++;
            }
        }
        
        return String.format("Course: %s\nTotal Enrollments: %d\nActive: %d\nCompleted: %d",
                           course.getCourseName(), enrollments.size(), active, completed);
    }
    
    /**
     * Validates if a status string is valid
     * @param status The status to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidStatus(String status) {
        return "ACTIVE".equals(status) || 
               "COMPLETED".equals(status) || 
               "CANCELLED".equals(status) ||
               "DROPPED".equals(status);
    }
}