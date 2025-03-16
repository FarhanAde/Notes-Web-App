package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.NotesModel;

import java.io.IOException;

public class DeleteNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String noteId = request.getParameter("id");
        System.out.println("DeleteNoteServlet.doGet - noteId: " + noteId);
        
        if (noteId != null) {
            try {
                NotesModel model = NotesModel.getInstance();
                model.deleteNote(noteId);
                System.out.println("Note deleted successfully");
                response.sendRedirect(request.getContextPath() + "/index");
            } catch (Exception e) {
                System.err.println("Error deleting note: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting note: " + e.getMessage());
            }
        } else {
            System.out.println("No note ID provided");
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
} 