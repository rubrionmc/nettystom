// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.LightingChunk;

// Import of a required class
import java.util.concurrent.TimeUnit;

// Type declaration (class/interface/enum/record)
public class RelightCommand extends Command {
    // Start of a method/block
    public RelightCommand() {
        // Access to the current/parent object
        super("relight");
        // Start of a method/block
        setDefaultExecutor((source, args) -> {
            // Branch: checks a condition
            if (source instanceof Player player) {
                // Calls a method
                long start = System.nanoTime();
                // Calls a method
                source.sendMessage("Relighting...");
                // Calls a method
                var relit = LightingChunk.relight(player.getInstance(), player.getInstance().getChunks());
                // Calls a method
                source.sendMessage("Relighted " + player.getInstance().getChunks().size() + " chunks in " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + "ms");
                // Calls a method
                relit.forEach(chunk -> chunk.sendChunk(player));
                // Calls a method
                source.sendMessage("Chunks Received");
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}