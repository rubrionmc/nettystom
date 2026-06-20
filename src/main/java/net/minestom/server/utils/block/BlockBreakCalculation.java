// Package declaration for this file
package net.minestom.server.utils.block;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.fluid.Fluid;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.Tool;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.registry.TagKey;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class BlockBreakCalculation {

    // Assigns a value
    public static final int UNBREAKABLE = -1;
    // Calls a method
    private static final RegistryTag<Fluid> WATER_TAG = Fluid.staticRegistry().getOrCreateTag(TagKey.ofHash("#minecraft:water"));
    // The vanilla client checks for bamboo breaking speed with item instanceof SwordItem.
    // We could either check all sword ID's, or the sword tag.
    // Since tags are immutable, checking the tag seems easier to understand
    // Calls a method
    private static final RegistryTag<Material> SWORD_TAG = Material.staticRegistry().getOrCreateTag(TagKey.ofHash("#minecraft:swords"));

    /**
     * Calculates the block break time in ticks
     *
     * @return the block break time in ticks, -1 if the block is unbreakable
     */
    // Start of a method/block
    public static int breakTicks(Block block, Player player) {
        // Branch: checks a condition
        if (player.getGameMode() == GameMode.CREATIVE) {
            // Creative can always break blocks instantly
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }
        // Taken from minecraft wiki Breaking#Calculation
        // https://minecraft.wiki/w/Breaking#Calculation
        // More information to mimic calculations taken from minecraft's source
        // Calls a method
        RegistryData.BlockEntry registry = block.registry();
        // Calls a method
        float blockHardness = registry.hardness();
        // Branch: checks a condition
        if (blockHardness == -1) {
            // Bedrock, barrier, and unbreakable blocks
            // Returns a value to the caller
            return UNBREAKABLE;
        // End of a block/expression
        }
        // Calls a method
        ItemStack item = player.getItemInMainHand();
        // Bamboo is hard-coded in client
        // Branch: checks a condition
        if (block.id() == Block.BAMBOO.id() || block.id() == Block.BAMBOO_SAPLING.id()) {
            // Branch: checks a condition
            if (SWORD_TAG.contains(item.material())) {
                // Returns a value to the caller
                return 0;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        Tool tool = item.get(DataComponents.TOOL);
        // Calls a method
        boolean isBestTool = canBreakBlock(tool, block);
        // Code statement
        float speedMultiplier;

        // Branch: checks a condition
        if (isBestTool) {
            // Calls a method
            speedMultiplier = getMiningSpeed(tool, block);

            // wiki seems to be incorrect here, taken from minecraft's code
            // Branch: checks a condition
            if (speedMultiplier > 1F) {
                // since data driven enchantments efficiency uses the PLAYER_MINING_EFFICIENCY attribute
                // If someone wants faster tools, they have to use player attributes or the TOOL component
                // Calls a method
                speedMultiplier += (float) player.getAttributeValue(Attribute.MINING_EFFICIENCY);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Assigns a value
            speedMultiplier = 1;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (player.hasEffect(PotionEffect.HASTE) || player.hasEffect(PotionEffect.CONDUIT_POWER)) {
            // Yes, conduit power is same as haste. I also had to go confirm, because I couldn't believe it
            // Calls a method
            speedMultiplier *= getHasteMultiplier(player);
        // End of a block/expression
        }

        // Branch: checks a condition
        if (player.hasEffect(PotionEffect.MINING_FATIGUE)) {
            // Calls a method
            speedMultiplier *= getMiningFatigueMultiplier(player);
        // End of a block/expression
        }

        // Calls a method
        speedMultiplier *= (float) player.getAttributeValue(Attribute.BLOCK_BREAK_SPEED);

        // Branch: checks a condition
        if (isInWater(player)) {
            // Calls a method
            speedMultiplier *= (float) player.getAttributeValue(Attribute.SUBMERGED_MINING_SPEED);
        // End of a block/expression
        }

        // Branch: checks a condition
        if (!player.isOnGround()) {
            // Code statement
            speedMultiplier /= 5;
        // End of a block/expression
        }

        // if speed multiplier is 0, the block is unbreakable
        // Branch: checks a condition
        if (speedMultiplier == 0) {
            // Returns a value to the caller
            return UNBREAKABLE;
        // End of a block/expression
        }

        // prevent division by zero
        // Branch: checks a condition
        if (blockHardness == 0) {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }

        // Assigns a value
        double damage = speedMultiplier / blockHardness;
        // Branch: checks a condition
        if (isBestTool) {
            // Code statement
            damage /= 30;
        // Alternative branch of the condition
        } else {
            // Code statement
            damage /= 100;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (damage >= 1) {
            // Instant breaking
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }

        // Returns a value to the caller
        return (int) Math.ceil(1 / damage);
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean isInWater(Player player) {
        // Calls a method
        Pos pos = player.getPosition();
        // Calls a method
        Instance instance = player.getInstance();
        // Calls a method
        double eyeY = pos.y() + player.getEyeHeight();
        // Calls a method
        int x = pos.blockX();
        // Calls a method
        int y = (int) Math.floor(eyeY);
        // Calls a method
        int z = pos.blockZ();
        // Calls a method
        Pos eye = player.getPosition().add(0, player.getEyeHeight(), 0);
        // Calls a method
        Block block = instance.getBlock(eye);

        // Calls a method
        final Fluid fluid = Fluid.fromKey(block.key());
        // Branch: checks a condition
        if (fluid == null || !WATER_TAG.contains(fluid)) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        float fluidHeight = getFluidHeight(player.getInstance(), x, y, z, block);
        // Returns a value to the caller
        return eyeY < y + fluidHeight;
    // End of a block/expression
    }

    // Start of a method/block
    private static float getFluidHeight(Instance instance, int x, int y, int z, Block block) {
        // Calls a method
        Block blockAbove = instance.getBlock(x, y + 1, z);
        // Branch: checks a condition
        if (blockAbove.id() == block.id()) {
            // Full block if block above is same type
            // Returns a value to the caller
            return 1F;
        // End of a block/expression
        }
        // We gotta be extra careful, someone could modify properties of the block!
        // Calls a method
        String levelString = block.getProperty("level");
        // Branch: checks a condition
        if (levelString == null) {
            // Something is weird, return a full block
            // Returns a value to the caller
            return 1F;
        // End of a block/expression
        }

        // Code statement
        int level;
        // Exception handling
        try {
            // Calls a method
            level = Integer.parseInt(levelString);
        // Start of a method/block
        } catch (Throwable ignored) {
            // Returns a value to the caller
            return 1;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (level >= 8) {
            // These levels are as high as source blocks, but are for flowing water
            // Set the level to 0 for full source block calculation
            // Assigns a value
            level = 0;
        // End of a block/expression
        }
        // Returns a value to the caller
        return (8 - level) / 9F;
    // End of a block/expression
    }

    // Start of a method/block
    private static float getMiningFatigueMultiplier(Player player) {
        // Calls a method
        int level = player.getEffectLevel(PotionEffect.MINING_FATIGUE) + 1;
        // Use switch to avoid expensive Math.pow
        // Returns a value to the caller
        return switch (level) { // 0.3 ^ min(level, 4)
            // Multiple branching (switch/case)
            case 0 -> 0;
            // Multiple branching (switch/case)
            case 1 -> 0.3F; // 0.3 ^ 1
            // Multiple branching (switch/case)
            case 2 -> 0.09F; // 0.3 ^ 2
            // Multiple branching (switch/case)
            case 3 -> 0.027F; // 0.3 ^ 3
            // Multiple branching (switch/case)
            default -> 0.0081F; // 0.3 ^ 4
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static float getHasteMultiplier(Player player) {
        // Add 1 to potion level for correct calculation
        // Calls a method
        float level = Math.max(player.getEffectLevel(PotionEffect.HASTE), player.getEffectLevel(PotionEffect.CONDUIT_POWER)) + 1;
        // Returns a value to the caller
        return (1F + 0.2F * level);
    // End of a block/expression
    }

    // Start of a method/block
    private static float getMiningSpeed(@Nullable Tool tool, Block block) {
        // Branch: checks a condition
        if (tool == null) {
            // Returns a value to the caller
            return 1;
        // End of a block/expression
        }
        // Returns a value to the caller
        return tool.getSpeed(block);
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean canBreakBlock(@Nullable Tool tool, Block block) {
        // Returns a value to the caller
        return !block.registry().requiresTool() || isEffective(tool, block);
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean isEffective(@Nullable Tool tool, Block block) {
        // Returns a value to the caller
        return tool != null && tool.isCorrectForDrops(block);
    // End of a block/expression
    }
// End of a block/expression
}
