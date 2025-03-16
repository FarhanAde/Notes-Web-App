package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Category;
import uk.ac.ucl.model.NotesModel;

import java.io.IOException;

public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the category creation form
        request.getRequestDispatcher("/jsp/createCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name != null && !name.trim().isEmpty()) {
            NotesModel model = NotesModel.getInstance();
            Category category = model.createCategory(name.trim());
            // Redirect to the index page after creating the category
            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            // If name is empty, go back to the form with an error message
            request.setAttribute("error", "Category name cannot be empty");
            request.getRequestDispatcher("/jsp/createCategory.jsp").forward(request, response);
        }
    }
} 