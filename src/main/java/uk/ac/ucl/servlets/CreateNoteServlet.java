package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Category;
import uk.ac.ucl.model.NotesModel;
import uk.ac.ucl.model.TextNote;

import java.io.IOException;
import java.util.Collection;

public class CreateNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get all categories to display in the form
        NotesModel model = NotesModel.getInstance();
        Collection<Category> categories = model.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/jsp/createNote.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String[] categoryIds = request.getParameterValues("categories");

        if (name != null && !name.trim().isEmpty() && content != null) {
            NotesModel model = NotesModel.getInstance();
            // Create the note in the default index
            TextNote note = model.createTextNote(name.trim(), content, model.getDefaultIndex().getId());
            
            // Update note categories if any were selected
            if (categoryIds != null && categoryIds.length > 0) {
                model.updateNoteCategories(note, categoryIds);
            }
            
            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            request.setAttribute("error", "Note name and content are required");
            request.setAttribute("categories", NotesModel.getInstance().getAllCategories());
            request.getRequestDispatcher("/jsp/createNote.jsp").forward(request, response);
        }
    }
} 