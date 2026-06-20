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
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.projectile.ArrowMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityProjectile;

// Import d'une classe nécessaire
import java.util.concurrent.ThreadLocalRandom;

// Déclaration de type (classe/interface/enum/record)
public class ShootCommand extends Command {

    // Début d'une méthode/d'un bloc
    public ShootCommand() {
        // Accès à l'objet courant/parent
        super("shoot");
        // Appelle une méthode
        setCondition(Conditions::playerOnly);
        // Appelle une méthode
        setDefaultExecutor(this::defaultExecutor);
        // Appelle une méthode
        var typeArg = ArgumentType.Word("type").from("default", "spectral", "colored");
        // Appelle une méthode
        setArgumentCallback(this::onTypeError, typeArg);
        // Appelle une méthode
        addSyntax(this::onShootCommand, typeArg);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void defaultExecutor(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        sender.sendMessage(Component.text("Correct usage: shoot [default/spectral/colored]"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onTypeError(CommandSender sender, ArgumentSyntaxException exception) {
        // Appelle une méthode
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by 'default', 'spectral' or 'colored'"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onShootCommand(CommandSender sender, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) sender;
        // Appelle une méthode
        String mode = context.get("type");
        // Instruction de code
        EntityProjectile projectile;
        // Embranchement multiple (switch/case)
        switch (mode) {
            // Embranchement multiple (switch/case)
            case "default":
                // Appelle une méthode
                projectile = new EntityProjectile(player, EntityType.ARROW);
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "spectral":
                // Appelle une méthode
                projectile = new EntityProjectile(player, EntityType.SPECTRAL_ARROW);
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "colored":
                // Appelle une méthode
                projectile = new EntityProjectile(player, EntityType.ARROW);
                // Appelle une méthode
                var meta = (ArrowMeta) projectile.getEntityMeta();
                // Appelle une méthode
                meta.setColor(ThreadLocalRandom.current().nextInt());
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            default:
                // Renvoie une valeur à l'appelant
                return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        var pos = player.getPosition().add(0D, player.getEyeHeight(), 0D);
        //noinspection ConstantConditions - It should be impossible to execute a command without being in an instance
        // Appelle une méthode
        projectile.setInstance(player.getInstance(), pos);
        // Appelle une méthode
        var dir = pos.direction().mul(30D);
        // Appelle une méthode
        pos = pos.add(dir);
        // Appelle une méthode
        projectile.shoot(pos, 1D, 0D);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
