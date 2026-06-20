// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.batch.RelativeBlockBatch;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryType;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.EnchantmentList;
// Import d'une classe nécessaire
import net.minestom.server.item.enchant.Enchantment;
// Import d'une classe nécessaire
import net.minestom.server.potion.Potion;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class TestInstabreakCommand extends Command {

    // Début d'une méthode/d'un bloc
    public TestInstabreakCommand() {
        // Accès à l'objet courant/parent
        super("testinstabreak");

        // Appelle une méthode
        ArgumentInteger level = ArgumentType.Integer("level");
        // Début d'une méthode/d'un bloc
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;

            // Appelle une méthode
            int l = context.get(level);
            // Appelle une méthode
            player.removeEffect(PotionEffect.HASTE);
            // Embranchement : vérifie une condition
            if (l != 0) {
                // Appelle une méthode
                player.addEffect(new Potion(PotionEffect.HASTE, (byte) (l - 1), -1));
            // Fin d'un bloc/d'une expression
            }
        // Appelle une méthode
        }, ArgumentType.Literal("haste"), level);
        // Début d'une méthode/d'un bloc
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;

            // Appelle une méthode
            int l = context.get(level);
            // Appelle une méthode
            player.removeEffect(PotionEffect.CONDUIT_POWER);
            // Embranchement : vérifie une condition
            if (l != 0) {
                // Appelle une méthode
                player.addEffect(new Potion(PotionEffect.CONDUIT_POWER, (byte) (l - 1), -1));
            // Fin d'un bloc/d'une expression
            }
        // Appelle une méthode
        }, ArgumentType.Literal("conduit"), level);
        // Début d'une méthode/d'un bloc
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;

            // Appelle une méthode
            int l = context.get(level);
            // Appelle une méthode
            player.removeEffect(PotionEffect.MINING_FATIGUE);
            // Embranchement : vérifie une condition
            if (l != 0) {
                // Appelle une méthode
                player.addEffect(new Potion(PotionEffect.MINING_FATIGUE, (byte) (l - 1), -1));
            // Fin d'un bloc/d'une expression
            }
        // Appelle une méthode
        }, ArgumentType.Literal("fatigue"), level);

        // Début d'une méthode/d'un bloc
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;
            // Appelle une méthode
            giveItems(player);
        // Appelle une méthode
        }, ArgumentType.Literal("giveItems"));

        // Début d'une méthode/d'un bloc
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;
            // Appelle une méthode
            player.openInventory(new Inventory(InventoryType.ANVIL, Component.translatable("container.repair")));
        // Appelle une méthode
        }, ArgumentType.Literal("anvil"));

        // Appelle une méthode
        RelativeBlockBatch areaBatch = new RelativeBlockBatch();
        // Boucle : répète un bloc
        for (int x = -20; x < 21; x++) {
            // Boucle : répète un bloc
            for (int z = -20; z < 21; z++) {
                // Boucle : répète un bloc
                for (int y = -10; y < 0; y++) {
                    // Appelle une méthode
                    areaBatch.setBlock(x, y, z, Block.WHITE_WOOL);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        areaBatch.setBlock(2, 0, 0, Block.WATER);
        // Appelle une méthode
        areaBatch.setBlock(3, 0, 0, Block.WATER);
        // Appelle une méthode
        areaBatch.setBlock(2, 0, 1, Block.WATER);
        // Appelle une méthode
        areaBatch.setBlock(3, 0, 1, Block.WATER);
        // Appelle une méthode
        areaBatch.setBlock(5, 1, 0, Block.WATER);
        // Appelle une méthode
        areaBatch.setBlock(6, 1, 0, Block.WATER);
        // Appelle une méthode
        areaBatch.setBlock(5, 1, 1, Block.WATER);
        // Appelle une méthode
        areaBatch.setBlock(6, 1, 1, Block.WATER);
        // Appelle une méthode
        areaBatch.setBlock(8, 1, 1, Block.WATER.withProperty("level", "0"));
        // Appelle une méthode
        areaBatch.setBlock(10, 1, 1, Block.WATER.withProperty("level", "1"));
        // Appelle une méthode
        areaBatch.setBlock(8, 1, 3, Block.WATER.withProperty("level", "2"));
        // Appelle une méthode
        areaBatch.setBlock(10, 1, 3, Block.WATER.withProperty("level", "3"));
        // Appelle une méthode
        areaBatch.setBlock(8, 1, 5, Block.WATER.withProperty("level", "4"));
        // Appelle une méthode
        areaBatch.setBlock(10, 1, 5, Block.WATER.withProperty("level", "5"));
        // Appelle une méthode
        areaBatch.setBlock(8, 1, 7, Block.WATER.withProperty("level", "6"));
        // Appelle une méthode
        areaBatch.setBlock(10, 1, 7, Block.WATER.withProperty("level", "7"));
        // Appelle une méthode
        areaBatch.setBlock(8, 1, 9, Block.WATER.withProperty("level", "8"));
        // Appelle une méthode
        areaBatch.setBlock(10, 1, 9, Block.WATER.withProperty("level", "13"));
        // Boucle : répète un bloc
        for (int x = -3; x < 0; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z < 0; z++) {
                // Boucle : répète un bloc
                for (int y = 0; y < 4; y++) {
                    // Appelle une méthode
                    areaBatch.setBlock(x, y, z, Block.WATER);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (int x = -9; x < -6; x++) {
            // Boucle : répète un bloc
            for (int z = -9; z < -6; z++) {
                // Boucle : répète un bloc
                for (int y = 0; y < 3; y++) {
                    // Appelle une méthode
                    areaBatch.setBlock(x, y, z, Block.BAMBOO);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                areaBatch.setBlock(x, 3, z, Block.BAMBOO_SAPLING);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }


        // Début d'une méthode/d'un bloc
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;
            // Appelle une méthode
            areaBatch.apply(player.getInstance(), player.getPosition(), null);
        // Appelle une méthode
        }, ArgumentType.Literal("placeArea"));

        // Début d'une méthode/d'un bloc
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;
            // Appelle une méthode
            boolean state = context.get("state");
            // Appelle une méthode
            player.setInstantBreak(state);
        // Appelle une méthode
        }, ArgumentType.Literal("instabreak"), ArgumentType.Boolean("state"));

        // Début d'une méthode/d'un bloc
        addConditionalSyntax((sender, commandString) -> sender instanceof Player, (sender, context) -> {
            // Affecte une valeur
            Player player = (Player) sender;
            // Appelle une méthode
            player.setGameMode(GameMode.SURVIVAL);
            // Appelle une méthode
            player.getInventory().clear();
            // Appelle une méthode
            giveItems(player);
            // Appelle une méthode
            areaBatch.apply(player.getInstance(), player.getPosition(), null);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void giveItems(Player player) {
        // Affecte une valeur
        List<ItemStack> items = new ArrayList<>();
        // Appelle une méthode
        items.add(ItemStack.builder(Material.SHEARS).set(DataComponents.ENCHANTMENTS, EnchantmentList.EMPTY.with(Enchantment.EFFICIENCY, 5)).build());
        // Appelle une méthode
        items.add(ItemStack.builder(Material.WHITE_WOOL).amount(64).build());
        // Appelle une méthode
        items.add(ItemStack.builder(Material.STONE).amount(64).build());
        // Appelle une méthode
        items.add(ItemStack.of(Material.DIAMOND_SWORD));
        // Appelle une méthode
        items.add(ItemStack.of(Material.DIAMOND_PICKAXE));
        // Boucle : répète un bloc
        for (ItemStack item : items) {
            // Appelle une méthode
            player.getInventory().addItemStack(item);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
