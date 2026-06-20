// Package declaration for this file
package net.minestom.server.command.builder.parser;

// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.CommandSyntax;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;

// Type declaration (class/interface/enum/record)
public record ArgumentQueryResult(CommandSyntax syntax,
                                  // Code statement
                                  Argument<?> argument,
                                  // Code statement
                                  CommandContext context,
                                  // Start of a method/block
                                  String input) {
// End of a block/expression
}
