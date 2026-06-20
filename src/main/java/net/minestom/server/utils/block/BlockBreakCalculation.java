// Déclaration du paquet de ce fichier
package net.minestom.server.utils.block;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.fluid.Fluid;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.Tool;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.registry.TagKey;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class BlockBreakCalculation {

    // Affecte une valeur
    public static final int UNBREAKABLE = -1;
    // Appelle une méthode
    private static final RegistryTag<Fluid> WATER_TAG = Fluid.staticRegistry().getOrCreateTag(TagKey.ofHash("#minecraft:water"));
    // The vanilla client checks for bamboo breaking speed with item instanceof SwordItem.
    // We could either check all sword ID's, or the sword tag.
    // Since tags are immutable, checking the tag seems easier to understand
    // Appelle une méthode
    private static final RegistryTag<Material> SWORD_TAG = Material.staticRegistry().getOrCreateTag(TagKey.ofHash("#minecraft:swords"));

    /**
     * Calculates the block break time in ticks
     *
     * @return the block break time in ticks, -1 if the block is unbreakable
     */
    // Début d'une méthode/d'un bloc
    public static int breakTicks(Block block, Player player) {
        // Embranchement : vérifie une condition
        if (player.getGameMode() == GameMode.CREATIVE) {
            // Creative can always break blocks instantly
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }
        // Taken from minecraft wiki Breaking#Calculation
        // https://minecraft.wiki/w/Breaking#Calculation
        // More information to mimic calculations taken from minecraft's source
        // Appelle une méthode
        RegistryData.BlockEntry registry = block.registry();
        // Appelle une méthode
        float blockHardness = registry.hardness();
        // Embranchement : vérifie une condition
        if (blockHardness == -1) {
            // Bedrock, barrier, and unbreakable blocks
            // Renvoie une valeur à l'appelant
            return UNBREAKABLE;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        ItemStack item = player.getItemInMainHand();
        // Bamboo is hard-coded in client
        // Embranchement : vérifie une condition
        if (block.id() == Block.BAMBOO.id() || block.id() == Block.BAMBOO_SAPLING.id()) {
            // Embranchement : vérifie une condition
            if (SWORD_TAG.contains(item.material())) {
                // Renvoie une valeur à l'appelant
                return 0;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Tool tool = item.get(DataComponents.TOOL);
        // Appelle une méthode
        boolean isBestTool = canBreakBlock(tool, block);
        // Instruction de code
        float speedMultiplier;

        // Embranchement : vérifie une condition
        if (isBestTool) {
            // Appelle une méthode
            speedMultiplier = getMiningSpeed(tool, block);

            // wiki seems to be incorrect here, taken from minecraft's code
            // Embranchement : vérifie une condition
            if (speedMultiplier > 1F) {
                // since data driven enchantments efficiency uses the PLAYER_MINING_EFFICIENCY attribute
                // If someone wants faster tools, they have to use player attributes or the TOOL component
                // Appelle une méthode
                speedMultiplier += (float) player.getAttributeValue(Attribute.MINING_EFFICIENCY);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            speedMultiplier = 1;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (player.hasEffect(PotionEffect.HASTE) || player.hasEffect(PotionEffect.CONDUIT_POWER)) {
            // Yes, conduit power is same as haste. I also had to go confirm, because I couldn't believe it
            // Appelle une méthode
            speedMultiplier *= getHasteMultiplier(player);
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (player.hasEffect(PotionEffect.MINING_FATIGUE)) {
            // Appelle une méthode
            speedMultiplier *= getMiningFatigueMultiplier(player);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        speedMultiplier *= (float) player.getAttributeValue(Attribute.BLOCK_BREAK_SPEED);

        // Embranchement : vérifie une condition
        if (isInWater(player)) {
            // Appelle une méthode
            speedMultiplier *= (float) player.getAttributeValue(Attribute.SUBMERGED_MINING_SPEED);
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (!player.isOnGround()) {
            // Affecte une valeur
            speedMultiplier /= 5;
        // Fin d'un bloc/d'une expression
        }

        // if speed multiplier is 0, the block is unbreakable
        // Embranchement : vérifie une condition
        if (speedMultiplier == 0) {
            // Renvoie une valeur à l'appelant
            return UNBREAKABLE;
        // Fin d'un bloc/d'une expression
        }

        // prevent division by zero
        // Embranchement : vérifie une condition
        if (blockHardness == 0) {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        double damage = speedMultiplier / blockHardness;
        // Embranchement : vérifie une condition
        if (isBestTool) {
            // Affecte une valeur
            damage /= 30;
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            damage /= 100;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (damage >= 1) {
            // Instant breaking
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return (int) Math.ceil(1 / damage);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean isInWater(Player player) {
        // Appelle une méthode
        Pos pos = player.getPosition();
        // Appelle une méthode
        Instance instance = player.getInstance();
        // Boucle : répète un bloc
        double eyeY = pos.y() + player.getEyeHeight();
        // Appelle une méthode
        int x = pos.blockX();
        // Appelle une méthode
        int y = (int) Math.floor(eyeY);
        // Appelle une méthode
        int z = pos.blockZ();
        // Appelle une méthode
        Pos eye = player.getPosition().add(0, player.getEyeHeight(), 0);
        // Appelle une méthode
        Block block = instance.getBlock(eye);

        // Appelle une méthode
        final Fluid fluid = Fluid.fromKey(block.key());
        // Embranchement : vérifie une condition
        if (fluid == null || !WATER_TAG.contains(fluid)) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        float fluidHeight = getFluidHeight(player.getInstance(), x, y, z, block);
        // Renvoie une valeur à l'appelant
        return eyeY < y + fluidHeight;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static float getFluidHeight(Instance instance, int x, int y, int z, Block block) {
        // Appelle une méthode
        Block blockAbove = instance.getBlock(x, y + 1, z);
        // Embranchement : vérifie une condition
        if (blockAbove.id() == block.id()) {
            // Full block if block above is same type
            // Renvoie une valeur à l'appelant
            return 1F;
        // Fin d'un bloc/d'une expression
        }
        // We gotta be extra careful, someone could modify properties of the block!
        // Appelle une méthode
        String levelString = block.getProperty("level");
        // Embranchement : vérifie une condition
        if (levelString == null) {
            // Something is weird, return a full block
            // Renvoie une valeur à l'appelant
            return 1F;
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        int level;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            level = Integer.parseInt(levelString);
        // Début d'une méthode/d'un bloc
        } catch (Throwable ignored) {
            // Renvoie une valeur à l'appelant
            return 1;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (level >= 8) {
            // These levels are as high as source blocks, but are for flowing water
            // Set the level to 0 for full source block calculation
            // Affecte une valeur
            level = 0;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return (8 - level) / 9F;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static float getMiningFatigueMultiplier(Player player) {
        // Appelle une méthode
        int level = player.getEffectLevel(PotionEffect.MINING_FATIGUE) + 1;
        // Use switch to avoid expensive Math.pow
        // Renvoie une valeur à l'appelant
        return switch (level) { // 0.3 ^ min(level, 4)
            // Embranchement multiple (switch/case)
            case 0 -> 0;
            // Embranchement multiple (switch/case)
            case 1 -> 0.3F; // 0.3 ^ 1
            // Embranchement multiple (switch/case)
            case 2 -> 0.09F; // 0.3 ^ 2
            // Embranchement multiple (switch/case)
            case 3 -> 0.027F; // 0.3 ^ 3
            // Instruction de code
            default -> 0.0081F; // 0.3 ^ 4
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static float getHasteMultiplier(Player player) {
        // Add 1 to potion level for correct calculation
        // Appelle une méthode
        float level = Math.max(player.getEffectLevel(PotionEffect.HASTE), player.getEffectLevel(PotionEffect.CONDUIT_POWER)) + 1;
        // Renvoie une valeur à l'appelant
        return (1F + 0.2F * level);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static float getMiningSpeed(@Nullable Tool tool, Block block) {
        // Embranchement : vérifie une condition
        if (tool == null) {
            // Renvoie une valeur à l'appelant
            return 1;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return tool.getSpeed(block);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean canBreakBlock(@Nullable Tool tool, Block block) {
        // Renvoie une valeur à l'appelant
        return !block.registry().requiresTool() || isEffective(tool, block);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean isEffective(@Nullable Tool tool, Block block) {
        // Renvoie une valeur à l'appelant
        return tool != null && tool.isCorrectForDrops(block);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
