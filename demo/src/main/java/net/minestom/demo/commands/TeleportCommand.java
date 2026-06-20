// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.utils.location.RelativeVec;

// Déclaration de type (classe/interface/enum/record)
public class TeleportCommand extends Command {

    // Début d'une méthode/d'un bloc
    public TeleportCommand() {
        // Accès à l'objet courant/parent
        super("tp");

        // Appelle une méthode
        setDefaultExecutor((source, context) -> source.sendMessage(Component.text("Usage: /tp x y z")));

        // Appelle une méthode
        var posArg = ArgumentType.RelativeVec3("pos");
        // Appelle une méthode
        var playerArg = ArgumentType.Word("player");

        // Appelle une méthode
        addSyntax(this::onPlayerTeleport, playerArg);
        // Appelle une méthode
        addSyntax(this::onPositionTeleport, posArg);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onPlayerTeleport(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        final String playerName = context.get("player");
        // Appelle une méthode
        Player pl = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(playerName);
        // Embranchement : vérifie une condition
        if (sender instanceof Player player) {
            // Appelle une méthode
            player.teleport(pl.getPosition());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        sender.sendMessage(Component.text("Teleported to player " + playerName));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onPositionTeleport(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        final Player player = (Player) sender;

        // Appelle une méthode
        final RelativeVec relativeVec = context.get("pos");
        // Appelle une méthode
        final Pos position = player.getPosition().withCoord(relativeVec.from(player));
        // Appelle une méthode
        player.teleport(position);
        // Appelle une méthode
        player.sendMessage(Component.text("You have been teleported to " + position));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
