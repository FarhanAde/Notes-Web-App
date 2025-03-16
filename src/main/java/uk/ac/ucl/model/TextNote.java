package uk.ac.ucl.model;

public class TextNote extends Note {
    private String text;
    
    public TextNote() {
        super();
    }
    
    public TextNote(String name, String text) {
        super(name);
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
        updateModificationTime();
    }
    
    @Override
    public String getSummary() {
        if (text == null || text.isEmpty()) {
            return "[Empty note]";
        }
        
        // Return first 50 characters or less
        return text.length() <= 50 ? text : text.substring(0, 47) + "...";
    }
    
    @Override
    public String getFullContent() {
        return text;
    }
    
    @Override
    public boolean containsText(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return false;
        }
        
        return name.toLowerCase().contains(searchText.toLowerCase()) || 
               (text != null && text.toLowerCase().contains(searchText.toLowerCase()));
    }
}