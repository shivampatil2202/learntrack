# Design Notes

## Why ArrayList Instead of Array?

### Arrays Limitations
```java
// Array - fixed size
Student[] students = new Student[10]; // Must know size upfront
// What if we need to add 11th student?
```

### ArrayList Advantages
```java
// ArrayList - dynamic size
ArrayList<Student> students = new ArrayList<>();
students.add(new Student(...)); // Grows automatically
```

**Reasons for choosing ArrayList:**

1. **Dynamic Sizing**: Automatically grows when elements are added
2. **Built-in Methods**: add(), remove(), size(), contains(), etc.
3. **Type Safety**: Generics prevent adding wrong types
4. **Easier to Use**: No manual index management
5. **Better API**: More intuitive than array manipulation

**When to use arrays:**
- Fixed-size collections known at compile time
- Performance-critical code with primitive types
- Multidimensional data (2D arrays)

**When to use ArrayList:**
- Dynamic collections (our use case)
- Frequent additions/removals
- Need for collection utilities
- Most business applications

## Where We Used Static Members and Why

### IdGenerator Class
```java
public class IdGenerator {
    private static int studentIdCounter = 1000;
    
    public static int getNextStudentId() {
        return ++studentIdCounter;
    }
}
```

**Why static?**
- ID counters must be shared across all instances
- No need to create IdGenerator objects
- Single source of truth for ID generation
- Memory efficient (one copy for entire application)

### Usage Pattern
```java
// Can call without creating object
int id = IdGenerator.getNextStudentId();

// Would be awkward with instance members
IdGenerator gen = new IdGenerator(); // Unnecessary
int id = gen.getNextStudentId(); // More complex
```

### Static vs Instance Decision Guide

**Use Static When:**
- Shared data across all instances (counters, configuration)
- Utility methods with no object state (Math.max, IdGenerator)
- Constants (final static values)

**Use Instance When:**
- Data unique to each object (student name, course title)
- Behavior depends on object state
- Polymorphic behavior needed

## Where We Used Inheritance and Benefits

### Person → Student Hierarchy
```java
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    // Common properties and methods
}

public class Student extends Person {
    private String batch;
    private boolean active;
    // Student-specific properties
}
```

**Benefits Gained:**

1. **Code Reuse**
   - Don't repeat id, firstName, lastName in Student
   - Person's methods automatically available in Student

2. **Logical Organization**
   - Reflects real-world relationship
   - Student IS-A Person
   - Clear hierarchy

3. **Flexibility for Future**
   - Can add Trainer extends Person
   - Can add Instructor extends Person
   - Common code stays in Person

4. **Polymorphism Possibility**
```java
   Person p = new Student(...);
   // Can treat Student as Person when needed
```

5. **Method Overriding**
```java
   @Override
   public String getDisplayName() {
       return super.getDisplayName() + " (Student ID: " + getId() + ")";
   }
```
   - Customize behavior while keeping base functionality
   - super keyword accesses parent implementation

### Design Decision

**Initially considered:**
```java
// Option 1: No inheritance
class Student {
    int id, firstName, lastName, email, batch;
    // All fields here
}

// Option 2: With inheritance (chosen)
class Person {
    int id, firstName, lastName, email;
}
class Student extends Person {
    String batch;
}
```

**Chose Option 2 because:**
- Anticipate adding more person types (Trainer, Admin)
- Common operations on all persons
- More maintainable (change Person, all subtypes benefit)
- Better represents domain model

## Architecture Decisions

### Layered Architecture
UI Layer (Main.java)
↓
Service Layer (StudentService, CourseService, EnrollmentService)
↓
Repository Layer (StudentRepository, CourseRepository, EnrollmentRepository)
↓
Entity Layer (Student, Course, Enrollment)

**Why layered?**
- **Separation of Concerns**: Each layer has one responsibility
- **Maintainability**: Easy to change one layer without affecting others
- **Testability**: Can test services without UI
- **Scalability**: Easy to add new features

**Example benefit:**
```java
// Can switch from in-memory to database storage
// by only changing repository layer
// Services and UI remain unchanged
```

### Service Layer Pattern

**Why services?**
- Encapsulate business logic
- Coordinate between multiple repositories
- Add validation
- Handle complex operations

**Example:**
```java
public Enrollment enrollStudent(int studentId, int courseId) {
    // Validates student exists
    // Validates course exists
    // Checks for duplicate enrollment
    // Creates enrollment
    // Complex logic in one place
}
```

### Exception Handling Strategy

**Custom Exceptions:**
- EntityNotFoundException: When searching fails
- InvalidInputException: When validation fails

**Benefits:**
- Specific error types
- Clear error messages
- Graceful error handling
- User-friendly feedback

## Trade-offs Made

### In-Memory Storage
**Chosen**: ArrayList for data storage  
**Alternative**: File/Database  
**Why**: Simplicity for learning project, add persistence later

### Simple Validation
**Chosen**: Basic email/name validation  
**Alternative**: Complex regex, third-party validators  
**Why**: Focus on core Java concepts

### String Status
**Chosen**: String for enrollment status  
**Alternative**: Enum  
**Why**: Keep it simple, can refactor to enum later

## Future Improvements

1. **Add Enum for Status**
```java
   enum EnrollmentStatus { ACTIVE, COMPLETED, CANCELLED }
```

2. **Add File Persistence**
   - Save data to files on exit
   - Load data on startup

3. **Add More Validation**
   - Email format (regex)
   - Phone number format
   - Duplicate detection

4. **Improve Search**
   - Search by name
   - Filter by batch
   - Sort results

5. **Add Relationships**
   - Store Student object in Enrollment (not just ID)
   - Bi-directional relationships