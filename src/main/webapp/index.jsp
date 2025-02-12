<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Online Submission System</title>
    <style>
        :root {
            --primary: #4a90e2;
            --secondary: #f5a623;
            --dark: #2d3436;
            --light: #f5f6fa;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
            min-height: 100vh;
        }

        .hero {
            padding: 4rem 2rem;
            text-align: center;
            color: white;
        }

        .hero h1 {
            font-size: 3.5rem;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
        }

        .hero p {
            font-size: 1.2rem;
            margin-bottom: 2rem;
        }

        .cta-container {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        .cta-btn {
            padding: 1rem 2rem;
            background: rgba(255,255,255,0.9);
            border: none;
            border-radius: 50px;
            color: var(--dark);
            font-size: 1.1rem;
            text-decoration: none;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        .cta-btn:hover {
            transform: translateY(-3px);
            background: white;
            box-shadow: 0 6px 20px rgba(0,0,0,0.15);
        }

        .features {
            padding: 4rem 2rem;
            background: white;
        }

        .features-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 2rem;
            max-width: 1200px;
            margin: 0 auto;
        }

        .feature-card {
            padding: 2rem;
            background: var(--light);
            border-radius: 10px;
            text-align: center;
        }

        footer {
            background: var(--dark);
            color: white;
            text-align: center;
            padding: 2rem;
        }
    </style>
</head>
<body>
<div class="hero">
    <h1>Online Submission System</h1>
    <p>Efficient assignment management for educators and students</p>
    <div class="cta-container">
        <a href="${pageContext.request.contextPath}/views/auth/login.jsp" class="cta-btn">Student Login</a>
        <a href="${pageContext.request.contextPath}/views/auth/login.jsp" class="cta-btn">Teacher Login</a>
        <a href="${pageContext.request.contextPath}/views/auth/register.jsp" class="cta-btn">Create Account</a>
    </div>
</div>

<section class="features">
    <div class="features-grid">
        <div class="feature-card">
            <h3>Easy Submission</h3>
            <p>Students can submit assignments with just a few clicks</p>
        </div>
        <div class="feature-card">
            <h3>Deadline Tracking</h3>
            <p>Real-time deadline tracking and notifications</p>
        </div>
        <div class="feature-card">
            <h3>Secure Storage</h3>
            <p>All submissions stored securely on our servers</p>
        </div>
    </div>
</section>

<footer>
    <p>&copy; 2024 Online Submission System. All rights reserved.</p>
</footer>
</body>
</html>