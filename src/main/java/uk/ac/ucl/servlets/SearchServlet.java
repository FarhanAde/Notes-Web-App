package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NotesModel;
import uk.ac.ucl.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        if (query != null && !query.trim().isEmpty()) {
            NotesModel model = NotesModel.getInstance();
            List<Note> matchingNotes = model.searchNotes(query);
            
            // Convert notes to search results with context
            List<SearchResult> searchResults = new ArrayList<>();
            for (Note note : matchingNotes) {
                String matchContext = note.getMatchContext(query);
                searchResults.add(new SearchResult(note, matchContext));
            }
            
            request.setAttribute("searchResults", searchResults);
        }
        
        request.getRequestDispatcher("/jsp/searchResults.jsp").forward(request, response);
    }
} 