// Package declaration for this file
package net.minestom.server.command.builder.suggestion;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public class SuggestionEntry {
    // Code statement
    private final String entry;
    // Code statement
    private final Component tooltip;

    // Start of a method/block
    public SuggestionEntry(String entry, @Nullable Component tooltip) {
        // Access to the current/parent object
        this.entry = entry;
        // Access to the current/parent object
        this.tooltip = tooltip;
    // End of a block/expression
    }

    // Start of a method/block
    public SuggestionEntry(String entry) {
        // Calls a method
        this(entry, null);
    // End of a block/expression
    }

    // Start of a method/block
    public String getEntry() {
        // Returns a value to the caller
        return entry;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Component getTooltip() {
        // Returns a value to the caller
        return tooltip;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (this == o) return true;
        // Branch: checks a condition
        if (o == null || getClass() != o.getClass()) return false;
        // Calls a method
        SuggestionEntry that = (SuggestionEntry) o;
        // Returns a value to the caller
        return Objects.equals(entry, that.entry) && Objects.equals(tooltip, that.tooltip);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return Objects.hash(entry, tooltip);
    // End of a block/expression
    }
// End of a block/expression
}
