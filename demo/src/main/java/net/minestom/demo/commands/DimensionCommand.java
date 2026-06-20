// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.Instance;

// Import of a required class
import java.util.concurrent.ThreadLocalRandom;

// Type declaration (class/interface/enum/record)
public class DimensionCommand extends Command {

    // Start of a method/block
    public DimensionCommand() {
        // Access to the current/parent object
        super("dimensiontest");
        // Calls a method
        setCondition(Conditions::playerOnly);

        // Start of a method/block
        addSyntax((sender, context) -> {
            // Calls a method
            final Player player = (Player) sender;
            // Calls a method
            final Instance instance = player.getInstance();
            // Calls a method
            final var instances = MinecraftServer.getInstanceManager().getInstances().stream().filter(instance1 -> !instance1.equals(instance)).toList();
            // Branch: checks a condition
            if (instances.isEmpty()) {
                // Calls a method
                player.sendMessage("No instance available");
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Calls a method
            final var newInstance = instances.get(ThreadLocalRandom.current().nextInt(instances.size()));
            // Calls a method
            player.setInstance(newInstance).thenRun(() -> player.sendMessage("Teleported"));
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
