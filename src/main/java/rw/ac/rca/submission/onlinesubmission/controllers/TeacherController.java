package rw.ac.rca.submission.onlinesubmission.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rw.ac.rca.submission.onlinesubmission.models.Assignment;
import rw.ac.rca.submission.onlinesubmission.models.Teacher;
import rw.ac.rca.submission.onlinesubmission.services.AssignmentService;
import rw.ac.rca.submission.onlinesubmission.services.TeacherService;
import rw.ac.rca.submission.onlinesubmission.services.SubmissionService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@WebServlet("/teacher/*")
public class TeacherController extends HttpServlet {

    private final TeacherService teacherService = new TeacherService();
    private final AssignmentService assignmentService = new AssignmentService();
    private final SubmissionService submissionService = new SubmissionService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        Long userId = (Long) request.getSession().getAttribute("userId");
        Teacher teacher = teacherService.getTeacherById(userId);

        switch (path){
            case "/dashboard":
                request.setAttribute("assignments",
                        assignmentService.getAssignmentsByTeacher(userId));
                request.getRequestDispatcher("/WEB-INF/views/teacher/dashboard.jsp").forward(request, response);
                break;

            case "/create-assignment":
                request.getRequestDispatcher("/WEB-INF/views/teacher/create-assignment.jsp").forward(request, response);
                break;

            case "/view-submissions":
                Long assignmetId = Long.parseLong(request.getParameter("id"));
                Assignment assignment = assignmentService.getAssignmentById(assignmetId);

                if (assignment.getTeacher().getId().equals(userId)) {
                    request.setAttribute("assignment", assignment);
                    request.setAttribute("submissions",
                            submissionService.getSubmissionsByStudent(assignmetId));
                    request.getRequestDispatcher("/WEB-INF/views/teacher/view-submissions.jsp").forward(request, response);
                }else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
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
        Teacher teacher = teacherService.getTeacherById(userId);

        switch (path){
            case "/create-assignment":
                handleCreateAssignment(request, response, teacher);
                break;

            case "/delete-assignment":
                handleDeleteAssignment(request, response, teacher);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }

    }


    private void handleCreateAssignment(HttpServletRequest request, HttpServletResponse response, Teacher teacher) throws IOException {

        try{
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String courseCode = request.getParameter("courseCode");
            LocalDateTime dueDate = LocalDateTime.parse(request.getParameter("dueDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String allowedFileTypes = request.getParameter("allowedFileTypes");

            assignmentService.createAssignment(
                    title, description, dueDate, teacher, courseCode, allowedFileTypes
            );

            response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
        }catch (Exception e){
            response.sendRedirect(request.getContextPath() + "/teacher/create-assignment?error" + e.getMessage());
        }

    }

    private void handleDeleteAssignment(HttpServletRequest request, HttpServletResponse response, Teacher teacher) throws IOException {

        try{
            Long assignmentId = Long.parseLong(request.getParameter("id"));
            Assignment assignment = assignmentService.getAssignmentById(assignmentId);

            if (assignment.getTeacher().getId().equals(teacher.getId())) {
                assignmentService.deleteAssignment(assignmentId);
                response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
            }else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }catch (Exception e){
            response.sendRedirect(request.getContextPath() + "/teacher/dashboard?error" + e.getMessage());
        }

    }

}
