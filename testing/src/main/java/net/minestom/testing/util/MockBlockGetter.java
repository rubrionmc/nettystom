// Déclaration du paquet de ce fichier
package net.minestom.testing.util;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public final class MockBlockGetter implements Block.Getter, Block.Setter {
    // Début d'une méthode/d'un bloc
    public static MockBlockGetter empty() {
        // Renvoie une valeur à l'appelant
        return new MockBlockGetter(Map.of(), Block.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static MockBlockGetter single(Block block) {
        // Renvoie une valeur à l'appelant
        return new MockBlockGetter(Map.of(Vec.ZERO, block), Block.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static MockBlockGetter all(Block block) {
        // Renvoie une valeur à l'appelant
        return new MockBlockGetter(Map.of(), block);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private final Map<Vec, Block> blocks = new HashMap<>();
    // Instruction de code
    private final Block defaultBlock;

    // Début d'une méthode/d'un bloc
    private MockBlockGetter(Map<Vec, Block> blocks, Block defaultBlock) {
        // Appelle une méthode
        blocks.forEach((pos, block) -> this.blocks.put(new Vec(pos.blockX(), pos.blockY(), pos.blockZ()), block));
        // Accès à l'objet courant/parent
        this.defaultBlock = defaultBlock;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @UnknownNullability Block getBlock(int x, int y, int z, Condition condition) {
        // Renvoie une valeur à l'appelant
        return blocks.getOrDefault(new Vec(x, y, z), defaultBlock);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBlock(int x, int y, int z, Block block) {
        // Appelle une méthode
        blocks.put(new Vec(x, y, z), block);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
