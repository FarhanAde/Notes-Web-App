package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Category;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NotesModel;

import java.io.IOException;
import java.util.List;

public class ViewCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryId = request.getParameter("id");
        if (categoryId != null) {
            NotesModel model = NotesModel.getInstance();
            Category category = model.getCategoryById(categoryId);
            
            if (category != null) {
                List<Note> notes = model.getNotesInCategory(categoryId);
                request.setAttribute("category", category);
                request.setAttribute("notes", notes);
                request.getRequestDispatcher("/jsp/viewCategory.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/index");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
} 