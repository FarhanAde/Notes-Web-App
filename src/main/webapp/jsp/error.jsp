<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: white;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 {
            color: #d9534f;
            margin-top: 0;
        }
        .error-details {
            margin: 20px 0;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
            text-align: left;
        }
        .back-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4285f4;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Oops! Something went wrong</h1>
        <p>We're sorry, but an error occurred while processing your request.</p>
        
        <% if (exception != null) { %>
            <div class="error-details">
                <h3>Error Details:</h3>
                <p><strong>Type:</strong> <%= exception.getClass().getName() %></p>
                <p><strong>Message:</strong> <%= exception.getMessage() %></p>
            </div>
        <% } %>
        
        <a href="index" class="back-button">Return to Home</a>
    </div>
</body>
</html> 