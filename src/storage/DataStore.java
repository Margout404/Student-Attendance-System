package storage;

import model.Student;
import model.Course;
import model.AttendanceRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory data storage for students, courses, and attendance records.
 * Provides basic CRUD-like utility methods.
 */

public class DataStore {

    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    private static int attendanceIdCounter = 1;


    private static DataStore instance = new DataStore();
    private static int courseIdCounter = 1;


    public static DataStore getInstance() {
        return instance;
    }


    public static List<Student> getStudents() {
        return students;
    }

    public static void addStudent(Student student) {
        students.add(student);
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static void addCourse(Course course) {
        course.setId(courseIdCounter++);
        courses.add(course);
    }

    public static Student getStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    /**
     * Updates a student based on matching ID.
     */
    public boolean updateStudent(Student updated) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updated.getId()) {
                students.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        return students.removeIf(s -> s.getId() == id);
    }
    public static List<AttendanceRecord> getAttendanceList() {
        return attendanceRecords;
    }
    /**
     * Adds a new attendance record with auto-generated ID.
     */
    public static void addAttendanceRecord(AttendanceRecord record) {
        record.setId(attendanceIdCounter++);
        attendanceRecords.add(record);
    }






}
