package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;

/**
 * Service class for Student business logic.
 * Handles validation and coordinates with StudentRepository.
 */
public class StudentService {
    private StudentRepository studentRepository;
    
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    /**
     * Adds a new student to the system
     * @param firstName Student's first name
     * @param lastName Student's last name
     * @param email Student's email
     * @param batch Student's batch
     * @return The created student
     * @throws InvalidInputException if validation fails
     */
    public Student addStudent(String firstName, String lastName, String email, String batch) 
            throws InvalidInputException {
        // Validation
        if (!InputValidator.isValidName(firstName)) {
            throw new InvalidInputException("First name must be between 2-50 characters");
        }
        if (!InputValidator.isValidName(lastName)) {
            throw new InvalidInputException("Last name must be between 2-50 characters");
        }
        if (email != null && !email.isEmpty() && !InputValidator.isValidEmail(email)) {
            throw new InvalidInputException("Invalid email format");
        }
        
        // Create student with auto-generated ID
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        studentRepository.save(student);
        
        return student;
    }
    
    /**
     * Finds a student by ID
     * @param id The student ID
     * @return The student
     * @throws EntityNotFoundException if student not found
     */
    public Student findStudentById(int id) throws EntityNotFoundException {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("Student with ID " + id + " not found");
        }
        return student;
    }
    
    /**
     * Gets all students in the system
     * @return List of all students
     */
    public ArrayList<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    /**
     * Gets all active students
     * @return List of active students
     */
    public ArrayList<Student> getActiveStudents() {
        return studentRepository.findAllActive();
    }
    
    /**
     * Finds students by batch
     * @param batch The batch name
     * @return List of students in the batch
     */
    public ArrayList<Student> getStudentsByBatch(String batch) {
        return studentRepository.findByBatch(batch);
    }
    
    /**
     * Deactivates a student (soft delete)
     * @param id The student ID
     * @throws EntityNotFoundException if student not found
     */
    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(false);
        studentRepository.update(student);
    }
    
    /**
     * Activates a previously deactivated student
     * @param id The student ID
     * @throws EntityNotFoundException if student not found
     */
    public void activateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(true);
        studentRepository.update(student);
    }
    
    /**
     * Updates student information
     * @param id Student ID
     * @param firstName New first name (null to keep existing)
     * @param lastName New last name (null to keep existing)
     * @param email New email (null to keep existing)
     * @param batch New batch (null to keep existing)
     * @throws EntityNotFoundException if student not found
     * @throws InvalidInputException if validation fails
     */
    public void updateStudent(int id, String firstName, String lastName, String email, String batch) 
            throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(id);
        
        if (firstName != null && !firstName.isEmpty()) {
            if (!InputValidator.isValidName(firstName)) {
                throw new InvalidInputException("First name must be between 2-50 characters");
            }
            student.setFirstName(firstName);
        }
        
        if (lastName != null && !lastName.isEmpty()) {
            if (!InputValidator.isValidName(lastName)) {
                throw new InvalidInputException("Last name must be between 2-50 characters");
            }
            student.setLastName(lastName);
        }
        
        if (email != null && !email.isEmpty()) {
            if (!InputValidator.isValidEmail(email)) {
                throw new InvalidInputException("Invalid email format");
            }
            student.setEmail(email);
        }
        
        if (batch != null && !batch.isEmpty()) {
            student.setBatch(batch);
        }
        
        studentRepository.update(student);
    }
    
    /**
     * Permanently deletes a student from the system
     * @param id The student ID
     * @return true if deleted, false if not found
     */
    public boolean deleteStudent(int id) {
        return studentRepository.delete(id);
    }
    
    /**
     * Gets the total count of students
     * @return Total student count
     */
    public int getTotalStudentCount() {
        return studentRepository.count();
    }
    
    /**
     * Gets the count of active students
     * @return Active student count
     */
    public int getActiveStudentCount() {
        return studentRepository.countActive();
    }
}

