<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.ucl.model.Category" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Category</title>
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
        .notes-list {
            list-style-type: none;
            padding: 0;
        }
        .notes-list li {
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
        .notes-list li:last-child {
            border-bottom: none;
        }
        .notes-list a {
            color: #4285f4;
            text-decoration: none;
        }
        .notes-list a:hover {
            text-decoration: underline;
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
        .empty-message {
            color: #666;
            font-style: italic;
        }
        .actions {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        .delete-button {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .delete-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="container">
        <% 
        Category category = (Category) request.getAttribute("category");
        List<Note> notes = (List<Note>) request.getAttribute("notes");
        %>
        
        <h1>Category: <%= category.getName() %></h1>
        
        <% if (notes != null && !notes.isEmpty()) { %>
            <ul class="notes-list">
                <% for (Note note : notes) { %>
                    <li>
                        <a href="viewNote?id=<%= note.getId() %>"><%= note.getName() %></a>
                        <span> - <%= note.getSummary() %></span>
                    </li>
                <% } %>
            </ul>
        <% } else { %>
            <p class="empty-message">No notes in this category yet.</p>
        <% } %>
        
        <div class="actions">
            <a href="index" class="back-link">Back to Notes</a>
            <a href="deleteCategory?id=<%= category.getId() %>" class="delete-button" 
               onclick="return confirm('Are you sure you want to delete this category? This action cannot be undone.')">
                Delete Category
            </a>
        </div>
    </div>
</body>
</html> 