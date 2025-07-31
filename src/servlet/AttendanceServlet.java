package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.AttendanceRecord;
import storage.DataStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servlet for managing attendance records.
 * Supports GET, POST, PUT, and DELETE operations.
 */
@WebServlet("/attendance")
public class AttendanceServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final DataStore dataStore = DataStore.getInstance();
    //Handles GET requests for attendance.
    //If no parameters are provided, returns all attendance records.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String studentIdParam = request.getParameter("studentId");
        String courseIdParam = request.getParameter("courseId");

        if (studentIdParam != null && courseIdParam != null) {
            int studentId = Integer.parseInt(studentIdParam);
            int courseId = Integer.parseInt(courseIdParam);

            List<AttendanceRecord> filtered = DataStore.getAttendanceList().stream()
                    .filter(a -> a.getStudentId() == studentId && a.getCourseId() == courseId)
                    .collect(Collectors.toList());

            out.print(gson.toJson(filtered));
        } else {
            // if no filters, return every recprd
            List<AttendanceRecord> all = DataStore.getAttendanceList();
            out.print(gson.toJson(all));
        }
    }


    //handles post requrest to create attendance record
    // needs a json body with studentID, courseID,date, and present
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        BufferedReader reader = request.getReader();
        AttendanceRecord record = gson.fromJson(reader, AttendanceRecord.class);
        DataStore.addAttendanceRecord(record);

        JsonObject resp = new JsonObject();
        resp.addProperty("status", "Attendance recorded");

        out.print(resp.toString());
    }

     //Handles PUT requests to update an existing attendance record by ID.
     // Expects the ID in the URL and the updated record in the request body.

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String pathInfo = request.getPathInfo(); // π.χ. /3
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Missing attendance ID in path.\"}");
            return;
        }

        int id = Integer.parseInt(pathInfo.substring(1)); // αφαιρεί το /

        BufferedReader reader = request.getReader();
        AttendanceRecord updated = gson.fromJson(reader, AttendanceRecord.class);

        List<AttendanceRecord> records = DataStore.getAttendanceList();
        Optional<AttendanceRecord> existingOpt = records.stream()
                .filter(a -> a.getId() == id)
                .findFirst();

        if (existingOpt.isPresent()) {
            AttendanceRecord existing = existingOpt.get();
            existing.setStudentId(updated.getStudentId());
            existing.setCourseId(updated.getCourseId());
            existing.setDate(updated.getDate());
            existing.setPresent(updated.isPresent());

            out.print("{\"status\": \"Attendance updated\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Attendance record not found.\"}");
        }
    }

     // Handles DELETE requests to remove an attendance record by ID.

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String pathInfo = request.getPathInfo(); // π.χ. /3
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Missing attendance ID in path.\"}");
            return;
        }

        int id = Integer.parseInt(pathInfo.substring(1));

        List<AttendanceRecord> records = DataStore.getAttendanceList();
        boolean removed = records.removeIf(r -> r.getId() == id);

        if (removed) {
            out.print("{\"status\": \"Attendance deleted\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Attendance record not found.\"}");
        }
    }
}
