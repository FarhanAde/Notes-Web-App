<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.Category" %>

<!DOCTYPE html>
<html>
<head>
    <title>Notes Application</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .section {
            margin-bottom: 30px;
        }
        .section h2 {
            color: #666;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        }
        .list {
            list-style: none;
            padding: 0;
        }
        .list-item {
            padding: 10px;
            border-bottom: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .list-item:last-child {
            border-bottom: none;
        }
        .actions {
            display: flex;
            gap: 10px;
        }
        .button {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            color: white;
            background-color: #4CAF50;
        }
        .button:hover {
            background-color: #45a049;
        }
        .button.delete {
            background-color: #f44336;
        }
        .button.delete:hover {
            background-color: #da190b;
        }
        .button.edit {
            background-color: #2196F3;
        }
        .button.edit:hover {
            background-color: #1976D2;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Notes Application</h1>
        
        <div class="section">
            <h2>Notes</h2>
            <a href="createNote" class="button">Create New Note</a>
            <ul class="list">
                <% List<Note> notes = (List<Note>) request.getAttribute("notes");
                   if (notes != null && !notes.isEmpty()) {
                       for (Note note : notes) { %>
                    <li class="list-item">
                        <span><%= note.getName() %></span>
                        <div class="actions">
                            <a href="viewNote?id=<%= note.getId() %>" class="button">View</a>
                            <a href="editNote?id=<%= note.getId() %>" class="button edit">Edit</a>
                            <a href="deleteNote?id=<%= note.getId() %>" class="button delete" onclick="return confirm('Are you sure you want to delete this note?')">Delete</a>
                        </div>
                    </li>
                <% }
                   } else { %>
                    <li class="list-item">No notes available</li>
                <% } %>
            </ul>
        </div>

        <div class="section">
            <h2>Categories</h2>
            <a href="category" class="button">Create New Category</a>
            <ul class="list">
                <% List<Category> categories = (List<Category>) request.getAttribute("categories");
                   if (categories != null && !categories.isEmpty()) {
                       for (Category category : categories) { %>
                    <li class="list-item">
                        <span><%= category.getName() %></span>
                        <div class="actions">
                            <a href="viewCategory?id=<%= category.getId() %>" class="button">View</a>
                            <a href="deleteCategory?id=<%= category.getId() %>" class="button delete" onclick="return confirm('Are you sure you want to delete this category?')">Delete</a>
                        </div>
                    </li>
                <% }
                   } else { %>
                    <li class="list-item">No categories available</li>
                <% } %>
            </ul>
        </div>
    </div>
</body>
</html> 