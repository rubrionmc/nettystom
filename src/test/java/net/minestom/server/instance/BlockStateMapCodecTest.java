// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;

// Import statique d'un membre
import static net.minestom.server.instance.block.Block.STATE_STRUCT_CODEC;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class BlockStateMapCodecTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEquivalence() throws IOException {
        // Affecte une valeur
        String particleString = "{Properties:{lit:\"true\"},Name:\"copper_bulb\"}";
        // Appelle une méthode
        CompoundBinaryTag nbt = MinestomAdventure.NBT_CODEC.decode(particleString);
        // Appelle une méthode
        Block block = STATE_STRUCT_CODEC.decode(Transcoder.NBT,nbt).orElseThrow();
        // Appelle une méthode
        assertEquals("true", block.getProperty("lit"));
        // Appelle une méthode
        assertEquals(block.defaultState().getProperty("powered"), block.getProperty("powered"));
        // Appelle une méthode
        assertEquals("minecraft:copper_bulb", block.name());
        // Appelle une méthode
        BinaryTag newNBT = STATE_STRUCT_CODEC.encode(Transcoder.NBT,block).orElseThrow();
        // Appelle une méthode
        String newString = MinestomAdventure.NBT_CODEC.encode((CompoundBinaryTag) newNBT);
        // Appelle une méthode
        assertEquals(particleString, newString);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDefaultBlockState() throws IOException {
        // Appelle une méthode
        String defaultFacing = Block.SPRUCE_STAIRS.defaultState().getProperty("facing");
        // Instruction de code
        assert defaultFacing != null;
        // Appelle une méthode
        Block block = Block.SPRUCE_STAIRS.withProperty("facing", defaultFacing);
        // Appelle une méthode
        BinaryTag nbt = STATE_STRUCT_CODEC.encode(Transcoder.NBT, block).orElseThrow();
        // Appelle une méthode
        String nbtString = MinestomAdventure.NBT_CODEC.encode((CompoundBinaryTag) nbt);
        // Appelle une méthode
        assertEquals("{Name:\"spruce_stairs\"}", nbtString);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPropertyBlockState() throws IOException {
        // Appelle une méthode
        Block block = Block.SPRUCE_STAIRS.withProperty("facing", "south");
        // Appelle une méthode
        BinaryTag nbt = STATE_STRUCT_CODEC.encode(Transcoder.NBT, block).orElseThrow();
        // Appelle une méthode
        String nbtString = MinestomAdventure.NBT_CODEC.encode((CompoundBinaryTag) nbt);
        // Appelle une méthode
        assertEquals("{Properties:{facing:\"south\"},Name:\"spruce_stairs\"}", nbtString);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
