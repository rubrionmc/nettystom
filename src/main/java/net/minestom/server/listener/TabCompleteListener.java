// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.suggestion.Suggestion;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientTabCompletePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TabCompletePacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class TabCompleteListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientTabCompletePacket packet, Player player) {
        // Appelle une méthode
        final String text = packet.text();
        // Appelle une méthode
        final Suggestion suggestion = getSuggestion(player, text);
        // Embranchement : vérifie une condition
        if (suggestion != null) {
            // Instruction de code
            player.sendPacket(new TabCompletePacket(
                    // Instruction de code
                    packet.transactionId(),
                    // Instruction de code
                    suggestion.getStart(),
                    // Instruction de code
                    suggestion.getLength(),
                    // Instruction de code
                    suggestion.getEntries().stream()
                            // Instruction de code
                            .map(suggestionEntry -> new TabCompletePacket.Match(suggestionEntry.getEntry(), suggestionEntry.getTooltip()))
                            // Instruction de code
                            .toList())
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @Nullable Suggestion getSuggestion(CommandSender commandSender, String text) {
        // Embranchement : vérifie une condition
        if (text.startsWith("/")) {
            // Appelle une méthode
            text = text.substring(1);
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (text.endsWith(" ")) {
            // Append a placeholder char if the command ends with a space allowing the parser to find suggestion
            // for the next arg without typing the first char of it, this is probably the most hacky solution, but hey
            // it works as intended :)
            // Affecte une valeur
            text = text + '\00';
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getCommandManager().parseCommand(commandSender, text).suggestion(commandSender);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
