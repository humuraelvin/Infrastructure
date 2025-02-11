package rw.ac.rca.submission.onlinesubmission.controllers;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import rw.ac.rca.submission.onlinesubmission.models.Assignment;
import rw.ac.rca.submission.onlinesubmission.models.Student;
import rw.ac.rca.submission.onlinesubmission.services.AssignmentService;
import rw.ac.rca.submission.onlinesubmission.services.StudentService;
import rw.ac.rca.submission.onlinesubmission.services.SubmissionService;

import java.io.IOException;

@WebServlet("/student/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 15
)
public class StudentController extends HttpServlet {


    private final StudentService studentService = new StudentService();
    private final AssignmentService assignmentService = new AssignmentService();
    private final SubmissionService submissionService = new SubmissionService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        Long userId = (Long) request.getSession().getAttribute("userId");
        Student student = studentService.getStudentById(userId);

        switch(path){

            case "/dashboard":
                request.setAttribute("assignments", assignmentService.getActiveAssignments());
                request.setAttribute("submissions", submissionService.getSubmissionsByStudent(userId));
                request.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp").forward(request, response);
                break;

            case "/submit-assignment":
                Long assignmentId = Long.parseLong(request.getParameter("id"));
                Assignment assignment = assignmentService.getAssignmentById(assignmentId);

                if (assignment != null && !assignmentService.isAssignmentOverdue(assignment)) {
                    request.setAttribute("assignment", assignment);
                    request.setAttribute("hasSubmitted",
                            submissionService.hasStudentSubmitted(userId, assignmentId));
                    request.getRequestDispatcher("/WEB-INF/views/student/submit-assignment.jsp").forward(request, response);
                }else {
                    response.sendRedirect(request.getContextPath() + "/student/dashboard?error= Assignment is not available");
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        Long userId = (Long) request.getSession().getAttribute("userId");
        Student student = studentService.getStudentById(userId);

        if ("/submit-assignment".equals(path)){
            handleSubmitAssignment(request, response, student);
        }else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

;    }


    private void handleSubmitAssignment(HttpServletRequest request, HttpServletResponse response, Student student) throws IOException, ServletException {

        try {
            Long assignmentId = Long.parseLong(request.getParameter("assignmentId"));
            Assignment assignment = assignmentService.getAssignmentById(assignmentId);

            if (assignment == null || assignmentService.isAssignmentOverdue(assignment)) {
                throw new IllegalStateException("Sorry,  Assignment is not available of has Expired!!");
            }

            Part filePart = request.getPart("file");
            if (filePart == null) {
                throw new IllegalStateException("No File was uploaded");
            }

            submissionService.submitAssignment(student, assignment, filePart);
            response.sendRedirect(request.getContextPath() + "/student/dashboard?success=true");

        }catch (Exception e) {

            response.sendRedirect(request.getContextPath() + "/student/submit-assignment?id=" + request.getParameter("assignmentId") + "&error=true" + e.getMessage());

        }

    }


}
