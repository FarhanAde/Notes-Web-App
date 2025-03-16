package uk.ac.ucl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class Note {
    protected String id;
    protected String name;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    
    public Note() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Note(String name) {
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
    
    public String getCreatedAt() {
        if (createdAt == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt.format(formatter);
    }
    
    public String getUpdatedAt() {
        if (updatedAt == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return updatedAt.format(formatter);
    }
    
    public void updateModificationTime() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public abstract String getSummary();
    
    public abstract String getFullContent();
    
    public abstract boolean containsText(String searchText);
    
    public String getMatchContext(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return "";
        }
        
        String content = getFullContent();
        if (content == null || content.isEmpty()) {
            return "";
        }
        
        int matchIndex = content.toLowerCase().indexOf(searchText.toLowerCase());
        if (matchIndex == -1) {
            return "";
        }
        
        // Get 50 characters before and after the match
        int start = Math.max(0, matchIndex - 50);
        int end = Math.min(content.length(), matchIndex + searchText.length() + 50);
        
        String context = content.substring(start, end);
        if (start > 0) {
            context = "..." + context;
        }
        if (end < content.length()) {
            context = context + "...";
        }
        
        return context;
    }
}