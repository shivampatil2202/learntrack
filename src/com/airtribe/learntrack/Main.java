// ============================================
// File: Main.java
// Complete Console Application with Menu System
// ============================================
package com.airtribe.learntrack;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main class for LearnTrack Student & Course Management System.
 * Provides a console-based menu-driven interface.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    
    public static void main(String[] args) {
        // Display welcome message
        displayWelcomeMessage();
        
        // Initialize repositories
        StudentRepository studentRepo = new StudentRepository();
        CourseRepository courseRepo = new CourseRepository();
        EnrollmentRepository enrollmentRepo = new EnrollmentRepository();
        
        // Initialize services
        studentService = new StudentService(studentRepo);
        courseService = new CourseService(courseRepo);
        enrollmentService = new EnrollmentService(enrollmentRepo, studentService, courseService);
        
        // Load sample data
        loadSampleData();
        
        // Run main menu
        showMainMenu();
        
        // Cleanup
        scanner.close();
        System.out.println("\nThank you for using LearnTrack!");
    }
    
    /**
     * Displays welcome message with app info
     */
    private static void displayWelcomeMessage() {
        System.out.println("\n========================================");
        System.out.println("  Welcome to LearnTrack v1.0.0");
        System.out.println("  Student & Course Management System");
        System.out.println("========================================\n");
    }
    
    /**
     * Loads sample data for testing
     */
    private static void loadSampleData() {
        try {
            // Add sample students
            studentService.addStudent("John", "Doe", "john.doe@example.com", "Batch-2024-A");
            studentService.addStudent("Jane", "Smith", "jane.smith@example.com", "Batch-2024-A");
            studentService.addStudent("Robert", "Johnson", "robert.j@example.com", "Batch-2024-B");
            
            // Add sample courses
            courseService.addCourse("Java Fundamentals", "Core Java Programming Concepts", 8);
            courseService.addCourse("Spring Boot", "Backend Development with Spring", 12);
            courseService.addCourse("React Basics", "Frontend Development with React", 10);
            
            System.out.println("Sample data loaded successfully!\n");
        } catch (InvalidInputException e) {
            System.out.println("Error loading sample data: " + e.getMessage());
        }
    }
    
    /**
     * Displays and handles main menu
     */
    private static void showMainMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n========================================");
            System.out.println("         MAIN MENU");
            System.out.println("========================================");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Enrollment Management");
            System.out.println("4. View Statistics");
            System.out.println("5. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        showStudentMenu();
                        break;
                    case 2:
                        showCourseMenu();
                        break;
                    case 3:
                        showEnrollmentMenu();
                        break;
                    case 4:
                        showStatisticsMenu();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.");
            }
        }
    }
    
    // ============================================
    // STUDENT MANAGEMENT MENU
    // ============================================
    
    private static void showStudentMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n========================================");
            System.out.println("      STUDENT MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Active Students");
            System.out.println("4. Search Student by ID");
            System.out.println("5. Update Student");
            System.out.println("6. Deactivate Student");
            System.out.println("7. Activate Student");
            System.out.println("8. Delete Student");
            System.out.println("9. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        viewActiveStudents();
                        break;
                    case 4:
                        searchStudent();
                        break;
                    case 5:
                        updateStudent();
                        break;
                    case 6:
                        deactivateStudent();
                        break;
                    case 7:
                        activateStudent();
                        break;
                    case 8:
                        deleteStudent();
                        break;
                    case 9:
                        back = true;
                        break;
                    default:
                        System.out.println("\nInvalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.");
            }
        }
    }
    
    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Batch: ");
        String batch = scanner.nextLine().trim();
        
        try {
            Student student = studentService.addStudent(firstName, lastName, email, batch);
            System.out.println("\nStudent added successfully!");
            System.out.println("Student ID: " + student.getId());
            System.out.println(student);
        } catch (InvalidInputException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void viewAllStudents() {
        ArrayList<Student> students = studentService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("\nNo students found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         ALL STUDENTS");
        System.out.println("========================================");
        
        for (Student student : students) {
            System.out.println(student);
        }
        
        System.out.println("========================================");
        System.out.println("Total Students: " + students.size());
    }
    
    private static void viewActiveStudents() {
        ArrayList<Student> students = studentService.getActiveStudents();
        
        if (students.isEmpty()) {
            System.out.println("\nNo active students found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("       ACTIVE STUDENTS");
        System.out.println("========================================");
        
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("========================================");
        System.out.println("Total Active Students: " + students.size());
    }
    
    private static void searchStudent() {
        System.out.print("\nEnter Student ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Student student = studentService.findStudentById(id);
            
            System.out.println("\n========================================");
            System.out.println("Student Found:");
            System.out.println("========================================");
            System.out.println(student);
            System.out.println("========================================");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void updateStudent() {
        System.out.print("\nEnter Student ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Student student = studentService.findStudentById(id);
            
            System.out.println("\nCurrent details: " + student);
            System.out.println("\nLeave blank to keep current value.");
            
            System.out.print("New First Name: ");
            String firstName = scanner.nextLine().trim();
            
            System.out.print("New Last Name: ");
            String lastName = scanner.nextLine().trim();
            
            System.out.print("New Email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("New Batch: ");
            String batch = scanner.nextLine().trim();
            
            studentService.updateStudent(id, 
                                        firstName.isEmpty() ? null : firstName,
                                        lastName.isEmpty() ? null : lastName,
                                        email.isEmpty() ? null : email,
                                        batch.isEmpty() ? null : batch);
            
            System.out.println("\nStudent updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void deactivateStudent() {
        System.out.print("\nEnter Student ID to deactivate: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            studentService.deactivateStudent(id);
            System.out.println("\nStudent deactivated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void activateStudent() {
        System.out.print("\nEnter Student ID to activate: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            studentService.activateStudent(id);
            System.out.println("\nStudent activated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void deleteStudent() {
        System.out.print("\nEnter Student ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Are you sure? This action cannot be undone (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("yes")) {
                boolean deleted = studentService.deleteStudent(id);
                if (deleted) {
                    System.out.println("\nStudent deleted successfully!");
                } else {
                    System.out.println("\nStudent not found.");
                }
            } else {
                System.out.println("\nDeletion cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        }
    }
    
    // ============================================
    // COURSE MANAGEMENT MENU
    // ============================================
    
    private static void showCourseMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n========================================");
            System.out.println("       COURSE MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. View Active Courses");
            System.out.println("4. Search Course by ID");
            System.out.println("5. Update Course");
            System.out.println("6. Toggle Course Status");
            System.out.println("7. Delete Course");
            System.out.println("8. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        addCourse();
                        break;
                    case 2:
                        viewAllCourses();
                        break;
                    case 3:
                        viewActiveCourses();
                        break;
                    case 4:
                        searchCourse();
                        break;
                    case 5:
                        updateCourse();
                        break;
                    case 6:
                        toggleCourseStatus();
                        break;
                    case 7:
                        deleteCourse();
                        break;
                    case 8:
                        back = true;
                        break;
                    default:
                        System.out.println("\nInvalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.");
            }
        }
    }
    
    private static void addCourse() {
        System.out.println("\n--- Add New Course ---");
        
        System.out.print("Course Name: ");
        String courseName = scanner.nextLine().trim();
        
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        
        System.out.print("Duration (in weeks): ");
        try {
            int duration = Integer.parseInt(scanner.nextLine().trim());
            
            Course course = courseService.addCourse(courseName, description, duration);
            System.out.println("\nCourse added successfully!");
            System.out.println("Course ID: " + course.getId());
            System.out.println(course);
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (InvalidInputException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void viewAllCourses() {
        ArrayList<Course> courses = courseService.getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("\nNo courses found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("          ALL COURSES");
        System.out.println("========================================");
        
        for (Course course : courses) {
            System.out.println(course);
        }
        
        System.out.println("========================================");
        System.out.println("Total Courses: " + courses.size());
    }
    
    private static void viewActiveCourses() {
        ArrayList<Course> courses = courseService.getActiveCourses();
        
        if (courses.isEmpty()) {
            System.out.println("\nNo active courses found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("        ACTIVE COURSES");
        System.out.println("========================================");
        
        for (Course course : courses) {
            System.out.println(course);
        }
        System.out.println("========================================");
        System.out.println("Total Active Courses: " + courses.size());
    }
    
    private static void searchCourse() {
        System.out.print("\nEnter Course ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Course course = courseService.findCourseById(id);
            
            System.out.println("\n========================================");
            System.out.println("Course Found:");
            System.out.println("========================================");
            System.out.println(course);
            System.out.println("========================================");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void updateCourse() {
        System.out.print("\nEnter Course ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Course course = courseService.findCourseById(id);
            
            System.out.println("\nCurrent details: " + course);
            System.out.println("\nLeave blank to keep current value.");
            
            System.out.print("New Course Name: ");
            String courseName = scanner.nextLine().trim();
            
            System.out.print("New Description: ");
            String description = scanner.nextLine().trim();
            
            System.out.print("New Duration (in weeks, 0 to keep current): ");
            String durationStr = scanner.nextLine().trim();
            int duration = durationStr.isEmpty() ? 0 : Integer.parseInt(durationStr);
            
            courseService.updateCourse(id, 
                                      courseName.isEmpty() ? null : courseName,
                                      description.isEmpty() ? null : description,
                                      duration);
            
            System.out.println("\nCourse updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void toggleCourseStatus() {
        System.out.print("\nEnter Course ID to toggle status: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            courseService.toggleCourseStatus(id);
            System.out.println("\nCourse status toggled successfully!");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void deleteCourse() {
        System.out.print("\nEnter Course ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Are you sure? This action cannot be undone (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("yes")) {
                boolean deleted = courseService.deleteCourse(id);
                if (deleted) {
                    System.out.println("\nCourse deleted successfully!");
                } else {
                    System.out.println("\nCourse not found.");
                }
            } else {
                System.out.println("\nDeletion cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        }
    }
    
    // ============================================
    // ENROLLMENT MANAGEMENT MENU
    // ============================================
    
    private static void showEnrollmentMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n========================================");
            System.out.println("     ENROLLMENT MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. View All Enrollments");
            System.out.println("3. View Student Enrollments");
            System.out.println("4. View Course Enrollments");
            System.out.println("5. Update Enrollment Status");
            System.out.println("6. Complete Enrollment");
            System.out.println("7. Cancel Enrollment");
            System.out.println("8. Delete Enrollment");
            System.out.println("9. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        enrollStudent();
                        break;
                    case 2:
                        viewAllEnrollments();
                        break;
                    case 3:
                        viewStudentEnrollments();
                        break;
                    case 4:
                        viewCourseEnrollments();
                        break;
                    case 5:
                        updateEnrollmentStatus();
                        break;
                    case 6:
                        completeEnrollment();
                        break;
                    case 7:
                        cancelEnrollment();
                        break;
                    case 8:
                        deleteEnrollment();
                        break;
                    case 9:
                        back = true;
                        break;
                    default:
                        System.out.println("\nInvalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.");
            }
        }
    }
    
    private static void enrollStudent() {
        System.out.println("\n--- Enroll Student in Course ---");
        
        System.out.print("Student ID: ");
        try {
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Course ID: ");
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            
            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
            System.out.println("\nEnrollment successful!");
            System.out.println("Enrollment ID: " + enrollment.getId());
            System.out.println(enrollment);
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void viewAllEnrollments() {
        ArrayList<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        
        if (enrollments.isEmpty()) {
            System.out.println("\nNo enrollments found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("        ALL ENROLLMENTS");
        System.out.println("========================================");
        
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
        
        System.out.println("========================================");
        System.out.println("Total Enrollments: " + enrollments.size());
    }
    
    private static void viewStudentEnrollments() {
        System.out.print("\nEnter Student ID: ");
        try {
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            ArrayList<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
            
            if (enrollments.isEmpty()) {
                System.out.println("\nNo enrollments found for this student.");
                return;
            }
            
            System.out.println("\n========================================");
            System.out.println("     STUDENT ENROLLMENTS");
            System.out.println("========================================");
            
            for (Enrollment enrollment : enrollments) {
                System.out.println(enrollment);
            }
            System.out.println("========================================");
            System.out.println("Total Enrollments: " + enrollments.size());
            
            // Show statistics
            System.out.println("\n" + enrollmentService.getStudentEnrollmentStats(studentId));
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void viewCourseEnrollments() {
        System.out.print("\nEnter Course ID: ");
        try {
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            ArrayList<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
            
            if (enrollments.isEmpty()) {
                System.out.println("\nNo enrollments found for this course.");
                return;
            }
            
            System.out.println("\n========================================");
            System.out.println("      COURSE ENROLLMENTS");
            System.out.println("========================================");
            
            for (Enrollment enrollment : enrollments) {
                System.out.println(enrollment);
            }
            System.out.println("========================================");
            System.out.println("Total Enrollments: " + enrollments.size());
            
            // Show statistics
            System.out.println("\n" + enrollmentService.getCourseEnrollmentStats(courseId));
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void updateEnrollmentStatus() {
        System.out.print("\nEnter Enrollment ID: ");
        try {
            int enrollmentId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.println("\nAvailable statuses:");
            System.out.println("1. ACTIVE");
            System.out.println("2. COMPLETED");
            System.out.println("3. CANCELLED");
            System.out.println("4. DROPPED");
            System.out.print("Enter new status: ");
            String status = scanner.nextLine().trim().toUpperCase();
            
            enrollmentService.updateEnrollmentStatus(enrollmentId, status);
            System.out.println("\nEnrollment status updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void completeEnrollment() {
        System.out.print("\nEnter Enrollment ID to mark as completed: ");
        try {
            int enrollmentId = Integer.parseInt(scanner.nextLine().trim());
            enrollmentService.completeEnrollment(enrollmentId);
            System.out.println("\nEnrollment marked as completed!");
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void cancelEnrollment() {
        System.out.print("\nEnter Enrollment ID to cancel: ");
        try {
            int enrollmentId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Are you sure you want to cancel this enrollment? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("yes")) {
                enrollmentService.cancelEnrollment(enrollmentId);
                System.out.println("\nEnrollment cancelled successfully!");
            } else {
                System.out.println("\nCancellation aborted.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void deleteEnrollment() {
        System.out.print("\nEnter Enrollment ID to delete: ");
        try {
            int enrollmentId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Are you sure? This action cannot be undone (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("yes")) {
                boolean deleted = enrollmentService.deleteEnrollment(enrollmentId);
                if (deleted) {
                    System.out.println("\nEnrollment deleted successfully!");
                } else {
                    System.out.println("\nEnrollment not found.");
                }
            } else {
                System.out.println("\nDeletion cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter a valid number.");
        }
    }
    
    // ============================================
    // STATISTICS MENU
    // ============================================
    
    private static void showStatisticsMenu() {
        System.out.println("\n========================================");
        System.out.println("          STATISTICS");
        System.out.println("========================================");
        
        // Student Statistics
        int totalStudents = studentService.getTotalStudentCount();
        int activeStudents = studentService.getActiveStudentCount();
        System.out.println("Students:");
        System.out.println("  Total: " + totalStudents);
        System.out.println("  Active: " + activeStudents);
        System.out.println("  Inactive: " + (totalStudents - activeStudents));
        
        System.out.println();
        
        // Course Statistics
        int totalCourses = courseService.getTotalCourseCount();
        int activeCourses = courseService.getActiveCourseCount();
        System.out.println("Courses:");
        System.out.println("  Total: " + totalCourses);
        System.out.println("  Active: " + activeCourses);
        System.out.println("  Inactive: " + (totalCourses - activeCourses));
        
        System.out.println();
        
        // Enrollment Statistics
        int totalEnrollments = enrollmentService.getTotalEnrollmentCount();
        int activeEnrollments = enrollmentService.getActiveEnrollmentCount();
        int completedEnrollments = enrollmentService.getCompletedEnrollmentCount();
        System.out.println("Enrollments:");
        System.out.println("  Total: " + totalEnrollments);
        System.out.println("  Active: " + activeEnrollments);
        System.out.println("  Completed: " + completedEnrollments);
        System.out.println("  Other: " + (totalEnrollments - activeEnrollments - completedEnrollments));
        
        System.out.println("========================================");
    }
}