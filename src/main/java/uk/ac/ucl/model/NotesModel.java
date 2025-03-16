package uk.ac.ucl.model;

import java.util.*;
import java.util.stream.Collectors;

public class NotesModel {
    private static volatile NotesModel instance;
    private static final Object lock = new Object();
    
    private final Map<String, NoteIndex> indices;
    private final Map<String, Category> categories;
    private final Map<String, Note> allNotes;
    
    private NotesModel() {
        // Initialize maps
        allNotes = new HashMap<>();
        indices = new HashMap<>();
        categories = new HashMap<>();
        
        // Load saved data
        loadData();
    }
    
    public static NotesModel getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new NotesModel();
                }
            }
        }
        return instance;
    }
    
    private synchronized void loadData() {
        // Load data from storage
        allNotes.clear();
        indices.clear();
        categories.clear();
        allNotes.putAll(FileStorage.loadAllNotes());
        indices.putAll(FileStorage.loadAllIndices());
        categories.putAll(FileStorage.loadAllCategories());
    }
    
    private synchronized void saveData() {
        // Save all data to storage
        FileStorage.saveAllData(allNotes, indices, categories);
    }
    
    // Note operations
    public synchronized Note getNoteById(String noteId) {
        return allNotes.get(noteId);
    }
    
    public synchronized Collection<Note> getAllNotes() {
        return new ArrayList<>(allNotes.values());
    }
    
    public synchronized void addNote(Note note, String indexId) {
        allNotes.put(note.getId(), note);
        
        NoteIndex index = indices.get(indexId);
        if (index != null) {
            index.addNote(note);
        }
        
        FileStorage.saveNote(note);
        if (index != null) {
            FileStorage.saveIndex(index);
        }
    }
    
    public synchronized void updateNote(Note note) {
        if (note == null) {
            System.err.println("Error: Attempted to update null note");
            throw new IllegalArgumentException("Note cannot be null");
        }
        System.out.println("Attempting to update note: " + note.getId());
        System.out.println("Note details - Name: " + note.getName() + ", Type: " + note.getClass().getSimpleName());
        
        if (allNotes.containsKey(note.getId())) {
            System.out.println("Found existing note in allNotes map");
            note.updateModificationTime();
            System.out.println("Updated modification time to: " + note.getUpdatedAt());
            
            allNotes.put(note.getId(), note);
            System.out.println("Updated note in memory");
            
            try {
                FileStorage.saveNote(note);
                System.out.println("Saved note to disk successfully");
            } catch (Exception e) {
                System.err.println("Error saving note to disk: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to save note: " + e.getMessage(), e);
            }
            
            System.out.println("Note update completed successfully: " + note.getId() + ", name: " + note.getName());
        } else {
            System.err.println("Error: Note not found in allNotes map: " + note.getId());
            throw new IllegalArgumentException("Note not found: " + note.getId());
        }
    }
    
    public synchronized void deleteNote(String noteId) {
        if (allNotes.containsKey(noteId)) {
            allNotes.remove(noteId);
            
            // Remove from all indices
            for (NoteIndex index : indices.values()) {
                index.removeNote(noteId);
                FileStorage.saveIndex(index);
            }
            
            // Remove from all categories
            for (Category category : categories.values()) {
                category.removeNote(noteId);
                FileStorage.saveCategory(category);
            }
            
            FileStorage.deleteNote(noteId);
        }
    }
    
    // Index operations
    public synchronized NoteIndex getIndexById(String indexId) {
        return indices.get(indexId);
    }
    
    public synchronized Collection<NoteIndex> getAllIndices() {
        return new ArrayList<>(indices.values());
    }
    
    public synchronized void addIndex(NoteIndex index) {
        indices.put(index.getId(), index);
        FileStorage.saveIndex(index);
    }
    
    public synchronized void updateIndex(NoteIndex index) {
        if (indices.containsKey(index.getId())) {
            indices.put(index.getId(), index);
            FileStorage.saveIndex(index);
        }
    }
    
    public synchronized void deleteIndex(String indexId) {
        if (indices.containsKey(indexId)) {
            indices.remove(indexId);
            FileStorage.deleteIndex(indexId);
        }
    }
    
    // Category operations
    public synchronized Category getCategoryById(String categoryId) {
        return categories.get(categoryId);
    }
    
    public synchronized Collection<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }
    
    public synchronized void addCategory(Category category) {
        categories.put(category.getId(), category);
        FileStorage.saveCategory(category);
    }
    
    public synchronized void updateCategory(Category category) {
        if (categories.containsKey(category.getId())) {
            categories.put(category.getId(), category);
            FileStorage.saveCategory(category);
        }
    }
    
    public synchronized void deleteCategory(String categoryId) {
        if (categories.containsKey(categoryId)) {
            categories.remove(categoryId);
            FileStorage.deleteCategory(categoryId);
        }
    }
    
    public synchronized void addNoteToCategory(String noteId, String categoryId) {
        Category category = categories.get(categoryId);
        if (category != null && allNotes.containsKey(noteId)) {
            category.addNote(noteId);
            FileStorage.saveCategory(category);
        }
    }
    
    public synchronized void removeNoteFromCategory(String noteId, String categoryId) {
        Category category = categories.get(categoryId);
        if (category != null) {
            category.removeNote(noteId);
            FileStorage.saveCategory(category);
        }
    }
    
    // Search operations
    public synchronized List<Note> searchNotes(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return new ArrayList<>();
        }
        
        return allNotes.values().stream()
                .filter(note -> note.containsText(searchText))
                .collect(Collectors.toList());
    }
    
    public synchronized List<NoteIndex> searchIndices(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return new ArrayList<>();
        }
        
        return indices.values().stream()
                .filter(index -> index.containsText(searchText))
                .collect(Collectors.toList());
    }
    
    public synchronized List<Category> searchCategories(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return new ArrayList<>();
        }
        
        return categories.values().stream()
                .filter(category -> category.containsText(searchText))
                .collect(Collectors.toList());
    }
    
    // Create a new note factory methods
    public synchronized TextNote createTextNote(String name, String text, String indexId) {
        TextNote note = new TextNote(name, text);
        addNote(note, indexId);
        return note;
    }
    
    public synchronized UrlNote createUrlNote(String name, String url, String description, String indexId) {
        UrlNote note = new UrlNote(name, url, description);
        addNote(note, indexId);
        return note;
    }
    
    public synchronized ImageNote createImageNote(String name, String imageFilePath, String caption, String indexId) {
        ImageNote note = new ImageNote(name, imageFilePath, caption);
        addNote(note, indexId);
        return note;
    }
    
    // Get notes in a category
    public synchronized List<Note> getNotesInCategory(String categoryId) {
        Category category = categories.get(categoryId);
        if (category == null) {
            return new ArrayList<>();
        }
        
        List<Note> notes = new ArrayList<>();
        for (String noteId : category.getNoteIds()) {
            Note note = allNotes.get(noteId);
            if (note != null) {
                notes.add(note);
            }
        }
        return notes;
    }
    
    // Add note to categories
    public synchronized void updateNoteCategories(Note note, String[] categoryIds) {
        if (note == null) return;
        
        // Remove note from all categories first
        for (Category category : categories.values()) {
            category.removeNote(note.getId());
        }
        
        // Add note to selected categories
        if (categoryIds != null) {
            for (String categoryId : categoryIds) {
                addNoteToCategory(note.getId(), categoryId);
            }
        }
        
        // Save all affected categories
        for (String categoryId : categoryIds) {
            Category category = categories.get(categoryId);
            if (category != null) {
                FileStorage.saveCategory(category);
            }
        }
    }
    
    // Create a new category
    public Category createCategory(String name) {
        Category category = new Category(name);
        addCategory(category);
        return category;
    }
    
    // Get default index
    public NoteIndex getDefaultIndex() {
        // If there's no index, create a default one
        if (indices.isEmpty()) {
            NoteIndex defaultIndex = new NoteIndex("Default");
            addIndex(defaultIndex);
            return defaultIndex;
        }
        
        // Return the first index (likely the default one)
        return indices.values().iterator().next();
    }
}