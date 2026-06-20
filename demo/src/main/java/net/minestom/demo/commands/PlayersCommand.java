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
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class PlayersCommand extends Command {

    // Début d'une méthode/d'un bloc
    public PlayersCommand() {
        // Accès à l'objet courant/parent
        super("players");
        // Appelle une méthode
        setDefaultExecutor(this::usage);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void usage(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        final var players = List.copyOf(MinecraftServer.getConnectionManager().getOnlinePlayers());
        // Appelle une méthode
        final int playerCount = players.size();
        // Appelle une méthode
        sender.sendMessage(Component.text("Total players: " + playerCount));

        // Affecte une valeur
        final int limit = 15;
        // Boucle : répète un bloc
        for (int i = 0; i < Math.min(limit, playerCount); i++) {
            // Appelle une méthode
            final var player = players.get(i);
            // Appelle une méthode
            sender.sendMessage(Component.text(player.getUsername()));
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (playerCount > limit) sender.sendMessage(Component.text("..."));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
