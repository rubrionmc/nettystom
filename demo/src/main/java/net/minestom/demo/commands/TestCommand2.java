// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;

// Type declaration (class/interface/enum/record)
public class TestCommand2 extends Command {
    // Start of a method/block
    public TestCommand2() {
        // Access to the current/parent object
        super("test2");

        // Calls a method
        var argA = ArgumentType.String("a");
        // Calls a method
        var argB = ArgumentType.String("b");

        // Calls a method
        addSyntax((sender, context) -> sender.sendMessage("a only"), argA);
        // Calls a method
        addSyntax((sender, context) -> sender.sendMessage("a and b"), argB, argA);
    // End of a block/expression
    }
// End of a block/expression
}
