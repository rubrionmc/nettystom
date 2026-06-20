// Package declaration for this file
package net.minestom.server.command.builder.parser;

// Import of a required class
import net.minestom.server.command.builder.CommandSyntax;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;

// Import of a required class
import java.util.Map;

/**
 * Holds the data of a validated syntax.
 */
// Type declaration (class/interface/enum/record)
public record ValidSyntaxHolder(String commandString,
                                // Code statement
                                CommandSyntax syntax,
                                // Start of a method/block
                                Map<Argument<?>, ArgumentParser.ArgumentResult> argumentResults) {

// End of a block/expression
}
