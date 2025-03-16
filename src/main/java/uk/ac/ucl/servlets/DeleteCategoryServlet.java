package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.NotesModel;

import java.io.IOException;

public class DeleteCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryId = request.getParameter("id");
        if (categoryId != null) {
            NotesModel model = NotesModel.getInstance();
            model.deleteCategory(categoryId);
        }
        response.sendRedirect(request.getContextPath() + "/index");
    }
} 