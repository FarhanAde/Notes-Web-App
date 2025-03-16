package uk.ac.ucl.model;

import java.time.LocalDateTime;
import java.util.*;

public class Category {
    private String id;
    private String name;
    private Set<String> noteIds;  // References to notes in this category
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Category() {
        this.id = UUID.randomUUID().toString();
        this.noteIds = new HashSet<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Category(String name) {
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
    
    public void addNote(String noteId) {
        noteIds.add(noteId);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void removeNote(String noteId) {
        noteIds.remove(noteId);
        this.updatedAt = LocalDateTime.now();
    }
    
    public Set<String> getNoteIds() {
        return Collections.unmodifiableSet(noteIds);
    }
    
    public boolean containsNote(String noteId) {
        return noteIds.contains(noteId);
    }
    
    public int getNoteCount() {
        return noteIds.size();
    }
    
    public boolean containsText(String searchText) {
        return name.toLowerCase().contains(searchText.toLowerCase());
    }
}