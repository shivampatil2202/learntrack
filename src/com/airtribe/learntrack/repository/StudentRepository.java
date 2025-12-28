package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.ArrayList;

/**
 * Repository for managing Student data storage and retrieval.
 * Uses ArrayList for in-memory storage.
 */
public class StudentRepository {
    private ArrayList<Student> students;
    
    public StudentRepository() {
        this.students = new ArrayList<>();
    }
    
    /**
     * Saves a new student to the repository
     * @param student The student to save
     */
    public void save(Student student) {
        students.add(student);
    }
    
    /**
     * Finds a student by their ID
     * @param id The student ID to search for
     * @return The student if found, null otherwise
     */
    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
    
    /**
     * Returns all students in the repository
     * @return A copy of the students list
     */
    public ArrayList<Student> findAll() {
        return new ArrayList<>(students);
    }
    
    /**
     * Finds all active students
     * @return List of active students
     */
    public ArrayList<Student> findAllActive() {
        ArrayList<Student> activeStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }
    
    /**
     * Finds students by batch
     * @param batch The batch name to search for
     * @return List of students in the batch
     */
    public ArrayList<Student> findByBatch(String batch) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (batch.equals(student.getBatch())) {
                result.add(student);
            }
        }
        return result;
    }
    
    /**
     * Deletes a student by ID
     * @param id The ID of the student to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(int id) {
        Student student = findById(id);
        if (student != null) {
            students.remove(student);
            return true;
        }
        return false;
    }
    
    /**
     * Updates an existing student
     * @param student The student with updated information
     */
    public void update(Student student) {
        Student existing = findById(student.getId());
        if (existing != null) {
            int index = students.indexOf(existing);
            students.set(index, student);
        }
    }
    
    /**
     * Returns the total number of students
     * @return Count of students
     */
    public int count() {
        return students.size();
    }
    
    /**
     * Returns the count of active students
     * @return Count of active students
     */
    public int countActive() {
        int count = 0;
        for (Student student : students) {
            if (student.isActive()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Checks if a student with the given ID exists
     * @param id The student ID
     * @return true if exists, false otherwise
     */
    public boolean exists(int id) {
        return findById(id) != null;
    }
}
