// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentEnum;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.*;
// Import of a required class
import net.minestom.server.utils.location.RelativeVec;

// Type declaration (class/interface/enum/record)
public class SummonCommand extends Command {

    // Code statement
    private final ArgumentEntityType entity;
    // Code statement
    private final Argument<RelativeVec> pos;
    // Code statement
    private final Argument<EntityClass> entityClass;

    // Start of a method/block
    public SummonCommand() {
        // Access to the current/parent object
        super("summon");
        // Calls a method
        setCondition(Conditions::playerOnly);

        // Calls a method
        entity = ArgumentType.EntityType("entity type");
        // Assigns a value
        pos = ArgumentType.RelativeVec3("pos").setDefaultValue(() -> new RelativeVec(
                // Creates a new object
                new Vec(0, 0, 0),
                // Code statement
                RelativeVec.CoordinateType.RELATIVE,
                // Code statement
                true, true, true
        // Code statement
        ));
        // Assigns a value
        entityClass = ArgumentType.Enum("class", EntityClass.class)
                // Code statement
                .setFormat(ArgumentEnum.Format.LOWER_CASED)
                // Calls a method
                .setDefaultValue(EntityClass.CREATURE);
        // Calls a method
        addSyntax(this::execute, entity, pos, entityClass);
        // Calls a method
        setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /summon <type> <x> <y> <z> <class>"));
    // End of a block/expression
    }

    // Start of a method/block
    private void execute(CommandSender commandSender, CommandContext commandContext) {
        // Calls a method
        final Entity entity = commandContext.get(entityClass).instantiate(commandContext.get(this.entity));
        //noinspection ConstantConditions - One couldn't possibly execute a command without being in an instance
        // Calls a method
        entity.setInstance(((Player) commandSender).getInstance(), commandContext.get(pos).fromSender(commandSender));
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unused")
    // Type declaration (class/interface/enum/record)
    enum EntityClass {
        // Code statement
        BASE(Entity::new),
        // Code statement
        LIVING(LivingEntity::new),
        // Calls a method
        CREATURE(EntityCreature::new);
        // Code statement
        private final EntityFactory factory;

        // Start of a method/block
        EntityClass(EntityFactory factory) {
            // Access to the current/parent object
            this.factory = factory;
        // End of a block/expression
        }

        // Start of a method/block
        public Entity instantiate(EntityType type) {
            // Returns a value to the caller
            return factory.newInstance(type);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    interface EntityFactory {
        // Calls a method
        Entity newInstance(EntityType type);
    // End of a block/expression
    }
// End of a block/expression
}
