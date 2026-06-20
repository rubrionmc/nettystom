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
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.utils.location.RelativeVec;

// Type declaration (class/interface/enum/record)
public class TeleportCommand extends Command {

    // Start of a method/block
    public TeleportCommand() {
        // Access to the current/parent object
        super("tp");

        // Calls a method
        setDefaultExecutor((source, context) -> source.sendMessage(Component.text("Usage: /tp x y z")));

        // Calls a method
        var posArg = ArgumentType.RelativeVec3("pos");
        // Calls a method
        var playerArg = ArgumentType.Word("player");

        // Calls a method
        addSyntax(this::onPlayerTeleport, playerArg);
        // Calls a method
        addSyntax(this::onPositionTeleport, posArg);
    // End of a block/expression
    }

    // Start of a method/block
    private void onPlayerTeleport(CommandSender sender, CommandContext context) {
        // Calls a method
        final String playerName = context.get("player");
        // Calls a method
        Player pl = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(playerName);
        // Branch: checks a condition
        if (sender instanceof Player player) {
            // Calls a method
            player.teleport(pl.getPosition());
        // End of a block/expression
        }
        // Calls a method
        sender.sendMessage(Component.text("Teleported to player " + playerName));
    // End of a block/expression
    }

    // Start of a method/block
    private void onPositionTeleport(CommandSender sender, CommandContext context) {
        // Calls a method
        final Player player = (Player) sender;

        // Calls a method
        final RelativeVec relativeVec = context.get("pos");
        // Calls a method
        final Pos position = player.getPosition().withCoord(relativeVec.from(player));
        // Calls a method
        player.teleport(position);
        // Calls a method
        player.sendMessage(Component.text("You have been teleported to " + position));
    // End of a block/expression
    }
// End of a block/expression
}
