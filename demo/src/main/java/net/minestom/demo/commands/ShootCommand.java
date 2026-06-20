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
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.metadata.projectile.ArrowMeta;
// Import of a required class
import net.minestom.server.entity.EntityProjectile;

// Import of a required class
import java.util.concurrent.ThreadLocalRandom;

// Type declaration (class/interface/enum/record)
public class ShootCommand extends Command {

    // Start of a method/block
    public ShootCommand() {
        // Access to the current/parent object
        super("shoot");
        // Calls a method
        setCondition(Conditions::playerOnly);
        // Calls a method
        setDefaultExecutor(this::defaultExecutor);
        // Calls a method
        var typeArg = ArgumentType.Word("type").from("default", "spectral", "colored");
        // Calls a method
        setArgumentCallback(this::onTypeError, typeArg);
        // Calls a method
        addSyntax(this::onShootCommand, typeArg);
    // End of a block/expression
    }

    // Start of a method/block
    private void defaultExecutor(CommandSender sender, CommandContext context) {
        // Calls a method
        sender.sendMessage(Component.text("Correct usage: shoot [default/spectral/colored]"));
    // End of a block/expression
    }

    // Start of a method/block
    private void onTypeError(CommandSender sender, ArgumentSyntaxException exception) {
        // Calls a method
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by 'default', 'spectral' or 'colored'"));
    // End of a block/expression
    }

    // Start of a method/block
    private void onShootCommand(CommandSender sender, CommandContext context) {
        // Calls a method
        Player player = (Player) sender;
        // Calls a method
        String mode = context.get("type");
        // Code statement
        EntityProjectile projectile;
        // Multiple branching (switch/case)
        switch (mode) {
            // Multiple branching (switch/case)
            case "default":
                // Calls a method
                projectile = new EntityProjectile(player, EntityType.ARROW);
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "spectral":
                // Calls a method
                projectile = new EntityProjectile(player, EntityType.SPECTRAL_ARROW);
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "colored":
                // Calls a method
                projectile = new EntityProjectile(player, EntityType.ARROW);
                // Calls a method
                var meta = (ArrowMeta) projectile.getEntityMeta();
                // Calls a method
                meta.setColor(ThreadLocalRandom.current().nextInt());
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            default:
                // Returns a value to the caller
                return;
        // End of a block/expression
        }
        // Calls a method
        var pos = player.getPosition().add(0D, player.getEyeHeight(), 0D);
        //noinspection ConstantConditions - It should be impossible to execute a command without being in an instance
        // Calls a method
        projectile.setInstance(player.getInstance(), pos);
        // Calls a method
        var dir = pos.direction().mul(30D);
        // Calls a method
        pos = pos.add(dir);
        // Calls a method
        projectile.shoot(pos, 1D, 0D);
    // End of a block/expression
    }
// End of a block/expression
}
