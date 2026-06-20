// Package declaration for this file
package net.minestom.server.instance.generator;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.concurrent.CopyOnWriteArrayList;
// Import of a required class
import java.util.function.Consumer;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.*;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class GeneratorImpl {
    // Type declaration (class/interface/enum/record)
    public record GenSection(Palette blocks, Palette biomes, Int2ObjectMap<Block> specials) {
        // Start of a method/block
        public GenSection(Palette blocks, Palette biomes) {
            // Calls a method
            this(blocks, biomes, new Int2ObjectOpenHashMap<>(0));
        // End of a block/expression
        }

        // Start of a method/block
        public GenSection() {
            // Calls a method
            this(Palette.blocks(), Palette.biomes());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    static GenerationUnit section(DynamicRegistry<Biome> biomeRegistry, GenSection section,
                                  // Code statement
                                  int sectionX, int sectionY, int sectionZ,
                                  // Start of a method/block
                                  boolean fork) {
        // Calls a method
        final Vec start = Vec.SECTION.mul(sectionX, sectionY, sectionZ);
        // Calls a method
        final Vec end = start.add(Vec.SECTION);
        // Calls a method
        final UnitModifier modifier = new SectionModifierImpl(biomeRegistry, start, end, section, fork);
        // Returns a value to the caller
        return unit(biomeRegistry, modifier, start, end, null);
    // End of a block/expression
    }

    // Start of a method/block
    public static GenerationUnit section(DynamicRegistry<Biome> biomeRegistry, GenSection section, int sectionX, int sectionY, int sectionZ) {
        // Returns a value to the caller
        return section(biomeRegistry, section, sectionX, sectionY, sectionZ, false);
    // End of a block/expression
    }

    // Start of a method/block
    public static UnitImpl chunk(DynamicRegistry<Biome> biomeRegistry, GenSection[] chunkSections, int chunkX, int minSection, int chunkZ) {
        // Calls a method
        final Vec start = Vec.SECTION.mul(chunkX, minSection, chunkZ);
        // Returns a value to the caller
        return area(biomeRegistry, start, 1, chunkSections.length, 1, chunkSections);
    // End of a block/expression
    }

    // Start of a method/block
    public static UnitImpl area(DynamicRegistry<Biome> biomeRegistry, Vec start, int width, int height, int depth, GenSection[] areaSections) {
        // Branch: checks a condition
        if (width == 0 || height == 0 || depth == 0) {
            // Throws an exception
            throw new IllegalArgumentException("Width, height and depth must be greater than 0, got " + width + ", " + height + ", " + depth);
        // End of a block/expression
        }
        // Branch: checks a condition
        if (areaSections.length != width * height * depth) {
            // Throws an exception
            throw new IllegalArgumentException("Invalid section count, expected " + width * height * depth + " but got " + areaSections.length);
        // End of a block/expression
        }

        // Assigns a value
        final int sectionCount = areaSections.length;
        // Assigns a value
        GenerationUnit[] sectionsArray = new GenerationUnit[sectionCount];
        // Calls a method
        final int startSectionX = start.sectionX(), startSectionY = start.sectionY(), startSectionZ = start.sectionZ();
        // Loop: repeats a block
        for (int i = 0; i < sectionCount; i++) {
            // Assigns a value
            GenSection section = areaSections[i];
            // Calls a method
            final int sectionX = indexToX(i, width) + startSectionX;
            // Calls a method
            final int sectionY = indexToY(i, width, height) + startSectionY;
            // Calls a method
            final int sectionZ = indexToZ(i, width, height) + startSectionZ;
            // Calls a method
            final GenerationUnit sectionUnit = section(biomeRegistry, section, sectionX, sectionY, sectionZ);
            // Assigns a value
            sectionsArray[i] = sectionUnit;
        // End of a block/expression
        }
        // Calls a method
        final List<GenerationUnit> sections = List.of(sectionsArray);
        // Calls a method
        final Vec size = Vec.SECTION.mul(width, height, depth);
        // Calls a method
        final Vec end = start.add(size);
        // Calls a method
        final UnitModifier modifier = new AreaModifierImpl(size, start, end, width, height, depth, sections);
        // Returns a value to the caller
        return unit(biomeRegistry, modifier, start, end, sections);
    // End of a block/expression
    }

    // Code statement
    public static UnitImpl unit(DynamicRegistry<Biome> biomeRegistry, UnitModifier modifier, Vec start, Vec end,
                                // Annotation for the following element
                                @Nullable List<GenerationUnit> divided) {
        // Branch: checks a condition
        if (start.x() > end.x() || start.y() > end.y() || start.z() > end.z()) {
            // Throws an exception
            throw new IllegalArgumentException("absoluteStart must be before absoluteEnd");
        // End of a block/expression
        }
        // Branch: checks a condition
        if (start.x() % 16 != 0 || start.y() % 16 != 0 || start.z() % 16 != 0) {
            // Throws an exception
            throw new IllegalArgumentException("absoluteStart must be a multiple of 16");
        // End of a block/expression
        }
        // Branch: checks a condition
        if (end.x() % 16 != 0 || end.y() % 16 != 0 || end.z() % 16 != 0) {
            // Throws an exception
            throw new IllegalArgumentException("absoluteEnd must be a multiple of 16");
        // End of a block/expression
        }
        // Calls a method
        final Vec size = end.sub(start);
        // Returns a value to the caller
        return new UnitImpl(biomeRegistry, modifier, size, start, end, divided, new CopyOnWriteArrayList<>());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class DynamicFork implements Block.Setter {
        // Code statement
        final DynamicRegistry<Biome> biomeRegistry;
        // Code statement
        Vec minSection;
        // Code statement
        int width, height, depth;
        // Code statement
        List<GenerationUnit> sections;

        // Start of a method/block
        DynamicFork(DynamicRegistry<Biome> biomeRegistry) {
            // Access to the current/parent object
            this.biomeRegistry = biomeRegistry;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setBlock(int x, int y, int z, Block block) {
            // Calls a method
            resize(x, y, z);
            // Calls a method
            GenerationUnit section = findAbsolute(sections, minSection, width, height, depth, x, y, z);
            // Code statement
            assert section.absoluteStart().sectionX() == globalToChunk(x) &&
                    // Code statement
                    section.absoluteStart().sectionY() == globalToChunk(y) &&
                    // Code statement
                    section.absoluteStart().sectionZ() == globalToChunk(z) :
                    // Calls a method
                    "Invalid section " + section.absoluteStart() + " for " + x + ", " + y + ", " + z;
            // Calls a method
            section.modifier().setBlock(x, y, z, block);
        // End of a block/expression
        }

        // Start of a method/block
        private void resize(int x, int y, int z) {
            // Calls a method
            final int sectionX = globalToChunk(x);
            // Calls a method
            final int sectionY = globalToChunk(y);
            // Calls a method
            final int sectionZ = globalToChunk(z);
            // Branch: checks a condition
            if (sections == null) {
                // Access to the current/parent object
                this.minSection = Vec.SECTION.mul(sectionX, sectionY, sectionZ);
                // Access to the current/parent object
                this.width = 1;
                // Access to the current/parent object
                this.height = 1;
                // Access to the current/parent object
                this.depth = 1;
                // Access to the current/parent object
                this.sections = List.of(section(biomeRegistry, new GenSection(), sectionX, sectionY, sectionZ, true));
            // Branch: checks a condition
            } else if (x < minSection.x() || y < minSection.y() || z < minSection.z() ||
                    // Start of a method/block
                    x >= minSection.x() + width * 16 || y >= minSection.y() + height * 16 || z >= minSection.z() + depth * 16) {
                // Resize necessary
                // Assigns a value
                final Vec newMin = new Vec(Math.min(minSection.x(), sectionX * 16),
                        // Code statement
                        Math.min(minSection.y(), sectionY * 16),
                        // Calls a method
                        Math.min(minSection.z(), sectionZ * 16));
                // Assigns a value
                final Vec newMax = new Vec(Math.max(minSection.x() + width * 16, sectionX * 16 + 16),
                        // Code statement
                        Math.max(minSection.y() + height * 16, sectionY * 16 + 16),
                        // Calls a method
                        Math.max(minSection.z() + depth * 16, sectionZ * 16 + 16));
                // Calls a method
                final int newWidth = globalToChunk(newMax.x() - newMin.x());
                // Calls a method
                final int newHeight = globalToChunk(newMax.y() - newMin.y());
                // Calls a method
                final int newDepth = globalToChunk(newMax.z() - newMin.z());
                // Resize
                // Assigns a value
                GenerationUnit[] newSections = new GenerationUnit[newWidth * newHeight * newDepth];
                // Copy old sections
                // Loop: repeats a block
                for (GenerationUnit s : sections) {
                    // Calls a method
                    final Point start = s.absoluteStart();
                    // Calls a method
                    final int newX = globalToChunk(start.x() - newMin.x());
                    // Calls a method
                    final int newY = globalToChunk(start.y() - newMin.y());
                    // Calls a method
                    final int newZ = globalToChunk(start.z() - newMin.z());
                    // Calls a method
                    final int index = findIndex(newWidth, newHeight, newDepth, newX, newY, newZ);
                    // Assigns a value
                    newSections[index] = s;
                // End of a block/expression
                }
                // Fill new sections
                // Calls a method
                final int startX = newMin.sectionX();
                // Calls a method
                final int startY = newMin.sectionY();
                // Calls a method
                final int startZ = newMin.sectionZ();
                // Loop: repeats a block
                for (int i = 0; i < newSections.length; i++) {
                    // Branch: checks a condition
                    if (newSections[i] == null) {
                        // Calls a method
                        final int newX = indexToX(i, newWidth) + startX;
                        // Calls a method
                        final int newY = indexToY(i, newWidth, newHeight) + startY;
                        // Calls a method
                        final int newZ = indexToZ(i, newWidth, newHeight) + startZ;
                        // Calls a method
                        final GenerationUnit unit = section(biomeRegistry, new GenSection(), newX, newY, newZ, true);
                        // Assigns a value
                        newSections[i] = unit;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Access to the current/parent object
                this.sections = List.of(newSections);
                // Access to the current/parent object
                this.minSection = newMin;
                // Access to the current/parent object
                this.width = newWidth;
                // Access to the current/parent object
                this.height = newHeight;
                // Access to the current/parent object
                this.depth = newDepth;
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record UnitImpl(DynamicRegistry<Biome> biomeRegistry, UnitModifier modifier,
                           // Code statement
                           Vec size, Vec absoluteStart, Vec absoluteEnd,
                           // Annotation for the following element
                           @Nullable List<GenerationUnit> divided,
                           // Start of a method/block
                           List<UnitImpl> forks) implements GenerationUnit {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public GenerationUnit fork(Point start, Point end) {
            // Calls a method
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Calls a method
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();

            // Calls a method
            final int minSectionX = floorSection(startX) / 16, minSectionY = floorSection(startY) / 16, minSectionZ = floorSection(startZ) / 16;
            // Calls a method
            final int maxSectionX = ceilSection(endX) / 16, maxSectionY = ceilSection(endY) / 16, maxSectionZ = ceilSection(endZ) / 16;

            // Assigns a value
            final int width = maxSectionX - minSectionX;
            // Assigns a value
            final int height = maxSectionY - minSectionY;
            // Assigns a value
            final int depth = maxSectionZ - minSectionZ;

            // Assigns a value
            GenerationUnit[] units = new GenerationUnit[width * height * depth];
            // Assigns a value
            int index = 0;
            // Z -> Y -> X order is important for indexing
            // Loop: repeats a block
            for (int sectionZ = minSectionZ; sectionZ < maxSectionZ; sectionZ++) {
                // Loop: repeats a block
                for (int sectionY = minSectionY; sectionY < maxSectionY; sectionY++) {
                    // Loop: repeats a block
                    for (int sectionX = minSectionX; sectionX < maxSectionX; sectionX++) {
                        // Calls a method
                        final GenerationUnit unit = section(biomeRegistry, new GenSection(), sectionX, sectionY, sectionZ, true);
                        // Assigns a value
                        units[index++] = unit;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            final List<GenerationUnit> sections = List.of(units);
            // Calls a method
            final Vec startSection = Vec.SECTION.mul(minSectionX, minSectionY, minSectionZ);
            // Returns a value to the caller
            return registerFork(startSection, sections, width, height, depth);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void fork(Consumer<Block.Setter> consumer) {
            // Calls a method
            DynamicFork dynamicFork = new DynamicFork(biomeRegistry);
            // Calls a method
            consumer.accept(dynamicFork);
            // Assigns a value
            final Vec startSection = dynamicFork.minSection;
            // Branch: checks a condition
            if (startSection == null)
                // Returns a value to the caller
                return; // No block has been placed
            // Assigns a value
            final int width = dynamicFork.width;
            // Assigns a value
            final int height = dynamicFork.height;
            // Assigns a value
            final int depth = dynamicFork.depth;
            // Assigns a value
            final List<GenerationUnit> sections = dynamicFork.sections;
            // Calls a method
            registerFork(startSection, sections, width, height, depth);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<GenerationUnit> subdivide() {
            // Returns a value to the caller
            return Objects.requireNonNullElseGet(divided, GenerationUnit.super::subdivide);
        // End of a block/expression
        }

        // Code statement
        private GenerationUnit registerFork(Vec start, List<GenerationUnit> sections,
                                            // Start of a method/block
                                            int width, int height, int depth) {
            // Calls a method
            final Vec end = start.add(width * 16, height * 16, depth * 16);
            // Calls a method
            final Vec size = end.sub(start);
            // Calls a method
            final AreaModifierImpl modifier = new AreaModifierImpl(size, start, end, width, height, depth, sections);
            // Calls a method
            final UnitImpl fork = new UnitImpl(biomeRegistry, modifier, size, start, end, sections, forks);
            // Calls a method
            forks.add(fork);
            // Returns a value to the caller
            return fork;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record SectionModifierImpl(DynamicRegistry<Biome> biomeRegistry, Vec start, Vec end,
                                      // Start of a method/block
                                      GenSection genSection, boolean fork) implements GenericModifier {

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
            // Branch: checks a condition
            if (fork) throw new IllegalStateException("Cannot modify biomes of a fork");
            // Calls a method
            final int id = biomeRegistry.getId(biome);
            // Calls a method
            Check.argCondition(id == -1, "Biome has not been registered: {0}", biome);
            // Access to the current/parent object
            this.genSection.biomes.set(
                    // Code statement
                    globalToSectionRelative(x) / 4,
                    // Code statement
                    globalToSectionRelative(y) / 4,
                    // Calls a method
                    globalToSectionRelative(z) / 4, id);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setBlock(int x, int y, int z, Block block) {
            // Calls a method
            final int localX = globalToSectionRelative(x);
            // Calls a method
            final int localY = globalToSectionRelative(y);
            // Calls a method
            final int localZ = globalToSectionRelative(z);
            // Calls a method
            handleCache(localX, localY, localZ, block);
            // Access to the current/parent object
            this.genSection.blocks.set(localX, localY, localZ, retrieveBlockId(block));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setRelative(int x, int y, int z, Block block) {
            // Calls a method
            handleCache(x, y, z, block);
            // Access to the current/parent object
            this.genSection.blocks.set(x, y, z, retrieveBlockId(block));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setAllRelative(Supplier supplier) {
            // Access to the current/parent object
            this.genSection.blocks.setAll((x, y, z) -> {
                // Calls a method
                final Block block = supplier.get(x, y, z);
                // Calls a method
                handleCache(x, y, z, block);
                // Returns a value to the caller
                return retrieveBlockId(block);
            // End of a block/expression
            });
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void fill(Block block) {
            // Access to the current/parent object
            this.genSection.specials.clear();
            // Branch: checks a condition
            if (requireCache(block)) {
                // Loop: repeats a block
                for (int x = 0; x < 16; x++) {
                    // Loop: repeats a block
                    for (int y = 0; y < 16; y++) {
                        // Loop: repeats a block
                        for (int z = 0; z < 16; z++) {
                            // Access to the current/parent object
                            this.genSection.specials.put(chunkBlockIndex(x, y, z), block);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Access to the current/parent object
            this.genSection.blocks.fill(retrieveBlockId(block));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void fill(Point start, Point end, Block block) {
            // Calls a method
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Calls a method
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Calls a method
            final int sectionStartX = this.start.blockX(), sectionStartY = this.start.blockY(), sectionStartZ = this.start.blockZ();
            // Calls a method
            final int sectionEndX = this.end.blockX(), sectionEndY = this.end.blockY(), sectionEndZ = this.end.blockZ();
            // Branch: checks a condition
            if (startX >= sectionStartX && startY >= sectionStartY && startZ >= sectionStartZ &&
                    // Start of a method/block
                    endX <= sectionEndX && endY <= sectionEndY && endZ <= sectionEndZ) {
                // Code statement
                fillRelative(startX - sectionStartX, startY - sectionStartY, startZ - sectionStartZ,
                        // Code statement
                        endX - sectionStartX, endY - sectionStartY, endZ - sectionStartZ, block);
            // Alternative branch of the condition
            } else {
                // Loop: repeats a block
                for (int x = startX; x < endX; x++) {
                    // Loop: repeats a block
                    for (int y = startY; y < endY; y++) {
                        // Loop: repeats a block
                        for (int z = startZ; z < endZ; z++) {
                            // Calls a method
                            setBlock(x, y, z, block);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void fillHeight(int minHeight, int maxHeight, Block block) {
            // Calls a method
            final int sectionStartY = start.blockY(), sectionEndY = end.blockY();
            // Calls a method
            final int localMinY = Math.max(minHeight, sectionStartY) - sectionStartY;
            // Calls a method
            final int localMaxY = Math.min(maxHeight, sectionEndY) - sectionStartY;
            // Branch: checks a condition
            if (localMinY >= localMaxY) return;
            // Calls a method
            fillRelative(0, localMinY, 0, 16, localMaxY, 16, block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void fillBiome(RegistryKey<Biome> biome) {
            // Branch: checks a condition
            if (fork) throw new IllegalStateException("Cannot modify biomes of a fork");
            // Calls a method
            final int id = biomeRegistry.getId(biome);
            // Calls a method
            Check.argCondition(id == -1, "Biome has not been registered: {0}", biome);
            // Access to the current/parent object
            this.genSection.biomes.fill(id);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Vec size() {
            // Returns a value to the caller
            return Vec.SECTION;
        // End of a block/expression
        }

        // Start of a method/block
        private int retrieveBlockId(Block block) {
            // Calls a method
            final int stateId = block.stateId();
            // Returns a value to the caller
            return fork ? stateId + 1 : stateId;
        // End of a block/expression
        }

        // Start of a method/block
        private void handleCache(int x, int y, int z, Block block) {
            // Branch: checks a condition
            if (requireCache(block)) {
                // Access to the current/parent object
                this.genSection.specials.put(chunkBlockIndex(x, y, z), block);
            // Branch: checks a condition
            } else if (!genSection.specials.isEmpty()) {
                // Access to the current/parent object
                this.genSection.specials.remove(chunkBlockIndex(x, y, z));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        private boolean requireCache(Block block) {
            // Returns a value to the caller
            return block.hasNbt() || block.handler() != null || block.registry().isBlockEntity();
        // End of a block/expression
        }

        // Start of a method/block
        private void fillRelative(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block) {
            // Branch: checks a condition
            if (minX == 0 && minY == 0 && minZ == 0 && maxX == 16 && maxY == 16 && maxZ == 16) {
                // Calls a method
                fill(block);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Calls a method
            final int stateId = retrieveBlockId(block);
            // Calls a method
            final boolean requireCache = requireCache(block);
            // Calls a method
            final boolean clearCache = !requireCache && !genSection.specials.isEmpty();
            // Loop: repeats a block
            for (int x = minX; x < maxX; x++) {
                // Loop: repeats a block
                for (int y = minY; y < maxY; y++) {
                    // Loop: repeats a block
                    for (int z = minZ; z < maxZ; z++) {
                        // Branch: checks a condition
                        if (requireCache) {
                            // Access to the current/parent object
                            this.genSection.specials.put(chunkBlockIndex(x, y, z), block);
                        // Branch: checks a condition
                        } else if (clearCache) {
                            // Access to the current/parent object
                            this.genSection.specials.remove(chunkBlockIndex(x, y, z));
                        // End of a block/expression
                        }
                        // Access to the current/parent object
                        this.genSection.blocks.set(x, y, z, stateId);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record AreaModifierImpl(Vec size, Vec start, Vec end,
                                   // Code statement
                                   int width, int height, int depth,
                                   // Start of a method/block
                                   List<GenerationUnit> sections) implements GenericModifier {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setBlock(int x, int y, int z, Block block) {
            // Calls a method
            checkBorder(x, y, z);
            // Calls a method
            final GenerationUnit section = findAbsoluteSection(x, y, z);
            // Calls a method
            y -= start.y();
            // Calls a method
            section.modifier().setBlock(x, y, z, block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
            // Calls a method
            checkBorder(x, y, z);
            // Calls a method
            final GenerationUnit section = findAbsoluteSection(x, y, z);
            // Calls a method
            y -= start.y();
            // Calls a method
            section.modifier().setBiome(x, y, z, biome);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setRelative(int x, int y, int z, Block block) {
            // Branch: checks a condition
            if (x < 0 || x >= size.x() || y < 0 || y >= size.y() || z < 0 || z >= size.z()) {
                // Throws an exception
                throw new IllegalArgumentException("x, y and z must be in the chunk: " + x + ", " + y + ", " + z);
            // End of a block/expression
            }
            // Calls a method
            final GenerationUnit section = findRelativeSection(x, y, z);
            // Calls a method
            x = globalToSectionRelative(x);
            // Calls a method
            y = globalToSectionRelative(y);
            // Calls a method
            z = globalToSectionRelative(z);
            // Calls a method
            section.modifier().setBlock(x, y, z, block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setAll(Supplier supplier) {
            // Loop: repeats a block
            for (GenerationUnit section : sections) {
                // Calls a method
                final Point start = section.absoluteStart();
                // Calls a method
                final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
                // Calls a method
                section.modifier().setAllRelative((x, y, z) -> supplier.get(x + startX, y + startY, z + startZ));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setAllRelative(Supplier supplier) {
            // Assigns a value
            final Point start = this.start;
            // Calls a method
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Loop: repeats a block
            for (GenerationUnit section : sections) {
                // Calls a method
                final Point sectionStart = section.absoluteStart();
                // Calls a method
                final int offsetX = sectionStart.blockX() - startX, offsetY = sectionStart.blockY() - startY, offsetZ = sectionStart.blockZ() - startZ;
                // Calls a method
                section.modifier().setAllRelative((x, y, z) -> supplier.get(x + offsetX, y + offsetY, z + offsetZ));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void fill(Block block) {
            // Loop: repeats a block
            for (GenerationUnit section : sections) {
                // Calls a method
                section.modifier().fill(block);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void fillBiome(RegistryKey<Biome> biome) {
            // Loop: repeats a block
            for (GenerationUnit section : sections) {
                // Calls a method
                section.modifier().fillBiome(biome);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void fillHeight(int minHeight, int maxHeight, Block block) {
            // Assigns a value
            final Vec start = this.start;
            // Calls a method
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Calls a method
            final int endY = end.blockY();
            // Calls a method
            minHeight = Math.max(minHeight, startY);
            // Calls a method
            maxHeight = Math.min(maxHeight, endY);
            // Branch: checks a condition
            if (minHeight >= maxHeight) return;
            // Assigns a value
            final int width = this.width, depth = this.depth;
            // Calls a method
            final int minMultiple = floorSection(minHeight);
            // Calls a method
            final int maxMultiple = ceilSection(maxHeight);
            // Assigns a value
            final boolean startOffset = minMultiple != minHeight;
            // Assigns a value
            final boolean endOffset = maxMultiple != maxHeight;
            // Branch: checks a condition
            if (startOffset || endOffset) {
                // Calls a method
                final int firstFill = Math.min(minMultiple + 16, maxHeight);
                // Calls a method
                final int lastFill = startOffset ? Math.max(firstFill, floorSection(maxHeight)) : floorSection(maxHeight);
                // Loop: repeats a block
                for (int x = 0; x < width; x++) {
                    // Loop: repeats a block
                    for (int z = 0; z < depth; z++) {
                        // Assigns a value
                        final int sectionX = startX + x * 16;
                        // Assigns a value
                        final int sectionZ = startZ + z * 16;
                        // Fill start
                        // Branch: checks a condition
                        if (startOffset) {
                            // Calls a method
                            final GenerationUnit section = findAbsoluteSection(sectionX, minMultiple, sectionZ);
                            // Calls a method
                            section.modifier().fillHeight(minHeight, firstFill, block);
                        // End of a block/expression
                        }
                        // Fill end
                        // Branch: checks a condition
                        if (endOffset) {
                            // Calls a method
                            final GenerationUnit section = findAbsoluteSection(sectionX, maxHeight, sectionZ);
                            // Calls a method
                            section.modifier().fillHeight(lastFill, maxHeight, block);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Middle sections (to fill)
            // Calls a method
            final int startSection = (minMultiple) / 16 + (startOffset ? 1 : 0);
            // Calls a method
            final int endSection = (maxMultiple) / 16 + (endOffset ? -1 : 0);
            // Loop: repeats a block
            for (int i = startSection; i < endSection; i++) {
                // Loop: repeats a block
                for (int x = 0; x < width; x++) {
                    // Loop: repeats a block
                    for (int z = 0; z < depth; z++) {
                        // Calls a method
                        final GenerationUnit section = findAbsoluteSection(startX + x * 16, i * 16, startZ + z * 16);
                        // Calls a method
                        section.modifier().fill(block);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        private GenerationUnit findAbsoluteSection(int x, int y, int z) {
            // Returns a value to the caller
            return findAbsolute(sections, start, width, height, depth, x, y, z);
        // End of a block/expression
        }

        // Start of a method/block
        private GenerationUnit findRelativeSection(int x, int y, int z) {
            // Returns a value to the caller
            return findAbsolute(sections, Vec.ZERO, width, height, depth, x, y, z);
        // End of a block/expression
        }

        // Start of a method/block
        private void checkBorder(int x, int y, int z) {
            // Calls a method
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Calls a method
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Branch: checks a condition
            if (x < startX || x >= endX || y < startY || y >= endY || z < startZ || z >= endZ) {
                // Calls a method
                final String format = String.format("Invalid coordinates: %d, %d, %d for area %s %s", x, y, z, start, end);
                // Throws an exception
                throw new IllegalArgumentException(format);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface GenericModifier extends UnitModifier
            // Start of a method/block
            permits AreaModifierImpl, SectionModifierImpl {
        // Calls a method
        Vec size();

        // Calls a method
        Vec start();

        // Calls a method
        Vec end();

        // Annotation for the following element
        @Override
        // Start of a method/block
        default void setAll(Supplier supplier) {
            // Calls a method
            final Vec start = start(), end = end();
            // Calls a method
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Calls a method
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Loop: repeats a block
            for (int x = startX; x < endX; x++) {
                // Loop: repeats a block
                for (int y = startY; y < endY; y++) {
                    // Loop: repeats a block
                    for (int z = startZ; z < endZ; z++) {
                        // Calls a method
                        setBlock(x, y, z, supplier.get(x, y, z));
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default void setAllRelative(Supplier supplier) {
            // Calls a method
            final Vec size = size();
            // Calls a method
            final int endX = size.blockX(), endY = size.blockY(), endZ = size.blockZ();
            // Loop: repeats a block
            for (int x = 0; x < endX; x++) {
                // Loop: repeats a block
                for (int y = 0; y < endY; y++) {
                    // Loop: repeats a block
                    for (int z = 0; z < endZ; z++) {
                        // Calls a method
                        setRelative(x, y, z, supplier.get(x, y, z));
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default void fill(Block block) {
            // Calls a method
            fill(start(), end(), block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default void fill(Point start, Point end, Block block) {
            // Calls a method
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Calls a method
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Loop: repeats a block
            for (int x = startX; x < endX; x++) {
                // Loop: repeats a block
                for (int y = startY; y < endY; y++) {
                    // Loop: repeats a block
                    for (int z = startZ; z < endZ; z++) {
                        // Calls a method
                        setBlock(x, y, z, block);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default void fillHeight(int minHeight, int maxHeight, Block block) {
            // Calls a method
            final Vec start = start();
            // Calls a method
            final Vec end = end();
            // Calls a method
            final int startY = start.blockY(), endY = end.blockY();
            // Branch: checks a condition
            if (startY >= minHeight && endY <= maxHeight) {
                // Fast path if the unit is fully contained in the height range
                // Calls a method
                fill(start, end, block);
            // Alternative branch of the condition
            } else {
                // Slow path if the unit is not fully contained in the height range
                // Calls a method
                fill(start.withY(Math.max(minHeight, startY)), end.withY(Math.min(maxHeight, endY)), block);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private static GenerationUnit findAbsolute(List<GenerationUnit> units, Vec start,
                                               // Code statement
                                               int width, int height, int depth,
                                               // Start of a method/block
                                               int x, int y, int z) {
        // Calls a method
        final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
        // Calls a method
        final int sectionX = globalToChunk(x - startX), sectionY = globalToChunk(y - startY), sectionZ = globalToChunk(z - startZ);
        // Calls a method
        final int index = findIndex(width, height, depth, sectionX, sectionY, sectionZ);
        // Returns a value to the caller
        return units.get(index);
    // End of a block/expression
    }

    // Code statement
    private static int findIndex(int width, int height, int depth,
                                 // Start of a method/block
                                 int x, int y, int z) {
        // Code statement
        assert width > 0 && height > 0 && depth > 0;
        // Returns a value to the caller
        return (z * width * height) + (y * width) + x;
    // End of a block/expression
    }

    // Start of a method/block
    private static int indexToX(int index, int width) {
        // Returns a value to the caller
        return index % width;
    // End of a block/expression
    }

    // Start of a method/block
    private static int indexToY(int index, int width, int height) {
        // Returns a value to the caller
        return (index / width) % height;
    // End of a block/expression
    }

    // Start of a method/block
    private static int indexToZ(int index, int width, int height) {
        // Returns a value to the caller
        return index / (width * height);
    // End of a block/expression
    }
// End of a block/expression
}
