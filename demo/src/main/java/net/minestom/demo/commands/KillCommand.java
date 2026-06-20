// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class KillCommand extends Command {
    // Début d'une méthode/d'un bloc
    public KillCommand() {
        // Accès à l'objet courant/parent
        super("kill");

        // Début d'une méthode/d'un bloc
        setDefaultExecutor((sender, context) -> {
            // Embranchement : vérifie une condition
            if (sender instanceof Player player) {
                // Appelle une méthode
                player.kill();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
