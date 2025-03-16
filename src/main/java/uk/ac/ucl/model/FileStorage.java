package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletContext;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileStorage {
    private static final String DATA_DIR = "data";
    private static final String NOTES_DIR = DATA_DIR + "/notes";
    private static final String INDICES_DIR = DATA_DIR + "/indices";
    private static final String CATEGORIES_DIR = DATA_DIR + "/categories";
    
    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build();
    
    private static String basePath;
    
    public static void initialize(ServletContext context) {
        try {
            // Use the user's home directory for data storage
            String userHome = System.getProperty("user.home");
            if (userHome == null || userHome.trim().isEmpty()) {
                throw new IllegalStateException("Could not determine user home directory");
            }
            
            basePath = Paths.get(userHome, "notes-app-data").toString();
            System.out.println("Initializing FileStorage with basePath: " + basePath);
            
            // Create base directory with full permissions
            Path baseDir = Paths.get(basePath);
            if (!Files.exists(baseDir)) {
                Files.createDirectories(baseDir);
                System.out.println("Created base directory: " + baseDir);
            }
            
            // Create subdirectories
            Path notesPath = Paths.get(basePath, NOTES_DIR);
            Path indicesPath = Paths.get(basePath, INDICES_DIR);
            Path categoriesPath = Paths.get(basePath, CATEGORIES_DIR);
            
            System.out.println("Creating directories:");
            System.out.println("Notes directory: " + notesPath);
            System.out.println("Indices directory: " + indicesPath);
            System.out.println("Categories directory: " + categoriesPath);
            
            Files.createDirectories(notesPath);
            Files.createDirectories(indicesPath);
            Files.createDirectories(categoriesPath);
            
            // Test write permissions
            Path testFile = notesPath.resolve("test.txt");
            Files.writeString(testFile, "test");
            Files.delete(testFile);
            
            System.out.println("Directories created successfully and write permissions verified");
        } catch (IOException e) {
            String errorMsg = "Error initializing FileStorage: " + e.getMessage();
            System.err.println(errorMsg);
            e.printStackTrace();
            throw new RuntimeException(errorMsg, e);
        }
    }
    
    // Save a note to file
    public static void saveNote(Note note) {
        if (note == null) {
            System.err.println("Error: Attempted to save null note");
            throw new IllegalArgumentException("Note cannot be null");
        }
        if (note.getId() == null || note.getId().trim().isEmpty()) {
            System.err.println("Error: Note has null or empty ID");
            throw new IllegalArgumentException("Note ID cannot be null or empty");
        }
        
        try {
            // Ensure base directory exists
            Path baseDir = Paths.get(basePath);
            if (!Files.exists(baseDir)) {
                System.out.println("Creating base directory: " + baseDir);
                Files.createDirectories(baseDir);
            }
            
            // Ensure notes directory exists
            Path notesDir = Paths.get(basePath, NOTES_DIR);
            if (!Files.exists(notesDir)) {
                System.out.println("Creating notes directory: " + notesDir);
                Files.createDirectories(notesDir);
            }
            
            String filename = Paths.get(basePath, NOTES_DIR, note.getId() + ".json").toString();
            System.out.println("Saving note to: " + filename);
            
            // Convert note to JSON string first to verify it can be serialized
            String jsonContent = mapper.writeValueAsString(note);
            System.out.println("Note serialized to JSON: " + jsonContent);
            
            // Write to a temporary file first
            Path tempFile = Paths.get(filename + ".tmp");
            System.out.println("Writing to temporary file: " + tempFile);
            mapper.writeValue(tempFile.toFile(), note);
            
            // Rename temp file to actual file
            Path targetFile = Paths.get(filename);
            System.out.println("Moving temporary file to target: " + targetFile);
            Files.move(tempFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            
            // Verify the file was saved
            if (Files.exists(targetFile)) {
                String savedContent = Files.readString(targetFile);
                System.out.println("Note saved successfully. File content: " + savedContent);
            } else {
                throw new IOException("File was not saved: " + filename);
            }
        } catch (IOException e) {
            System.err.println("Error saving note: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save note: " + e.getMessage(), e);
        }
    }
    
    // Save an index to file
    public static void saveIndex(NoteIndex index) {
        try {
            String filename = Paths.get(basePath, INDICES_DIR, index.getId() + ".json").toString();
            System.out.println("Saving index to: " + filename);
            mapper.writeValue(new File(filename), index);
            System.out.println("Index saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving index: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save index: " + e.getMessage(), e);
        }
    }
    
    // Save a category to file
    public static void saveCategory(Category category) {
        try {
            String filename = Paths.get(basePath, CATEGORIES_DIR, category.getId() + ".json").toString();
            System.out.println("Saving category to: " + filename);
            mapper.writeValue(new File(filename), category);
            System.out.println("Category saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving category: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save category: " + e.getMessage(), e);
        }
    }
    
    // Load all notes from files
    public static Map<String, Note> loadAllNotes() {
        Map<String, Note> notes = new HashMap<>();
        
        try {
            Path notesPath = Paths.get(basePath, NOTES_DIR);
            if (Files.exists(notesPath)) {
                Files.list(notesPath)
                     .filter(path -> path.toString().endsWith(".json"))
                     .forEach(path -> {
                         try {
                             // Need to determine the note type
                             String json = Files.readString(path);
                             // Check note type from JSON
                             if (json.contains("\"text\":")) {
                                 TextNote note = mapper.readValue(path.toFile(), TextNote.class);
                                 notes.put(note.getId(), note);
                             } else if (json.contains("\"url\":")) {
                                 UrlNote note = mapper.readValue(path.toFile(), UrlNote.class);
                                 notes.put(note.getId(), note);
                             } else if (json.contains("\"imageFilePath\":")) {
                                 ImageNote note = mapper.readValue(path.toFile(), ImageNote.class);
                                 notes.put(note.getId(), note);
                             }
                         } catch (IOException e) {
                             System.err.println("Error loading note from " + path + ": " + e.getMessage());
                             e.printStackTrace();
                         }
                     });
            }
        } catch (IOException e) {
            System.err.println("Error loading notes directory: " + e.getMessage());
            e.printStackTrace();
        }
        
        return notes;
    }
    
    // Load all indices from files
    public static Map<String, NoteIndex> loadAllIndices() {
        Map<String, NoteIndex> indices = new HashMap<>();
        
        try {
            Path indicesPath = Paths.get(basePath, INDICES_DIR);
            if (Files.exists(indicesPath)) {
                Files.list(indicesPath)
                     .filter(path -> path.toString().endsWith(".json"))
                     .forEach(path -> {
                         try {
                             NoteIndex index = mapper.readValue(path.toFile(), NoteIndex.class);
                             indices.put(index.getId(), index);
                         } catch (IOException e) {
                             System.err.println("Error loading index from " + path + ": " + e.getMessage());
                             e.printStackTrace();
                         }
                     });
            }
        } catch (IOException e) {
            System.err.println("Error loading indices directory: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Create default index if none exists
        if (indices.isEmpty()) {
            NoteIndex defaultIndex = new NoteIndex("Default");
            indices.put(defaultIndex.getId(), defaultIndex);
            saveIndex(defaultIndex);
        }
        
        return indices;
    }
    
    // Load all categories from files
    public static Map<String, Category> loadAllCategories() {
        Map<String, Category> categories = new HashMap<>();
        
        try {
            Path categoriesPath = Paths.get(basePath, CATEGORIES_DIR);
            if (Files.exists(categoriesPath)) {
                Files.list(categoriesPath)
                     .filter(path -> path.toString().endsWith(".json"))
                     .forEach(path -> {
                         try {
                             Category category = mapper.readValue(path.toFile(), Category.class);
                             categories.put(category.getId(), category);
                         } catch (IOException e) {
                             System.err.println("Error loading category from " + path + ": " + e.getMessage());
                             e.printStackTrace();
                         }
                     });
            }
        } catch (IOException e) {
            System.err.println("Error loading categories directory: " + e.getMessage());
            e.printStackTrace();
        }
        
        return categories;
    }
    
    // Save all data
    public static void saveAllData(Map<String, Note> notes, Map<String, NoteIndex> indices, Map<String, Category> categories) {
        // Save all notes
        for (Note note : notes.values()) {
            saveNote(note);
        }
        
        // Save all indices
        for (NoteIndex index : indices.values()) {
            saveIndex(index);
        }
        
        // Save all categories
        for (Category category : categories.values()) {
            saveCategory(category);
        }
    }
    
    // Delete a note file
    public static void deleteNote(String noteId) {
        if (noteId == null || noteId.trim().isEmpty()) {
            throw new IllegalArgumentException("Note ID cannot be null or empty");
        }
        
        try {
            Path notePath = Paths.get(basePath, NOTES_DIR, noteId + ".json");
            System.out.println("Attempting to delete note: " + notePath);
            
            if (Files.exists(notePath)) {
                Files.delete(notePath);
                System.out.println("Note deleted successfully");
            } else {
                System.out.println("Note file does not exist: " + notePath);
            }
        } catch (IOException e) {
            String errorMsg = "Error deleting note " + noteId + ": " + e.getMessage();
            System.err.println(errorMsg);
            e.printStackTrace();
            throw new RuntimeException(errorMsg, e);
        }
    }
    
    // Delete an index file
    public static void deleteIndex(String indexId) {
        try {
            Files.deleteIfExists(Paths.get(basePath, INDICES_DIR, indexId + ".json"));
        } catch (IOException e) {
            System.err.println("Error deleting index " + indexId + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete index: " + e.getMessage(), e);
        }
    }
    
    // Delete a category file
    public static void deleteCategory(String categoryId) {
        try {
            Files.deleteIfExists(Paths.get(basePath, CATEGORIES_DIR, categoryId + ".json"));
        } catch (IOException e) {
            System.err.println("Error deleting category " + categoryId + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete category: " + e.getMessage(), e);
        }
    }
}