package servlet;

import com.google.gson.Gson;
import model.Course;
import storage.DataStore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for managing course records.
 * Supports GET and POST operations.
 */
@WebServlet("/courses")
public class CourseServlet extends HttpServlet {

    private Gson gson = new Gson();

    /**
     * Handles GET requests to retrieve all courses.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Course> courses = DataStore.getCourses();
        String json = gson.toJson(courses);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    /**
     * Handles POST requests to create a new course.
     * Expects a JSON body with name and instructor.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Course newCourse = gson.fromJson(reader, Course.class);
        DataStore.addCourse(newCourse);
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\": \"Course added\"}");
    }
}
