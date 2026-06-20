// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockUtils;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Déclaration de type (classe/interface/enum/record)
public class BlockClientNbtTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic() {
        // Appelle une méthode
        assertNull(BlockUtils.extractClientNbt(Block.STONE));
        // Appelle une méthode
        assertNull(BlockUtils.extractClientNbt(Block.GRASS_BLOCK));
        // Appelle une méthode
        assertEquals(CompoundBinaryTag.empty(), BlockUtils.extractClientNbt(Block.CHEST));

        // Appelle une méthode
        var nbt = CompoundBinaryTag.builder().putString("test", "test").build();
        // Appelle une méthode
        assertEquals(nbt, BlockUtils.extractClientNbt(Block.CHEST.withNbt(nbt)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void handler() {
        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Collection<Tag<?>> getBlockEntityTags() {
                // Renvoie une valeur à l'appelant
                return List.of(Tag.String("test"));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key getKey() {
                // Renvoie une valeur à l'appelant
                return Key.key("minestom:test");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        var nbt = CompoundBinaryTag.builder().putString("test", "test").build();
        // Appelle une méthode
        assertNull(BlockUtils.extractClientNbt(Block.STONE.withNbt(nbt).withHandler(handler)));
        // Appelle une méthode
        assertEquals(nbt, BlockUtils.extractClientNbt(Block.CHEST.withNbt(nbt).withHandler(handler)));
        // Instruction de code
        assertEquals(nbt, BlockUtils.extractClientNbt(Block.CHEST
                // Instruction de code
                .withNbt(CompoundBinaryTag.builder().putString("test", "test").putString("test2", "test2").build())
                // Appelle une méthode
                .withHandler(handler)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
