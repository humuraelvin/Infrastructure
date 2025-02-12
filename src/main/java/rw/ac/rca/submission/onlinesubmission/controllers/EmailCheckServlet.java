package rw.ac.rca.submission.onlinesubmission.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rw.ac.rca.submission.onlinesubmission.services.UserService;

import java.io.IOException;

//@WebServlet("/check-email")
public class EmailCheckServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        boolean exists = new UserService().isEmailTaken(email);

        response.setContentType("application/json");
        response.getWriter().print("{\"exists\":" + exists + "}");
    }
}