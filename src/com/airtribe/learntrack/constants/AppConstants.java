package com.airtribe.learntrack.constants;

public class AppConstants {
    // Application Info
    public static final String APP_NAME = "LearnTrack";
    public static final String APP_VERSION = "1.0.0";
    
    // ID Generation
    public static final int STUDENT_ID_START = 1000;
    public static final int COURSE_ID_START = 2000;
    public static final int ENROLLMENT_ID_START = 3000;
    
    // Validation
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MIN_COURSE_DURATION = 1;
    public static final int MAX_COURSE_DURATION = 52;
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    // Display
    public static final String SEPARATOR = "========================================";
    
    // Messages
    public static final String MSG_INVALID_OPTION = "Invalid option. Please try again.";
    public static final String MSG_INVALID_NUMBER = "Please enter a valid number.";
    public static final String MSG_THANK_YOU = "Thank you for using LearnTrack!";
    
    private AppConstants() {
    }
}
