# Student Attendance Management System

A simple Java Servlet-based web application for managing students,
courses, and their attendance.

## Technologies Used

-   Java (Servlets)
-   HTML (basic UI)
-   Gson (JSON parsing)
-   Postman (API testing)

------------------------------------------------------------------------

## Folder Structure

    src/
    ├── model/               # Model classes: Student, Course, AttendanceRecord
    ├── servlet/             # Servlets: StudentServlet, CourseServlet, AttendanceServlet
    ├── storage/             # In-memory DataStore
    WebContent/
    ├── index.html           # Entry point
    ├── WEB-INF/             # Deployment descriptors
    └── lib/                 # External libraries (e.g., gson.jar)

------------------------------------------------------------------------

## API Endpoints

### Students

  ------------------------------------------------------------------------
  Method   Endpoint                         Description
  -------- -------------------------------- ------------------------------
  GET      `/students`                      Get all students

  POST     `/students`                      Create new student

  GET      `/students/{id}`                 Get student by ID

  PUT      `/students/{id}`                 Update student by ID

  DELETE   `/students/{id}`                 Delete student by ID
  ------------------------------------------------------------------------

------------------------------------------------------------------------

### Courses

  ------------------------------------------------------------------------
  Method   Endpoint                         Description
  -------- -------------------------------- ------------------------------
  GET      `/courses`                       Get all courses

  POST     `/courses`                       Create new course
  ------------------------------------------------------------------------

------------------------------------------------------------------------

### Attendance

  -----------------------------------------------------------------------------
  Method   Endpoint                               Description
  -------- -------------------------------------- -----------------------------
  GET      `/attendance`                          Get all attendance records

  GET      `/attendance?studentId=1&courseId=2`   Get filtered attendance by
                                                  student & course

  POST     `/attendance`                          Add new attendance record

  PUT      `/attendance/{id}`                     Update attendance record by
                                                  ID

  DELETE   `/attendance/{id}`                     Delete attendance record by
                                                  ID
  -----------------------------------------------------------------------------

------------------------------------------------------------------------

## JSON Request Examples

### Create Student

``` json
{
  "fullName": "John Doe",
  "email": "john@example.com"
}
```

### Create Course

``` json
{
  "name": "Java Programming",
  "instructor": "Maria Papadopoulou"
}
```

### Record Attendance

``` json
{
  "studentId": 1,
  "courseId": 2,
  "date": "2025-07-31",
  "present": true
}
```

### Update Attendance

``` json
{
  "studentId": 1,
  "courseId": 2,
  "date": "2025-07-31",
  "present": false
}
```

------------------------------------------------------------------------

## Setup

1.  Compile the `.java` files with the proper `javac -cp` command
    including Gson and Servlet JARs.
2.  Package the compiled `.class` files in the `WEB-INF/classes/`
    folder.
3.  Deploy with Apache Tomcat or similar servlet container.
4.  Use Postman or `curl` to interact with the API.

------------------------------------------------------------------------

## Author

Marios Goutidis\
https://github.com/Margout404
