// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class ConfigCommand extends Command {
    // Start of a method/block
    public ConfigCommand() {
        // Access to the current/parent object
        super("config");

        // Start of a method/block
        setDefaultExecutor((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            player.startConfigurationPhase();
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
