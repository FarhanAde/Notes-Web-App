package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.Category;
import uk.ac.ucl.model.NotesModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NotesModel model = NotesModel.getInstance();
        Collection<Note> notes = model.getAllNotes();
        Collection<Category> categories = model.getAllCategories();

        request.setAttribute("notes", new ArrayList<>(notes));
        request.setAttribute("categories", new ArrayList<>(categories));
        request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
    }
} 