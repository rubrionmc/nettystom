// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.BelowNameTag;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;

// Déclaration de type (classe/interface/enum/record)
public class BelowNameCommand extends Command {

    // Appelle une méthode
    private final ArgumentEntity target = ArgumentType.Entity("target").onlyPlayers(true).singleEntity(true);
    // Appelle une méthode
    private final Argument<Integer> value = ArgumentType.Integer("value");

    // Début d'une méthode/d'un bloc
    public BelowNameCommand() {
        // Accès à l'objet courant/parent
        super("belowname");

        // Appelle une méthode
        BelowNameTag belowNameTag = new BelowNameTag("test", Component.text("lorum"));

        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;
            // Appelle une méthode
            Player targetPlayer = context.get(target).findFirstPlayer(player);
            // Embranchement : vérifie une condition
            if (targetPlayer == null) return;
            // Appelle une méthode
            belowNameTag.addViewer(player);
            // Appelle une méthode
            Integer targetValue = context.get(value);
            // Appelle une méthode
            belowNameTag.updateScore(targetPlayer, targetValue);
        // Appelle une méthode
        }, Literal("set"), target, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
