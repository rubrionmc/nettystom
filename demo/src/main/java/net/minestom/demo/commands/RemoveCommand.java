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
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.utils.entity.EntityFinder;

// Déclaration de type (classe/interface/enum/record)
public class RemoveCommand extends Command {

    // Début d'une méthode/d'un bloc
    public RemoveCommand() {
        // Accès à l'objet courant/parent
        super("remove");
        // Appelle une méthode
        addSubcommand(new RemoveEntities());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static class RemoveEntities extends Command {
        // Instruction de code
        private final ArgumentEntity entity;

        // Début d'une méthode/d'un bloc
        public RemoveEntities() {
            // Accès à l'objet courant/parent
            super("entities");
            // Appelle une méthode
            setCondition(Conditions::playerOnly);
            // Appelle une méthode
            entity = ArgumentType.Entity("entity");
            // Appelle une méthode
            addSyntax(this::remove, entity);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void remove(CommandSender commandSender, CommandContext commandContext) {
            // Appelle une méthode
            final EntityFinder entityFinder = commandContext.get(entity);
            // Appelle une méthode
            entityFinder.find(commandSender).forEach(Entity::remove);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}