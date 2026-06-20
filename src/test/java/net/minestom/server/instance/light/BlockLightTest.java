// Déclaration du paquet de ce fichier
package net.minestom.server.instance.light;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.fail;

// Déclaration de type (classe/interface/enum/record)
public class BlockLightTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        var result = LightCompute.compute(palette, BlockLight.buildInternalQueue(palette));
        // Boucle : répète un bloc
        for (byte light : result) {
            // Appelle une méthode
            assertEquals(0, light);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void glowstone() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(0, 1, 0, Block.GLOWSTONE.stateId());
        // Instruction de code
        assertLight(palette, Map.of(
                // Crée un nouvel objet
                new Vec(0, 1, 0), 15,
                // Crée un nouvel objet
                new Vec(0, 1, 1), 14,
                // Crée un nouvel objet
                new Vec(0, 1, 2), 13));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doubleGlowstone() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(0, 1, 0, Block.GLOWSTONE.stateId());
        // Appelle une méthode
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());

        // Instruction de code
        assertLight(palette, Map.of(
                // Crée un nouvel objet
                new Vec(1, 1, 3), 11,
                // Crée un nouvel objet
                new Vec(3, 3, 7), 9,
                // Crée un nouvel objet
                new Vec(1, 1, 1), 13,
                // Crée un nouvel objet
                new Vec(3, 1, 4), 14));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void glowstoneBorder() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(0, 1, 0, Block.GLOWSTONE.stateId());
        // Instruction de code
        assertLight(palette, Map.of(
                // X axis
                // Crée un nouvel objet
                new Vec(-1, 0, 0), 13,
                // Crée un nouvel objet
                new Vec(-1, 1, 0), 14,
                // Crée un nouvel objet
                new Vec(-1, 2, 0), 13,
                // Crée un nouvel objet
                new Vec(-1, 3, 0), 12,
                // Z axis
                // Crée un nouvel objet
                new Vec(0, 0, -1), 13,
                // Crée un nouvel objet
                new Vec(0, 1, -1), 14,
                // Crée un nouvel objet
                new Vec(0, 2, -1), 13,
                // Crée un nouvel objet
                new Vec(0, 3, -1), 12));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void glowstoneBlock() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(0, 1, 0, Block.GLOWSTONE.stateId());
        // Appelle une méthode
        palette.set(0, 1, 1, Block.STONE.stateId());
        // Instruction de code
        assertLight(palette, Map.of(
                // Crée un nouvel objet
                new Vec(0, 1, 0), 15,
                // Crée un nouvel objet
                new Vec(0, 1, 1), 0,
                // Crée un nouvel objet
                new Vec(0, 1, 2), 11));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void isolated() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());

        // Appelle une méthode
        palette.set(3, 1, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Instruction de code
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Instruction de code
                entry(new Vec(4, 1, 4), 15),
                // Isolation
                // Instruction de code
                entry(new Vec(3, 1, 4), 0),
                // Instruction de code
                entry(new Vec(4, 1, 5), 0),
                // Instruction de code
                entry(new Vec(4, 1, 3), 0),
                // Instruction de code
                entry(new Vec(5, 1, 4), 0),
                // Instruction de code
                entry(new Vec(4, 2, 4), 0),
                // Instruction de code
                entry(new Vec(4, 0, 4), 0),
                // Outside location
                // Appelle une méthode
                entry(new Vec(2, 2, 3), 0)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void isolatedStair() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());
        // Instruction de code
        palette.set(3, 1, 4, Block.OAK_STAIRS.withProperties(Map.of(
                // Instruction de code
                "facing", "east",
                // Instruction de code
                "half", "bottom",
                // Appelle une méthode
                "shape", "straight")).stateId());
        // Appelle une méthode
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Instruction de code
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Instruction de code
                entry(new Vec(4, 1, 4), 15),
                // Front of stair
                // Appelle une méthode
                entry(new Vec(2, 1, 4), 0)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void isolatedStairOpposite() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());
        // Instruction de code
        palette.set(3, 1, 4, Block.OAK_STAIRS.withProperties(Map.of(
                // Instruction de code
                "facing", "west",
                // Instruction de code
                "half", "bottom",
                // Appelle une méthode
                "shape", "straight")).stateId());
        // Appelle une méthode
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Instruction de code
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Instruction de code
                entry(new Vec(4, 1, 4), 15),
                // Stair
                // Instruction de code
                entry(new Vec(3, 1, 4), 14),
                // Front of stair
                // Instruction de code
                entry(new Vec(2, 1, 4), 11),
                // Others
                // Instruction de code
                entry(new Vec(3, 0, 5), 12),
                // Appelle une méthode
                entry(new Vec(3, 0, 3), 12)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void isolatedStairWest() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());
        // Instruction de code
        palette.set(3, 1, 4, Block.OAK_STAIRS.withProperties(Map.of(
                // Instruction de code
                "facing", "west",
                // Instruction de code
                "half", "bottom",
                // Appelle une méthode
                "shape", "straight")).stateId());
        // Appelle une méthode
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Instruction de code
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Instruction de code
                entry(new Vec(4, 1, 4), 15),
                // Stair
                // Instruction de code
                entry(new Vec(3, 1, 4), 14),
                // Front of stair
                // Instruction de code
                entry(new Vec(2, 1, 4), 11),
                // Others
                // Instruction de code
                entry(new Vec(3, 0, 5), 12),
                // Instruction de code
                entry(new Vec(3, 0, 3), 12),
                // Instruction de code
                entry(new Vec(3, 2, 4), 13),
                // Instruction de code
                entry(new Vec(3, -1, 4), 10),
                // Appelle une méthode
                entry(new Vec(2, 0, 4), 10)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void isolatedStairSouth() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());
        // Instruction de code
        palette.set(3, 1, 4, Block.OAK_STAIRS.withProperties(Map.of(
                // Instruction de code
                "facing", "south",
                // Instruction de code
                "half", "bottom",
                // Appelle une méthode
                "shape", "straight")).stateId());
        // Appelle une méthode
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Appelle une méthode
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Instruction de code
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Instruction de code
                entry(new Vec(4, 1, 4), 15),
                // Stair
                // Instruction de code
                entry(new Vec(3, 1, 4), 14),
                // Front of stair
                // Instruction de code
                entry(new Vec(2, 1, 4), 13),
                // Others
                // Instruction de code
                entry(new Vec(3, 0, 5), 10),
                // Appelle une méthode
                entry(new Vec(3, 0, 3), 12)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void assertLight(Palette palette, Map<Vec, Integer> expectedLights) {
        // Appelle une méthode
        byte[] result = LightCompute.compute(palette, BlockLight.buildInternalQueue(palette));
        // Affecte une valeur
        List<String> errors = new ArrayList<>();
        // Boucle : répète un bloc
        for (int x = 0; x < 16; x++) {
            // Boucle : répète un bloc
            for (int y = 0; y < 16; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z < 16; z++) {
                    // Appelle une méthode
                    var expected = expectedLights.get(new Vec(x, y, z));
                    // Embranchement : vérifie une condition
                    if (expected != null) {
                        // Appelle une méthode
                        final int light = LightCompute.getLight(result, x, y, z);
                        // Embranchement : vérifie une condition
                        if (light != expected) {
                            // Appelle une méthode
                            errors.add(String.format("Expected %d at [%d,%d,%d] but got %d", expected, x, y, z, light));
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (!errors.isEmpty()) {
            // Appelle une méthode
            StringBuilder sb = new StringBuilder();
            // Boucle : répète un bloc
            for (String s : errors) {
                // Appelle une méthode
                sb.append(s).append("\n");
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            System.err.println(sb);
            // Appelle une méthode
            fail();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
