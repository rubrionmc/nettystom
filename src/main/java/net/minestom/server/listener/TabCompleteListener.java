// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.suggestion.Suggestion;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientTabCompletePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.TabCompletePacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class TabCompleteListener {

    // Start of a method/block
    public static void listener(ClientTabCompletePacket packet, Player player) {
        // Calls a method
        final String text = packet.text();
        // Calls a method
        final Suggestion suggestion = getSuggestion(player, text);
        // Branch: checks a condition
        if (suggestion != null) {
            // Code statement
            player.sendPacket(new TabCompletePacket(
                    // Code statement
                    packet.transactionId(),
                    // Code statement
                    suggestion.getStart(),
                    // Code statement
                    suggestion.getLength(),
                    // Code statement
                    suggestion.getEntries().stream()
                            // Code statement
                            .map(suggestionEntry -> new TabCompletePacket.Match(suggestionEntry.getEntry(), suggestionEntry.getTooltip()))
                            // Code statement
                            .toList())
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static @Nullable Suggestion getSuggestion(CommandSender commandSender, String text) {
        // Branch: checks a condition
        if (text.startsWith("/")) {
            // Calls a method
            text = text.substring(1);
        // End of a block/expression
        }
        // Branch: checks a condition
        if (text.endsWith(" ")) {
            // Append a placeholder char if the command ends with a space allowing the parser to find suggestion
            // for the next arg without typing the first char of it, this is probably the most hacky solution, but hey
            // it works as intended :)
            // Assigns a value
            text = text + '\00';
        // End of a block/expression
        }
        // Returns a value to the caller
        return MinecraftServer.getCommandManager().parseCommand(commandSender, text).suggestion(commandSender);
    // End of a block/expression
    }
// End of a block/expression
}
