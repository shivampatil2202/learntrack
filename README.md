# LearnTrack - Student & Course Management System

A console-based application for managing students, courses, and enrollments built with Core Java.

## Features

- **Student Management**
  - Add new students
  - View all students
  - Search students by ID
  - Deactivate students

- **Course Management**
  - Add new courses
  - View all courses
  - Activate/Deactivate courses

- **Enrollment Management**
  - Enroll students in courses
  - View all enrollments
  - View student-specific enrollments
  - Update enrollment status (ACTIVE/COMPLETED/CANCELLED)

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code with Java extensions)

## Project Structure

src/
└── com/
    └── airtribe/
        └── learntrack/
            ├── Main.java
            │
            ├── entity/
            │   ├── Person.java
            │   ├── Student.java
            │   ├── Course.java
            │   └── Enrollment.java
            │
            ├── repository/
            │   ├── StudentRepository.java
            │   ├── CourseRepository.java
            │   └── EnrollmentRepository.java
            │
            ├── service/
            │   ├── StudentService.java
            │   ├── CourseService.java
            │   └── EnrollmentService.java
            │
            ├── exception/
            │   ├── EntityNotFoundException.java
            │   └── InvalidInputException.java
            │
            ├── util/
            │   ├── IdGenerator.java
            │   └── InputValidator.java
            │
            ├── constants/
            │   ├── MenuOptions.java
            │   └── AppConstants.java
            │
            └── enums/
                ├── EnrollmentStatus.java
                └── CourseStatus.java

## How to Run

### Using IDE
1. Open the project in your IDE
2. Navigate to `Main.java`
3. Right-click and select "Run Main.main()"

### Using Command Line
1. Navigate to the project directory
2. Compile all files:
```bash
   javac -d bin src/com/airtribe/learntrack/**/*.java
```
3. Run the application:
```bash
   java -cp bin com.airtribe.learntrack.Main
```

## Usage

Upon running the application, you'll see a main menu with three options:

1. **Student Management** - Add, view, search, and deactivate students
2. **Course Management** - Add, view, and toggle course status
3. **Enrollment Management** - Enroll students, view enrollments, update status

Navigate through menus by entering the corresponding number.

## Sample Data

The application comes pre-loaded with sample data:
- 2 sample students
- 2 sample courses

You can add more data through the menu options.

## Technical Highlights

- **OOP Principles**: Demonstrates encapsulation, inheritance, and polymorphism
- **Clean Architecture**: Separation of concerns (Entity, Repository, Service, UI)
- **Exception Handling**: Custom exceptions with user-friendly error messages
- **Input Validation**: Validates email format, names, and positive numbers
- **Auto-generated IDs**: Uses static utility class for unique ID generation

## Team Members

- Member 1: [Name] - Entity classes and inheritance
- Member 2: [Name] - Services and business logic
- Member 3: [Name] - Utilities, exceptions, and documentation

## Future Enhancements

- File persistence for data storage
- More advanced search capabilities
- Grade tracking and reporting
- Attendance management
- Email notifications

