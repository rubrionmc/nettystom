// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.ArgumentResource;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentDouble;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.utils.entity.EntityFinder;
// Import of a required class
import net.minestom.server.utils.identity.NamedAndIdentified;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Static import of a member
import static net.kyori.adventure.text.Component.text;
// Static import of a member
import static net.kyori.adventure.text.Component.translatable;

// Type declaration (class/interface/enum/record)
public class AttributeCommand extends Command {
    // Start of a method/block
    public AttributeCommand() {
        // Access to the current/parent object
        super("attribute");

        // Calls a method
        ArgumentEntity target = ArgumentType.Entity("target").singleEntity(true);
        // Calls a method
        ArgumentResource attribute = ArgumentType.Resource("attribute", "minecraft:attribute");
        // Calls a method
        ArgumentLiteral base = ArgumentType.Literal("base");
        // Calls a method
        ArgumentLiteral get = ArgumentType.Literal("get");
        // Calls a method
        ArgumentLiteral set = ArgumentType.Literal("set");
        // Calls a method
        ArgumentDouble value = ArgumentType.Double("value");

        // Calls a method
        addSyntax(this::get, target, attribute, get);
        // Calls a method
        addSyntax(this::setBase, target, attribute, base, set, value);
        // Calls a method
        addSyntax(this::getBase, target, attribute, base, get);
    // End of a block/expression
    }

    // Start of a method/block
    private void setBase(CommandSender sender, CommandContext ctx) {
        // Calls a method
        LivingEntity target = target(sender, ctx);
        // Branch: checks a condition
        if (check(target, ctx, sender)) return;
        // Calls a method
        Attribute attribute = attribute(ctx);
        // Branch: checks a condition
        if (check(attribute, ctx, sender)) return;
        // Calls a method
        double value = value(ctx);
        // Calls a method
        target.getAttribute(attribute).setBaseValue(value);
        // Calls a method
        sender.sendMessage(translatable("commands.attribute.base_value.set.success").arguments(description(attribute), name(target), text(value)));
    // End of a block/expression
    }

    // Start of a method/block
    private void getBase(CommandSender sender, CommandContext ctx) {
        // Calls a method
        LivingEntity target = target(sender, ctx);
        // Branch: checks a condition
        if (check(target, ctx, sender)) return;
        // Calls a method
        Attribute attribute = attribute(ctx);
        // Branch: checks a condition
        if (check(attribute, ctx, sender)) return;
        // Calls a method
        double value = target.getAttribute(attribute).getBaseValue();
        // Calls a method
        sender.sendMessage(translatable("commands.attribute.base_value.get.success").arguments(description(attribute), name(target), text(value)));
    // End of a block/expression
    }

    // Start of a method/block
    private void get(CommandSender sender, CommandContext ctx) {
        // Calls a method
        LivingEntity target = target(sender, ctx);
        // Branch: checks a condition
        if (check(target, ctx, sender)) return;
        // Calls a method
        Attribute attribute = attribute(ctx);
        // Branch: checks a condition
        if (check(attribute, ctx, sender)) return;
        // Calls a method
        double value = target.getAttributeValue(attribute);
        // Calls a method
        sender.sendMessage(translatable("commands.attribute.value.get.success").arguments(description(attribute), name(target), text(value)));
    // End of a block/expression
    }

    // Start of a method/block
    private Component description(Attribute attribute) {
        // Returns a value to the caller
        return translatable(attribute);
    // End of a block/expression
    }

    // Start of a method/block
    private double value(CommandContext ctx) {
        // Returns a value to the caller
        return ctx.get("value");
    // End of a block/expression
    }

    // Start of a method/block
    private LivingEntity target(CommandSender sender, CommandContext ctx) {
        // Calls a method
        EntityFinder finder = ctx.get("target");
        // Calls a method
        Entity entity = finder.findFirstEntity(sender);
        // Branch: checks a condition
        if (!(entity instanceof LivingEntity livingEntity)) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
        // Returns a value to the caller
        return livingEntity;
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    private Attribute attribute(CommandContext ctx) {
        // Calls a method
        String namespaceId = ctx.get("attribute");
        // Returns a value to the caller
        return Attribute.fromKey(namespaceId);
    // End of a block/expression
    }

    // Start of a method/block
    private Component name(Entity entity) {
        // Branch: checks a condition
        if (entity instanceof NamedAndIdentified named) {
            // Returns a value to the caller
            return named.getName();
        // End of a block/expression
        }
        // Returns a value to the caller
        return entity.getCustomName() == null ? entity.getCustomName() : text(entity.getEntityType().name());
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("!null, _, _ -> false; null, _, _ -> true")
    // Start of a method/block
    private boolean check(@Nullable LivingEntity livingEntity, CommandContext ctx, CommandSender sender) {
        // Branch: checks a condition
        if (livingEntity == null) {
            // Calls a method
            Entity entity = ctx.get("target");
            // Calls a method
            sender.sendMessage(translatable("commands.attribute.failed.entity").arguments(name(entity)));
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("!null, _, _ -> false; null, _, _ -> true")
    // Start of a method/block
    private boolean check(@Nullable Attribute attribute, CommandContext ctx, CommandSender sender) {
        // Branch: checks a condition
        if (attribute == null) {
            // Calls a method
            sender.sendMessage(translatable("argument.resource.invalid_type").arguments(text(ctx.<String>get("attribute")), text("minecraft:attribute"), text("minecraft:attribute")));
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
