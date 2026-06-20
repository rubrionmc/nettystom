// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentEnum;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.location.RelativeVec;

// Déclaration de type (classe/interface/enum/record)
public class SummonCommand extends Command {

    // Instruction de code
    private final ArgumentEntityType entity;
    // Instruction de code
    private final Argument<RelativeVec> pos;
    // Instruction de code
    private final Argument<EntityClass> entityClass;

    // Début d'une méthode/d'un bloc
    public SummonCommand() {
        // Accès à l'objet courant/parent
        super("summon");
        // Appelle une méthode
        setCondition(Conditions::playerOnly);

        // Appelle une méthode
        entity = ArgumentType.EntityType("entity type");
        // Affecte une valeur
        pos = ArgumentType.RelativeVec3("pos").setDefaultValue(() -> new RelativeVec(
                // Crée un nouvel objet
                new Vec(0, 0, 0),
                // Instruction de code
                RelativeVec.CoordinateType.RELATIVE,
                // Instruction de code
                true, true, true
        // Instruction de code
        ));
        // Affecte une valeur
        entityClass = ArgumentType.Enum("class", EntityClass.class)
                // Instruction de code
                .setFormat(ArgumentEnum.Format.LOWER_CASED)
                // Appelle une méthode
                .setDefaultValue(EntityClass.CREATURE);
        // Appelle une méthode
        addSyntax(this::execute, entity, pos, entityClass);
        // Appelle une méthode
        setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /summon <type> <x> <y> <z> <class>"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void execute(CommandSender commandSender, CommandContext commandContext) {
        // Appelle une méthode
        final Entity entity = commandContext.get(entityClass).instantiate(commandContext.get(this.entity));
        //noinspection ConstantConditions - One couldn't possibly execute a command without being in an instance
        // Appelle une méthode
        entity.setInstance(((Player) commandSender).getInstance(), commandContext.get(pos).fromSender(commandSender));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unused")
    // Déclaration de type (classe/interface/enum/record)
    enum EntityClass {
        // Instruction de code
        BASE(Entity::new),
        // Instruction de code
        LIVING(LivingEntity::new),
        // Appelle une méthode
        CREATURE(EntityCreature::new);
        // Instruction de code
        private final EntityFactory factory;

        // Début d'une méthode/d'un bloc
        EntityClass(EntityFactory factory) {
            // Accès à l'objet courant/parent
            this.factory = factory;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Entity instantiate(EntityType type) {
            // Renvoie une valeur à l'appelant
            return factory.newInstance(type);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    interface EntityFactory {
        // Appelle une méthode
        Entity newInstance(EntityType type);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
