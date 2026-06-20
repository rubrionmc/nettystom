// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.batch.RelativeBlockBatch;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryType;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.EnchantmentList;
// Import of a required class
import net.minestom.server.item.enchant.Enchantment;
// Import of a required class
import net.minestom.server.potion.Potion;
// Import of a required class
import net.minestom.server.potion.PotionEffect;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class TestInstabreakCommand extends Command {

    // Start of a method/block
    public TestInstabreakCommand() {
        // Access to the current/parent object
        super("testinstabreak");

        // Calls a method
        ArgumentInteger level = ArgumentType.Integer("level");
        // Start of a method/block
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Calls a method
            Player player = (Player) sender;

            // Calls a method
            int l = context.get(level);
            // Calls a method
            player.removeEffect(PotionEffect.HASTE);
            // Branch: checks a condition
            if (l != 0) {
                // Calls a method
                player.addEffect(new Potion(PotionEffect.HASTE, (byte) (l - 1), -1));
            // End of a block/expression
            }
        // Calls a method
        }, ArgumentType.Literal("haste"), level);
        // Start of a method/block
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Calls a method
            Player player = (Player) sender;

            // Calls a method
            int l = context.get(level);
            // Calls a method
            player.removeEffect(PotionEffect.CONDUIT_POWER);
            // Branch: checks a condition
            if (l != 0) {
                // Calls a method
                player.addEffect(new Potion(PotionEffect.CONDUIT_POWER, (byte) (l - 1), -1));
            // End of a block/expression
            }
        // Calls a method
        }, ArgumentType.Literal("conduit"), level);
        // Start of a method/block
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Calls a method
            Player player = (Player) sender;

            // Calls a method
            int l = context.get(level);
            // Calls a method
            player.removeEffect(PotionEffect.MINING_FATIGUE);
            // Branch: checks a condition
            if (l != 0) {
                // Calls a method
                player.addEffect(new Potion(PotionEffect.MINING_FATIGUE, (byte) (l - 1), -1));
            // End of a block/expression
            }
        // Calls a method
        }, ArgumentType.Literal("fatigue"), level);

        // Start of a method/block
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Calls a method
            Player player = (Player) sender;
            // Calls a method
            giveItems(player);
        // Calls a method
        }, ArgumentType.Literal("giveItems"));

        // Start of a method/block
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Calls a method
            Player player = (Player) sender;
            // Calls a method
            player.openInventory(new Inventory(InventoryType.ANVIL, Component.translatable("container.repair")));
        // Calls a method
        }, ArgumentType.Literal("anvil"));

        // Calls a method
        RelativeBlockBatch areaBatch = new RelativeBlockBatch();
        // Loop: repeats a block
        for (int x = -20; x < 21; x++) {
            // Loop: repeats a block
            for (int z = -20; z < 21; z++) {
                // Loop: repeats a block
                for (int y = -10; y < 0; y++) {
                    // Calls a method
                    areaBatch.setBlock(x, y, z, Block.WHITE_WOOL);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        areaBatch.setBlock(2, 0, 0, Block.WATER);
        // Calls a method
        areaBatch.setBlock(3, 0, 0, Block.WATER);
        // Calls a method
        areaBatch.setBlock(2, 0, 1, Block.WATER);
        // Calls a method
        areaBatch.setBlock(3, 0, 1, Block.WATER);
        // Calls a method
        areaBatch.setBlock(5, 1, 0, Block.WATER);
        // Calls a method
        areaBatch.setBlock(6, 1, 0, Block.WATER);
        // Calls a method
        areaBatch.setBlock(5, 1, 1, Block.WATER);
        // Calls a method
        areaBatch.setBlock(6, 1, 1, Block.WATER);
        // Calls a method
        areaBatch.setBlock(8, 1, 1, Block.WATER.withProperty("level", "0"));
        // Calls a method
        areaBatch.setBlock(10, 1, 1, Block.WATER.withProperty("level", "1"));
        // Calls a method
        areaBatch.setBlock(8, 1, 3, Block.WATER.withProperty("level", "2"));
        // Calls a method
        areaBatch.setBlock(10, 1, 3, Block.WATER.withProperty("level", "3"));
        // Calls a method
        areaBatch.setBlock(8, 1, 5, Block.WATER.withProperty("level", "4"));
        // Calls a method
        areaBatch.setBlock(10, 1, 5, Block.WATER.withProperty("level", "5"));
        // Calls a method
        areaBatch.setBlock(8, 1, 7, Block.WATER.withProperty("level", "6"));
        // Calls a method
        areaBatch.setBlock(10, 1, 7, Block.WATER.withProperty("level", "7"));
        // Calls a method
        areaBatch.setBlock(8, 1, 9, Block.WATER.withProperty("level", "8"));
        // Calls a method
        areaBatch.setBlock(10, 1, 9, Block.WATER.withProperty("level", "13"));
        // Loop: repeats a block
        for (int x = -3; x < 0; x++) {
            // Loop: repeats a block
            for (int z = -3; z < 0; z++) {
                // Loop: repeats a block
                for (int y = 0; y < 4; y++) {
                    // Calls a method
                    areaBatch.setBlock(x, y, z, Block.WATER);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Loop: repeats a block
        for (int x = -9; x < -6; x++) {
            // Loop: repeats a block
            for (int z = -9; z < -6; z++) {
                // Loop: repeats a block
                for (int y = 0; y < 3; y++) {
                    // Calls a method
                    areaBatch.setBlock(x, y, z, Block.BAMBOO);
                // End of a block/expression
                }
                // Calls a method
                areaBatch.setBlock(x, 3, z, Block.BAMBOO_SAPLING);
            // End of a block/expression
            }
        // End of a block/expression
        }


        // Start of a method/block
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Calls a method
            Player player = (Player) sender;
            // Calls a method
            areaBatch.apply(player.getInstance(), player.getPosition(), null);
        // Calls a method
        }, ArgumentType.Literal("placeArea"));

        // Start of a method/block
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Calls a method
            Player player = (Player) sender;
            // Calls a method
            boolean state = context.get("state");
            // Calls a method
            player.setInstantBreak(state);
        // Calls a method
        }, ArgumentType.Literal("instabreak"), ArgumentType.Boolean("state"));

        // Start of a method/block
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Calls a method
            Player player = (Player) sender;
            // Calls a method
            player.setGameMode(GameMode.SURVIVAL);
            // Calls a method
            player.getInventory().clear();
            // Calls a method
            giveItems(player);
            // Calls a method
            areaBatch.apply(player.getInstance(), player.getPosition(), null);
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    private void giveItems(Player player) {
        // Calls a method
        List<ItemStack> items = new ArrayList<>();
        // Calls a method
        items.add(ItemStack.builder(Material.SHEARS).set(DataComponents.ENCHANTMENTS, EnchantmentList.EMPTY.with(Enchantment.EFFICIENCY, 5)).build());
        // Calls a method
        items.add(ItemStack.builder(Material.WHITE_WOOL).amount(64).build());
        // Calls a method
        items.add(ItemStack.builder(Material.STONE).amount(64).build());
        // Calls a method
        items.add(ItemStack.of(Material.DIAMOND_SWORD));
        // Calls a method
        items.add(ItemStack.of(Material.DIAMOND_PICKAXE));
        // Loop: repeats a block
        for (ItemStack item : items) {
            // Calls a method
            player.getInventory().addItemStack(item);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
