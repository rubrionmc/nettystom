// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class ConfigCommand extends Command {
    // Début d'une méthode/d'un bloc
    public ConfigCommand() {
        // Accès à l'objet courant/parent
        super("config");

        // Début d'une méthode/d'un bloc
        setDefaultExecutor((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            player.startConfigurationPhase();
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
