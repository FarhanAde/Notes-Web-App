<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.TextNote" %>
<%@ page import="uk.ac.ucl.model.Category" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${param.id != null ? 'Edit' : 'Create'} Note</title>
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
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
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
        button {
            padding: 10px 20px;
            background-color: #4285f4;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .actions {
            margin-top: 20px;
        }
        .actions a {
            display: inline-block;
            margin-right: 10px;
            color: #4285f4;
            text-decoration: none;
        }
        .error {
            color: #d93025;
            margin-bottom: 15px;
            padding: 10px;
            background-color: #fce8e6;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <% 
        Note note = (Note) request.getAttribute("note");
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        boolean isEdit = note != null;
        String noteType = "";
        if (isEdit) {
            if (note instanceof TextNote) {
                noteType = "text";
            }
        }
        %>
        
        <h1><%= isEdit ? "Edit Note" : "Create New Note" %></h1>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <form action="${pageContext.request.contextPath}<%= isEdit ? "/editNote" : "/createNote" %>" method="POST">
            <% if (isEdit) { %>
                <input type="hidden" name="id" value="<%= note.getId() %>">
                <input type="hidden" name="noteType" value="<%= noteType %>">
            <% } %>
            
            <div class="form-group">
                <label for="name">Note Title:</label>
                <input type="text" id="name" name="name" value="<%= isEdit ? note.getName() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label for="content">Content:</label>
                <textarea id="content" name="text" required><%= isEdit && note instanceof TextNote ? ((TextNote) note).getText() : "" %></textarea>
            </div>
            
            <% if (categories != null && !categories.isEmpty()) { %>
                <div class="form-group">
                    <label>Categories:</label>
                    <div class="categories">
                        <% for (Category category : categories) { %>
                            <div class="category-option">
                                <input type="checkbox" id="category_<%= category.getId() %>" 
                                       name="categories" value="<%= category.getId() %>"
                                       <%= (isEdit && category.containsNote(note.getId())) ? "checked" : "" %>>
                                <label for="category_<%= category.getId() %>"><%= category.getName() %></label>
                            </div>
                        <% } %>
                    </div>
                </div>
            <% } %>
            
            <div class="form-group">
                <button type="submit"><%= isEdit ? "Update Note" : "Create Note" %></button>
            </div>
        </form>
        
        <div class="actions">
            <a href="index">Back to Notes</a>
        </div>
    </div>
</body>
</html> 