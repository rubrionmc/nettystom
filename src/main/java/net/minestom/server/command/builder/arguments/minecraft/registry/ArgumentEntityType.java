// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft.registry;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType;
// Import of a required class
import net.minestom.server.entity.EntityType;

/**
 * Represents an argument giving an {@link EntityType}.
 */
// Type declaration (class/interface/enum/record)
public class ArgumentEntityType extends ArgumentRegistry<EntityType> {

    // Start of a method/block
    public ArgumentEntityType(String id) {
        // Access to the current/parent object
        super(id);
        // Assigns a value
        suggestionType = SuggestionType.SUMMONABLE_ENTITIES;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.RESOURCE_LOCATION;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EntityType getRegistry(String value) {
        // Returns a value to the caller
        return EntityType.fromKey(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("EntityType<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
