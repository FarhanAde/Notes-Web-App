package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.Category;
import uk.ac.ucl.model.NotesModel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String noteId = request.getParameter("id");
        if (noteId != null) {
            NotesModel model = NotesModel.getInstance();
            Note note = model.getNoteById(noteId);
            
            if (note != null) {
                // Get categories that contain this note
                List<Category> categories = model.getAllCategories().stream()
                    .filter(category -> category.getNoteIds().contains(noteId))
                    .collect(Collectors.toList());
                
                request.setAttribute("note", note);
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/jsp/viewNote.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/index");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
} 