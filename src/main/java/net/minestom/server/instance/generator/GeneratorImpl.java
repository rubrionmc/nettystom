// Déclaration du paquet de ce fichier
package net.minestom.server.instance.generator;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArrayList;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.*;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class GeneratorImpl {
    // Déclaration de type (classe/interface/enum/record)
    public record GenSection(Palette blocks, Palette biomes, Int2ObjectMap<Block> specials) {
        // Début d'une méthode/d'un bloc
        public GenSection(Palette blocks, Palette biomes) {
            // Appelle une méthode
            this(blocks, biomes, new Int2ObjectOpenHashMap<>(0));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public GenSection() {
            // Appelle une méthode
            this(Palette.blocks(), Palette.biomes());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static GenerationUnit section(DynamicRegistry<Biome> biomeRegistry, GenSection section,
                                  // Instruction de code
                                  int sectionX, int sectionY, int sectionZ,
                                  // Début d'une méthode/d'un bloc
                                  boolean fork) {
        // Appelle une méthode
        final Vec start = Vec.SECTION.mul(sectionX, sectionY, sectionZ);
        // Appelle une méthode
        final Vec end = start.add(Vec.SECTION);
        // Appelle une méthode
        final UnitModifier modifier = new SectionModifierImpl(biomeRegistry, start, end, section, fork);
        // Renvoie une valeur à l'appelant
        return unit(biomeRegistry, modifier, start, end, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static GenerationUnit section(DynamicRegistry<Biome> biomeRegistry, GenSection section, int sectionX, int sectionY, int sectionZ) {
        // Renvoie une valeur à l'appelant
        return section(biomeRegistry, section, sectionX, sectionY, sectionZ, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static UnitImpl chunk(DynamicRegistry<Biome> biomeRegistry, GenSection[] chunkSections, int chunkX, int minSection, int chunkZ) {
        // Appelle une méthode
        final Vec start = Vec.SECTION.mul(chunkX, minSection, chunkZ);
        // Renvoie une valeur à l'appelant
        return area(biomeRegistry, start, 1, chunkSections.length, 1, chunkSections);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static UnitImpl area(DynamicRegistry<Biome> biomeRegistry, Vec start, int width, int height, int depth, GenSection[] areaSections) {
        // Embranchement : vérifie une condition
        if (width == 0 || height == 0 || depth == 0) {
            // Lève une exception
            throw new IllegalArgumentException("Width, height and depth must be greater than 0, got " + width + ", " + height + ", " + depth);
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (areaSections.length != width * height * depth) {
            // Lève une exception
            throw new IllegalArgumentException("Invalid section count, expected " + width * height * depth + " but got " + areaSections.length);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        final int sectionCount = areaSections.length;
        // Affecte une valeur
        GenerationUnit[] sectionsArray = new GenerationUnit[sectionCount];
        // Appelle une méthode
        final int startSectionX = start.sectionX(), startSectionY = start.sectionY(), startSectionZ = start.sectionZ();
        // Boucle : répète un bloc
        for (int i = 0; i < sectionCount; i++) {
            // Affecte une valeur
            GenSection section = areaSections[i];
            // Appelle une méthode
            final int sectionX = indexToX(i, width) + startSectionX;
            // Appelle une méthode
            final int sectionY = indexToY(i, width, height) + startSectionY;
            // Appelle une méthode
            final int sectionZ = indexToZ(i, width, height) + startSectionZ;
            // Appelle une méthode
            final GenerationUnit sectionUnit = section(biomeRegistry, section, sectionX, sectionY, sectionZ);
            // Affecte une valeur
            sectionsArray[i] = sectionUnit;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final List<GenerationUnit> sections = List.of(sectionsArray);
        // Appelle une méthode
        final Vec size = Vec.SECTION.mul(width, height, depth);
        // Appelle une méthode
        final Vec end = start.add(size);
        // Appelle une méthode
        final UnitModifier modifier = new AreaModifierImpl(size, start, end, width, height, depth, sections);
        // Renvoie une valeur à l'appelant
        return unit(biomeRegistry, modifier, start, end, sections);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static UnitImpl unit(DynamicRegistry<Biome> biomeRegistry, UnitModifier modifier, Vec start, Vec end,
                                // Annotation pour l'élément suivant
                                @Nullable List<GenerationUnit> divided) {
        // Embranchement : vérifie une condition
        if (start.x() > end.x() || start.y() > end.y() || start.z() > end.z()) {
            // Lève une exception
            throw new IllegalArgumentException("absoluteStart must be before absoluteEnd");
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (start.x() % 16 != 0 || start.y() % 16 != 0 || start.z() % 16 != 0) {
            // Lève une exception
            throw new IllegalArgumentException("absoluteStart must be a multiple of 16");
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (end.x() % 16 != 0 || end.y() % 16 != 0 || end.z() % 16 != 0) {
            // Lève une exception
            throw new IllegalArgumentException("absoluteEnd must be a multiple of 16");
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final Vec size = end.sub(start);
        // Renvoie une valeur à l'appelant
        return new UnitImpl(biomeRegistry, modifier, size, start, end, divided, new CopyOnWriteArrayList<>());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class DynamicFork implements Block.Setter {
        // Instruction de code
        final DynamicRegistry<Biome> biomeRegistry;
        // Instruction de code
        Vec minSection;
        // Instruction de code
        int width, height, depth;
        // Instruction de code
        List<GenerationUnit> sections;

        // Début d'une méthode/d'un bloc
        DynamicFork(DynamicRegistry<Biome> biomeRegistry) {
            // Accès à l'objet courant/parent
            this.biomeRegistry = biomeRegistry;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setBlock(int x, int y, int z, Block block) {
            // Appelle une méthode
            resize(x, y, z);
            // Appelle une méthode
            GenerationUnit section = findAbsolute(sections, minSection, width, height, depth, x, y, z);
            // Instruction de code
            assert section.absoluteStart().sectionX() == globalToChunk(x) &&
                    // Instruction de code
                    section.absoluteStart().sectionY() == globalToChunk(y) &&
                    // Instruction de code
                    section.absoluteStart().sectionZ() == globalToChunk(z) :
                    // Appelle une méthode
                    "Invalid section " + section.absoluteStart() + " for " + x + ", " + y + ", " + z;
            // Appelle une méthode
            section.modifier().setBlock(x, y, z, block);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void resize(int x, int y, int z) {
            // Appelle une méthode
            final int sectionX = globalToChunk(x);
            // Appelle une méthode
            final int sectionY = globalToChunk(y);
            // Appelle une méthode
            final int sectionZ = globalToChunk(z);
            // Embranchement : vérifie une condition
            if (sections == null) {
                // Accès à l'objet courant/parent
                this.minSection = Vec.SECTION.mul(sectionX, sectionY, sectionZ);
                // Accès à l'objet courant/parent
                this.width = 1;
                // Accès à l'objet courant/parent
                this.height = 1;
                // Accès à l'objet courant/parent
                this.depth = 1;
                // Accès à l'objet courant/parent
                this.sections = List.of(section(biomeRegistry, new GenSection(), sectionX, sectionY, sectionZ, true));
            // Embranchement : vérifie une condition
            } else if (x < minSection.x() || y < minSection.y() || z < minSection.z() ||
                    // Début d'une méthode/d'un bloc
                    x >= minSection.x() + width * 16 || y >= minSection.y() + height * 16 || z >= minSection.z() + depth * 16) {
                // Resize necessary
                // Affecte une valeur
                final Vec newMin = new Vec(Math.min(minSection.x(), sectionX * 16),
                        // Instruction de code
                        Math.min(minSection.y(), sectionY * 16),
                        // Appelle une méthode
                        Math.min(minSection.z(), sectionZ * 16));
                // Affecte une valeur
                final Vec newMax = new Vec(Math.max(minSection.x() + width * 16, sectionX * 16 + 16),
                        // Instruction de code
                        Math.max(minSection.y() + height * 16, sectionY * 16 + 16),
                        // Appelle une méthode
                        Math.max(minSection.z() + depth * 16, sectionZ * 16 + 16));
                // Appelle une méthode
                final int newWidth = globalToChunk(newMax.x() - newMin.x());
                // Appelle une méthode
                final int newHeight = globalToChunk(newMax.y() - newMin.y());
                // Appelle une méthode
                final int newDepth = globalToChunk(newMax.z() - newMin.z());
                // Resize
                // Affecte une valeur
                GenerationUnit[] newSections = new GenerationUnit[newWidth * newHeight * newDepth];
                // Copy old sections
                // Boucle : répète un bloc
                for (GenerationUnit s : sections) {
                    // Appelle une méthode
                    final Point start = s.absoluteStart();
                    // Appelle une méthode
                    final int newX = globalToChunk(start.x() - newMin.x());
                    // Appelle une méthode
                    final int newY = globalToChunk(start.y() - newMin.y());
                    // Appelle une méthode
                    final int newZ = globalToChunk(start.z() - newMin.z());
                    // Appelle une méthode
                    final int index = findIndex(newWidth, newHeight, newDepth, newX, newY, newZ);
                    // Affecte une valeur
                    newSections[index] = s;
                // Fin d'un bloc/d'une expression
                }
                // Fill new sections
                // Appelle une méthode
                final int startX = newMin.sectionX();
                // Appelle une méthode
                final int startY = newMin.sectionY();
                // Appelle une méthode
                final int startZ = newMin.sectionZ();
                // Boucle : répète un bloc
                for (int i = 0; i < newSections.length; i++) {
                    // Embranchement : vérifie une condition
                    if (newSections[i] == null) {
                        // Appelle une méthode
                        final int newX = indexToX(i, newWidth) + startX;
                        // Appelle une méthode
                        final int newY = indexToY(i, newWidth, newHeight) + startY;
                        // Appelle une méthode
                        final int newZ = indexToZ(i, newWidth, newHeight) + startZ;
                        // Appelle une méthode
                        final GenerationUnit unit = section(biomeRegistry, new GenSection(), newX, newY, newZ, true);
                        // Affecte une valeur
                        newSections[i] = unit;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Accès à l'objet courant/parent
                this.sections = List.of(newSections);
                // Accès à l'objet courant/parent
                this.minSection = newMin;
                // Accès à l'objet courant/parent
                this.width = newWidth;
                // Accès à l'objet courant/parent
                this.height = newHeight;
                // Accès à l'objet courant/parent
                this.depth = newDepth;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record UnitImpl(DynamicRegistry<Biome> biomeRegistry, UnitModifier modifier,
                           // Instruction de code
                           Vec size, Vec absoluteStart, Vec absoluteEnd,
                           // Annotation pour l'élément suivant
                           @Nullable List<GenerationUnit> divided,
                           // Début d'une méthode/d'un bloc
                           List<UnitImpl> forks) implements GenerationUnit {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public GenerationUnit fork(Point start, Point end) {
            // Appelle une méthode
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Appelle une méthode
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();

            // Appelle une méthode
            final int minSectionX = floorSection(startX) / 16, minSectionY = floorSection(startY) / 16, minSectionZ = floorSection(startZ) / 16;
            // Appelle une méthode
            final int maxSectionX = ceilSection(endX) / 16, maxSectionY = ceilSection(endY) / 16, maxSectionZ = ceilSection(endZ) / 16;

            // Affecte une valeur
            final int width = maxSectionX - minSectionX;
            // Affecte une valeur
            final int height = maxSectionY - minSectionY;
            // Affecte une valeur
            final int depth = maxSectionZ - minSectionZ;

            // Affecte une valeur
            GenerationUnit[] units = new GenerationUnit[width * height * depth];
            // Affecte une valeur
            int index = 0;
            // Z -> Y -> X order is important for indexing
            // Boucle : répète un bloc
            for (int sectionZ = minSectionZ; sectionZ < maxSectionZ; sectionZ++) {
                // Boucle : répète un bloc
                for (int sectionY = minSectionY; sectionY < maxSectionY; sectionY++) {
                    // Boucle : répète un bloc
                    for (int sectionX = minSectionX; sectionX < maxSectionX; sectionX++) {
                        // Appelle une méthode
                        final GenerationUnit unit = section(biomeRegistry, new GenSection(), sectionX, sectionY, sectionZ, true);
                        // Affecte une valeur
                        units[index++] = unit;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final List<GenerationUnit> sections = List.of(units);
            // Appelle une méthode
            final Vec startSection = Vec.SECTION.mul(minSectionX, minSectionY, minSectionZ);
            // Renvoie une valeur à l'appelant
            return registerFork(startSection, sections, width, height, depth);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void fork(Consumer<Block.Setter> consumer) {
            // Appelle une méthode
            DynamicFork dynamicFork = new DynamicFork(biomeRegistry);
            // Appelle une méthode
            consumer.accept(dynamicFork);
            // Affecte une valeur
            final Vec startSection = dynamicFork.minSection;
            // Embranchement : vérifie une condition
            if (startSection == null)
                // Renvoie une valeur à l'appelant
                return; // No block has been placed
            // Affecte une valeur
            final int width = dynamicFork.width;
            // Affecte une valeur
            final int height = dynamicFork.height;
            // Affecte une valeur
            final int depth = dynamicFork.depth;
            // Affecte une valeur
            final List<GenerationUnit> sections = dynamicFork.sections;
            // Appelle une méthode
            registerFork(startSection, sections, width, height, depth);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<GenerationUnit> subdivide() {
            // Renvoie une valeur à l'appelant
            return Objects.requireNonNullElseGet(divided, GenerationUnit.super::subdivide);
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        private GenerationUnit registerFork(Vec start, List<GenerationUnit> sections,
                                            // Début d'une méthode/d'un bloc
                                            int width, int height, int depth) {
            // Appelle une méthode
            final Vec end = start.add(width * 16, height * 16, depth * 16);
            // Appelle une méthode
            final Vec size = end.sub(start);
            // Appelle une méthode
            final AreaModifierImpl modifier = new AreaModifierImpl(size, start, end, width, height, depth, sections);
            // Appelle une méthode
            final UnitImpl fork = new UnitImpl(biomeRegistry, modifier, size, start, end, sections, forks);
            // Appelle une méthode
            forks.add(fork);
            // Renvoie une valeur à l'appelant
            return fork;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record SectionModifierImpl(DynamicRegistry<Biome> biomeRegistry, Vec start, Vec end,
                                      // Début d'une méthode/d'un bloc
                                      GenSection genSection, boolean fork) implements GenericModifier {

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
            // Embranchement : vérifie une condition
            if (fork) throw new IllegalStateException("Cannot modify biomes of a fork");
            // Appelle une méthode
            final int id = biomeRegistry.getId(biome);
            // Appelle une méthode
            Check.argCondition(id == -1, "Biome has not been registered: {0}", biome);
            // Accès à l'objet courant/parent
            this.genSection.biomes.set(
                    // Instruction de code
                    globalToSectionRelative(x) / 4,
                    // Instruction de code
                    globalToSectionRelative(y) / 4,
                    // Appelle une méthode
                    globalToSectionRelative(z) / 4, id);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setBlock(int x, int y, int z, Block block) {
            // Appelle une méthode
            final int localX = globalToSectionRelative(x);
            // Appelle une méthode
            final int localY = globalToSectionRelative(y);
            // Appelle une méthode
            final int localZ = globalToSectionRelative(z);
            // Appelle une méthode
            handleCache(localX, localY, localZ, block);
            // Accès à l'objet courant/parent
            this.genSection.blocks.set(localX, localY, localZ, retrieveBlockId(block));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setRelative(int x, int y, int z, Block block) {
            // Appelle une méthode
            handleCache(x, y, z, block);
            // Accès à l'objet courant/parent
            this.genSection.blocks.set(x, y, z, retrieveBlockId(block));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setAllRelative(Supplier supplier) {
            // Accès à l'objet courant/parent
            this.genSection.blocks.setAll((x, y, z) -> {
                // Appelle une méthode
                final Block block = supplier.get(x, y, z);
                // Appelle une méthode
                handleCache(x, y, z, block);
                // Renvoie une valeur à l'appelant
                return retrieveBlockId(block);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void fill(Block block) {
            // Accès à l'objet courant/parent
            this.genSection.specials.clear();
            // Embranchement : vérifie une condition
            if (requireCache(block)) {
                // Boucle : répète un bloc
                for (int x = 0; x < 16; x++) {
                    // Boucle : répète un bloc
                    for (int y = 0; y < 16; y++) {
                        // Boucle : répète un bloc
                        for (int z = 0; z < 16; z++) {
                            // Accès à l'objet courant/parent
                            this.genSection.specials.put(chunkBlockIndex(x, y, z), block);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.genSection.blocks.fill(retrieveBlockId(block));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void fill(Point start, Point end, Block block) {
            // Appelle une méthode
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Appelle une méthode
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Appelle une méthode
            final int sectionStartX = this.start.blockX(), sectionStartY = this.start.blockY(), sectionStartZ = this.start.blockZ();
            // Appelle une méthode
            final int sectionEndX = this.end.blockX(), sectionEndY = this.end.blockY(), sectionEndZ = this.end.blockZ();
            // Embranchement : vérifie une condition
            if (startX >= sectionStartX && startY >= sectionStartY && startZ >= sectionStartZ &&
                    // Début d'une méthode/d'un bloc
                    endX <= sectionEndX && endY <= sectionEndY && endZ <= sectionEndZ) {
                // Instruction de code
                fillRelative(startX - sectionStartX, startY - sectionStartY, startZ - sectionStartZ,
                        // Instruction de code
                        endX - sectionStartX, endY - sectionStartY, endZ - sectionStartZ, block);
            // Branche alternative de la condition
            } else {
                // Boucle : répète un bloc
                for (int x = startX; x < endX; x++) {
                    // Boucle : répète un bloc
                    for (int y = startY; y < endY; y++) {
                        // Boucle : répète un bloc
                        for (int z = startZ; z < endZ; z++) {
                            // Appelle une méthode
                            setBlock(x, y, z, block);
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

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void fillHeight(int minHeight, int maxHeight, Block block) {
            // Appelle une méthode
            final int sectionStartY = start.blockY(), sectionEndY = end.blockY();
            // Appelle une méthode
            final int localMinY = Math.max(minHeight, sectionStartY) - sectionStartY;
            // Appelle une méthode
            final int localMaxY = Math.min(maxHeight, sectionEndY) - sectionStartY;
            // Embranchement : vérifie une condition
            if (localMinY >= localMaxY) return;
            // Appelle une méthode
            fillRelative(0, localMinY, 0, 16, localMaxY, 16, block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void fillBiome(RegistryKey<Biome> biome) {
            // Embranchement : vérifie une condition
            if (fork) throw new IllegalStateException("Cannot modify biomes of a fork");
            // Appelle une méthode
            final int id = biomeRegistry.getId(biome);
            // Appelle une méthode
            Check.argCondition(id == -1, "Biome has not been registered: {0}", biome);
            // Accès à l'objet courant/parent
            this.genSection.biomes.fill(id);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Vec size() {
            // Renvoie une valeur à l'appelant
            return Vec.SECTION;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private int retrieveBlockId(Block block) {
            // Appelle une méthode
            final int stateId = block.stateId();
            // Renvoie une valeur à l'appelant
            return fork ? stateId + 1 : stateId;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void handleCache(int x, int y, int z, Block block) {
            // Embranchement : vérifie une condition
            if (requireCache(block)) {
                // Accès à l'objet courant/parent
                this.genSection.specials.put(chunkBlockIndex(x, y, z), block);
            // Embranchement : vérifie une condition
            } else if (!genSection.specials.isEmpty()) {
                // Accès à l'objet courant/parent
                this.genSection.specials.remove(chunkBlockIndex(x, y, z));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private boolean requireCache(Block block) {
            // Renvoie une valeur à l'appelant
            return block.hasNbt() || block.handler() != null || block.registry().isBlockEntity();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void fillRelative(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block) {
            // Embranchement : vérifie une condition
            if (minX == 0 && minY == 0 && minZ == 0 && maxX == 16 && maxY == 16 && maxZ == 16) {
                // Appelle une méthode
                fill(block);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final int stateId = retrieveBlockId(block);
            // Appelle une méthode
            final boolean requireCache = requireCache(block);
            // Appelle une méthode
            final boolean clearCache = !requireCache && !genSection.specials.isEmpty();
            // Boucle : répète un bloc
            for (int x = minX; x < maxX; x++) {
                // Boucle : répète un bloc
                for (int y = minY; y < maxY; y++) {
                    // Boucle : répète un bloc
                    for (int z = minZ; z < maxZ; z++) {
                        // Embranchement : vérifie une condition
                        if (requireCache) {
                            // Accès à l'objet courant/parent
                            this.genSection.specials.put(chunkBlockIndex(x, y, z), block);
                        // Embranchement : vérifie une condition
                        } else if (clearCache) {
                            // Accès à l'objet courant/parent
                            this.genSection.specials.remove(chunkBlockIndex(x, y, z));
                        // Fin d'un bloc/d'une expression
                        }
                        // Accès à l'objet courant/parent
                        this.genSection.blocks.set(x, y, z, stateId);
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

    // Déclaration de type (classe/interface/enum/record)
    public record AreaModifierImpl(Vec size, Vec start, Vec end,
                                   // Instruction de code
                                   int width, int height, int depth,
                                   // Début d'une méthode/d'un bloc
                                   List<GenerationUnit> sections) implements GenericModifier {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setBlock(int x, int y, int z, Block block) {
            // Appelle une méthode
            checkBorder(x, y, z);
            // Appelle une méthode
            final GenerationUnit section = findAbsoluteSection(x, y, z);
            // Appelle une méthode
            y -= start.y();
            // Appelle une méthode
            section.modifier().setBlock(x, y, z, block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
            // Appelle une méthode
            checkBorder(x, y, z);
            // Appelle une méthode
            final GenerationUnit section = findAbsoluteSection(x, y, z);
            // Appelle une méthode
            y -= start.y();
            // Appelle une méthode
            section.modifier().setBiome(x, y, z, biome);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setRelative(int x, int y, int z, Block block) {
            // Embranchement : vérifie une condition
            if (x < 0 || x >= size.x() || y < 0 || y >= size.y() || z < 0 || z >= size.z()) {
                // Lève une exception
                throw new IllegalArgumentException("x, y and z must be in the chunk: " + x + ", " + y + ", " + z);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final GenerationUnit section = findRelativeSection(x, y, z);
            // Appelle une méthode
            x = globalToSectionRelative(x);
            // Appelle une méthode
            y = globalToSectionRelative(y);
            // Appelle une méthode
            z = globalToSectionRelative(z);
            // Appelle une méthode
            section.modifier().setBlock(x, y, z, block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setAll(Supplier supplier) {
            // Boucle : répète un bloc
            for (GenerationUnit section : sections) {
                // Appelle une méthode
                final Point start = section.absoluteStart();
                // Appelle une méthode
                final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
                // Appelle une méthode
                section.modifier().setAllRelative((x, y, z) -> supplier.get(x + startX, y + startY, z + startZ));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setAllRelative(Supplier supplier) {
            // Affecte une valeur
            final Point start = this.start;
            // Appelle une méthode
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Boucle : répète un bloc
            for (GenerationUnit section : sections) {
                // Appelle une méthode
                final Point sectionStart = section.absoluteStart();
                // Appelle une méthode
                final int offsetX = sectionStart.blockX() - startX, offsetY = sectionStart.blockY() - startY, offsetZ = sectionStart.blockZ() - startZ;
                // Appelle une méthode
                section.modifier().setAllRelative((x, y, z) -> supplier.get(x + offsetX, y + offsetY, z + offsetZ));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void fill(Block block) {
            // Boucle : répète un bloc
            for (GenerationUnit section : sections) {
                // Appelle une méthode
                section.modifier().fill(block);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void fillBiome(RegistryKey<Biome> biome) {
            // Boucle : répète un bloc
            for (GenerationUnit section : sections) {
                // Appelle une méthode
                section.modifier().fillBiome(biome);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void fillHeight(int minHeight, int maxHeight, Block block) {
            // Affecte une valeur
            final Vec start = this.start;
            // Appelle une méthode
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Appelle une méthode
            final int endY = end.blockY();
            // Appelle une méthode
            minHeight = Math.max(minHeight, startY);
            // Appelle une méthode
            maxHeight = Math.min(maxHeight, endY);
            // Embranchement : vérifie une condition
            if (minHeight >= maxHeight) return;
            // Affecte une valeur
            final int width = this.width, depth = this.depth;
            // Appelle une méthode
            final int minMultiple = floorSection(minHeight);
            // Appelle une méthode
            final int maxMultiple = ceilSection(maxHeight);
            // Affecte une valeur
            final boolean startOffset = minMultiple != minHeight;
            // Affecte une valeur
            final boolean endOffset = maxMultiple != maxHeight;
            // Embranchement : vérifie une condition
            if (startOffset || endOffset) {
                // Appelle une méthode
                final int firstFill = Math.min(minMultiple + 16, maxHeight);
                // Appelle une méthode
                final int lastFill = startOffset ? Math.max(firstFill, floorSection(maxHeight)) : floorSection(maxHeight);
                // Boucle : répète un bloc
                for (int x = 0; x < width; x++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < depth; z++) {
                        // Affecte une valeur
                        final int sectionX = startX + x * 16;
                        // Affecte une valeur
                        final int sectionZ = startZ + z * 16;
                        // Fill start
                        // Embranchement : vérifie une condition
                        if (startOffset) {
                            // Appelle une méthode
                            final GenerationUnit section = findAbsoluteSection(sectionX, minMultiple, sectionZ);
                            // Appelle une méthode
                            section.modifier().fillHeight(minHeight, firstFill, block);
                        // Fin d'un bloc/d'une expression
                        }
                        // Fill end
                        // Embranchement : vérifie une condition
                        if (endOffset) {
                            // Appelle une méthode
                            final GenerationUnit section = findAbsoluteSection(sectionX, maxHeight, sectionZ);
                            // Appelle une méthode
                            section.modifier().fillHeight(lastFill, maxHeight, block);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Middle sections (to fill)
            // Appelle une méthode
            final int startSection = (minMultiple) / 16 + (startOffset ? 1 : 0);
            // Appelle une méthode
            final int endSection = (maxMultiple) / 16 + (endOffset ? -1 : 0);
            // Boucle : répète un bloc
            for (int i = startSection; i < endSection; i++) {
                // Boucle : répète un bloc
                for (int x = 0; x < width; x++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < depth; z++) {
                        // Appelle une méthode
                        final GenerationUnit section = findAbsoluteSection(startX + x * 16, i * 16, startZ + z * 16);
                        // Appelle une méthode
                        section.modifier().fill(block);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private GenerationUnit findAbsoluteSection(int x, int y, int z) {
            // Renvoie une valeur à l'appelant
            return findAbsolute(sections, start, width, height, depth, x, y, z);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private GenerationUnit findRelativeSection(int x, int y, int z) {
            // Renvoie une valeur à l'appelant
            return findAbsolute(sections, Vec.ZERO, width, height, depth, x, y, z);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void checkBorder(int x, int y, int z) {
            // Appelle une méthode
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Appelle une méthode
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Embranchement : vérifie une condition
            if (x < startX || x >= endX || y < startY || y >= endY || z < startZ || z >= endZ) {
                // Appelle une méthode
                final String format = String.format("Invalid coordinates: %d, %d, %d for area %s %s", x, y, z, start, end);
                // Lève une exception
                throw new IllegalArgumentException(format);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface GenericModifier extends UnitModifier
            // Début d'une méthode/d'un bloc
            permits AreaModifierImpl, SectionModifierImpl {
        // Appelle une méthode
        Vec size();

        // Appelle une méthode
        Vec start();

        // Appelle une méthode
        Vec end();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default void setAll(Supplier supplier) {
            // Appelle une méthode
            final Vec start = start(), end = end();
            // Appelle une méthode
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Appelle une méthode
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Boucle : répète un bloc
            for (int x = startX; x < endX; x++) {
                // Boucle : répète un bloc
                for (int y = startY; y < endY; y++) {
                    // Boucle : répète un bloc
                    for (int z = startZ; z < endZ; z++) {
                        // Appelle une méthode
                        setBlock(x, y, z, supplier.get(x, y, z));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default void setAllRelative(Supplier supplier) {
            // Appelle une méthode
            final Vec size = size();
            // Appelle une méthode
            final int endX = size.blockX(), endY = size.blockY(), endZ = size.blockZ();
            // Boucle : répète un bloc
            for (int x = 0; x < endX; x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < endY; y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < endZ; z++) {
                        // Appelle une méthode
                        setRelative(x, y, z, supplier.get(x, y, z));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default void fill(Block block) {
            // Appelle une méthode
            fill(start(), end(), block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default void fill(Point start, Point end, Block block) {
            // Appelle une méthode
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Appelle une méthode
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Boucle : répète un bloc
            for (int x = startX; x < endX; x++) {
                // Boucle : répète un bloc
                for (int y = startY; y < endY; y++) {
                    // Boucle : répète un bloc
                    for (int z = startZ; z < endZ; z++) {
                        // Appelle une méthode
                        setBlock(x, y, z, block);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default void fillHeight(int minHeight, int maxHeight, Block block) {
            // Appelle une méthode
            final Vec start = start();
            // Appelle une méthode
            final Vec end = end();
            // Appelle une méthode
            final int startY = start.blockY(), endY = end.blockY();
            // Embranchement : vérifie une condition
            if (startY >= minHeight && endY <= maxHeight) {
                // Fast path if the unit is fully contained in the height range
                // Appelle une méthode
                fill(start, end, block);
            // Branche alternative de la condition
            } else {
                // Slow path if the unit is not fully contained in the height range
                // Appelle une méthode
                fill(start.withY(Math.max(minHeight, startY)), end.withY(Math.min(maxHeight, endY)), block);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static GenerationUnit findAbsolute(List<GenerationUnit> units, Vec start,
                                               // Instruction de code
                                               int width, int height, int depth,
                                               // Début d'une méthode/d'un bloc
                                               int x, int y, int z) {
        // Appelle une méthode
        final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
        // Appelle une méthode
        final int sectionX = globalToChunk(x - startX), sectionY = globalToChunk(y - startY), sectionZ = globalToChunk(z - startZ);
        // Appelle une méthode
        final int index = findIndex(width, height, depth, sectionX, sectionY, sectionZ);
        // Renvoie une valeur à l'appelant
        return units.get(index);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static int findIndex(int width, int height, int depth,
                                 // Début d'une méthode/d'un bloc
                                 int x, int y, int z) {
        // Instruction de code
        assert width > 0 && height > 0 && depth > 0;
        // Renvoie une valeur à l'appelant
        return (z * width * height) + (y * width) + x;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int indexToX(int index, int width) {
        // Renvoie une valeur à l'appelant
        return index % width;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int indexToY(int index, int width, int height) {
        // Renvoie une valeur à l'appelant
        return (index / width) % height;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int indexToZ(int index, int width, int height) {
        // Renvoie une valeur à l'appelant
        return index / (width * height);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
