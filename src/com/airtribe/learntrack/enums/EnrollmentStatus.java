package com.airtribe.learntrack.enums;

public enum EnrollmentStatus {
    ACTIVE("Active"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    DROPPED("Dropped");
    
    private final String displayName;
    
    EnrollmentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static EnrollmentStatus fromString(String status) {
        for (EnrollmentStatus es : EnrollmentStatus.values()) {
            if (es.name().equalsIgnoreCase(status)) {
                return es;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}