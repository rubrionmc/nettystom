// Déclaration du paquet de ce fichier
package net.minestom.server.instance.generator;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.GeneratorImpl.GenSection;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.*;
// Import statique d'un membre
import static net.minestom.server.instance.generator.GeneratorImpl.unit;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class GeneratorTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void unitSize() {
        // Appelle une méthode
        assertDoesNotThrow(() -> dummyUnit(Vec.ZERO, Vec.SECTION));
        // Appelle une méthode
        assertDoesNotThrow(() -> dummyUnit(Vec.SECTION, new Vec(32)));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> dummyUnit(new Vec(15), Vec.ZERO));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> dummyUnit(new Vec(15), new Vec(32)));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> dummyUnit(new Vec(15), new Vec(31)));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> dummyUnit(Vec.ZERO, new Vec(15)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("sectionFloorParam")
    // Début d'une méthode/d'un bloc
    public void sectionFloor(int expected, int input) {
        // Appelle une méthode
        assertEquals(expected, floorSection(input), "floorSection(" + input + ")");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Stream<Arguments> sectionFloorParam() {
        // Renvoie une valeur à l'appelant
        return Stream.of(Arguments.of(-32, -32),
                // Instruction de code
                Arguments.of(-32, -31),
                // Instruction de code
                Arguments.of(-32, -17),
                // Instruction de code
                Arguments.of(-16, -16),
                // Instruction de code
                Arguments.of(-16, -15),
                // Instruction de code
                Arguments.of(0, 0),
                // Instruction de code
                Arguments.of(0, 1),
                // Instruction de code
                Arguments.of(0, 2),
                // Instruction de code
                Arguments.of(16, 16),
                // Appelle une méthode
                Arguments.of(16, 17));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("sectionCeilParam")
    // Début d'une méthode/d'un bloc
    public void sectionCeil(int expected, int input) {
        // Appelle une méthode
        assertEquals(expected, ceilSection(input), "ceilSection(" + input + ")");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Stream<Arguments> sectionCeilParam() {
        // Renvoie une valeur à l'appelant
        return Stream.of(Arguments.of(-32, -32),
                // Instruction de code
                Arguments.of(-16, -31),
                // Instruction de code
                Arguments.of(-16, -17),
                // Instruction de code
                Arguments.of(-16, -16),
                // Instruction de code
                Arguments.of(-0, -15),
                // Instruction de code
                Arguments.of(0, 0),
                // Instruction de code
                Arguments.of(16, 1),
                // Instruction de code
                Arguments.of(16, 2),
                // Instruction de code
                Arguments.of(16, 16),
                // Appelle une méthode
                Arguments.of(32, 17));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkSize() {
        // Affecte une valeur
        final int minSection = 0;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        GenerationUnit chunk = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Appelle une méthode
        assertEquals(new Vec(16, sectionCount * 16, 16), chunk.size());
        // Appelle une méthode
        assertEquals(new Vec(chunkX * 16, minSection * 16, chunkZ * 16), chunk.absoluteStart());
        // Appelle une méthode
        assertEquals(new Vec(chunkX * 16 + 16, maxSection * 16, chunkZ * 16 + 16), chunk.absoluteEnd());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkSizeNeg() {
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        GenerationUnit chunk = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Appelle une méthode
        assertEquals(new Vec(16, sectionCount * 16, 16), chunk.size());
        // Appelle une méthode
        assertEquals(new Vec(chunkX * 16, minSection * 16, chunkZ * 16), chunk.absoluteStart());
        // Appelle une méthode
        assertEquals(new Vec(chunkX * 16 + 16, maxSection * 16, chunkZ * 16 + 16), chunk.absoluteEnd());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionSize() {
        // Affecte une valeur
        final int sectionX = 3;
        // Affecte une valeur
        final int sectionY = -5;
        // Affecte une valeur
        final int sectionZ = -2;
        // Appelle une méthode
        GenerationUnit section = GeneratorImpl.section(null, new GenSection(), sectionX, sectionY, sectionZ);
        // Appelle une méthode
        assertEquals(Vec.SECTION, section.size());
        // Appelle une méthode
        assertEquals(new Vec(sectionX * 16, sectionY * 16, sectionZ * 16), section.absoluteStart());
        // Appelle une méthode
        assertEquals(new Vec(sectionX * 16 + 16, sectionY * 16 + 16, sectionZ * 16 + 16), section.absoluteEnd());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkSubdivide() {
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        GenerationUnit chunk = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Appelle une méthode
        var subUnits = chunk.subdivide();
        // Appelle une méthode
        assertEquals(sectionCount, subUnits.size());
        // Boucle : répète un bloc
        for (int i = 0; i < sectionCount; i++) {
            // Appelle une méthode
            var subUnit = subUnits.get(i);
            // Appelle une méthode
            assertEquals(Vec.SECTION, subUnit.size());
            // Appelle une méthode
            assertEquals(new Vec(chunkX * 16, (i + minSection) * 16, chunkZ * 16), subUnit.absoluteStart());
            // Appelle une méthode
            assertEquals(subUnit.absoluteStart().add(16), subUnit.absoluteEnd());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkAbsolute() {
        // Affecte une valeur
        final int minSection = 0;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Affecte une valeur
        Generator generator = chunk -> {
            // Appelle une méthode
            var modifier = chunk.modifier();
            // Appelle une méthode
            assertThrows(Exception.class, () -> modifier.setBlock(0, 0, 0, Block.STONE), "Block outside of chunk");
            // Appelle une méthode
            modifier.setBlock(56, 0, -25, Block.STONE);
            // Appelle une méthode
            modifier.setBlock(56, 17, -25, Block.STONE);
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        generator.generate(chunkUnits);
        // Appelle une méthode
        assertEquals(Block.STONE.stateId(), sections[0].blocks().get(8, 0, 7));
        // Appelle une méthode
        assertEquals(Block.STONE.stateId(), sections[1].blocks().get(8, 1, 7));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkAbsoluteAll() {
        // Affecte une valeur
        final int minSection = 0;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Affecte une valeur
        Generator generator = chunk -> {
            // Appelle une méthode
            var modifier = chunk.modifier();
            // Appelle une méthode
            Set<Point> points = new HashSet<>();
            // Début d'une méthode/d'un bloc
            modifier.setAll((x, y, z) -> {
                // Appelle une méthode
                assertTrue(points.add(new Vec(x, y, z)), "Duplicate point: " + x + ", " + y + ", " + z);
                // Appelle une méthode
                assertEquals(chunkX, CoordConversion.globalToChunk(x));
                // Appelle une méthode
                assertEquals(chunkZ, CoordConversion.globalToChunk(z));
                // Renvoie une valeur à l'appelant
                return Block.STONE;
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            assertEquals(SECTION_BLOCK_COUNT * sectionCount, points.size());
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        generator.generate(chunkUnits);
        // Boucle : répète un bloc
        for (var section : sections) {
            // Instruction de code
            section.blocks().getAll((x, y, z, value) ->
                    // Appelle une méthode
                    assertEquals(Block.STONE.stateId(), value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkRelative() {
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Affecte une valeur
        Generator generator = chunk -> {
            // Appelle une méthode
            var modifier = chunk.modifier();
            // Appelle une méthode
            assertThrows(Exception.class, () -> modifier.setRelative(-1, 0, 0, Block.STONE));
            // Appelle une méthode
            assertThrows(Exception.class, () -> modifier.setRelative(16, 0, 0, Block.STONE));
            // Appelle une méthode
            assertThrows(Exception.class, () -> modifier.setRelative(17, 0, 0, Block.STONE));
            // Appelle une méthode
            assertThrows(Exception.class, () -> modifier.setRelative(0, -1, 0, Block.STONE));
            // Appelle une méthode
            assertThrows(Exception.class, () -> modifier.setRelative(0, 96, 0, Block.STONE));
            // Appelle une méthode
            modifier.setRelative(0, 0, 0, Block.STONE);
            // Appelle une méthode
            modifier.setRelative(0, 16, 2, Block.STONE);
            // Appelle une méthode
            modifier.setRelative(5, 33, 5, Block.STONE);
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        generator.generate(chunkUnits);
        // Appelle une méthode
        assertEquals(Block.STONE.stateId(), sections[0].blocks().get(0, 0, 0));
        // Appelle une méthode
        assertEquals(Block.STONE.stateId(), sections[1].blocks().get(0, 0, 2));
        // Appelle une méthode
        assertEquals(Block.STONE.stateId(), sections[2].blocks().get(5, 1, 5));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkRelativeAll() {
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Affecte une valeur
        Generator generator = chunk -> {
            // Appelle une méthode
            var modifier = chunk.modifier();
            // Appelle une méthode
            Set<Point> points = new HashSet<>();
            // Début d'une méthode/d'un bloc
            modifier.setAllRelative((x, y, z) -> {
                // Appelle une méthode
                assertTrue(MathUtils.isBetween(x, 0, 16), "x out of bounds: " + x);
                // Appelle une méthode
                assertTrue(MathUtils.isBetween(y, 0, sectionCount * 16), "y out of bounds: " + y);
                // Appelle une méthode
                assertTrue(MathUtils.isBetween(z, 0, 16), "z out of bounds: " + z);
                // Appelle une méthode
                assertTrue(points.add(new Vec(x, y, z)), "Duplicate point: " + x + ", " + y + ", " + z);
                // Renvoie une valeur à l'appelant
                return Block.STONE;
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            assertEquals(SECTION_BLOCK_COUNT * sectionCount, points.size());
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        generator.generate(chunkUnits);
        // Boucle : répète un bloc
        for (var section : sections) {
            // Instruction de code
            section.blocks().getAll((x, y, z, value) ->
                    // Appelle une méthode
                    assertEquals(Block.STONE.stateId(), value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkFillHeightExact() {
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(null, sections, 3, minSection, -2);
        // Appelle une méthode
        Generator generator = chunk -> chunk.modifier().fillHeight(0, 32, Block.STONE);
        // Appelle une méthode
        generator.generate(chunkUnits);

        // Appelle une méthode
        AtomicInteger index = new AtomicInteger(minSection);
        // Boucle : répète un bloc
        for (var section : sections) {
            // Début d'une méthode/d'un bloc
            section.blocks().getAll((x, y, z, value) -> {
                // Embranchement : vérifie une condition
                if (index.get() == 0 || index.get() == 1) {
                    // Appelle une méthode
                    assertEquals(Block.STONE.stateId(), value, "filling failed for section " + index.get());
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    assertEquals(0, value);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            index.incrementAndGet();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkFillHeightOneOff() {
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(null, sections, 3, minSection, -2);
        // Appelle une méthode
        Generator generator = chunk -> chunk.modifier().fillHeight(1, 33, Block.STONE);
        // Appelle une méthode
        generator.generate(chunkUnits);

        // Appelle une méthode
        AtomicInteger index = new AtomicInteger(minSection);
        // Boucle : répète un bloc
        for (var section : sections) {
            // Début d'une méthode/d'un bloc
            section.blocks().getAll((x, y, z, value) -> {
                // Instruction de code
                Block expected;
                // Embranchement : vérifie une condition
                if (index.get() == 0) {
                    // Embranchement : vérifie une condition
                    if (y > 0) {
                        // Affecte une valeur
                        expected = Block.STONE;
                    // Branche alternative de la condition
                    } else {
                        // Affecte une valeur
                        expected = Block.AIR;
                    // Fin d'un bloc/d'une expression
                    }
                // Embranchement : vérifie une condition
                } else if (index.get() == 1) {
                    // Affecte une valeur
                    expected = Block.STONE;
                // Embranchement : vérifie une condition
                } else if (index.get() == 2) {
                    // Embranchement : vérifie une condition
                    if (y == 0) {
                        // Affecte une valeur
                        expected = Block.STONE;
                    // Branche alternative de la condition
                    } else {
                        // Affecte une valeur
                        expected = Block.AIR;
                    // Fin d'un bloc/d'une expression
                    }
                // Branche alternative de la condition
                } else {
                    // Affecte une valeur
                    expected = Block.AIR;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                assertEquals(expected.stateId(), value, "fail for coordinate: " + x + "," + y + "," + z + " for index " + index.get());
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            index.incrementAndGet();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionFill() {
        // Appelle une méthode
        GenSection section = new GenSection();
        // Appelle une méthode
        var chunkUnit = GeneratorImpl.section(null, section, -1, -1, 0);
        // Appelle une méthode
        Generator generator = chunk -> chunk.modifier().fill(Block.STONE);
        // Appelle une méthode
        generator.generate(chunkUnit);
        // Instruction de code
        section.blocks().getAll((x, y, z, value) ->
                // Appelle une méthode
                assertEquals(Block.STONE.stateId(), value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionFillClearsSpecialCache() {
        // Appelle une méthode
        GenSection section = new GenSection();
        // Appelle une méthode
        var chunkUnit = GeneratorImpl.section(null, section, 0, 0, 0);
        // Appelle une méthode
        var special = Block.CHEST.withNbt(CompoundBinaryTag.builder().putString("key", "value").build());
        // Appelle une méthode
        chunkUnit.modifier().setRelative(0, 0, 0, special);
        // Appelle une méthode
        assertFalse(section.specials().isEmpty());

        // Appelle une méthode
        chunkUnit.modifier().fill(Block.STONE);

        // Appelle une méthode
        assertTrue(section.specials().isEmpty());
        // Appelle une méthode
        section.blocks().getAll((_, _, _, value) -> assertEquals(Block.STONE.stateId(), value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionPartialFillClearsSpecialCache() {
        // Appelle une méthode
        GenSection section = new GenSection();
        // Appelle une méthode
        var chunkUnit = GeneratorImpl.section(null, section, 0, 0, 0);
        // Appelle une méthode
        var special = Block.CHEST.withNbt(CompoundBinaryTag.builder().putString("key", "value").build());
        // Appelle une méthode
        chunkUnit.modifier().setRelative(0, 1, 0, special);
        // Appelle une méthode
        assertFalse(section.specials().isEmpty());

        // Appelle une méthode
        chunkUnit.modifier().fillHeight(1, 2, Block.STONE);

        // Appelle une méthode
        assertTrue(section.specials().isEmpty());
        // Appelle une méthode
        assertEquals(Block.STONE.stateId(), section.blocks().get(0, 1, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testForkAcrossBorders() {
        // Affecte une valeur
        final int minSection = -4;
        // Affecte une valeur
        final int maxSection = 4;

        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnits = GeneratorImpl.chunk(null, sections, 0, minSection, 0);
        // Affecte une valeur
        Generator generator = unit -> {
            // Embranchement : vérifie une condition
            if (unit.absoluteStart().x() == 0 && unit.absoluteStart().z() == 0) {
                // Appelle une méthode
                var start = unit.absoluteStart().withY(0).add(0, 0, 8).sub(2, 2, 0);
                // Appelle une méthode
                var end = unit.absoluteStart().withY(0).add(0, 0, 8).add(2, 2, 1);

                // Appelle une méthode
                var fork = unit.fork(start, end);
                // Appelle une méthode
                fork.modifier().fill(start, end, Block.STONE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        generator.generate(chunkUnits);

        // Appelle une méthode
        Set<Point> stones = new HashSet<>();

        // Boucle : répète un bloc
        for (GeneratorImpl.UnitImpl fork : chunkUnits.forks()) {
            // Appelle une méthode
            GeneratorImpl.AreaModifierImpl impl = (GeneratorImpl.AreaModifierImpl) fork.modifier();

            // Boucle : répète un bloc
            for (GenerationUnit section : impl.sections()) {
                // Appelle une méthode
                GeneratorImpl.UnitImpl unit = (GeneratorImpl.UnitImpl) section;
                // Appelle une méthode
                GeneratorImpl.SectionModifierImpl modifier = (GeneratorImpl.SectionModifierImpl) unit.modifier();

                // Début d'une méthode/d'un bloc
                modifier.genSection().blocks().getAllPresent((x, y, z, state) -> {
                    // Appelle une méthode
                    final Point blockPos = modifier.start().add(x, y, z);
                    // Appelle une méthode
                    stones.add(blockPos);
                // Fin d'un bloc/d'une expression
                });
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var expectedStones = Set.of(
                // Crée un nouvel objet
                new Vec(-2, -2, 8),
                // Crée un nouvel objet
                new Vec(-2, -1, 8),
                // Crée un nouvel objet
                new Vec(-2, 0, 8),
                // Crée un nouvel objet
                new Vec(-2, 1, 8),
                // Crée un nouvel objet
                new Vec(-1, -2, 8),
                // Crée un nouvel objet
                new Vec(-1, -1, 8),
                // Crée un nouvel objet
                new Vec(-1, 0, 8),
                // Crée un nouvel objet
                new Vec(-1, 1, 8),
                // Crée un nouvel objet
                new Vec(0, -2, 8),
                // Crée un nouvel objet
                new Vec(0, -1, 8),
                // Crée un nouvel objet
                new Vec(0, 0, 8),
                // Crée un nouvel objet
                new Vec(0, 1, 8),
                // Crée un nouvel objet
                new Vec(1, -2, 8),
                // Crée un nouvel objet
                new Vec(1, -1, 8),
                // Crée un nouvel objet
                new Vec(1, 0, 8),
                // Crée un nouvel objet
                new Vec(1, 1, 8)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        assertEquals(expectedStones.size(), stones.size());
        // Appelle une méthode
        assertEquals(expectedStones, stones);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsSingleSection() {
        // Test a unit that covers exactly one section
        // Appelle une méthode
        var unit = dummyUnit(new Vec(0, 0, 0), new Vec(16, 16, 16));
        // Appelle une méthode
        var sections = unit.sections();

        // Appelle une méthode
        assertEquals(1, sections.size());
        // Appelle une méthode
        assertTrue(sections.contains(new Vec(0, 0, 0)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsMultipleSections() {
        // Test a unit that covers multiple sections (2x2x2 = 8 sections)
        // Appelle une méthode
        var unit = dummyUnit(new Vec(0, 0, 0), new Vec(32, 32, 32));
        // Appelle une méthode
        var sections = unit.sections();

        // Appelle une méthode
        assertEquals(8, sections.size());
        // Check all expected sections are present
        // Affecte une valeur
        Set<Point> expectedSections = Set.of(
                // Crée un nouvel objet
                new Vec(0, 0, 0), new Vec(0, 0, 1),
                // Crée un nouvel objet
                new Vec(0, 1, 0), new Vec(0, 1, 1),
                // Crée un nouvel objet
                new Vec(1, 0, 0), new Vec(1, 0, 1),
                // Crée un nouvel objet
                new Vec(1, 1, 0), new Vec(1, 1, 1)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertEquals(expectedSections, sections);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsNegativeCoordinates() {
        // Test a unit with negative coordinates
        // Appelle une méthode
        var unit = dummyUnit(new Vec(-32, -16, -48), new Vec(-16, 0, -32));
        // Appelle une méthode
        var sections = unit.sections();

        // Appelle une méthode
        assertEquals(1, sections.size());
        // Appelle une méthode
        assertTrue(sections.contains(new Vec(-2, -1, -3)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsAsymmetricUnit() {
        // Test a unit that is not square (different dimensions)
        // Appelle une méthode
        var unit = dummyUnit(new Vec(16, 0, 0), new Vec(64, 16, 32));
        // Appelle une méthode
        var sections = unit.sections();

        // 3 sections wide (x), 1 section high (y), 2 sections deep (z) = 6 sections
        // Appelle une méthode
        assertEquals(6, sections.size());
        // Affecte une valeur
        Set<Point> expectedSections = Set.of(
                // Crée un nouvel objet
                new Vec(1, 0, 0), new Vec(1, 0, 1),
                // Crée un nouvel objet
                new Vec(2, 0, 0), new Vec(2, 0, 1),
                // Crée un nouvel objet
                new Vec(3, 0, 0), new Vec(3, 0, 1)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertEquals(expectedSections, sections);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsLargeUnit() {
        // Test a larger unit to verify the algorithm scales
        // Appelle une méthode
        var unit = dummyUnit(new Vec(0, 0, 0), new Vec(48, 64, 32));
        // Appelle une méthode
        var sections = unit.sections();

        // 3 sections wide (x), 4 sections high (y), 2 sections deep (z) = 24 sections
        // Appelle une méthode
        assertEquals(24, sections.size());

        // Verify all sections are within expected bounds
        // Boucle : répète un bloc
        for (Point section : sections) {
            // Appelle une méthode
            assertTrue(section.x() >= 0 && section.x() < 3, "Section X out of bounds: " + section.x());
            // Appelle une méthode
            assertTrue(section.y() >= 0 && section.y() < 4, "Section Y out of bounds: " + section.y());
            // Appelle une méthode
            assertTrue(section.z() >= 0 && section.z() < 2, "Section Z out of bounds: " + section.z());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsOffsetCoordinates() {
        // Test a unit that doesn't start at section boundaries but is aligned to sections
        // Appelle une méthode
        var unit = dummyUnit(new Vec(32, 48, 16), new Vec(64, 80, 48));
        // Appelle une méthode
        var sections = unit.sections();

        // 2 sections wide (x), 2 sections high (y), 2 sections deep (z) = 8 sections
        // Appelle une méthode
        assertEquals(8, sections.size());
        // Affecte une valeur
        Set<Point> expectedSections = Set.of(
                // Crée un nouvel objet
                new Vec(2, 3, 1), new Vec(2, 3, 2),
                // Crée un nouvel objet
                new Vec(2, 4, 1), new Vec(2, 4, 2),
                // Crée un nouvel objet
                new Vec(3, 3, 1), new Vec(3, 3, 2),
                // Crée un nouvel objet
                new Vec(3, 4, 1), new Vec(3, 4, 2)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertEquals(expectedSections, sections);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsChunkUnit() {
        // Test sections() on an actual chunk unit
        // Affecte une valeur
        final int minSection = -1;
        // Affecte une valeur
        final int maxSection = 5;
        // Affecte une valeur
        final int chunkX = 3;
        // Affecte une valeur
        final int chunkZ = -2;
        // Affecte une valeur
        final int sectionCount = maxSection - minSection;
        // Affecte une valeur
        GenSection[] sections = new GenSection[sectionCount];
        // Appelle une méthode
        Arrays.setAll(sections, i -> new GenSection());
        // Appelle une méthode
        var chunkUnit = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);

        // Appelle une méthode
        var unitSections = chunkUnit.sections();
        // Appelle une méthode
        assertEquals(sectionCount, unitSections.size());

        // Verify all sections have the correct chunk coordinates and are within the height range
        // Boucle : répète un bloc
        for (Point section : unitSections) {
            // Appelle une méthode
            assertEquals(chunkX, section.x(), "Section X should match chunk X");
            // Appelle une méthode
            assertEquals(chunkZ, section.z(), "Section Z should match chunk Z");
            // Instruction de code
            assertTrue(section.y() >= minSection && section.y() < maxSection,
                    // Appelle une méthode
                    "Section Y should be within height range: " + section.y());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsSingleSectionUnit() {
        // Test sections() on a single section unit
        // Affecte une valeur
        final int sectionX = 3;
        // Affecte une valeur
        final int sectionY = -5;
        // Affecte une valeur
        final int sectionZ = -2;
        // Appelle une méthode
        var sectionUnit = GeneratorImpl.section(null, new GenSection(), sectionX, sectionY, sectionZ);

        // Appelle une méthode
        var sections = sectionUnit.sections();
        // Appelle une méthode
        assertEquals(1, sections.size());
        // Appelle une méthode
        assertTrue(sections.contains(new Vec(sectionX, sectionY, sectionZ)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsReturnType() {
        // Test that sections() returns an immutable set
        // Appelle une méthode
        var unit = dummyUnit(new Vec(0, 0, 0), new Vec(32, 16, 16));
        // Appelle une méthode
        var sections = unit.sections();

        // Verify it's a Set and contains the expected number of elements
        // Appelle une méthode
        assertInstanceOf(Set.class, sections);
        // Affecte une valeur
        assertEquals(2, sections.size()); // 2x1x1 = 2 sections

        // Verify immutability by attempting to modify (should throw exception)
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> sections.add(new Vec(99, 99, 99)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionsCoordinateConsistency() {
        // Test that section coordinates are consistent with the unit's absolute coordinates
        // Appelle une méthode
        var unit = dummyUnit(new Vec(48, 64, 32), new Vec(80, 96, 64));
        // Appelle une méthode
        var sections = unit.sections();

        // Appelle une méthode
        Point start = unit.absoluteStart();
        // Appelle une méthode
        Point end = unit.absoluteEnd();

        // Calculate expected section bounds
        // Appelle une méthode
        int expectedMinX = start.sectionX();
        // Appelle une méthode
        int expectedMinY = start.sectionY();
        // Appelle une méthode
        int expectedMinZ = start.sectionZ();
        // Appelle une méthode
        int expectedMaxX = end.sectionX();
        // Appelle une méthode
        int expectedMaxY = end.sectionY();
        // Appelle une méthode
        int expectedMaxZ = end.sectionZ();

        // Verify all sections are within the expected bounds
        // Boucle : répète un bloc
        for (Point section : sections) {
            // Instruction de code
            assertTrue(section.x() >= expectedMinX && section.x() < expectedMaxX,
                    // Appelle une méthode
                    "Section X coordinate out of bounds: " + section.x());
            // Instruction de code
            assertTrue(section.y() >= expectedMinY && section.y() < expectedMaxY,
                    // Appelle une méthode
                    "Section Y coordinate out of bounds: " + section.y());
            // Instruction de code
            assertTrue(section.z() >= expectedMinZ && section.z() < expectedMaxZ,
                    // Appelle une méthode
                    "Section Z coordinate out of bounds: " + section.z());
        // Fin d'un bloc/d'une expression
        }

        // Verify we have the expected total count
        // Appelle une méthode
        int expectedCount = (expectedMaxX - expectedMinX) * (expectedMaxY - expectedMinY) * (expectedMaxZ - expectedMinZ);
        // Appelle une méthode
        assertEquals(expectedCount, sections.size());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static GenerationUnit dummyUnit(Vec start, Vec end) {
        // Renvoie une valeur à l'appelant
        return unit(null, null, start, end, null);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
