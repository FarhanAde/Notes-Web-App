<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.SearchResult" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results - Notes Application</title>
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
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .search-container {
            flex: 1;
            max-width: 500px;
            margin-left: 20px;
        }
        .search-form {
            display: flex;
            gap: 10px;
        }
        .search-form input[type="text"] {
            flex: 1;
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .search-form button {
            padding: 8px 16px;
            background-color: #4285f4;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .search-form button:hover {
            background-color: #357abd;
        }
        h1 {
            color: #333;
            margin: 0;
        }
        .results-info {
            margin: 20px 0;
            color: #666;
            font-size: 16px;
        }
        .list {
            list-style: none;
            padding: 0;
        }
        .list-item {
            padding: 15px;
            border-bottom: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .list-item:last-child {
            border-bottom: none;
        }
        .note-content {
            flex: 1;
        }
        .note-title {
            font-size: 18px;
            color: #1a0dab;
            margin-bottom: 5px;
        }
        .note-title a {
            color: #1a0dab;
            text-decoration: none;
        }
        .note-title a:hover {
            text-decoration: underline;
        }
        .note-summary {
            color: #4d5156;
            font-size: 14px;
            margin-bottom: 5px;
        }
        .match-context {
            color: #666;
            font-size: 13px;
            font-style: italic;
        }
        .highlight {
            background-color: #fff2a8;
            padding: 2px;
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
        .button.view {
            background-color: #2196F3;
        }
        .button.view:hover {
            background-color: #1976D2;
        }
        .no-results {
            text-align: center;
            padding: 40px;
            color: #666;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Search Results</h1>
            <div class="search-container">
                <form action="search" method="GET" class="search-form">
                    <input type="text" name="query" value="${param.query}" placeholder="Search notes..." required>
                    <button type="submit" class="button">Search</button>
                </form>
            </div>
        </div>
        
        <% 
        List<SearchResult> searchResults = (List<SearchResult>) request.getAttribute("searchResults");
        String query = request.getParameter("query");
        
        if (searchResults != null && !searchResults.isEmpty()) {
        %>
            <div class="results-info">
                Found <%= searchResults.size() %> results for "<%= query %>"
            </div>
            
            <ul class="list">
                <% for (SearchResult result : searchResults) { %>
                    <li class="list-item">
                        <div class="note-content">
                            <div class="note-title">
                                <a href="viewNote?id=<%= result.getNote().getId() %>">
                                    <%= result.getNote().getName() %>
                                </a>
                            </div>
                            <div class="note-summary">
                                <%= result.getNote().getSummary() %>
                            </div>
                            <% if (result.getMatchContext() != null && !result.getMatchContext().isEmpty()) { %>
                                <div class="match-context">
                                    <%= result.getMatchContext().replace(query, "<span class='highlight'>" + query + "</span>") %>
                                </div>
                            <% } %>
                        </div>
                        <div class="actions">
                            <a href="viewNote?id=<%= result.getNote().getId() %>" class="button view">View</a>
                        </div>
                    </li>
                <% } %>
            </ul>
        <% } else if (query != null && !query.isEmpty()) { %>
            <div class="no-results">
                No results found for "<%= query %>". Try a different search term.
            </div>
        <% } %>
    </div>
</body>
</html> 