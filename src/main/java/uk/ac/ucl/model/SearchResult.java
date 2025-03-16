package uk.ac.ucl.model;

public class SearchResult {
    private final Note note;
    private final String matchContext;

    public SearchResult(Note note, String matchContext) {
        this.note = note;
        this.matchContext = matchContext;
    }

    public Note getNote() {
        return note;
    }

    public String getMatchContext() {
        return matchContext;
    }
} 