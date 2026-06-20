// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class SleepCommand extends Command {

    // Start of a method/block
    public SleepCommand() {
        // Access to the current/parent object
        super("sleep");

        // Calls a method
        setCondition(Conditions::playerOnly);
        // Start of a method/block
        setDefaultExecutor((sender, context) -> {
            // Calls a method
            Player player = (Player) sender;
            // Calls a method
            player.enterBed(player.getPosition());
        // End of a block/expression
        });

    // End of a block/expression
    }
// End of a block/expression
}
