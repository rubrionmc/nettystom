// Package declaration for this file
package net.minestom.server.command.builder.suggestion;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class Suggestion {

    // Code statement
    private final String input;
    // Code statement
    private int start;
    // Code statement
    private int length;
    // Calls a method
    private final List<SuggestionEntry> suggestionEntries = new ArrayList<>();

    // Start of a method/block
    public Suggestion(String input, int start, int length) {
        // Access to the current/parent object
        this.input = input;
        // Access to the current/parent object
        this.start = start;
        // Access to the current/parent object
        this.length = length;
    // End of a block/expression
    }

    // Start of a method/block
    public String getInput() {
        // Returns a value to the caller
        return input;
    // End of a block/expression
    }

    // Start of a method/block
    public int getStart() {
        // Returns a value to the caller
        return start;
    // End of a block/expression
    }

    // Start of a method/block
    public void setStart(int start) {
        // Access to the current/parent object
        this.start = start;
    // End of a block/expression
    }

    // Start of a method/block
    public int getLength() {
        // Returns a value to the caller
        return length;
    // End of a block/expression
    }

    // Start of a method/block
    public void setLength(int length) {
        // Access to the current/parent object
        this.length = length;
    // End of a block/expression
    }

    // Start of a method/block
    public List<SuggestionEntry> getEntries() {
        // Returns a value to the caller
        return suggestionEntries;
    // End of a block/expression
    }

    // Start of a method/block
    public void addEntry(SuggestionEntry entry) {
        // Access to the current/parent object
        this.suggestionEntries.add(entry);
    // End of a block/expression
    }

// End of a block/expression
}
