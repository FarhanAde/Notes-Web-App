package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NotesModel;
import uk.ac.ucl.model.TextNote;
import uk.ac.ucl.model.UrlNote;
import uk.ac.ucl.model.ImageNote;
import uk.ac.ucl.model.Category;

import java.io.IOException;
import java.util.Collection;

public class EditNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String noteId = request.getParameter("id");
        System.out.println("EditNoteServlet.doGet - noteId: " + noteId);
        
        if (noteId != null) {
            NotesModel model = NotesModel.getInstance();
            Note note = model.getNoteById(noteId);
            
            if (note != null) {
                System.out.println("Found note: " + note.getName() + " of type: " + note.getClass().getSimpleName());
                request.setAttribute("note", note);
                Collection<Category> categories = model.getAllCategories();
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/jsp/createEditNote.jsp").forward(request, response);
            } else {
                System.out.println("Note not found with ID: " + noteId);
                response.sendRedirect(request.getContextPath() + "/index");
            }
        } else {
            System.out.println("No note ID provided");
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String noteId = request.getParameter("id");
        String name = request.getParameter("name");
        String text = request.getParameter("text");
        String[] categoryIds = request.getParameterValues("categories");
        
        System.out.println("EditNoteServlet.doPost - noteId: " + noteId + ", name: " + name);

        if (noteId != null && name != null && !name.trim().isEmpty()) {
            NotesModel model = NotesModel.getInstance();
            Note note = model.getNoteById(noteId);

            if (note != null) {
                System.out.println("Found note to update: " + note.getName());
                note.setName(name.trim());

                if (note instanceof TextNote && text != null) {
                    System.out.println("Updating text note with content: " + text);
                    ((TextNote) note).setText(text);
                }

                try {
                    model.updateNote(note);
                    
                    // Update note categories if any were selected
                    if (categoryIds != null && categoryIds.length > 0) {
                        model.updateNoteCategories(note, categoryIds);
                    }
                    
                    System.out.println("Note updated successfully");
                    response.sendRedirect(request.getContextPath() + "/viewNote?id=" + noteId);
                } catch (Exception e) {
                    System.err.println("Error updating note: " + e.getMessage());
                    e.printStackTrace();
                    request.setAttribute("error", "Error updating note: " + e.getMessage());
                    request.setAttribute("note", note);
                    Collection<Category> categories = model.getAllCategories();
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("/jsp/createEditNote.jsp").forward(request, response);
                }
            } else {
                System.out.println("Note not found with ID: " + noteId);
                response.sendRedirect(request.getContextPath() + "/index");
            }
        } else {
            System.out.println("Invalid request parameters - noteId: " + noteId + ", name: " + name);
            request.setAttribute("error", "Note name is required");
            if (noteId != null) {
                NotesModel model = NotesModel.getInstance();
                Note note = model.getNoteById(noteId);
                if (note != null) {
                    request.setAttribute("note", note);
                    Collection<Category> categories = model.getAllCategories();
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("/jsp/createEditNote.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/index");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/index");
            }
        }
    }
} 