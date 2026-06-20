// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentCommand;

// Type declaration (class/interface/enum/record)
public class ExecuteCommand extends Command {

    // Start of a method/block
    public ExecuteCommand() {
        // Access to the current/parent object
        super("execute");
        // Calls a method
        ArgumentCommand run = new ArgumentCommand("run");

        // Calls a method
        addSyntax(((sender, context) -> {}), run);
    // End of a block/expression
    }
// End of a block/expression
}
