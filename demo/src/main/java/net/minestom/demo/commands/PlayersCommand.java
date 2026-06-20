// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class PlayersCommand extends Command {

    // Start of a method/block
    public PlayersCommand() {
        // Access to the current/parent object
        super("players");
        // Calls a method
        setDefaultExecutor(this::usage);
    // End of a block/expression
    }

    // Start of a method/block
    private void usage(CommandSender sender, CommandContext context) {
        // Calls a method
        final var players = List.copyOf(MinecraftServer.getConnectionManager().getOnlinePlayers());
        // Calls a method
        final int playerCount = players.size();
        // Calls a method
        sender.sendMessage(Component.text("Total players: " + playerCount));

        // Assigns a value
        final int limit = 15;
        // Loop: repeats a block
        for (int i = 0; i < Math.min(limit, playerCount); i++) {
            // Calls a method
            final var player = players.get(i);
            // Calls a method
            sender.sendMessage(Component.text(player.getUsername()));
        // End of a block/expression
        }

        // Branch: checks a condition
        if (playerCount > limit) sender.sendMessage(Component.text("..."));
    // End of a block/expression
    }

// End of a block/expression
}
