// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.utils.entity.EntityFinder;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class EntitySelectorCommand extends Command {

    // Début d'une méthode/d'un bloc
    public EntitySelectorCommand() {
        // Accès à l'objet courant/parent
        super("ent");

        // Appelle une méthode
        setDefaultExecutor((sender, context) -> System.out.println("DEFAULT"));

        // Appelle une méthode
        ArgumentEntity argumentEntity = ArgumentType.Entity("entities").onlyPlayers(true);

        // Appelle une méthode
        setArgumentCallback((sender, exception) -> exception.printStackTrace(), argumentEntity);

        // Appelle une méthode
        addSyntax(this::executor, argumentEntity);

    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void executor(CommandSender commandSender, CommandContext context) {
        // Appelle une méthode
        EntityFinder entityFinder = context.get("entities");
        // Appelle une méthode
        List<Entity> entities = entityFinder.find(commandSender);
        // Appelle une méthode
        System.out.println("found " + entities.size() + " entities");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
