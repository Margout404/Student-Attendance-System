package servlet;

import model.Student;
import storage.DataStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import com.google.gson.Gson;


 //Servlet for managing student records.
 // Supports CRUD operations via RESTful endpoints.

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {
    private DataStore dataStore = DataStore.getInstance();


    private Gson gson = new Gson();

     //Handles GET requests for students.
     // If an ID is provided in the path, returns that specific student.
     // Otherwise, returns all students.

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // π.χ. /5

        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            // return all students
            List<Student> students = DataStore.getStudents();
            resp.getWriter().write(gson.toJson(students));
        } else {
            // return one student based on id
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Student student = DataStore.getStudentById(id);

                if (student != null) {
                    resp.getWriter().write(gson.toJson(student));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\": \"Student not found\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Invalid ID\"}");
            }
        }
    }

     //Handles POST requests to create a new student.
     //Expects a JSON body with fullName and email.

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        Student newStudent = gson.fromJson(reader, Student.class);

        DataStore.addStudent(newStudent);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"status\":\"Student added\"}");
    }

    // handles put(update) requests to update an existing student by id
    // needs an id in URL and updated student data in rq body
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // π.χ. /2
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing student ID in path.\"}");
            return;
        }

        int id = Integer.parseInt(pathInfo.substring(1)); // αφαιρεί το "/"
        Student updatedStudent = gson.fromJson(req.getReader(), Student.class);
        updatedStudent.setId(id);

        boolean updated = dataStore.updateStudent(updatedStudent);
        if (updated) {
            resp.getWriter().write("{\"status\":\"Student updated\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Student not found\"}");
        }
    }

    //handles delete request to remove a student by id
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // π.χ. /2
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing student ID in path.\"}");
            return;
        }

        int id = Integer.parseInt(pathInfo.substring(1));
        boolean deleted = dataStore.deleteStudent(id);
        if (deleted) {
            resp.getWriter().write("{\"status\":\"Student deleted\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Student not found\"}");
        }
    }


}
