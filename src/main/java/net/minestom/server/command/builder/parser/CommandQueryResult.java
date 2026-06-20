// Package declaration for this file
package net.minestom.server.command.builder.parser;

// Import of a required class
import net.minestom.server.command.builder.Command;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record CommandQueryResult(List<Command> parents,
                                 // Code statement
                                 Command command,
                                 // Code statement
                                 String commandName,
                                 // Start of a method/block
                                 String[] args) {
// End of a block/expression
}
