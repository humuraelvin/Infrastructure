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
    <title>Submit Assignment</title>
    <style>
        .upload-container {
            border: 2px dashed #ddd;
            padding: 2rem;
            text-align: center;
            border-radius: 8px;
            margin: 2rem 0;
        }
        #fileInput {
            display: none;
        }
        .file-preview {
            margin-top: 1rem;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Submit: ${assignment.title}</h1>
    <p class="due-date">Due: ${assignment.dueDate}</p>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <form action="/student/submit-assignment" method="post" enctype="multipart/form-data">
        <input type="hidden" name="assignmentId" value="${assignment.id}">

        <div class="upload-container">
            <label for="fileInput" class="cta-btn">Choose File</label>
            <input type="file" name="file" id="fileInput" required>
            <div class="file-preview" id="filePreview"></div>
        </div>

        <button type="submit" class="cta-btn">Submit Assignment</button>
    </form>
</div>

<script>
    document.getElementById('fileInput').addEventListener('change', function(e) {
        const preview = document.getElementById('filePreview');
        preview.innerHTML = `<i class="fas fa-file"></i> ${e.target.files[0].name}`;
    });
</script>
</body>
</html>