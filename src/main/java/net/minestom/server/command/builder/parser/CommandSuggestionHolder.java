// Package declaration for this file
package net.minestom.server.command.builder.parser;

// Import of a required class
import net.minestom.server.command.builder.CommandSyntax;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

/**
 * Holds the data of an invalidated syntax.
 */
// Type declaration (class/interface/enum/record)
public record CommandSuggestionHolder(CommandSyntax syntax,
                                      // Code statement
                                      ArgumentSyntaxException argumentSyntaxException,
                                      // Start of a method/block
                                      int argIndex) {
// End of a block/expression
}
