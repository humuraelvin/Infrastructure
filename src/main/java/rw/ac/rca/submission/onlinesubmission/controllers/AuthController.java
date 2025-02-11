package rw.ac.rca.submission.onlinesubmission.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import rw.ac.rca.submission.onlinesubmission.models.User;
import rw.ac.rca.submission.onlinesubmission.services.UserService;

import java.io.IOException;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
        }else {
            response.sendRedirect(request.getContextPath() + "/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if ("/login".equals(path)) {
            handleLogin(request, response);
        }else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try{
            User user = userService.authenticate(email, password);

            if (user != null) {
                HttpSession session = request.getSession();

                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole());

                if ("TEACHER".equals(user.getEmail())){
                    response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
                }else {
                    response.sendRedirect(request.getContextPath() + "/student/dashboard");
                }

            }else {
                response.sendRedirect(request.getContextPath() + "/login?error=Invalid email or password!!!");
            }
        }catch (Exception e){
            response.sendRedirect(request.getContextPath() + "/login?error=Sorry, an unexpected error occurred");
        }

    }

}
