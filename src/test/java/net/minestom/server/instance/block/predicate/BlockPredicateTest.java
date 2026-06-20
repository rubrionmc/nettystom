// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.predicate;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.SuspiciousGravelBlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Nested;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Déclaration de type (classe/interface/enum/record)
public class BlockPredicateTest {

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // See sibling files for blocks and properties tests

    // Annotation pour l'élément suivant
    @Nested
    // Déclaration de type (classe/interface/enum/record)
    class NbtPredicate {
        // Appelle une méthode
        private static final Block SUS_GRAVEL = Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE);

        // Annotation pour l'élément suivant
        @Test
        // Début d'une méthode/d'un bloc
        public void testMatching() {
            // Affecte une valeur
            var predicate = new BlockPredicate(CompoundBinaryTag.builder()
                    // Instruction de code
                    .putString("LootTable", "minecraft:test")
                    // Appelle une méthode
                    .build());
            // Affecte une valeur
            var block = SUS_GRAVEL.withNbt(CompoundBinaryTag.builder()
                    // Instruction de code
                    .putString("LootTable", "minecraft:test")
                    // Appelle une méthode
                    .build());
            // Appelle une méthode
            assertTrue(predicate.test(block));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Début d'une méthode/d'un bloc
        public void testEmptyTarget() {
            // Affecte une valeur
            var predicate = new BlockPredicate(CompoundBinaryTag.builder()
                    // Instruction de code
                    .putString("LootTable", "minecraft:test")
                    // Appelle une méthode
                    .build());
            // Affecte une valeur
            var block = SUS_GRAVEL.withNbt(CompoundBinaryTag.builder()
                    // Appelle une méthode
                    .build());
            // Appelle une méthode
            assertFalse(predicate.test(block));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Début d'une méthode/d'un bloc
        public void testEmptySource() {
            // Appelle une méthode
            var itemNbt = ItemStack.of(Material.STONE).toItemNBT();
            // Affecte une valeur
            var predicate = new BlockPredicate(CompoundBinaryTag.builder()
                    // Instruction de code
                    .putString("LootTable", "minecraft:test")
                    // Instruction de code
                    .put("item", itemNbt)
                    // Appelle une méthode
                    .build());
            // Affecte une valeur
            var block = SUS_GRAVEL.withNbt(CompoundBinaryTag.builder()
                    // Instruction de code
                    .putString("LootTable", "minecraft:test")
                    // Instruction de code
                    .put("item", itemNbt)
                    // Appelle une méthode
                    .build());
            // Appelle une méthode
            assertTrue(predicate.test(block));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Début d'une méthode/d'un bloc
        public void testNoMatchDeep() {
            // Appelle une méthode
            var itemNbt1 = ItemStack.of(Material.STONE).toItemNBT();
            // Appelle une méthode
            var itemNbt2 = ItemStack.of(Material.STONE).withAmount(2).toItemNBT();
            // Affecte une valeur
            var predicate = new BlockPredicate(CompoundBinaryTag.builder()
                    // Instruction de code
                    .putString("LootTable", "minecraft:test")
                    // Instruction de code
                    .put("item", itemNbt1)
                    // Appelle une méthode
                    .build());
            // Affecte une valeur
            var block = SUS_GRAVEL.withNbt(CompoundBinaryTag.builder()
                    // Instruction de code
                    .putString("LootTable", "minecraft:test")
                    // Instruction de code
                    .put("item", itemNbt2)
                    // Appelle une méthode
                    .build());
            // Appelle une méthode
            assertFalse(predicate.test(block));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Début d'une méthode/d'un bloc
        public void testNoBlockEntity() {
            // Never match if the block has no client block entity

            // Appelle une méthode
            var predicate = new BlockPredicate(CompoundBinaryTag.builder().build());
            // Affecte une valeur
            var block = Block.STONE;
            // Appelle une méthode
            assertFalse(predicate.test(block), "stone should not match empty");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Test
        // Début d'une méthode/d'un bloc
        public void testNoExposedTags() {
            // Appelle une méthode
            var predicate = new BlockPredicate(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build());
            // No exposed tags because no block handler so cannot match
            // Instruction de code
            assertFalse(predicate.test(Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE_NO_TAGS)
                    // Appelle une méthode
                    .withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build())));

            // In this case its fine because when there is no block handler we send the entire block entity
            // Appelle une méthode
            assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build())));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }


    // Combinations

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void emptyMatchAnything() {
        // Appelle une méthode
        var predicate = new BlockPredicate(null, null, null);
        // Appelle une méthode
        assertTrue(predicate.test(Block.STONE_STAIRS));
        // Appelle une méthode
        assertTrue(predicate.test(Block.STONE_STAIRS.withProperty("facing", "east")));
        // Appelle une méthode
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE)));
        // Appelle une méthode
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withNbt(CompoundBinaryTag.builder().build())));
        // Appelle une méthode
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:test").build())));
        // Instruction de code
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE)
                // Appelle une méthode
                .withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:test").build())));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockAlone() {
        // Appelle une méthode
        var predicate = new BlockPredicate(Block.STONE);
        // Appelle une méthode
        assertTrue(predicate.test(Block.STONE));
        // Appelle une méthode
        assertFalse(predicate.test(Block.DIRT));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void propsAlone() {
        // Appelle une méthode
        var predicate = new BlockPredicate(PropertiesPredicate.exact("facing", "east"));
        // Appelle une méthode
        assertTrue(predicate.test(Block.STONE_STAIRS.withProperty("facing", "east")));
        // Appelle une méthode
        assertTrue(predicate.test(Block.FURNACE.withProperty("facing", "east")));
        // Appelle une méthode
        assertFalse(predicate.test(Block.FURNACE));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nbtAlone() {
        // Appelle une méthode
        var predicate = new BlockPredicate(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build());
        // Instruction de code
        assertTrue(predicate.test(Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE)
                // Appelle une méthode
                .withNbt(CompoundBinaryTag.builder().putString("LootTable", "minecraft:stone").build())));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
