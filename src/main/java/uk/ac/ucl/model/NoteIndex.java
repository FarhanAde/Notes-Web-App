package uk.ac.ucl.model;

import java.time.LocalDateTime;
import java.util.*;

public class NoteIndex {
    private String id;
    private String name;
    private Map<String, Note> notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public NoteIndex() {
        this.id = UUID.randomUUID().toString();
        this.notes = new LinkedHashMap<>();  // Preserves insertion order
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public NoteIndex(String name) {
        this();
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void addNote(Note note) {
        notes.put(note.getId(), note);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void removeNote(String noteId) {
        notes.remove(noteId);
        this.updatedAt = LocalDateTime.now();
    }
    
    public Note getNote(String noteId) {
        return notes.get(noteId);
    }
    
    public Collection<Note> getAllNotes() {
        return notes.values();
    }
    
    public int getNoteCount() {
        return notes.size();
    }
    
    // Get notes sorted by name
    public List<Note> getNotesSortedByName() {
        List<Note> noteList = new ArrayList<>(notes.values());
        noteList.sort(Comparator.comparing(Note::getName));
        return noteList;
    }
    
    // Get notes sorted by creation date
    public List<Note> getNotesSortedByCreationDate() {
        List<Note> noteList = new ArrayList<>(notes.values());
        noteList.sort(Comparator.comparing(Note::getCreatedAt));
        return noteList;
    }
    
    // Get notes sorted by modification date
    public List<Note> getNotesSortedByModificationDate() {
        List<Note> noteList = new ArrayList<>(notes.values());
        noteList.sort(Comparator.comparing(Note::getUpdatedAt).reversed());  // Latest first
        return noteList;
    }
    
    // Search notes by text
    public List<Note> searchNotes(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Note> results = new ArrayList<>();
        for (Note note : notes.values()) {
            if (note.containsText(searchText)) {
                results.add(note);
            }
        }
        return results;
    }
    
    // Check if index name contains search text
    public boolean containsText(String searchText) {
        return name.toLowerCase().contains(searchText.toLowerCase());
    }
}