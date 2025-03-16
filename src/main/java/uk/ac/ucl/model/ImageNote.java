package uk.ac.ucl.model;

public class ImageNote extends Note {
    private String imageFilePath;
    private String caption;
    
    public ImageNote() {
        super();
    }
    
    public ImageNote(String name, String imageFilePath, String caption) {
        super(name);
        this.imageFilePath = imageFilePath;
        this.caption = caption;
    }
    
    public String getImageFilePath() {
        return imageFilePath;
    }
    
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
        updateModificationTime();
    }
    
    public String getCaption() {
        return caption;
    }
    
    public void setCaption(String caption) {
        this.caption = caption;
        updateModificationTime();
    }
    
    @Override
    public String getSummary() {
        return "[Image] " + (caption != null && !caption.isEmpty() ? caption : name);
    }
    
    @Override
    public String getFullContent() {
        return "Image: " + imageFilePath + "\n\nCaption: " + (caption != null ? caption : "");
    }
    
    @Override
    public boolean containsText(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return false;
        }
        
        String lowerSearch = searchText.toLowerCase();
        return name.toLowerCase().contains(lowerSearch) || 
               (caption != null && caption.toLowerCase().contains(lowerSearch));
    }
}