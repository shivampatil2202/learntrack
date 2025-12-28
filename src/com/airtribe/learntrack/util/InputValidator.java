package com.airtribe.learntrack.util;

import com.airtribe.learntrack.constants.AppConstants;

public class InputValidator {
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches(AppConstants.EMAIL_PATTERN);
    }
    
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.length() >= AppConstants.MIN_NAME_LENGTH && 
               name.length() <= AppConstants.MAX_NAME_LENGTH;
    }
    
    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }
    
    public static boolean isValidDuration(int duration) {
        return duration >= AppConstants.MIN_COURSE_DURATION && 
               duration <= AppConstants.MAX_COURSE_DURATION;
    }
}
