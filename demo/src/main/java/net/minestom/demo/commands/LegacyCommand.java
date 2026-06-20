// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class LegacyCommand extends net.minestom.server.command.builder.SimpleCommand {
    // Start of a method/block
    public LegacyCommand() {
        // Access to the current/parent object
        super("test", "alias");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean process(CommandSender sender, String command, String[] args) {
        // Branch: checks a condition
        if (!(sender instanceof Player)) return false;

        // Calls a method
        System.gc();
        // Calls a method
        sender.sendMessage("Explicit GC");
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean hasAccess(CommandSender sender, @Nullable String commandString) {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }
// End of a block/expression
}
