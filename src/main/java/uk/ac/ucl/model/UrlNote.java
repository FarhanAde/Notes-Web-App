package uk.ac.ucl.model;

public class UrlNote extends Note {
    private String url;
    private String description;
    
    public UrlNote() {
        super();
    }
    
    public UrlNote(String name, String url, String description) {
        super(name);
        this.url = url;
        this.description = description;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
        updateModificationTime();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        updateModificationTime();
    }
    
    @Override
    public String getSummary() {
        return url + (description != null && !description.isEmpty() ? " - " + description : "");
    }
    
    @Override
    public String getFullContent() {
        return "URL: " + url + "\n\nDescription: " + (description != null ? description : "");
    }
    
    @Override
    public boolean containsText(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return false;
        }
        
        String lowerSearch = searchText.toLowerCase();
        return name.toLowerCase().contains(lowerSearch) || 
               (url != null && url.toLowerCase().contains(lowerSearch)) || 
               (description != null && description.toLowerCase().contains(lowerSearch));
    }
}