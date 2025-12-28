package com.airtribe.learntrack.util;

import com.airtribe.learntrack.constants.AppConstants;

public class IdGenerator {
    private static int studentIdCounter = AppConstants.STUDENT_ID_START;
    private static int courseIdCounter = AppConstants.COURSE_ID_START;
    private static int enrollmentIdCounter = AppConstants.ENROLLMENT_ID_START;
    
    public static int getNextStudentId() {
        return ++studentIdCounter;
    }
    
    public static int getNextCourseId() {
        return ++courseIdCounter;
    }
    
    public static int getNextEnrollmentId() {
        return ++enrollmentIdCounter;
    }
    
    public static void resetCounters() {
        studentIdCounter = AppConstants.STUDENT_ID_START;
        courseIdCounter = AppConstants.COURSE_ID_START;
        enrollmentIdCounter = AppConstants.ENROLLMENT_ID_START;
    }
}