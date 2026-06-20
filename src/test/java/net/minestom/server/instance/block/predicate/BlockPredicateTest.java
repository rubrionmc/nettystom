// Package declaration for this file
package net.minestom.server.instance.block.predicate;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.SuspiciousGravelBlockHandler;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.junit.jupiter.api.Nested;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Type declaration (class/interface/enum/record)
public class BlockPredicateTest {

    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // See sibling files for blocks and properties tests

    // Annotation for the following element
    @Nested
    // Type declaration (class/interface/enum/record)
    class NbtPredicate {
        // Calls a method
        private static final Block SUS_GRAVEL = Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE);

        // Annotation for the following element
        @Test
        // Start of a method/block
        public void testMatching() {
            // Assigns a value
            var predicate = new BlockPredicate(CompoundBinaryTag.builder()
                    // Code statement
                    .putString("LootTable", "minecraft:test")
                    // Calls a method
                    .build());
            // Assigns a value
            var block = SUS_GRAVEL.withNbt(CompoundBinaryTag.builder()
                    // Code statement
                    .putString("LootTable", "minecraft:test")
                    // Calls a method
                    .build());
            // Calls a method
            assertTrue(predicate.test(block));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Start of a method/block
        public void testEmptyTarget() {
            // Assigns a value
            var predicate = new BlockPredicate(CompoundBinaryTag.builder()
                    // Code statement
                    .putString("LootTable", "minecraft:test")
                    // Calls a method
                    .build());
            // Assigns a value
            var block = SUS_GRAVEL.withNbt(CompoundBinaryTag.builder()
                    // Calls a method
                    .build());
            // Calls a method
            assertFalse(predicate.test(block));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Start of a method/block
        public void testEmptySource() {
            // Calls a method
            var itemNbt = ItemStack.of(Material.STONE).toItemNBT();
            // Assigns a value
            var predicate = new BlockPredicate(CompoundBinaryTag.builder()
                    // Code statement
                    .putString("LootTable", "minecraft:test")
                    // Code statement
                    .put("item", itemNbt)
                    // Calls a method
                    .build());
            // Assigns a value
            var block = SUS_GRAVEL.withNbt(CompoundBinaryTag.builder()
                    // Code statement
                    .putString("LootTable", "minecraft:test")
                    // Code statement
                    .put("item", itemNbt)
                    // Calls a method
                    .build());
            // Calls a method
            assertTrue(predicate.test(block));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Start of a method/block
        public void testNoMatchDeep() {
            // Calls a method
            var itemNbt1 = ItemStack.of(Material.STONE).toItemNBT();
            // Calls a method
            var itemNbt2 = ItemStack.of(Material.STONE).withAmount(2).toItemNBT();
            // Assigns a value
            var predicate = new BlockPredicate(CompoundBinaryTag.builder()
                    // Code statement
                    .putString("LootTable", "minecraft:test")
                    // Code statement
                    .put("item", itemNbt1)
                    // Calls a method
                    .build());
            // Assigns a value
            var block = SUS_GRAVEL.withNbt(CompoundBinaryTag.builder()
                    // Code statement
                    .putString("LootTable", "minecraft:test")
                    // Code statement
                    .put("item", itemNbt2)
                    // Calls a method
                    .build());
            // Calls a method
            assertFalse(predicate.test(block));
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Start of a method/block
        public void testNoBlockEntity() {
            // Never match if the block has no client block entity

            // Calls a method
            var predicate = new BlockPredicate(CompoundBinaryTag.builder().build());
            // Assigns a value
            var block = Block.STONE;
            // Calls a method
            assertFalse(predicate.test(block), "stone should not match empty");
        // End of a block/expression
        }

        // Annotation for the following element
        @Test
        // Start of a method/block
        public void testNoExposedTags() {
            // Calls a method
            var predicate = new BlockPredicate(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build());
            // No exposed tags because no block handler so cannot match
            // Code statement
            assertFalse(predicate.test(Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE_NO_TAGS)
                    // Calls a method
                    .withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build())));

            // In this case its fine because when there is no block handler we send the entire block entity
            // Calls a method
            assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build())));
        // End of a block/expression
        }
    // End of a block/expression
    }


    // Combinations

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void emptyMatchAnything() {
        // Calls a method
        var predicate = new BlockPredicate(null, null, null);
        // Calls a method
        assertTrue(predicate.test(Block.STONE_STAIRS));
        // Calls a method
        assertTrue(predicate.test(Block.STONE_STAIRS.withProperty("facing", "east")));
        // Calls a method
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE)));
        // Calls a method
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withNbt(CompoundBinaryTag.builder().build())));
        // Calls a method
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:test").build())));
        // Code statement
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE)
                // Calls a method
                .withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:test").build())));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockAlone() {
        // Calls a method
        var predicate = new BlockPredicate(Block.STONE);
        // Calls a method
        assertTrue(predicate.test(Block.STONE));
        // Calls a method
        assertFalse(predicate.test(Block.DIRT));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void propsAlone() {
        // Calls a method
        var predicate = new BlockPredicate(PropertiesPredicate.exact("facing", "east"));
        // Calls a method
        assertTrue(predicate.test(Block.STONE_STAIRS.withProperty("facing", "east")));
        // Calls a method
        assertTrue(predicate.test(Block.FURNACE.withProperty("facing", "east")));
        // Calls a method
        assertFalse(predicate.test(Block.FURNACE));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nbtAlone() {
        // Calls a method
        var predicate = new BlockPredicate(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build());
        // Code statement
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE)
                // Calls a method
                .withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build())));
    // End of a block/expression
    }
// End of a block/expression
}
