// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class KillCommand extends Command {
    // Start of a method/block
    public KillCommand() {
        // Access to the current/parent object
        super("kill");

        // Start of a method/block
        setDefaultExecutor((sender, context) -> {
            // Branch: checks a condition
            if (sender instanceof Player player) {
                // Calls a method
                player.kill();
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
