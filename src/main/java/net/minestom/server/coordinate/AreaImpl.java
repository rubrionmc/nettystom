// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import java.util.*;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.sectionIndex;

// Déclaration de type (classe/interface/enum/record)
final class AreaImpl {

    // Déclaration de type (classe/interface/enum/record)
    record Single(BlockVec point) implements Area.Single {
        // Début d'une méthode/d'un bloc
        public Single {
            // Appelle une méthode
            Objects.requireNonNull(point, "Point cannot be null");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<BlockVec> iterator() {
            // Renvoie une valeur à l'appelant
            return new Iterator<>() {
                // Affecte une valeur
                private boolean hasNext = true;

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public boolean hasNext() {
                    // Renvoie une valeur à l'appelant
                    return hasNext;
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public BlockVec next() {
                    // Embranchement : vérifie une condition
                    if (!hasNext) throw new NoSuchElementException();
                    // Affecte une valeur
                    hasNext = false;
                    // Renvoie une valeur à l'appelant
                    return point;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<Area.Cuboid> split() {
            // Renvoie une valeur à l'appelant
            return List.of(new AreaImpl.Cuboid(point, point));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Line(BlockVec start, BlockVec end) implements Area.Line {
        // Début d'une méthode/d'un bloc
        public Line {
            // Appelle une méthode
            Objects.requireNonNull(start, "Start point cannot be null");
            // Appelle une méthode
            Objects.requireNonNull(end, "End point cannot be null");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<BlockVec> iterator() {
            // Embranchement : vérifie une condition
            if (start.samePoint(end)) return List.of(start).iterator();
            // Renvoie une valeur à l'appelant
            return new Iterator<>() {
                // Appelle une méthode
                private final int x1 = start.blockX(), y1 = start.blockY(), z1 = start.blockZ();
                // Appelle une méthode
                private final int x2 = end.blockX(), y2 = end.blockY(), z2 = end.blockZ();
                // Affecte une valeur
                private int x = x1, y = y1, z = z1;
                // Affecte une valeur
                private boolean done = false;

                // 3D Bresenham algorithm
                // Appelle une méthode
                private final int dx = Math.abs(x2 - x1);
                // Appelle une méthode
                private final int dy = Math.abs(y2 - y1);
                // Appelle une méthode
                private final int dz = Math.abs(z2 - z1);
                // Affecte une valeur
                private final int sx = x1 < x2 ? 1 : -1;
                // Affecte une valeur
                private final int sy = y1 < y2 ? 1 : -1;
                // Affecte une valeur
                private final int sz = z1 < z2 ? 1 : -1;
                // Instruction de code
                private int err1, err2;

                // Début d'un bloc
                {
                    // Initialize error terms based on the dominant axis
                    // Embranchement : vérifie une condition
                    if (dx >= dy && dx >= dz) {
                        // Affecte une valeur
                        err1 = dx / 2;
                        // Affecte une valeur
                        err2 = dx / 2;
                    // Embranchement : vérifie une condition
                    } else if (dy >= dx && dy >= dz) {
                        // Affecte une valeur
                        err1 = dy / 2;
                        // Affecte une valeur
                        err2 = dy / 2;
                    // Branche alternative de la condition
                    } else {
                        // Affecte une valeur
                        err1 = dz / 2;
                        // Affecte une valeur
                        err2 = dz / 2;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public boolean hasNext() {
                    // Renvoie une valeur à l'appelant
                    return !done;
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public BlockVec next() {
                    // Embranchement : vérifie une condition
                    if (done) throw new NoSuchElementException();
                    // Appelle une méthode
                    BlockVec result = new BlockVec(x, y, z);
                    // Check if we've reached the end
                    // Embranchement : vérifie une condition
                    if (x == x2 && y == y2 && z == z2) {
                        // Boucle : répète un bloc
                        done = true;
                        // Renvoie une valeur à l'appelant
                        return result;
                    // Fin d'un bloc/d'une expression
                    }
                    // Move to next position using 3D Bresenham
                    // Embranchement : vérifie une condition
                    if (dx >= dy && dx >= dz) {
                        // X is the dominant axis
                        // Affecte une valeur
                        x += sx;
                        // Affecte une valeur
                        err1 -= dy;
                        // Affecte une valeur
                        err2 -= dz;
                        // Embranchement : vérifie une condition
                        if (err1 < 0) {
                            // Affecte une valeur
                            y += sy;
                            // Affecte une valeur
                            err1 += dx;
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement : vérifie une condition
                        if (err2 < 0) {
                            // Affecte une valeur
                            z += sz;
                            // Affecte une valeur
                            err2 += dx;
                        // Fin d'un bloc/d'une expression
                        }
                    // Embranchement : vérifie une condition
                    } else if (dy >= dx && dy >= dz) {
                        // Y is the dominant axis
                        // Affecte une valeur
                        y += sy;
                        // Affecte une valeur
                        err1 -= dx;
                        // Affecte une valeur
                        err2 -= dz;
                        // Embranchement : vérifie une condition
                        if (err1 < 0) {
                            // Affecte une valeur
                            x += sx;
                            // Affecte une valeur
                            err1 += dy;
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement : vérifie une condition
                        if (err2 < 0) {
                            // Affecte une valeur
                            z += sz;
                            // Affecte une valeur
                            err2 += dy;
                        // Fin d'un bloc/d'une expression
                        }
                    // Branche alternative de la condition
                    } else {
                        // Z is the dominant axis
                        // Affecte une valeur
                        z += sz;
                        // Affecte une valeur
                        err1 -= dx;
                        // Affecte une valeur
                        err2 -= dy;
                        // Embranchement : vérifie une condition
                        if (err1 < 0) {
                            // Affecte une valeur
                            x += sx;
                            // Affecte une valeur
                            err1 += dz;
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement : vérifie une condition
                        if (err2 < 0) {
                            // Affecte une valeur
                            y += sy;
                            // Affecte une valeur
                            err2 += dz;
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Renvoie une valeur à l'appelant
                    return result;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<Area.Cuboid> split() {
            // Collect all actual line blocks
            // Affecte une valeur
            Set<BlockVec> lineBlocks = new HashSet<>();
            // Boucle : répète un bloc
            for (BlockVec block : this) {
                // Appelle une méthode
                lineBlocks.add(block);
            // Fin d'un bloc/d'une expression
            }

            // Group blocks by section coordinates
            // Appelle une méthode
            int sectionSize = BlockVec.SECTION.blockX();
            // Affecte une valeur
            Map<Long, Set<BlockVec>> sectionGroups = new HashMap<>();

            // Boucle : répète un bloc
            for (BlockVec block : lineBlocks) {
                // Appelle une méthode
                int sectionX = Math.floorDiv(block.blockX(), sectionSize);
                // Appelle une méthode
                int sectionY = Math.floorDiv(block.blockY(), sectionSize);
                // Appelle une méthode
                int sectionZ = Math.floorDiv(block.blockZ(), sectionSize);
                // Appelle une méthode
                long sectionKey = sectionIndex(sectionX, sectionY, sectionZ);
                // Appelle une méthode
                sectionGroups.computeIfAbsent(sectionKey, k -> new HashSet<>()).add(block);
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
            List<Area.Cuboid> result = new ArrayList<>();
            // Boucle : répète un bloc
            for (Set<BlockVec> blocks : sectionGroups.values()) {
                // Boucle : répète un bloc
                for (BlockVec block : blocks) {
                    // Appelle une méthode
                    result.add(Area.cuboid(block, block));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Cuboid(BlockVec min, BlockVec max) implements Area.Cuboid {
        // Début d'une méthode/d'un bloc
        public Cuboid {
            // Appelle une méthode
            Objects.requireNonNull(min, "min cannot be null");
            // Appelle une méthode
            Objects.requireNonNull(max, "max cannot be null");
            // Affecte une valeur
            final BlockVec origMin = min;
            // Affecte une valeur
            final BlockVec origMax = max;
            // Appelle une méthode
            min = origMin.min(origMax);
            // Appelle une méthode
            max = origMin.max(origMax);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<BlockVec> iterator() {
            // Appelle une méthode
            final int minX = min.blockX(), minY = min.blockY(), minZ = min.blockZ();
            // Appelle une méthode
            final int maxX = max.blockX(), maxY = max.blockY(), maxZ = max.blockZ();
            // Renvoie une valeur à l'appelant
            return new Iterator<>() {
                // Affecte une valeur
                private int x = minX;
                // Affecte une valeur
                private int y = minY;
                // Affecte une valeur
                private int z = minZ;
                // Affecte une valeur
                private boolean hasNext = true;

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public boolean hasNext() {
                    // Renvoie une valeur à l'appelant
                    return hasNext;
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public BlockVec next() {
                    // Embranchement : vérifie une condition
                    if (!hasNext) throw new NoSuchElementException();
                    // Appelle une méthode
                    BlockVec vec = new BlockVec(x, y, z);
                    // Determine next position or finish
                    // Embranchement : vérifie une condition
                    if (x == maxX && y == maxY && z == maxZ) {
                        // Affecte une valeur
                        hasNext = false;
                    // Embranchement : vérifie une condition
                    } else if (x < maxX) {
                        // Instruction de code
                        x++;
                    // Embranchement : vérifie une condition
                    } else if (y < maxY) {
                        // Affecte une valeur
                        x = minX;
                        // Instruction de code
                        y++;
                    // Embranchement : vérifie une condition
                    } else if (z < maxZ) {
                        // Affecte une valeur
                        x = minX;
                        // Affecte une valeur
                        y = minY;
                        // Instruction de code
                        z++;
                    // Fin d'un bloc/d'une expression
                    }
                    // Renvoie une valeur à l'appelant
                    return vec;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<Area.Cuboid> split() {
            // Appelle une méthode
            int sectionSize = BlockVec.SECTION.blockX();
            // Appelle une méthode
            int minSecX = Math.floorDiv(min.blockX(), sectionSize);
            // Appelle une méthode
            int minSecY = Math.floorDiv(min.blockY(), sectionSize);
            // Appelle une méthode
            int minSecZ = Math.floorDiv(min.blockZ(), sectionSize);
            // Appelle une méthode
            int maxSecX = Math.floorDiv(max.blockX(), sectionSize);
            // Appelle une méthode
            int maxSecY = Math.floorDiv(max.blockY(), sectionSize);
            // Appelle une méthode
            int maxSecZ = Math.floorDiv(max.blockZ(), sectionSize);

            // Affecte une valeur
            List<Area.Cuboid> result = new ArrayList<>();

            // Split into cuboids per section
            // Boucle : répète un bloc
            for (int sx = minSecX; sx <= maxSecX; sx++) {
                // Boucle : répète un bloc
                for (int sy = minSecY; sy <= maxSecY; sy++) {
                    // Boucle : répète un bloc
                    for (int sz = minSecZ; sz <= maxSecZ; sz++) {
                        // Affecte une valeur
                        int sectionMinX = sx * sectionSize;
                        // Affecte une valeur
                        int sectionMinY = sy * sectionSize;
                        // Affecte une valeur
                        int sectionMinZ = sz * sectionSize;
                        // Affecte une valeur
                        int sectionMaxX = sectionMinX + sectionSize - 1;
                        // Affecte une valeur
                        int sectionMaxY = sectionMinY + sectionSize - 1;
                        // Affecte une valeur
                        int sectionMaxZ = sectionMinZ + sectionSize - 1;

                        // Calculate intersection with this section
                        // Appelle une méthode
                        int intersectMinX = Math.max(min.blockX(), sectionMinX);
                        // Appelle une méthode
                        int intersectMinY = Math.max(min.blockY(), sectionMinY);
                        // Appelle une méthode
                        int intersectMinZ = Math.max(min.blockZ(), sectionMinZ);
                        // Appelle une méthode
                        int intersectMaxX = Math.min(max.blockX(), sectionMaxX);
                        // Appelle une méthode
                        int intersectMaxY = Math.min(max.blockY(), sectionMaxY);
                        // Appelle une méthode
                        int intersectMaxZ = Math.min(max.blockZ(), sectionMaxZ);

                        // Only add if there's a valid intersection
                        // Embranchement : vérifie une condition
                        if (intersectMinX <= intersectMaxX &&
                                // Instruction de code
                                intersectMinY <= intersectMaxY &&
                                // Début d'une méthode/d'un bloc
                                intersectMinZ <= intersectMaxZ) {
                            // Instruction de code
                            result.add(Area.cuboid(
                                    // Crée un nouvel objet
                                    new BlockVec(intersectMinX, intersectMinY, intersectMinZ),
                                    // Crée un nouvel objet
                                    new BlockVec(intersectMaxX, intersectMaxY, intersectMaxZ)
                            // Instruction de code
                            ));
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Sphere(BlockVec center, int radius) implements Area.Sphere {
        // Début d'une méthode/d'un bloc
        public Sphere {
            // Appelle une méthode
            Objects.requireNonNull(center, "Center cannot be null");
            // Embranchement : vérifie une condition
            if (radius < 0) throw new IllegalArgumentException("Radius must be non-negative");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<BlockVec> iterator() {
            // Appelle une méthode
            final int minX = center.blockX() - radius;
            // Appelle une méthode
            final int minY = center.blockY() - radius;
            // Appelle une méthode
            final int minZ = center.blockZ() - radius;
            // Appelle une méthode
            final int maxX = center.blockX() + radius;
            // Appelle une méthode
            final int maxY = center.blockY() + radius;
            // Appelle une méthode
            final int maxZ = center.blockZ() + radius;
            // Affecte une valeur
            final double radiusSquared = radius * radius;
            // Renvoie une valeur à l'appelant
            return new Iterator<>() {
                // Affecte une valeur
                private int x = minX;
                // Affecte une valeur
                private int y = minY;
                // Affecte une valeur
                private int z = minZ;
                // Affecte une valeur
                private boolean hasNextValue = true;
                // Appelle une méthode
                private BlockVec nextVec = findNext();

                // Début d'une méthode/d'un bloc
                private BlockVec findNext() {
                    // Boucle : répète un bloc
                    while (z <= maxZ) {
                        // Boucle : répète un bloc
                        while (y <= maxY) {
                            // Boucle : répète un bloc
                            while (x <= maxX) {
                                // Check if this block is within the sphere
                                // Boucle : répète un bloc
                                double dx = x - center.blockX();
                                // Boucle : répète un bloc
                                double dy = y - center.blockY();
                                // Boucle : répète un bloc
                                double dz = z - center.blockZ();
                                // Boucle : répète un bloc
                                double distanceSquared = dx * dx + dy * dy + dz * dz;

                                // Embranchement : vérifie une condition
                                if (distanceSquared <= radiusSquared) {
                                    // Appelle une méthode
                                    BlockVec result = new BlockVec(x, y, z);
                                    // Advance to next position
                                    // Instruction de code
                                    x++;
                                    // Renvoie une valeur à l'appelant
                                    return result;
                                // Fin d'un bloc/d'une expression
                                }
                                // Instruction de code
                                x++;
                            // Fin d'un bloc/d'une expression
                            }
                            // Affecte une valeur
                            x = minX;
                            // Instruction de code
                            y++;
                        // Fin d'un bloc/d'une expression
                        }
                        // Affecte une valeur
                        y = minY;
                        // Instruction de code
                        z++;
                    // Fin d'un bloc/d'une expression
                    }
                    // Affecte une valeur
                    hasNextValue = false;
                    // Renvoie une valeur à l'appelant
                    return new BlockVec(0, 0, 0); // dummy value, won't be used
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public boolean hasNext() {
                    // Renvoie une valeur à l'appelant
                    return hasNextValue;
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public BlockVec next() {
                    // Embranchement : vérifie une condition
                    if (!hasNextValue) throw new NoSuchElementException();
                    // Affecte une valeur
                    BlockVec result = nextVec;
                    // Appelle une méthode
                    nextVec = findNext();
                    // Renvoie une valeur à l'appelant
                    return result;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<Area.Cuboid> split() {
            // Appelle une méthode
            int sectionSize = BlockVec.SECTION.blockX();

            // Calculate the bounding sections for the sphere
            // Appelle une méthode
            int minSecX = Math.floorDiv(center.blockX() - radius, sectionSize);
            // Appelle une méthode
            int maxSecX = Math.floorDiv(center.blockX() + radius, sectionSize);
            // Appelle une méthode
            int minSecY = Math.floorDiv(center.blockY() - radius, sectionSize);
            // Appelle une méthode
            int maxSecY = Math.floorDiv(center.blockY() + radius, sectionSize);
            // Appelle une méthode
            int minSecZ = Math.floorDiv(center.blockZ() - radius, sectionSize);
            // Appelle une méthode
            int maxSecZ = Math.floorDiv(center.blockZ() + radius, sectionSize);

            // Affecte une valeur
            List<Area.Cuboid> result = new ArrayList<>();
            // Boucle : répète un bloc
            double radiusSquared = radius * radius;

            // For each section that might contain sphere blocks
            // Boucle : répète un bloc
            for (int sx = minSecX; sx <= maxSecX; sx++) {
                // Boucle : répète un bloc
                for (int sy = minSecY; sy <= maxSecY; sy++) {
                    // Boucle : répète un bloc
                    for (int sz = minSecZ; sz <= maxSecZ; sz++) {
                        // Affecte une valeur
                        int sectionMinX = sx * sectionSize;
                        // Affecte une valeur
                        int sectionMinY = sy * sectionSize;
                        // Affecte une valeur
                        int sectionMinZ = sz * sectionSize;
                        // Affecte une valeur
                        int sectionMaxX = sectionMinX + sectionSize - 1;
                        // Affecte une valeur
                        int sectionMaxY = sectionMinY + sectionSize - 1;
                        // Affecte une valeur
                        int sectionMaxZ = sectionMinZ + sectionSize - 1;

                        // Check if this entire section is within the sphere
                        // Affecte une valeur
                        boolean fullSection = true;
                        // Boucle : répète un bloc
                        for (int x = sectionMinX; x <= sectionMaxX && fullSection; x++) {
                            // Boucle : répète un bloc
                            for (int y = sectionMinY; y <= sectionMaxY && fullSection; y++) {
                                // Boucle : répète un bloc
                                for (int z = sectionMinZ; z <= sectionMaxZ && fullSection; z++) {
                                    // Boucle : répète un bloc
                                    double dx = x - center.blockX();
                                    // Boucle : répète un bloc
                                    double dy = y - center.blockY();
                                    // Boucle : répète un bloc
                                    double dz = z - center.blockZ();
                                    // Embranchement : vérifie une condition
                                    if (dx * dx + dy * dy + dz * dz > radiusSquared) {
                                        // Affecte une valeur
                                        fullSection = false;
                                    // Fin d'un bloc/d'une expression
                                    }
                                // Fin d'un bloc/d'une expression
                                }
                            // Fin d'un bloc/d'une expression
                            }
                        // Fin d'un bloc/d'une expression
                        }

                        // Embranchement : vérifie une condition
                        if (fullSection) {
                            // Entire section is within sphere
                            // Instruction de code
                            result.add(Area.cuboid(
                                    // Crée un nouvel objet
                                    new BlockVec(sectionMinX, sectionMinY, sectionMinZ),
                                    // Crée un nouvel objet
                                    new BlockVec(sectionMaxX, sectionMaxY, sectionMaxZ)
                            // Instruction de code
                            ));
                        // Branche alternative de la condition
                        } else {
                            // Partial section - create individual cuboids for each sphere block
                            // This ensures we only include blocks that are actually part of the sphere
                            // Boucle : répète un bloc
                            for (int x = sectionMinX; x <= sectionMaxX; x++) {
                                // Boucle : répète un bloc
                                for (int y = sectionMinY; y <= sectionMaxY; y++) {
                                    // Boucle : répète un bloc
                                    for (int z = sectionMinZ; z <= sectionMaxZ; z++) {
                                        // Boucle : répète un bloc
                                        double dx = x - center.blockX();
                                        // Boucle : répète un bloc
                                        double dy = y - center.blockY();
                                        // Boucle : répète un bloc
                                        double dz = z - center.blockZ();
                                        // Embranchement : vérifie une condition
                                        if (dx * dx + dy * dy + dz * dz <= radiusSquared) {
                                            // Appelle une méthode
                                            BlockVec block = new BlockVec(x, y, z);
                                            // Appelle une méthode
                                            result.add(Area.cuboid(block, block));
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
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
