<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.SearchResult" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results</title>
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
        .search-form {
            margin-bottom: 20px;
        }
        .search-form input[type="text"] {
            padding: 8px;
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-form button {
            padding: 8px 16px;
            background-color: #4285f4;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .results-info {
            margin-bottom: 20px;
            color: #666;
        }
        .results-list {
            list-style-type: none;
            padding: 0;
        }
        .result-item {
            padding: 15px;
            margin-bottom: 15px;
            border: 1px solid #eee;
            border-radius: 4px;
        }
        .result-title {
            font-size: 1.2em;
            margin-bottom: 5px;
        }
        .result-title a {
            color: #4285f4;
            text-decoration: none;
        }
        .result-title a:hover {
            text-decoration: underline;
        }
        .result-match {
            margin-top: 10px;
            font-size: 0.9em;
            color: #333;
        }
        .highlight {
            background-color: #fff2a8;
            padding: 2px;
        }
        .actions {
            margin-top: 20px;
        }
        .actions a {
            display: inline-block;
            padding: 8px 16px;
            background-color: #4285f4;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Search Results</h1>
        
        <div class="search-form">
            <form action="search" method="GET">
                <input type="text" name="query" value="${param.query}" placeholder="Search notes..." required>
                <button type="submit">Search</button>
            </form>
        </div>
        
        <% 
        List<SearchResult> searchResults = (List<SearchResult>) request.getAttribute("searchResults");
        String query = request.getParameter("query");
        
        if (searchResults != null && !searchResults.isEmpty()) {
        %>
            <div class="results-info">
                Found <%= searchResults.size() %> results for "<%= query %>"
            </div>
            
            <ul class="results-list">
                <% for (SearchResult result : searchResults) { %>
                    <li class="result-item">
                        <div class="result-title">
                            <a href="viewNote?id=<%= result.getNote().getId() %>"><%= result.getNote().getName() %></a>
                        </div>
                        <div>
                            <span><%= result.getNote().getSummary() %></span>
                        </div>
                        <% if (result.getMatchContext() != null && !result.getMatchContext().isEmpty()) { %>
                            <div class="result-match">
                                <%= result.getMatchContext().replace(query, "<span class='highlight'>" + query + "</span>") %>
                            </div>
                        <% } %>
                    </li>
                <% } %>
            </ul>
        <% } else { %>
            <div class="results-info">
                No results found for "<%= query %>". Try a different search term.
            </div>
        <% } %>
        
        <div class="actions">
            <a href="index">Back to Index</a>
        </div>
    </div>
</body>
</html> 