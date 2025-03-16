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

public class ManageNoteCategoriesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String noteId = request.getParameter("id");
        if (noteId != null) {
            NotesModel model = NotesModel.getInstance();
            Note note = model.getNoteById(noteId);
            List<Category> categories = model.getAllCategories().stream().toList();
            
            if (note != null) {
                request.setAttribute("note", note);
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/jsp/manageNoteCategories.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/index");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String noteId = request.getParameter("id");
        String[] categoryIds = request.getParameterValues("categoryIds");
        
        if (noteId != null) {
            NotesModel model = NotesModel.getInstance();
            Note note = model.getNoteById(noteId);
            
            if (note != null) {
                model.updateNoteCategories(note, categoryIds);
                response.sendRedirect(request.getContextPath() + "/viewNote?id=" + noteId);
            } else {
                response.sendRedirect(request.getContextPath() + "/index");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
} 