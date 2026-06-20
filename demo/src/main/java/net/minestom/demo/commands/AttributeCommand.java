// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.ArgumentResource;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentDouble;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.utils.entity.EntityFinder;
// Import d'une classe nécessaire
import net.minestom.server.utils.identity.NamedAndIdentified;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.kyori.adventure.text.Component.text;
// Import statique d'un membre
import static net.kyori.adventure.text.Component.translatable;

// Déclaration de type (classe/interface/enum/record)
public class AttributeCommand extends Command {
    // Début d'une méthode/d'un bloc
    public AttributeCommand() {
        // Accès à l'objet courant/parent
        super("attribute");

        // Appelle une méthode
        ArgumentEntity target = ArgumentType.Entity("target").singleEntity(true);
        // Appelle une méthode
        ArgumentResource attribute = ArgumentType.Resource("attribute", "minecraft:attribute");
        // Appelle une méthode
        ArgumentLiteral base = ArgumentType.Literal("base");
        // Appelle une méthode
        ArgumentLiteral get = ArgumentType.Literal("get");
        // Appelle une méthode
        ArgumentLiteral set = ArgumentType.Literal("set");
        // Appelle une méthode
        ArgumentDouble value = ArgumentType.Double("value");

        // Appelle une méthode
        addSyntax(this::get, target, attribute, get);
        // Appelle une méthode
        addSyntax(this::setBase, target, attribute, base, set, value);
        // Appelle une méthode
        addSyntax(this::getBase, target, attribute, base, get);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void setBase(CommandSender sender, CommandContext ctx) {
        // Appelle une méthode
        LivingEntity target = target(sender, ctx);
        // Embranchement : vérifie une condition
        if (check(target, ctx, sender)) return;
        // Appelle une méthode
        Attribute attribute = attribute(ctx);
        // Embranchement : vérifie une condition
        if (check(attribute, ctx, sender)) return;
        // Boucle : répète un bloc
        double value = value(ctx);
        // Appelle une méthode
        target.getAttribute(attribute).setBaseValue(value);
        // Appelle une méthode
        sender.sendMessage(translatable("commands.attribute.base_value.set.success").arguments(description(attribute), name(target), text(value)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void getBase(CommandSender sender, CommandContext ctx) {
        // Appelle une méthode
        LivingEntity target = target(sender, ctx);
        // Embranchement : vérifie une condition
        if (check(target, ctx, sender)) return;
        // Appelle une méthode
        Attribute attribute = attribute(ctx);
        // Embranchement : vérifie une condition
        if (check(attribute, ctx, sender)) return;
        // Boucle : répète un bloc
        double value = target.getAttribute(attribute).getBaseValue();
        // Appelle une méthode
        sender.sendMessage(translatable("commands.attribute.base_value.get.success").arguments(description(attribute), name(target), text(value)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void get(CommandSender sender, CommandContext ctx) {
        // Appelle une méthode
        LivingEntity target = target(sender, ctx);
        // Embranchement : vérifie une condition
        if (check(target, ctx, sender)) return;
        // Appelle une méthode
        Attribute attribute = attribute(ctx);
        // Embranchement : vérifie une condition
        if (check(attribute, ctx, sender)) return;
        // Boucle : répète un bloc
        double value = target.getAttributeValue(attribute);
        // Appelle une méthode
        sender.sendMessage(translatable("commands.attribute.value.get.success").arguments(description(attribute), name(target), text(value)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private Component description(Attribute attribute) {
        // Renvoie une valeur à l'appelant
        return translatable(attribute.registry().translationKey());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private double value(CommandContext ctx) {
        // Renvoie une valeur à l'appelant
        return ctx.get("value");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private LivingEntity target(CommandSender sender, CommandContext ctx) {
        // Appelle une méthode
        EntityFinder finder = ctx.get("target");
        // Appelle une méthode
        Entity entity = finder.findFirstEntity(sender);
        // Embranchement : vérifie une condition
        if (!(entity instanceof LivingEntity livingEntity)) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return livingEntity;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    private Attribute attribute(CommandContext ctx) {
        // Appelle une méthode
        String namespaceId = ctx.get("attribute");
        // Renvoie une valeur à l'appelant
        return Attribute.fromKey(namespaceId);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private Component name(Entity entity) {
        // Embranchement : vérifie une condition
        if (entity instanceof NamedAndIdentified named) {
            // Renvoie une valeur à l'appelant
            return named.getName();
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return entity.getCustomName() == null ? entity.getCustomName() : text(entity.getEntityType().name());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("!null, _, _ -> false; null, _, _ -> true")
    // Début d'une méthode/d'un bloc
    private boolean check(@Nullable LivingEntity livingEntity, CommandContext ctx, CommandSender sender) {
        // Embranchement : vérifie une condition
        if (livingEntity == null) {
            // Appelle une méthode
            Entity entity = ctx.get("target");
            // Appelle une méthode
            sender.sendMessage(translatable("commands.attribute.failed.entity").arguments(name(entity)));
            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("!null, _, _ -> false; null, _, _ -> true")
    // Début d'une méthode/d'un bloc
    private boolean check(@Nullable Attribute attribute, CommandContext ctx, CommandSender sender) {
        // Embranchement : vérifie une condition
        if (attribute == null) {
            // Appelle une méthode
            sender.sendMessage(translatable("argument.resource.invalid_type").arguments(text(ctx.<String>get("attribute")), text("minecraft:attribute"), text("minecraft:attribute")));
            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
