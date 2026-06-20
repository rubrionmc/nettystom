// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Type declaration (class/interface/enum/record)
public enum SuggestionType {

    // Code statement
    ASK_SERVER("minecraft:ask_server"),
    // Code statement
    ALL_RECIPES("minecraft:all_recipes"),
    // Code statement
    AVAILABLE_SOUNDS("minecraft:available_sounds"),
    // Calls a method
    SUMMONABLE_ENTITIES("minecraft:summonable_entities");

    // Code statement
    private final String identifier;

    // Start of a method/block
    SuggestionType(String identifier) {
        // Access to the current/parent object
        this.identifier = identifier;
    // End of a block/expression
    }

    // Start of a method/block
    public String getIdentifier() {
        // Returns a value to the caller
        return identifier;
    // End of a block/expression
    }
// End of a block/expression
}
