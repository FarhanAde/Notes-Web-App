<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.ucl.model.Category" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Note Categories</title>
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
        .checkbox-group {
            margin-bottom: 10px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
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
        <% 
        Note note = (Note) request.getAttribute("note");
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        %>
        
        <h1>Manage Categories for Note: <%= note.getName() %></h1>
        
        <form action="manageNoteCategories" method="POST">
            <input type="hidden" name="id" value="<%= note.getId() %>">
            
            <div class="form-group">
                <label>Select Categories:</label>
                <% for (Category category : categories) { %>
                    <div class="checkbox-group">
                        <input type="checkbox" id="category_<%= category.getId() %>" 
                               name="categoryIds" value="<%= category.getId() %>"
                               <%= category.containsNote(note.getId()) ? "checked" : "" %>>
                        <label for="category_<%= category.getId() %>"><%= category.getName() %></label>
                    </div>
                <% } %>
            </div>
            
            <button type="submit">Save Categories</button>
        </form>
        
        <a href="viewNote?id=<%= note.getId() %>" class="back-link">Back to Note</a>
    </div>
</body>
</html> 