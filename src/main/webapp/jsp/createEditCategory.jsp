<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.ucl.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${param.id != null ? 'Edit' : 'Create'} Category</title>
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
        input[type="text"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
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
        .checkbox-group {
            margin-top: 20px;
            max-height: 300px;
            overflow-y: auto;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 4px;
        }
        .checkbox-item {
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <% 
        Category category = (Category) request.getAttribute("category");
        List<Note> allNotes = (List<Note>) request.getAttribute("allNotes");
        boolean isEdit = category != null;
        %>
        
        <h1><%= isEdit ? "Edit Category" : "Create New Category" %></h1>
        
        <form action="<%= isEdit ? "update-category" : "save-category" %>" method="POST">
            <% if (isEdit) { %>
                <input type="hidden" name="id" value="<%= category.getId() %>">
            <% } %>
            
            <div class="form-group">
                <label for="name">Category Name:</label>
                <input type="text" id="name" name="name" value="<%= isEdit ? category.getName() : "" %>" required>
            </div>
            
            <% if (allNotes != null && !allNotes.isEmpty()) { %>
                <div class="form-group">
                    <label>Include Notes:</label>
                    <div class="checkbox-group">
                        <% for (Note note : allNotes) { %>
                            <div class="checkbox-item">
                                <input type="checkbox" id="note-<%= note.getId() %>" name="notes" value="<%= note.getId() %>"
                                       <%= (isEdit && category.getNotes().contains(note)) ? "checked" : "" %>>
                                <label for="note-<%= note.getId() %>"><%= note.getName() %></label>
                            </div>
                        <% } %>
                    </div>
                </div>
            <% } %>
            
            <div class="form-group">
                <button type="submit"><%= isEdit ? "Update Category" : "Create Category" %></button>
            </div>
        </form>
        
        <div class="actions">
            <a href="index">Back to Index</a>
            <% if (isEdit) { %>
                <a href="viewCategory?id=<%= category.getId() %>">Back to Category</a>
            <% } %>
        </div>
    </div>
</body>
</html> 