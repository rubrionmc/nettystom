// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentEnum;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.utils.entity.EntityFinder;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Locale;

/**
 * Command that make a player change gamemode, made in
 * the style of the vanilla /gamemode command.
 *
 * @see <a href="https://minecraft.wiki/w/Commands/gamemode">...</a>
 */
// Type declaration (class/interface/enum/record)
public class GamemodeCommand extends Command {

    // Start of a method/block
    public GamemodeCommand() {
        // Access to the current/parent object
        super("gamemode", "gm");

        //GameMode parameter
        // Calls a method
        ArgumentEnum<GameMode> gamemode = ArgumentType.Enum("gamemode", GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);
        // Code statement
        gamemode.setCallback((sender, exception) -> sender.sendMessage(
                // Code statement
                Component.text("Invalid gamemode ", NamedTextColor.RED)
                        // Code statement
                        .append(Component.text(exception.getInput(), NamedTextColor.WHITE))
                        // Calls a method
                        .append(Component.text("!"))));

        // Calls a method
        ArgumentEntity player = ArgumentType.Entity("targets").onlyPlayers(true);

        //Upon invalid usage, print the correct usage of the command to the sender
        // Start of a method/block
        setDefaultExecutor((sender, context) -> {
            // Calls a method
            String commandName = context.getCommandName();

            // Calls a method
            sender.sendMessage(Component.text("Usage: /" + commandName + " <gamemode> [targets]", NamedTextColor.RED));
        // End of a block/expression
        });

        //Command Syntax for /gamemode <gamemode>
        // Start of a method/block
        addSyntax((sender, context) -> {
            //Limit execution to players only
            // Branch: checks a condition
            if (!(sender instanceof Player p)) {
                // Calls a method
                sender.sendMessage(Component.text("Please run this command in-game.", NamedTextColor.RED));
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (p.getPermissionLevel() < 2) {
                // Calls a method
                sender.sendMessage(Component.text("You don't have permission to use this command.", NamedTextColor.RED));
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Calls a method
            GameMode mode = context.get(gamemode);

            //Set the gamemode for the sender
            // Calls a method
            executeSelf(p, mode);
        // Code statement
        }, gamemode);

        //Command Syntax for /gamemode <gamemode> [targets]
        // Start of a method/block
        addSyntax((sender, context) -> {
            //Check permission for players only
            //This allows the console to use this syntax too
            // Branch: checks a condition
            if (sender instanceof Player p && p.getPermissionLevel() < 2) {
                // Calls a method
                sender.sendMessage(Component.text("You don't have permission to use this command.", NamedTextColor.RED));
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Calls a method
            EntityFinder finder = context.get(player);
            // Calls a method
            GameMode mode = context.get(gamemode);

            //Set the gamemode for the targets
            // Calls a method
            executeOthers(sender, mode, finder.find(sender));
        // Code statement
        }, gamemode, player);
    // End of a block/expression
    }

    /**
     * Sets the gamemode for the specified entities, and
     * notifies them (and the sender) in the chat.
     */
    // Start of a method/block
    private void executeOthers(CommandSender sender, GameMode mode, List<Entity> entities) {
        // Branch: checks a condition
        if (entities.isEmpty()) {
            //If there are no players that could be modified, display an error message
            // Branch: checks a condition
            if (sender instanceof Player)
                // Calls a method
                sender.sendMessage(Component.translatable("argument.entity.notfound.player", NamedTextColor.RED));
            // Alternative branch of the condition
            else sender.sendMessage(Component.text("No player was found", NamedTextColor.RED));
        // Alternative branch of the condition
        } else for (Entity entity : entities) {
            // Branch: checks a condition
            if (entity instanceof Player p) {
                // Branch: checks a condition
                if (p == sender) {
                    //If the player is the same as the sender, call
                    //executeSelf to display one message instead of two
                    // Calls a method
                    executeSelf((Player) sender, mode);
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    p.setGameMode(mode);

                    // Calls a method
                    String gamemodeString = "gameMode." + mode.name().toLowerCase(Locale.ROOT);
                    // Calls a method
                    Component gamemodeComponent = Component.translatable(gamemodeString);
                    // Calls a method
                    Component playerName = p.getDisplayName() == null ? p.getName() : p.getDisplayName();

                    //Send a message to the changed player and the sender
                    // Calls a method
                    p.sendMessage(Component.translatable("gameMode.changed", gamemodeComponent));
                    // Calls a method
                    sender.sendMessage(Component.translatable("commands.gamemode.success.other", playerName, gamemodeComponent));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Sets the gamemode for the executing Player, and
     * notifies them in the chat.
     */
    // Start of a method/block
    private void executeSelf(Player sender, GameMode mode) {
        // Calls a method
        sender.setGameMode(mode);

        //The translation keys 'gameMode.survival', 'gameMode.creative', etc.
        //correspond to the translated game mode names.
        // Calls a method
        String gamemodeString = "gameMode." + mode.name().toLowerCase(Locale.ROOT);
        // Calls a method
        Component gamemodeComponent = Component.translatable(gamemodeString);

        //Send the translated message to the player.
        // Calls a method
        sender.sendMessage(Component.translatable("commands.gamemode.success.self", gamemodeComponent));
    // End of a block/expression
    }
// End of a block/expression
}
