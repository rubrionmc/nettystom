// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class SleepCommand extends Command {

    // Début d'une méthode/d'un bloc
    public SleepCommand() {
        // Accès à l'objet courant/parent
        super("sleep");

        // Appelle une méthode
        setCondition(Conditions::playerOnly);
        // Début d'une méthode/d'un bloc
        setDefaultExecutor((sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;
            // Appelle une méthode
            player.enterBed(player.getPosition());
        // Fin d'un bloc/d'une expression
        });

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
