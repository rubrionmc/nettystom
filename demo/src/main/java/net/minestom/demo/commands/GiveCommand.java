// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.inventory.PlayerInventory;
// Import of a required class
import net.minestom.server.inventory.TransactionOption;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.utils.entity.EntityFinder;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.*;

// Type declaration (class/interface/enum/record)
public class GiveCommand extends Command {
    // Start of a method/block
    public GiveCommand() {
        // Access to the current/parent object
        super("give");

        // Code statement
        setDefaultExecutor((sender, context) ->
                // Calls a method
                sender.sendMessage(Component.text("Usage: /give <target> <item> [<count>]")));

        // Start of a method/block
        addSyntax((sender, context) -> {
            // Calls a method
            final EntityFinder entityFinder = context.get("target");
            // Calls a method
            int count = context.get("count");
            // Calls a method
            count = Math.min(count, PlayerInventory.INVENTORY_SIZE * 64);
            // Calls a method
            ItemStack itemStack = context.get("item");

            // Code statement
            List<ItemStack> itemStacks;
            // Branch: checks a condition
            if (count <= 64) {
                // Calls a method
                itemStack = itemStack.withAmount(count);
                // Calls a method
                itemStacks = List.of(itemStack);
            // Alternative branch of the condition
            } else {
                // Calls a method
                itemStacks = new ArrayList<>();
                // Loop: repeats a block
                while (count > 64) {
                    // Calls a method
                    itemStacks.add(itemStack.withAmount(64));
                    // Code statement
                    count -= 64;
                // End of a block/expression
                }
                // Calls a method
                itemStacks.add(itemStack.withAmount(count));
            // End of a block/expression
            }

            // Calls a method
            final List<Entity> targets = entityFinder.find(sender);
            // Loop: repeats a block
            for (Entity target : targets) {
                // Branch: checks a condition
                if (target instanceof Player player) {
                    // Calls a method
                    player.getInventory().addItemStacks(itemStacks, TransactionOption.ALL);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Calls a method
            sender.sendMessage(Component.text("Items have been given successfully!"));

        // Calls a method
        }, Entity("target").onlyPlayers(true), ItemStack("item"), Integer("count").setDefaultValue(() -> 1));

    // End of a block/expression
    }
// End of a block/expression
}
