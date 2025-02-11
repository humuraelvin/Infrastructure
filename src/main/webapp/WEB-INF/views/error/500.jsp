<%--
  Created by IntelliJ IDEA.
  User: humura
  Date: 2/11/2025
  Time: 7:26 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>500 - Server Error</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f6fa;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .error-container {
            text-align: center;
            padding: 2rem;
        }

        .error-code {
            font-size: 8rem;
            font-weight: bold;
            color: #e74c3c;
            margin-bottom: 1rem;
        }

        .error-message {
            font-size: 1.5rem;
            color: #2d3436;
            margin-bottom: 1rem;
        }

        .error-details {
            color: #636e72;
            margin-bottom: 2rem;
        }

        .home-btn {
            padding: 1rem 2rem;
            background: #e74c3c;
            color: white;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 1rem;
            transition: transform 0.2s ease;
            display: inline-block;
        }

        .home-btn:hover {
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="error-code">500</div>
    <div class="error-message">Internal Server Error</div>
    <div class="error-details">Something went wrong. Please try again later.</div>
    <a href="${pageContext.request.contextPath}/" class="home-btn">Go Home</a>
</div>
</body>
</html>
