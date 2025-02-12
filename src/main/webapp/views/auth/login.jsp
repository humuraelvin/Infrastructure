<%--
  Created by IntelliJ IDEA.
  User: humura
  Date: 2/11/2025
  Time: 7:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Online Submission System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f6d365 0%, #fda085 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .container {
            background: white;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }

        h1 {
            color: #2d3436;
            margin-bottom: 1.5rem;
            text-align: center;
            font-size: 2rem;
        }

        .form-group {
            margin-bottom: 1.2rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #636e72;
            font-size: 0.9rem;
        }

        input {
            width: 100%;
            padding: 0.8rem;
            border: 2px solid #dfe6e9;
            border-radius: 6px;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }

        input:focus {
            outline: none;
            border-color: #fda085;
        }

        button {
            width: 100%;
            padding: 1rem;
            background: linear-gradient(to right, #f6d365, #fda085);
            border: none;
            border-radius: 6px;
            color: white;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s ease;
        }

        button:hover {
            transform: translateY(-2px);
        }

        .switch-form {
            text-align: center;
            margin-top: 1rem;
            color: #636e72;
        }

        .switch-form a {
            color: #fda085;
            text-decoration: none;
            font-weight: 600;
        }

        .error-message {
            background: #fff3f3;
            color: #ff4757;
            padding: 0.8rem;
            border-radius: 6px;
            margin-bottom: 1rem;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome Back</h1>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
    <form action="AuthController" method="post">
        <input type="hidden" name="action" value="login">
        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="userType">I am a:</label>
            <select id="userType" name="userType" required>
                <option value="student">Student</option>
                <option value="teacher">Teacher</option>
            </select>
        </div>
        <button type="submit">Login</button>
    </form>
    <div class="switch-form">
        Don't have an account? <a href="register.jsp">Register</a>
    </div>
</div>
</body>
</html>
