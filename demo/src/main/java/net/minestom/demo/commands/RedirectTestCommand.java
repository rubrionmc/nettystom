// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentLoop;

// Type declaration (class/interface/enum/record)
public class RedirectTestCommand extends Command {
    // Start of a method/block
    public RedirectTestCommand() {
        // Access to the current/parent object
        super("redirect");

        // Calls a method
        final ArgumentLiteral a = new ArgumentLiteral("a");
        // Calls a method
        final ArgumentLiteral b = new ArgumentLiteral("b");
        // Calls a method
        final ArgumentLiteral c = new ArgumentLiteral("c");
        // Calls a method
        final ArgumentLiteral d = new ArgumentLiteral("d");

        // Calls a method
        addSyntax(((sender, context) -> {}), new ArgumentLoop<>("test", a,b,c,d));
    // End of a block/expression
    }
// End of a block/expression
}
