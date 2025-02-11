<%--
  Created by IntelliJ IDEA.
  User: humura
  Date: 2/12/2025
  Time: 12:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
    <style>
        .dashboard-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }
        .assignment-list {
            display: grid;
            gap: 1.5rem;
        }
        .assignment-card {
            background: white;
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .assignment-meta {
            display: flex;
            justify-content: space-between;
            margin-top: 1rem;
            color: #666;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="dashboard-header">
        <h1>Your Assignments</h1>
        <a href="/teacher/create-assignment" class="cta-btn">+ New Assignment</a>
    </div>

    <div class="assignment-list">
        <c:forEach items="${assignments}" var="assignment">
            <div class="assignment-card">
                <h3>${assignment.title}</h3>
                <p>${assignment.description}</p>
                <div class="assignment-meta">
                    <span>Due: ${assignment.dueDate}</span>
                    <div>
                        <a href="/teacher/view-submissions?id=${assignment.id}" class="view-submissions">View Submissions (${assignment.submissions.size()})</a>
                        <form action="/teacher/delete-assignment" method="post" style="display: inline;">
                            <input type="hidden" name="id" value="${assignment.id}">
                            <button type="submit" class="delete-btn">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
