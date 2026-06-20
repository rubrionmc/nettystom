// Package declaration for this file
package net.minestom.server.command.builder.suggestion;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;

// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface SuggestionCallback {
    // Calls a method
    void apply(CommandSender sender, CommandContext context, Suggestion suggestion);
// End of a block/expression
}
