<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.TextNote" %>
<%@ page import="uk.ac.ucl.model.UrlNote" %>
<%@ page import="uk.ac.ucl.model.ImageNote" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Category" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Note</title>
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
        .note-info {
            color: #666;
            font-size: 0.9em;
            margin-bottom: 20px;
        }
        .note-content {
            margin-top: 20px;
            line-height: 1.6;
        }
        .note-image {
            max-width: 100%;
            margin-top: 20px;
        }
        .actions {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
        .actions a {
            display: inline-block;
            margin-right: 10px;
            padding: 8px 16px;
            background-color: #4285f4;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .actions .delete {
            background-color: #dc3545;
        }
        .categories {
            margin-top: 20px;
            color: #666;
        }
        .categories span {
            display: inline-block;
            margin-right: 10px;
            padding: 4px 8px;
            background-color: #f1f1f1;
            border-radius: 4px;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <div class="container">
        <% 
        Note note = (Note) request.getAttribute("note");
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        
        if (note != null) {
        %>
            <h1><%= note.getName() %></h1>
            
            <div class="note-info">
                Created: <%= note.getCreatedAt() %>
                <% String updatedAt = note.getUpdatedAt(); %>
                <% if (!updatedAt.equals("N/A") && !updatedAt.equals(note.getCreatedAt())) { %>
                    | Last modified: <%= updatedAt %>
                <% } %>
            </div>
            
            <% if (categories != null && !categories.isEmpty()) { %>
                <div class="categories">
                    Categories: 
                    <% for (Category category : categories) { %>
                        <span><a href="viewCategory?id=<%= category.getId() %>"><%= category.getName() %></a></span>
                    <% } %>
                </div>
            <% } %>
            
            <div class="note-content">
                <% if (note instanceof TextNote) { %>
                    <%= ((TextNote) note).getText() %>
                <% } else if (note instanceof UrlNote) { %>
                    <p><strong>URL:</strong> <a href="<%= ((UrlNote) note).getUrl() %>" target="_blank"><%= ((UrlNote) note).getUrl() %></a></p>
                    <p><strong>Description:</strong> <%= ((UrlNote) note).getDescription() %></p>
                <% } else if (note instanceof ImageNote) { %>
                    <img src="<%= ((ImageNote) note).getImageFilePath() %>" alt="<%= ((ImageNote) note).getCaption() %>" class="note-image">
                    <p><strong>Caption:</strong> <%= ((ImageNote) note).getCaption() %></p>
                <% } %>
            </div>
            
            <div class="actions">
                <a href="editNote?id=<%= note.getId() %>">Edit Note</a>
                <a href="manageNoteCategories?id=<%= note.getId() %>">Manage Categories</a>
                <a href="deleteNote?id=<%= note.getId() %>" class="delete" onclick="return confirm('Are you sure you want to delete this note?')">Delete Note</a>
                <a href="index">Back to Index</a>
            </div>
        <% } else { %>
            <p>Note not found.</p>
            <div class="actions">
                <a href="index">Back to Index</a>
            </div>
        <% } %>
    </div>
</body>
</html> 