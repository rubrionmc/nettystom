// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class CookieCommand extends Command {
    // Start of a method/block
    public CookieCommand() {
        // Access to the current/parent object
        super("cookie");

        // Calls a method
        addSubcommand(new Store());
        // Calls a method
        addSubcommand(new Fetch());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public static class Store extends Command {
        // Calls a method
        private final Argument<Key> keyArg = ArgumentType.ResourceLocation("key");
        // Calls a method
        private final Argument<String[]> valueArg = ArgumentType.StringArray("value");

        // Start of a method/block
        public Store() {
            // Access to the current/parent object
            super("store");

            // Calls a method
            addSyntax(this::store, keyArg, valueArg);
        // End of a block/expression
        }

        // Start of a method/block
        private void store(CommandSender sender, CommandContext context) {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;

            // Calls a method
            String key = context.get(keyArg).asString();
            // Calls a method
            byte[] value = String.join(" ", context.get(valueArg)).getBytes();

            // Calls a method
            player.getPlayerConnection().storeCookie(key, value);
            // Calls a method
            player.sendMessage(key + " stored");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public static class Fetch extends Command {
        // Calls a method
        private final Argument<Key> keyArg = ArgumentType.ResourceLocation("key");

        // Start of a method/block
        public Fetch() {
            // Access to the current/parent object
            super("fetch");

            // Calls a method
            addSyntax(this::fetch, keyArg);
        // End of a block/expression
        }

        // Start of a method/block
        private void fetch(CommandSender sender, CommandContext context) {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;

            // Calls a method
            String key = context.get(keyArg).asString();

            // Start of a method/block
            player.getPlayerConnection().fetchCookie(key).thenAccept(value -> {
                // Branch: checks a condition
                if (value == null) {
                    // Calls a method
                    player.sendMessage(key + ": null");
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    player.sendMessage(key + ": " + new String(value));
                // End of a block/expression
                }
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
