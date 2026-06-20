// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

// Import d'une classe nécessaire
import java.util.concurrent.ThreadLocalRandom;

// Déclaration de type (classe/interface/enum/record)
public class DimensionCommand extends Command {

    // Début d'une méthode/d'un bloc
    public DimensionCommand() {
        // Accès à l'objet courant/parent
        super("dimensiontest");
        // Appelle une méthode
        setCondition(Conditions::playerOnly);

        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Appelle une méthode
            final Player player = (Player) sender;
            // Appelle une méthode
            final Instance instance = player.getInstance();
            // Appelle une méthode
            final var instances = MinecraftServer.getInstanceManager().getInstances().stream().filter(instance1 -> !instance1.equals(instance)).toList();
            // Embranchement : vérifie une condition
            if (instances.isEmpty()) {
                // Appelle une méthode
                player.sendMessage("No instance available");
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final var newInstance = instances.get(ThreadLocalRandom.current().nextInt(instances.size()));
            // Appelle une méthode
            player.setInstance(newInstance).thenRun(() -> player.sendMessage("Teleported"));
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
