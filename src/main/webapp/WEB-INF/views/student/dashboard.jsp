<%--
  Created by IntelliJ IDEA.
  User: humura
  Date: 2/12/2025
  Time: 12:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <style>
        /* Add matching dashboard styles */
        .assignment-card {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 1rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .status-indicator {
            display: inline-block;
            padding: 0.3rem 0.8rem;
            border-radius: 20px;
            font-size: 0.9rem;
        }
        .status-submitted { background: #4CAF50; color: white; }
        .status-pending { background: #FFC107; color: black; }
    </style>
</head>
<body>
<div class="container">
    <h1>Your Assignments</h1>
    <c:forEach items="${assignments}" var="assignment">
        <div class="assignment-card">
            <h3>${assignment.title}</h3>
            <p>${assignment.description}</p>
            <p>Due: ${assignment.dueDate}</p>
            <c:choose>
                <c:when test="${submissionService.hasStudentSubmitted(userId, assignment.id)}">
                    <span class="status-indicator status-submitted">Submitted</span>
                </c:when>
                <c:otherwise>
                    <a href="/student/submit-assignment?id=${assignment.id}" class="cta-btn">Submit Now</a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
</div>
</body>
</html>
