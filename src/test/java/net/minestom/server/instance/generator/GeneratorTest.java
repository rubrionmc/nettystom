// Package declaration for this file
package net.minestom.server.instance.generator;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.generator.GeneratorImpl.GenSection;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.stream.Stream;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.*;
// Static import of a member
import static net.minestom.server.instance.generator.GeneratorImpl.unit;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class GeneratorTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void unitSize() {
        // Calls a method
        assertDoesNotThrow(() -> dummyUnit(Vec.ZERO, Vec.SECTION));
        // Calls a method
        assertDoesNotThrow(() -> dummyUnit(Vec.SECTION, new Vec(32)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> dummyUnit(new Vec(15), Vec.ZERO));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> dummyUnit(new Vec(15), new Vec(32)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> dummyUnit(new Vec(15), new Vec(31)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> dummyUnit(Vec.ZERO, new Vec(15)));
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("sectionFloorParam")
    // Start of a method/block
    public void sectionFloor(int expected, int input) {
        // Calls a method
        assertEquals(expected, floorSection(input), "floorSection(" + input + ")");
    // End of a block/expression
    }

    // Start of a method/block
    private static Stream<Arguments> sectionFloorParam() {
        // Returns a value to the caller
        return Stream.of(Arguments.of(-32, -32),
                // Code statement
                Arguments.of(-32, -31),
                // Code statement
                Arguments.of(-32, -17),
                // Code statement
                Arguments.of(-16, -16),
                // Code statement
                Arguments.of(-16, -15),
                // Code statement
                Arguments.of(0, 0),
                // Code statement
                Arguments.of(0, 1),
                // Code statement
                Arguments.of(0, 2),
                // Code statement
                Arguments.of(16, 16),
                // Calls a method
                Arguments.of(16, 17));
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("sectionCeilParam")
    // Start of a method/block
    public void sectionCeil(int expected, int input) {
        // Calls a method
        assertEquals(expected, ceilSection(input), "ceilSection(" + input + ")");
    // End of a block/expression
    }

    // Start of a method/block
    private static Stream<Arguments> sectionCeilParam() {
        // Returns a value to the caller
        return Stream.of(Arguments.of(-32, -32),
                // Code statement
                Arguments.of(-16, -31),
                // Code statement
                Arguments.of(-16, -17),
                // Code statement
                Arguments.of(-16, -16),
                // Code statement
                Arguments.of(-0, -15),
                // Code statement
                Arguments.of(0, 0),
                // Code statement
                Arguments.of(16, 1),
                // Code statement
                Arguments.of(16, 2),
                // Code statement
                Arguments.of(16, 16),
                // Calls a method
                Arguments.of(32, 17));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkSize() {
        // Assigns a value
        final int minSection = 0;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        GenerationUnit chunk = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Calls a method
        assertEquals(new Vec(16, sectionCount * 16, 16), chunk.size());
        // Calls a method
        assertEquals(new Vec(chunkX * 16, minSection * 16, chunkZ * 16), chunk.absoluteStart());
        // Calls a method
        assertEquals(new Vec(chunkX * 16 + 16, maxSection * 16, chunkZ * 16 + 16), chunk.absoluteEnd());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkSizeNeg() {
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        GenerationUnit chunk = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Calls a method
        assertEquals(new Vec(16, sectionCount * 16, 16), chunk.size());
        // Calls a method
        assertEquals(new Vec(chunkX * 16, minSection * 16, chunkZ * 16), chunk.absoluteStart());
        // Calls a method
        assertEquals(new Vec(chunkX * 16 + 16, maxSection * 16, chunkZ * 16 + 16), chunk.absoluteEnd());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionSize() {
        // Assigns a value
        final int sectionX = 3;
        // Assigns a value
        final int sectionY = -5;
        // Assigns a value
        final int sectionZ = -2;
        // Calls a method
        GenerationUnit section = GeneratorImpl.section(null, new GenSection(), sectionX, sectionY, sectionZ);
        // Calls a method
        assertEquals(Vec.SECTION, section.size());
        // Calls a method
        assertEquals(new Vec(sectionX * 16, sectionY * 16, sectionZ * 16), section.absoluteStart());
        // Calls a method
        assertEquals(new Vec(sectionX * 16 + 16, sectionY * 16 + 16, sectionZ * 16 + 16), section.absoluteEnd());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkSubdivide() {
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        GenerationUnit chunk = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Calls a method
        var subUnits = chunk.subdivide();
        // Calls a method
        assertEquals(sectionCount, subUnits.size());
        // Loop: repeats a block
        for (int i = 0; i < sectionCount; i++) {
            // Calls a method
            var subUnit = subUnits.get(i);
            // Calls a method
            assertEquals(Vec.SECTION, subUnit.size());
            // Calls a method
            assertEquals(new Vec(chunkX * 16, (i + minSection) * 16, chunkZ * 16), subUnit.absoluteStart());
            // Calls a method
            assertEquals(subUnit.absoluteStart().add(16), subUnit.absoluteEnd());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkAbsolute() {
        // Assigns a value
        final int minSection = 0;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Assigns a value
        Generator generator = chunk -> {
            // Calls a method
            var modifier = chunk.modifier();
            // Calls a method
            assertThrows(Exception.class, () -> modifier.setBlock(0, 0, 0, Block.STONE), "Block outside of chunk");
            // Calls a method
            modifier.setBlock(56, 0, -25, Block.STONE);
            // Calls a method
            modifier.setBlock(56, 17, -25, Block.STONE);
        // End of a block/expression
        };
        // Calls a method
        generator.generate(chunkUnits);
        // Calls a method
        assertEquals(Block.STONE.stateId(), sections[0].blocks().get(8, 0, 7));
        // Calls a method
        assertEquals(Block.STONE.stateId(), sections[1].blocks().get(8, 1, 7));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkAbsoluteAll() {
        // Assigns a value
        final int minSection = 0;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Assigns a value
        Generator generator = chunk -> {
            // Calls a method
            var modifier = chunk.modifier();
            // Calls a method
            Set<Point> points = new HashSet<>();
            // Start of a method/block
            modifier.setAll((x, y, z) -> {
                // Calls a method
                assertTrue(points.add(new Vec(x, y, z)), "Duplicate point: " + x + ", " + y + ", " + z);
                // Calls a method
                assertEquals(chunkX, CoordConversion.globalToChunk(x));
                // Calls a method
                assertEquals(chunkZ, CoordConversion.globalToChunk(z));
                // Returns a value to the caller
                return Block.STONE;
            // End of a block/expression
            });
            // Calls a method
            assertEquals(SECTION_BLOCK_COUNT * sectionCount, points.size());
        // End of a block/expression
        };
        // Calls a method
        generator.generate(chunkUnits);
        // Loop: repeats a block
        for (var section : sections) {
            // Code statement
            section.blocks().getAll((x, y, z, value) ->
                    // Calls a method
                    assertEquals(Block.STONE.stateId(), value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkRelative() {
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Assigns a value
        Generator generator = chunk -> {
            // Calls a method
            var modifier = chunk.modifier();
            // Calls a method
            assertThrows(Exception.class, () -> modifier.setRelative(-1, 0, 0, Block.STONE));
            // Calls a method
            assertThrows(Exception.class, () -> modifier.setRelative(16, 0, 0, Block.STONE));
            // Calls a method
            assertThrows(Exception.class, () -> modifier.setRelative(17, 0, 0, Block.STONE));
            // Calls a method
            assertThrows(Exception.class, () -> modifier.setRelative(0, -1, 0, Block.STONE));
            // Calls a method
            assertThrows(Exception.class, () -> modifier.setRelative(0, 96, 0, Block.STONE));
            // Calls a method
            modifier.setRelative(0, 0, 0, Block.STONE);
            // Calls a method
            modifier.setRelative(0, 16, 2, Block.STONE);
            // Calls a method
            modifier.setRelative(5, 33, 5, Block.STONE);
        // End of a block/expression
        };
        // Calls a method
        generator.generate(chunkUnits);
        // Calls a method
        assertEquals(Block.STONE.stateId(), sections[0].blocks().get(0, 0, 0));
        // Calls a method
        assertEquals(Block.STONE.stateId(), sections[1].blocks().get(0, 0, 2));
        // Calls a method
        assertEquals(Block.STONE.stateId(), sections[2].blocks().get(5, 1, 5));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkRelativeAll() {
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);
        // Assigns a value
        Generator generator = chunk -> {
            // Calls a method
            var modifier = chunk.modifier();
            // Calls a method
            Set<Point> points = new HashSet<>();
            // Start of a method/block
            modifier.setAllRelative((x, y, z) -> {
                // Calls a method
                assertTrue(MathUtils.isBetween(x, 0, 16), "x out of bounds: " + x);
                // Calls a method
                assertTrue(MathUtils.isBetween(y, 0, sectionCount * 16), "y out of bounds: " + y);
                // Calls a method
                assertTrue(MathUtils.isBetween(z, 0, 16), "z out of bounds: " + z);
                // Calls a method
                assertTrue(points.add(new Vec(x, y, z)), "Duplicate point: " + x + ", " + y + ", " + z);
                // Returns a value to the caller
                return Block.STONE;
            // End of a block/expression
            });
            // Calls a method
            assertEquals(SECTION_BLOCK_COUNT * sectionCount, points.size());
        // End of a block/expression
        };
        // Calls a method
        generator.generate(chunkUnits);
        // Loop: repeats a block
        for (var section : sections) {
            // Code statement
            section.blocks().getAll((x, y, z, value) ->
                    // Calls a method
                    assertEquals(Block.STONE.stateId(), value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkFillHeightExact() {
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(null, sections, 3, minSection, -2);
        // Calls a method
        Generator generator = chunk -> chunk.modifier().fillHeight(0, 32, Block.STONE);
        // Calls a method
        generator.generate(chunkUnits);

        // Calls a method
        AtomicInteger index = new AtomicInteger(minSection);
        // Loop: repeats a block
        for (var section : sections) {
            // Start of a method/block
            section.blocks().getAll((x, y, z, value) -> {
                // Branch: checks a condition
                if (index.get() == 0 || index.get() == 1) {
                    // Calls a method
                    assertEquals(Block.STONE.stateId(), value, "filling failed for section " + index.get());
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    assertEquals(0, value);
                // End of a block/expression
                }
            // End of a block/expression
            });
            // Calls a method
            index.incrementAndGet();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkFillHeightOneOff() {
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(null, sections, 3, minSection, -2);
        // Calls a method
        Generator generator = chunk -> chunk.modifier().fillHeight(1, 33, Block.STONE);
        // Calls a method
        generator.generate(chunkUnits);

        // Calls a method
        AtomicInteger index = new AtomicInteger(minSection);
        // Loop: repeats a block
        for (var section : sections) {
            // Start of a method/block
            section.blocks().getAll((x, y, z, value) -> {
                // Code statement
                Block expected;
                // Branch: checks a condition
                if (index.get() == 0) {
                    // Branch: checks a condition
                    if (y > 0) {
                        // Assigns a value
                        expected = Block.STONE;
                    // Alternative branch of the condition
                    } else {
                        // Assigns a value
                        expected = Block.AIR;
                    // End of a block/expression
                    }
                // Branch: checks a condition
                } else if (index.get() == 1) {
                    // Assigns a value
                    expected = Block.STONE;
                // Branch: checks a condition
                } else if (index.get() == 2) {
                    // Branch: checks a condition
                    if (y == 0) {
                        // Assigns a value
                        expected = Block.STONE;
                    // Alternative branch of the condition
                    } else {
                        // Assigns a value
                        expected = Block.AIR;
                    // End of a block/expression
                    }
                // Alternative branch of the condition
                } else {
                    // Assigns a value
                    expected = Block.AIR;
                // End of a block/expression
                }
                // Calls a method
                assertEquals(expected.stateId(), value, "fail for coordinate: " + x + "," + y + "," + z + " for index " + index.get());
            // End of a block/expression
            });
            // Calls a method
            index.incrementAndGet();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionFill() {
        // Calls a method
        GenSection section = new GenSection();
        // Calls a method
        var chunkUnit = GeneratorImpl.section(null, section, -1, -1, 0);
        // Calls a method
        Generator generator = chunk -> chunk.modifier().fill(Block.STONE);
        // Calls a method
        generator.generate(chunkUnit);
        // Code statement
        section.blocks().getAll((x, y, z, value) ->
                // Calls a method
                assertEquals(Block.STONE.stateId(), value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionFillClearsSpecialCache() {
        // Calls a method
        GenSection section = new GenSection();
        // Calls a method
        var chunkUnit = GeneratorImpl.section(null, section, 0, 0, 0);
        // Calls a method
        var special = Block.CHEST.withNbt(CompoundBinaryTag.builder().putString("key", "value").build());
        // Calls a method
        chunkUnit.modifier().setRelative(0, 0, 0, special);
        // Calls a method
        assertFalse(section.specials().isEmpty());

        // Calls a method
        chunkUnit.modifier().fill(Block.STONE);

        // Calls a method
        assertTrue(section.specials().isEmpty());
        // Calls a method
        section.blocks().getAll((_, _, _, value) -> assertEquals(Block.STONE.stateId(), value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionPartialFillClearsSpecialCache() {
        // Calls a method
        GenSection section = new GenSection();
        // Calls a method
        var chunkUnit = GeneratorImpl.section(null, section, 0, 0, 0);
        // Calls a method
        var special = Block.CHEST.withNbt(CompoundBinaryTag.builder().putString("key", "value").build());
        // Calls a method
        chunkUnit.modifier().setRelative(0, 1, 0, special);
        // Calls a method
        assertFalse(section.specials().isEmpty());

        // Calls a method
        chunkUnit.modifier().fillHeight(1, 2, Block.STONE);

        // Calls a method
        assertTrue(section.specials().isEmpty());
        // Calls a method
        assertEquals(Block.STONE.stateId(), section.blocks().get(0, 1, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testForkAcrossBorders() {
        // Assigns a value
        final int minSection = -4;
        // Assigns a value
        final int maxSection = 4;

        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(null, sections, 0, minSection, 0);
        // Assigns a value
        Generator generator = unit -> {
            // Branch: checks a condition
            if (unit.absoluteStart().x() == 0 && unit.absoluteStart().z() == 0) {
                // Calls a method
                var start = unit.absoluteStart().withY(0).add(0, 0, 8).sub(2, 2, 0);
                // Calls a method
                var end = unit.absoluteStart().withY(0).add(0, 0, 8).add(2, 2, 1);

                // Calls a method
                var fork = unit.fork(start, end);
                // Calls a method
                fork.modifier().fill(start, end, Block.STONE);
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        generator.generate(chunkUnits);

        // Calls a method
        Set<Point> stones = new HashSet<>();

        // Loop: repeats a block
        for (GeneratorImpl.UnitImpl fork : chunkUnits.forks()) {
            // Calls a method
            GeneratorImpl.AreaModifierImpl impl = (GeneratorImpl.AreaModifierImpl) fork.modifier();

            // Loop: repeats a block
            for (GenerationUnit section : impl.sections()) {
                // Calls a method
                GeneratorImpl.UnitImpl unit = (GeneratorImpl.UnitImpl) section;
                // Calls a method
                GeneratorImpl.SectionModifierImpl modifier = (GeneratorImpl.SectionModifierImpl) unit.modifier();

                // Start of a method/block
                modifier.genSection().blocks().getAllPresent((x, y, z, state) -> {
                    // Calls a method
                    final Point blockPos = modifier.start().add(x, y, z);
                    // Calls a method
                    stones.add(blockPos);
                // End of a block/expression
                });
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Assigns a value
        var expectedStones = Set.of(
                // Creates a new object
                new Vec(-2, -2, 8),
                // Creates a new object
                new Vec(-2, -1, 8),
                // Creates a new object
                new Vec(-2, 0, 8),
                // Creates a new object
                new Vec(-2, 1, 8),
                // Creates a new object
                new Vec(-1, -2, 8),
                // Creates a new object
                new Vec(-1, -1, 8),
                // Creates a new object
                new Vec(-1, 0, 8),
                // Creates a new object
                new Vec(-1, 1, 8),
                // Creates a new object
                new Vec(0, -2, 8),
                // Creates a new object
                new Vec(0, -1, 8),
                // Creates a new object
                new Vec(0, 0, 8),
                // Creates a new object
                new Vec(0, 1, 8),
                // Creates a new object
                new Vec(1, -2, 8),
                // Creates a new object
                new Vec(1, -1, 8),
                // Creates a new object
                new Vec(1, 0, 8),
                // Creates a new object
                new Vec(1, 1, 8)
        // End of a block/expression
        );

        // Calls a method
        assertEquals(expectedStones.size(), stones.size());
        // Calls a method
        assertEquals(expectedStones, stones);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsSingleSection() {
        // Test a unit that covers exactly one section
        // Calls a method
        var unit = dummyUnit(new Vec(0, 0, 0), new Vec(16, 16, 16));
        // Calls a method
        var sections = unit.sections();

        // Calls a method
        assertEquals(1, sections.size());
        // Calls a method
        assertTrue(sections.contains(new Vec(0, 0, 0)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsMultipleSections() {
        // Test a unit that covers multiple sections (2x2x2 = 8 sections)
        // Calls a method
        var unit = dummyUnit(new Vec(0, 0, 0), new Vec(32, 32, 32));
        // Calls a method
        var sections = unit.sections();

        // Calls a method
        assertEquals(8, sections.size());
        // Check all expected sections are present
        // Assigns a value
        Set<Point> expectedSections = Set.of(
                // Creates a new object
                new Vec(0, 0, 0), new Vec(0, 0, 1),
                // Creates a new object
                new Vec(0, 1, 0), new Vec(0, 1, 1),
                // Creates a new object
                new Vec(1, 0, 0), new Vec(1, 0, 1),
                // Creates a new object
                new Vec(1, 1, 0), new Vec(1, 1, 1)
        // End of a block/expression
        );
        // Calls a method
        assertEquals(expectedSections, sections);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsNegativeCoordinates() {
        // Test a unit with negative coordinates
        // Calls a method
        var unit = dummyUnit(new Vec(-32, -16, -48), new Vec(-16, 0, -32));
        // Calls a method
        var sections = unit.sections();

        // Calls a method
        assertEquals(1, sections.size());
        // Calls a method
        assertTrue(sections.contains(new Vec(-2, -1, -3)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsAsymmetricUnit() {
        // Test a unit that is not square (different dimensions)
        // Calls a method
        var unit = dummyUnit(new Vec(16, 0, 0), new Vec(64, 16, 32));
        // Calls a method
        var sections = unit.sections();

        // 3 sections wide (x), 1 section high (y), 2 sections deep (z) = 6 sections
        // Calls a method
        assertEquals(6, sections.size());
        // Assigns a value
        Set<Point> expectedSections = Set.of(
                // Creates a new object
                new Vec(1, 0, 0), new Vec(1, 0, 1),
                // Creates a new object
                new Vec(2, 0, 0), new Vec(2, 0, 1),
                // Creates a new object
                new Vec(3, 0, 0), new Vec(3, 0, 1)
        // End of a block/expression
        );
        // Calls a method
        assertEquals(expectedSections, sections);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsLargeUnit() {
        // Test a larger unit to verify the algorithm scales
        // Calls a method
        var unit = dummyUnit(new Vec(0, 0, 0), new Vec(48, 64, 32));
        // Calls a method
        var sections = unit.sections();

        // 3 sections wide (x), 4 sections high (y), 2 sections deep (z) = 24 sections
        // Calls a method
        assertEquals(24, sections.size());

        // Verify all sections are within expected bounds
        // Loop: repeats a block
        for (Point section : sections) {
            // Calls a method
            assertTrue(section.x() >= 0 && section.x() < 3, "Section X out of bounds: " + section.x());
            // Calls a method
            assertTrue(section.y() >= 0 && section.y() < 4, "Section Y out of bounds: " + section.y());
            // Calls a method
            assertTrue(section.z() >= 0 && section.z() < 2, "Section Z out of bounds: " + section.z());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsOffsetCoordinates() {
        // Test a unit that doesn't start at section boundaries but is aligned to sections
        // Calls a method
        var unit = dummyUnit(new Vec(32, 48, 16), new Vec(64, 80, 48));
        // Calls a method
        var sections = unit.sections();

        // 2 sections wide (x), 2 sections high (y), 2 sections deep (z) = 8 sections
        // Calls a method
        assertEquals(8, sections.size());
        // Assigns a value
        Set<Point> expectedSections = Set.of(
                // Creates a new object
                new Vec(2, 3, 1), new Vec(2, 3, 2),
                // Creates a new object
                new Vec(2, 4, 1), new Vec(2, 4, 2),
                // Creates a new object
                new Vec(3, 3, 1), new Vec(3, 3, 2),
                // Creates a new object
                new Vec(3, 4, 1), new Vec(3, 4, 2)
        // End of a block/expression
        );
        // Calls a method
        assertEquals(expectedSections, sections);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsChunkUnit() {
        // Test sections() on an actual chunk unit
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnit = GeneratorImpl.chunk(null, sections, chunkX, minSection, chunkZ);

        // Calls a method
        var unitSections = chunkUnit.sections();
        // Calls a method
        assertEquals(sectionCount, unitSections.size());

        // Verify all sections have the correct chunk coordinates and are within the height range
        // Loop: repeats a block
        for (Point section : unitSections) {
            // Calls a method
            assertEquals(chunkX, section.x(), "Section X should match chunk X");
            // Calls a method
            assertEquals(chunkZ, section.z(), "Section Z should match chunk Z");
            // Code statement
            assertTrue(section.y() >= minSection && section.y() < maxSection,
                    // Calls a method
                    "Section Y should be within height range: " + section.y());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsSingleSectionUnit() {
        // Test sections() on a single section unit
        // Assigns a value
        final int sectionX = 3;
        // Assigns a value
        final int sectionY = -5;
        // Assigns a value
        final int sectionZ = -2;
        // Calls a method
        var sectionUnit = GeneratorImpl.section(null, new GenSection(), sectionX, sectionY, sectionZ);

        // Calls a method
        var sections = sectionUnit.sections();
        // Calls a method
        assertEquals(1, sections.size());
        // Calls a method
        assertTrue(sections.contains(new Vec(sectionX, sectionY, sectionZ)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsReturnType() {
        // Test that sections() returns an immutable set
        // Calls a method
        var unit = dummyUnit(new Vec(0, 0, 0), new Vec(32, 16, 16));
        // Calls a method
        var sections = unit.sections();

        // Verify it's a Set and contains the expected number of elements
        // Calls a method
        assertInstanceOf(Set.class, sections);
        // Assigns a value
        assertEquals(2, sections.size()); // 2x1x1 = 2 sections

        // Verify immutability by attempting to modify (should throw exception)
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> sections.add(new Vec(99, 99, 99)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionsCoordinateConsistency() {
        // Test that section coordinates are consistent with the unit's absolute coordinates
        // Calls a method
        var unit = dummyUnit(new Vec(48, 64, 32), new Vec(80, 96, 64));
        // Calls a method
        var sections = unit.sections();

        // Calls a method
        Point start = unit.absoluteStart();
        // Calls a method
        Point end = unit.absoluteEnd();

        // Calculate expected section bounds
        // Calls a method
        int expectedMinX = start.sectionX();
        // Calls a method
        int expectedMinY = start.sectionY();
        // Calls a method
        int expectedMinZ = start.sectionZ();
        // Calls a method
        int expectedMaxX = end.sectionX();
        // Calls a method
        int expectedMaxY = end.sectionY();
        // Calls a method
        int expectedMaxZ = end.sectionZ();

        // Verify all sections are within the expected bounds
        // Loop: repeats a block
        for (Point section : sections) {
            // Code statement
            assertTrue(section.x() >= expectedMinX && section.x() < expectedMaxX,
                    // Calls a method
                    "Section X coordinate out of bounds: " + section.x());
            // Code statement
            assertTrue(section.y() >= expectedMinY && section.y() < expectedMaxY,
                    // Calls a method
                    "Section Y coordinate out of bounds: " + section.y());
            // Code statement
            assertTrue(section.z() >= expectedMinZ && section.z() < expectedMaxZ,
                    // Calls a method
                    "Section Z coordinate out of bounds: " + section.z());
        // End of a block/expression
        }

        // Verify we have the expected total count
        // Calls a method
        int expectedCount = (expectedMaxX - expectedMinX) * (expectedMaxY - expectedMinY) * (expectedMaxZ - expectedMinZ);
        // Calls a method
        assertEquals(expectedCount, sections.size());
    // End of a block/expression
    }

    // Start of a method/block
    static GenerationUnit dummyUnit(Vec start, Vec end) {
        // Returns a value to the caller
        return unit(null, null, start, end, null);
    // End of a block/expression
    }
// End of a block/expression
}
