<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection" %>
<%@ page import="uk.ac.ucl.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Note</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-top: 0;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 200px;
            resize: vertical;
        }
        .categories {
            margin-top: 15px;
        }
        .category-option {
            margin-right: 15px;
            display: inline-block;
        }
        button {
            padding: 10px 20px;
            background-color: #4285f4;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #3367d6;
        }
        .error {
            color: #d93025;
            margin-bottom: 15px;
        }
        .back-link {
            display: inline-block;
            margin-top: 15px;
            color: #4285f4;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Create New Note</h1>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <form action="createNote" method="POST">
            <div class="form-group">
                <label for="name">Note Title:</label>
                <input type="text" id="name" name="name" required>
            </div>
            
            <div class="form-group">
                <label for="content">Content:</label>
                <textarea id="content" name="content" required></textarea>
            </div>
            
            <% Collection<Category> categories = (Collection<Category>) request.getAttribute("categories");
            if (categories != null && !categories.isEmpty()) { %>
                <div class="form-group">
                    <label>Categories:</label>
                    <div class="categories">
                        <% for (Category category : categories) { %>
                            <div class="category-option">
                                <input type="checkbox" id="category_<%= category.getId() %>" 
                                       name="categories" value="<%= category.getId() %>">
                                <label for="category_<%= category.getId() %>"><%= category.getName() %></label>
                            </div>
                        <% } %>
                    </div>
                </div>
            <% } %>
            
            <button type="submit">Create Note</button>
        </form>
        
        <a href="index" class="back-link">Back to Notes</a>
    </div>
</body>
</html> 