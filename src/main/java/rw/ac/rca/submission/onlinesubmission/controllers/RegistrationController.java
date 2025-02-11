package rw.ac.rca.submission.onlinesubmission.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rw.ac.rca.submission.onlinesubmission.services.StudentService;
import rw.ac.rca.submission.onlinesubmission.services.TeacherService;
import rw.ac.rca.submission.onlinesubmission.services.UserService;

import java.io.IOException;

@WebServlet("/register/*")
public class RegistrationController extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/student":
                request.getRequestDispatcher("/WEB-INF/views/registration/student-registration.jsp")
                        .forward(request, response);
                break;
            case "/teacher":
                request.getRequestDispatcher("/WEB-INF/views/registration/teacher-registration.jsp")
                        .forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        try {
            switch (path) {
                case "/student":
                    handleStudentRegistration(request, response);
                    break;
                case "/teacher":
                    handleTeacherRegistration(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() +
                    "/register" + path + "?error=" + e.getMessage());
        }
    }

    private void handleStudentRegistration(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        String registrationNumber = request.getParameter("registrationNumber");

        studentService.registerStudent(firstName, lastName, email, password, registrationNumber);
        response.sendRedirect(request.getContextPath() + "/login?success=Registration successful");
    }

    private void handleTeacherRegistration(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String department = request.getParameter("department");

        teacherService.registerTeacher(firstName, lastName, email, password, department);
        response.sendRedirect(request.getContextPath() + "/login?success=Registration successful");
    }
}

// Add to RegistrationController.java
@WebServlet("/check-email")
public class EmailCheckServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        boolean exists = new UserService().isEmailTaken(email);

        response.setContentType("application/json");
        response.getWriter().write("{\"exists\":" + exists + "}");
    }
}