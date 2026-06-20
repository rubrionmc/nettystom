// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.LightingChunk;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Déclaration de type (classe/interface/enum/record)
public class RelightCommand extends Command {
    // Début d'une méthode/d'un bloc
    public RelightCommand() {
        // Accès à l'objet courant/parent
        super("relight");
        // Début d'une méthode/d'un bloc
        setDefaultExecutor((source, args) -> {
            // Embranchement : vérifie une condition
            if (source instanceof Player player) {
                // Appelle une méthode
                long start = System.nanoTime();
                // Appelle une méthode
                source.sendMessage("Relighting...");
                // Appelle une méthode
                var relit = LightingChunk.relight(player.getInstance(), player.getInstance().getChunks());
                // Appelle une méthode
                source.sendMessage("Relighted " + player.getInstance().getChunks().size() + " chunks in " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + "ms");
                // Appelle une méthode
                relit.forEach(chunk -> chunk.sendChunk(player));
                // Appelle une méthode
                source.sendMessage("Chunks Received");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}