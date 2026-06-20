// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.*;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.SECTION_BOUND;
// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.SECTION_SIZE;

// Déclaration de type (classe/interface/enum/record)
final class AreaImpl {

    // Début d'une méthode/d'un bloc
    static Area.Cuboid section(int sectionX, int sectionY, int sectionZ) {
        // Appelle une méthode
        final BlockVec min = new BlockVec(sectionX * SECTION_SIZE, sectionY * SECTION_SIZE, sectionZ * SECTION_SIZE);
        // Renvoie une valeur à l'appelant
        return new Cuboid(min, min.add(SECTION_BOUND));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Area.Cuboid cube(Point center, int size) {
        // Appelle une méthode
        Check.argCondition(size < 0, "Cube size must be non-negative: {0}", size);
        // Renvoie une valeur à l'appelant
        return new Cuboid(
                // Instruction de code
                center.sub((double) size / 2).asBlockVec(),
                // Appelle une méthode
                center.add((double) size / 2).asBlockVec());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Area.Cuboid box(Point center, Point size) {
        // Instruction de code
        Check.argCondition(size.x() < 0 || size.y() < 0 || size.z() < 0,
                // Appelle une méthode
                "Box size must be non-negative on each axis: ({0}, {1}, {2})", size.x(), size.y(), size.z());
        // Appelle une méthode
        final Point half = size.div(2);
        // Renvoie une valeur à l'appelant
        return new Cuboid(center.sub(half).asBlockVec(), center.add(half).asBlockVec());
    // Fin d'un bloc/d'une expression
    }

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
            return List.of(point).iterator();
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

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean contains(int x, int y, int z) {
            // Renvoie une valeur à l'appelant
            return this.point.blockX() == x && this.point.blockY() == y && this.point.blockZ() == z;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public long blockCount() {
            // Renvoie une valeur à l'appelant
            return 1;
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
            // Appelle une méthode
            final int x1 = start.blockX(), y1 = start.blockY(), z1 = start.blockZ();
            // Appelle une méthode
            final int x2 = end.blockX(), y2 = end.blockY(), z2 = end.blockZ();
            // Appelle une méthode
            final int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), dz = Math.abs(z2 - z1);
            // Affecte une valeur
            final int sx = x1 < x2 ? 1 : -1, sy = y1 < y2 ? 1 : -1, sz = z1 < z2 ? 1 : -1;
            // Appelle une méthode
            final int errInit = Math.max(dx, Math.max(dy, dz)) / 2;
            // Renvoie une valeur à l'appelant
            return new Iterator<>() {
                // Affecte une valeur
                private int x = x1, y = y1, z = z1;
                // Affecte une valeur
                private int err1 = errInit, err2 = errInit;
                // Instruction de code
                private boolean done;

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
                    final BlockVec result = new BlockVec(x, y, z);
                    // Embranchement : vérifie une condition
                    if (x == x2 && y == y2 && z == z2) {
                        // Affecte une valeur
                        done = true;
                        // Renvoie une valeur à l'appelant
                        return result;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (dx >= dy && dx >= dz) {
                        // Instruction de code
                        x += sx;
                        // Instruction de code
                        err1 -= dy;
                        // Instruction de code
                        err2 -= dz;
                        // Embranchement : vérifie une condition
                        if (err1 < 0) {
                            // Instruction de code
                            y += sy;
                            // Instruction de code
                            err1 += dx;
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement : vérifie une condition
                        if (err2 < 0) {
                            // Instruction de code
                            z += sz;
                            // Instruction de code
                            err2 += dx;
                        // Fin d'un bloc/d'une expression
                        }
                    // Embranchement : vérifie une condition
                    } else if (dy >= dz) {
                        // Instruction de code
                        y += sy;
                        // Instruction de code
                        err1 -= dx;
                        // Instruction de code
                        err2 -= dz;
                        // Embranchement : vérifie une condition
                        if (err1 < 0) {
                            // Instruction de code
                            x += sx;
                            // Instruction de code
                            err1 += dy;
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement : vérifie une condition
                        if (err2 < 0) {
                            // Instruction de code
                            z += sz;
                            // Instruction de code
                            err2 += dy;
                        // Fin d'un bloc/d'une expression
                        }
                    // Branche alternative de la condition
                    } else {
                        // Instruction de code
                        z += sz;
                        // Instruction de code
                        err1 -= dx;
                        // Instruction de code
                        err2 -= dy;
                        // Embranchement : vérifie une condition
                        if (err1 < 0) {
                            // Instruction de code
                            x += sx;
                            // Instruction de code
                            err1 += dz;
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement : vérifie une condition
                        if (err2 < 0) {
                            // Instruction de code
                            y += sy;
                            // Instruction de code
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
            // Appelle une méthode
            final int x1 = start.blockX(), y1 = start.blockY(), z1 = start.blockZ();
            // Appelle une méthode
            final int x2 = end.blockX(), y2 = end.blockY(), z2 = end.blockZ();
            // Embranchement : vérifie une condition
            if (x1 == x2 && y1 == y2 && z1 == z2) {
                // Renvoie une valeur à l'appelant
                return List.of(new AreaImpl.Cuboid(start, end));
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), dz = Math.abs(z2 - z1);
            // Axis-aligned lines cover the same blocks as an inclusive cuboid; reuse Cuboid.split.
            // Embranchement : vérifie une condition
            if ((dx == 0 ? 1 : 0) + (dy == 0 ? 1 : 0) + (dz == 0 ? 1 : 0) >= 2) {
                // Renvoie une valeur à l'appelant
                return new AreaImpl.Cuboid(
                        // Crée un nouvel objet
                        new BlockVec(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2)),
                        // Crée un nouvel objet
                        new BlockVec(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2))).split();
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final List<Area.Cuboid> result = new ArrayList<>();
            // Affecte une valeur
            final int sx = x1 < x2 ? 1 : -1, sy = y1 < y2 ? 1 : -1, sz = z1 < z2 ? 1 : -1;
            // Appelle une méthode
            int err1 = Math.max(dx, Math.max(dy, dz)) / 2, err2 = err1;
            // Affecte une valeur
            int runStartX = x1, runStartY = y1, runStartZ = z1;
            // Affecte une valeur
            int runStartSecX = x1 >> 4, runStartSecY = y1 >> 4, runStartSecZ = z1 >> 4;
            // Affecte une valeur
            int runEndX = x1, runEndY = y1, runEndZ = z1;
            // Boucle : répète un bloc
            while (runEndX != x2 || runEndY != y2 || runEndZ != z2) {
                // Affecte une valeur
                int nextX = runEndX, nextY = runEndY, nextZ = runEndZ;
                // Embranchement : vérifie une condition
                if (dx >= dy && dx >= dz) {
                    // Instruction de code
                    nextX += sx;
                    // Instruction de code
                    err1 -= dy;
                    // Instruction de code
                    err2 -= dz;
                    // Embranchement : vérifie une condition
                    if (err1 < 0) {
                        // Instruction de code
                        nextY += sy;
                        // Instruction de code
                        err1 += dx;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (err2 < 0) {
                        // Instruction de code
                        nextZ += sz;
                        // Instruction de code
                        err2 += dx;
                    // Fin d'un bloc/d'une expression
                    }
                // Embranchement : vérifie une condition
                } else if (dy >= dz) {
                    // Instruction de code
                    nextY += sy;
                    // Instruction de code
                    err1 -= dx;
                    // Instruction de code
                    err2 -= dz;
                    // Embranchement : vérifie une condition
                    if (err1 < 0) {
                        // Instruction de code
                        nextX += sx;
                        // Instruction de code
                        err1 += dy;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (err2 < 0) {
                        // Instruction de code
                        nextZ += sz;
                        // Instruction de code
                        err2 += dy;
                    // Fin d'un bloc/d'une expression
                    }
                // Branche alternative de la condition
                } else {
                    // Instruction de code
                    nextZ += sz;
                    // Instruction de code
                    err1 -= dx;
                    // Instruction de code
                    err2 -= dy;
                    // Embranchement : vérifie une condition
                    if (err1 < 0) {
                        // Instruction de code
                        nextX += sx;
                        // Instruction de code
                        err1 += dz;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (err2 < 0) {
                        // Instruction de code
                        nextY += sy;
                        // Instruction de code
                        err2 += dz;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Affecte une valeur
                final int nextSecX = nextX >> 4, nextSecY = nextY >> 4, nextSecZ = nextZ >> 4;
                // Affecte une valeur
                final boolean sameSection = runStartSecX == nextSecX && runStartSecY == nextSecY && runStartSecZ == nextSecZ;
                // Embranchement : vérifie une condition
                if (!sameSection || !canExtendAxisAlignedRun(runStartX, runStartY, runStartZ, runEndX, runEndY, runEndZ, nextX, nextY, nextZ)) {
                    // Appelle une méthode
                    result.add(buildRunCuboid(runStartX, runStartY, runStartZ, runEndX, runEndY, runEndZ));
                    // Affecte une valeur
                    runStartX = nextX;
                    // Affecte une valeur
                    runStartY = nextY;
                    // Affecte une valeur
                    runStartZ = nextZ;
                    // Affecte une valeur
                    runStartSecX = nextSecX;
                    // Affecte une valeur
                    runStartSecY = nextSecY;
                    // Affecte une valeur
                    runStartSecZ = nextSecZ;
                // Fin d'un bloc/d'une expression
                }
                // Affecte une valeur
                runEndX = nextX;
                // Affecte une valeur
                runEndY = nextY;
                // Affecte une valeur
                runEndZ = nextZ;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            result.add(buildRunCuboid(runStartX, runStartY, runStartZ, runEndX, runEndY, runEndZ));
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean contains(int targetX, int targetY, int targetZ) {
            // Appelle une méthode
            final int x1 = start.blockX(), y1 = start.blockY(), z1 = start.blockZ();
            // Appelle une méthode
            final int x2 = end.blockX(), y2 = end.blockY(), z2 = end.blockZ();
            // Embranchement : vérifie une condition
            if (targetX < Math.min(x1, x2) || targetX > Math.max(x1, x2) ||
                    // Instruction de code
                    targetY < Math.min(y1, y2) || targetY > Math.max(y1, y2) ||
                    // Début d'une méthode/d'un bloc
                    targetZ < Math.min(z1, z2) || targetZ > Math.max(z1, z2)) {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), dz = Math.abs(z2 - z1);
            // Affecte une valeur
            final int sx = x1 < x2 ? 1 : -1, sy = y1 < y2 ? 1 : -1, sz = z1 < z2 ? 1 : -1;
            // Affecte une valeur
            int x = x1, y = y1, z = z1;
            // Appelle une méthode
            int err1 = Math.max(dx, Math.max(dy, dz)) / 2, err2 = err1;
            // Boucle : répète un bloc
            while (true) {
                // Embranchement : vérifie une condition
                if (x == targetX && y == targetY && z == targetZ) return true;
                // Embranchement : vérifie une condition
                if (x == x2 && y == y2 && z == z2) return false;
                // Embranchement : vérifie une condition
                if (dx >= dy && dx >= dz) {
                    // Instruction de code
                    x += sx;
                    // Instruction de code
                    err1 -= dy;
                    // Instruction de code
                    err2 -= dz;
                    // Embranchement : vérifie une condition
                    if (err1 < 0) {
                        // Instruction de code
                        y += sy;
                        // Instruction de code
                        err1 += dx;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (err2 < 0) {
                        // Instruction de code
                        z += sz;
                        // Instruction de code
                        err2 += dx;
                    // Fin d'un bloc/d'une expression
                    }
                // Embranchement : vérifie une condition
                } else if (dy >= dz) {
                    // Instruction de code
                    y += sy;
                    // Instruction de code
                    err1 -= dx;
                    // Instruction de code
                    err2 -= dz;
                    // Embranchement : vérifie une condition
                    if (err1 < 0) {
                        // Instruction de code
                        x += sx;
                        // Instruction de code
                        err1 += dy;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (err2 < 0) {
                        // Instruction de code
                        z += sz;
                        // Instruction de code
                        err2 += dy;
                    // Fin d'un bloc/d'une expression
                    }
                // Branche alternative de la condition
                } else {
                    // Instruction de code
                    z += sz;
                    // Instruction de code
                    err1 -= dx;
                    // Instruction de code
                    err2 -= dy;
                    // Embranchement : vérifie une condition
                    if (err1 < 0) {
                        // Instruction de code
                        x += sx;
                        // Instruction de code
                        err1 += dz;
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (err2 < 0) {
                        // Instruction de code
                        y += sy;
                        // Instruction de code
                        err2 += dz;
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
        public long blockCount() {
            // Appelle une méthode
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Appelle une méthode
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Appelle une méthode
            final long dx = Math.abs((long) endX - startX), dy = Math.abs((long) endY - startY), dz = Math.abs((long) endZ - startZ);
            // Renvoie une valeur à l'appelant
            return Math.max(dx, Math.max(dy, dz)) + 1;
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
            // Embranchement : vérifie une condition
            if (min.blockX() > max.blockX() || min.blockY() > max.blockY() || min.blockZ() > max.blockZ()) {
                // Affecte une valeur
                final BlockVec origMin = min;
                // Appelle une méthode
                min = origMin.min(max);
                // Appelle une méthode
                max = origMin.max(max);
            // Fin d'un bloc/d'une expression
            }
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
                    final BlockVec vec = new BlockVec(x, y, z);
                    // Embranchement : vérifie une condition
                    if (x < maxX) {
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
                    // Branche alternative de la condition
                    } else {
                        // Affecte une valeur
                        hasNext = false;
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
            final int minX = min.blockX(), minY = min.blockY(), minZ = min.blockZ();
            // Appelle une méthode
            final int maxX = max.blockX(), maxY = max.blockY(), maxZ = max.blockZ();
            // Affecte une valeur
            final int minSecX = minX >> 4, minSecY = minY >> 4, minSecZ = minZ >> 4;
            // Affecte une valeur
            final int maxSecX = maxX >> 4, maxSecY = maxY >> 4, maxSecZ = maxZ >> 4;
            // Embranchement : vérifie une condition
            if (minSecX == maxSecX && minSecY == maxSecY && minSecZ == maxSecZ) {
                // Renvoie une valeur à l'appelant
                return List.of(this);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final long total = (long) (maxSecX - minSecX + 1) * (maxSecY - minSecY + 1) * (maxSecZ - minSecZ + 1);
            // Appelle une méthode
            final List<Area.Cuboid> result = new ArrayList<>(total > 1_000_000 ? 16 : (int) total);
            // Boucle : répète un bloc
            for (int sx = minSecX; sx <= maxSecX; sx++) {
                // Affecte une valeur
                final int sectionBaseX = sx << 4;
                // Affecte une valeur
                final int ixMin = sx == minSecX ? minX : sectionBaseX;
                // Affecte une valeur
                final int ixMax = sx == maxSecX ? maxX : sectionBaseX | SECTION_BOUND;
                // Boucle : répète un bloc
                for (int sy = minSecY; sy <= maxSecY; sy++) {
                    // Affecte une valeur
                    final int sectionBaseY = sy << 4;
                    // Affecte une valeur
                    final int iyMin = sy == minSecY ? minY : sectionBaseY;
                    // Affecte une valeur
                    final int iyMax = sy == maxSecY ? maxY : sectionBaseY | SECTION_BOUND;
                    // Boucle : répète un bloc
                    for (int sz = minSecZ; sz <= maxSecZ; sz++) {
                        // Affecte une valeur
                        final int sectionBaseZ = sz << 4;
                        // Affecte une valeur
                        final int izMin = sz == minSecZ ? minZ : sectionBaseZ;
                        // Affecte une valeur
                        final int izMax = sz == maxSecZ ? maxZ : sectionBaseZ | SECTION_BOUND;
                        // Instruction de code
                        result.add(new AreaImpl.Cuboid(
                                // Crée un nouvel objet
                                new BlockVec(ixMin, iyMin, izMin),
                                // Crée un nouvel objet
                                new BlockVec(ixMax, iyMax, izMax)));
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

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean contains(int x, int y, int z) {
            // Appelle une méthode
            final int minX = min.blockX(), minY = min.blockY(), minZ = min.blockZ();
            // Appelle une méthode
            final int maxX = max.blockX(), maxY = max.blockY(), maxZ = max.blockZ();
            // Renvoie une valeur à l'appelant
            return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public long blockCount() {
            // Appelle une méthode
            final int minX = min.blockX(), minY = min.blockY(), minZ = min.blockZ();
            // Appelle une méthode
            final int maxX = max.blockX(), maxY = max.blockY(), maxZ = max.blockZ();
            // Appelle une méthode
            final long width = (long) maxX - minX + 1, height = (long) maxY - minY + 1, depth = (long) maxZ - minZ + 1;
            // Renvoie une valeur à l'appelant
            return width * height * depth;
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
            // Appelle une méthode
            Check.argCondition(radius < 0, "Radius must be non-negative: {0}", radius);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<BlockVec> iterator() {
            // Appelle une méthode
            final int centerX = center.blockX(), centerY = center.blockY(), centerZ = center.blockZ();
            // Affecte une valeur
            final int radius = this.radius;
            // Appelle une méthode
            final long radiusSquared = (long) radius * radius;
            // Renvoie une valeur à l'appelant
            return new Iterator<>() {
                // Instruction de code
                private int x;
                // Affecte une valeur
                private int xEnd = Integer.MIN_VALUE;
                // Affecte une valeur
                private int y = -radius;
                // Affecte une valeur
                private int z = -radius;
                // Instruction de code
                private boolean done;

                // Début d'un bloc
                {
                    // Appelle une méthode
                    advance();
                // Fin d'un bloc/d'une expression
                }

                // Début d'une méthode/d'un bloc
                private void advance() {
                    // Boucle : répète un bloc
                    while (z <= radius) {
                        // Boucle : répète un bloc
                        while (y <= radius) {
                            // Affecte une valeur
                            final long dy = y, dz = z;
                            // Affecte une valeur
                            final long remaining = radiusSquared - dy * dy - dz * dz;
                            // Embranchement : vérifie une condition
                            if (remaining >= 0) {
                                // Appelle une méthode
                                final int halfWidth = (int) floorSqrt(remaining);
                                // Affecte une valeur
                                x = -halfWidth;
                                // Affecte une valeur
                                xEnd = halfWidth;
                                // Renvoie une valeur à l'appelant
                                return;
                            // Fin d'un bloc/d'une expression
                            }
                            // Instruction de code
                            y++;
                        // Fin d'un bloc/d'une expression
                        }
                        // Affecte une valeur
                        y = -radius;
                        // Instruction de code
                        z++;
                    // Fin d'un bloc/d'une expression
                    }
                    // Affecte une valeur
                    done = true;
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
                    final BlockVec result = new BlockVec(centerX + x, centerY + y, centerZ + z);
                    // Embranchement : vérifie une condition
                    if (++x > xEnd) {
                        // Instruction de code
                        y++;
                        // Appelle une méthode
                        advance();
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
            // Appelle une méthode
            final int centerX = center.blockX(), centerY = center.blockY(), centerZ = center.blockZ();
            // Affecte une valeur
            final int radius = this.radius;
            // Embranchement : vérifie une condition
            if (radius == 0) {
                // Renvoie une valeur à l'appelant
                return List.of(new AreaImpl.Cuboid(center, center));
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final long radiusSquared = (long) radius * radius;
            // Affecte une valeur
            final int bbMinX = centerX - radius, bbMinY = centerY - radius, bbMinZ = centerZ - radius;
            // Affecte une valeur
            final int bbMaxX = centerX + radius, bbMaxY = centerY + radius, bbMaxZ = centerZ + radius;
            // Affecte une valeur
            final int minSecX = bbMinX >> 4, minSecY = bbMinY >> 4, minSecZ = bbMinZ >> 4;
            // Affecte une valeur
            final int maxSecX = bbMaxX >> 4, maxSecY = bbMaxY >> 4, maxSecZ = bbMaxZ >> 4;
            // Appelle une méthode
            final long sectionCount = (long) (maxSecX - minSecX + 1) * (maxSecY - minSecY + 1) * (maxSecZ - minSecZ + 1);
            // Appelle une méthode
            final List<Area.Cuboid> result = new ArrayList<>(sectionCount > 1_000_000 ? 16 : (int) sectionCount);
            // Scratch buffers for the current and previous z-slice rectangles (xMin, xMax, yStart, yEnd per rect).
            // Affecte une valeur
            int[] prevSlice = new int[64];
            // Affecte une valeur
            int[] currentSlice = new int[64];
            // Boucle : répète un bloc
            for (int sx = minSecX; sx <= maxSecX; sx++) {
                // Affecte une valeur
                final int sectionMinX = sx << 4, sectionMaxX = sectionMinX | SECTION_BOUND;
                // Boucle : répète un bloc
                for (int sy = minSecY; sy <= maxSecY; sy++) {
                    // Affecte une valeur
                    final int sectionMinY = sy << 4, sectionMaxY = sectionMinY | SECTION_BOUND;
                    // Appelle une méthode
                    final int yLo = Math.max(bbMinY, sectionMinY), yHi = Math.min(bbMaxY, sectionMaxY);
                    // Boucle : répète un bloc
                    for (int sz = minSecZ; sz <= maxSecZ; sz++) {
                        // Affecte une valeur
                        final int sectionMinZ = sz << 4, sectionMaxZ = sectionMinZ | SECTION_BOUND;
                        // Appelle une méthode
                        final int zLo = Math.max(bbMinZ, sectionMinZ), zHi = Math.min(bbMaxZ, sectionMaxZ);
                        // Embranchement : vérifie une condition
                        if (sectionInsideSphere(sectionMinX, sectionMinY, sectionMinZ, sectionMaxX, sectionMaxY, sectionMaxZ, centerX, centerY, centerZ, radius)) {
                            // Appelle une méthode
                            result.add(AreaImpl.section(sx, sy, sz));
                            // Passe à l'itération suivante de la boucle
                            continue;
                        // Fin d'un bloc/d'une expression
                        }
                        // Affecte une valeur
                        int prevCount = 0;
                        // Affecte une valeur
                        int prevZStart = Integer.MIN_VALUE;
                        // Boucle : répète un bloc
                        for (int z = zLo; z <= zHi; z++) {
                            // Appelle une méthode
                            final long dz = (long) z - centerZ;
                            // Affecte une valeur
                            final long remZ = radiusSquared - dz * dz;
                            // Embranchement : vérifie une condition
                            if (remZ < 0) {
                                // Embranchement : vérifie une condition
                                if (prevCount > 0) {
                                    // Appelle une méthode
                                    emitSliceCuboids(result, prevSlice, prevCount, prevZStart, z - 1);
                                    // Affecte une valeur
                                    prevCount = 0;
                                // Fin d'un bloc/d'une expression
                                }
                                // Passe à l'itération suivante de la boucle
                                continue;
                            // Fin d'un bloc/d'une expression
                            }
                            // Build the (x, y) rectangles for this z slice via y-row merging.
                            // Affecte une valeur
                            int currentCount = 0;
                            // Affecte une valeur
                            int runMinX = 0, runMaxX = -1;
                            // Affecte une valeur
                            int runStartY = Integer.MIN_VALUE, runEndY = Integer.MIN_VALUE;
                            // Boucle : répète un bloc
                            for (int y = yLo; y <= yHi; y++) {
                                // Appelle une méthode
                                final long dy = (long) y - centerY;
                                // Affecte une valeur
                                final long rem = remZ - dy * dy;
                                // Affecte une valeur
                                int stripMinX = 0, stripMaxX = -1;
                                // Embranchement : vérifie une condition
                                if (rem >= 0) {
                                    // Appelle une méthode
                                    final int halfWidth = (int) floorSqrt(rem);
                                    // Appelle une méthode
                                    stripMinX = Math.max(sectionMinX, centerX - halfWidth);
                                    // Appelle une méthode
                                    stripMaxX = Math.min(sectionMaxX, centerX + halfWidth);
                                // Fin d'un bloc/d'une expression
                                }
                                // Affecte une valeur
                                final boolean hasStrip = stripMinX <= stripMaxX;
                                // Embranchement : vérifie une condition
                                if (hasStrip && runStartY != Integer.MIN_VALUE && stripMinX == runMinX && stripMaxX == runMaxX) {
                                    // Affecte une valeur
                                    runEndY = y;
                                // Branche alternative de la condition
                                } else {
                                    // Embranchement : vérifie une condition
                                    if (runStartY != Integer.MIN_VALUE) {
                                        // Embranchement : vérifie une condition
                                        if ((currentCount + 1) * 4 > currentSlice.length) {
                                            // Appelle une méthode
                                            currentSlice = Arrays.copyOf(currentSlice, currentSlice.length * 2);
                                        // Fin d'un bloc/d'une expression
                                        }
                                        // Affecte une valeur
                                        final int base = currentCount * 4;
                                        // Affecte une valeur
                                        currentSlice[base] = runMinX;
                                        // Affecte une valeur
                                        currentSlice[base + 1] = runMaxX;
                                        // Affecte une valeur
                                        currentSlice[base + 2] = runStartY;
                                        // Affecte une valeur
                                        currentSlice[base + 3] = runEndY;
                                        // Instruction de code
                                        currentCount++;
                                        // Affecte une valeur
                                        runStartY = Integer.MIN_VALUE;
                                    // Fin d'un bloc/d'une expression
                                    }
                                    // Embranchement : vérifie une condition
                                    if (hasStrip) {
                                        // Affecte une valeur
                                        runMinX = stripMinX;
                                        // Affecte une valeur
                                        runMaxX = stripMaxX;
                                        // Affecte une valeur
                                        runStartY = y;
                                        // Affecte une valeur
                                        runEndY = y;
                                    // Fin d'un bloc/d'une expression
                                    }
                                // Fin d'un bloc/d'une expression
                                }
                            // Fin d'un bloc/d'une expression
                            }
                            // Embranchement : vérifie une condition
                            if (runStartY != Integer.MIN_VALUE) {
                                // Embranchement : vérifie une condition
                                if ((currentCount + 1) * 4 > currentSlice.length) {
                                    // Appelle une méthode
                                    currentSlice = Arrays.copyOf(currentSlice, currentSlice.length * 2);
                                // Fin d'un bloc/d'une expression
                                }
                                // Affecte une valeur
                                final int base = currentCount * 4;
                                // Affecte une valeur
                                currentSlice[base] = runMinX;
                                // Affecte une valeur
                                currentSlice[base + 1] = runMaxX;
                                // Affecte une valeur
                                currentSlice[base + 2] = runStartY;
                                // Affecte une valeur
                                currentSlice[base + 3] = runEndY;
                                // Instruction de code
                                currentCount++;
                            // Fin d'un bloc/d'une expression
                            }
                            // Compare this z slice to the prev one to extend a z-run if they match.
                            // Embranchement : vérifie une condition
                            if (currentCount > 0 && currentCount == prevCount
                                    // Début d'une méthode/d'un bloc
                                    && Arrays.equals(currentSlice, 0, currentCount * 4, prevSlice, 0, currentCount * 4)) {
                                // Passe à l'itération suivante de la boucle
                                continue;
                            // Fin d'un bloc/d'une expression
                            }
                            // Embranchement : vérifie une condition
                            if (prevCount > 0) {
                                // Appelle une méthode
                                emitSliceCuboids(result, prevSlice, prevCount, prevZStart, z - 1);
                            // Fin d'un bloc/d'une expression
                            }
                            // Affecte une valeur
                            final int[] tmp = prevSlice;
                            // Affecte une valeur
                            prevSlice = currentSlice;
                            // Affecte une valeur
                            currentSlice = tmp;
                            // Affecte une valeur
                            prevCount = currentCount;
                            // Affecte une valeur
                            prevZStart = z;
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement : vérifie une condition
                        if (prevCount > 0) {
                            // Appelle une méthode
                            emitSliceCuboids(result, prevSlice, prevCount, prevZStart, zHi);
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

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean contains(int x, int y, int z) {
            // Appelle une méthode
            final int centerX = center.blockX(), centerY = center.blockY(), centerZ = center.blockZ();
            // Appelle une méthode
            final long dx = (long) x - centerX, dy = (long) y - centerY, dz = (long) z - centerZ;
            // Renvoie une valeur à l'appelant
            return withinSphereRadius(dx, dy, dz, radius);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public long blockCount() {
            // Affecte une valeur
            final int radius = this.radius;
            // Embranchement : vérifie une condition
            if (radius == 0) return 1;
            // Appelle une méthode
            final long radiusSquared = (long) radius * radius;
            // Affecte une valeur
            long count = 2L * radius + 1; // center column
            // Boucle : répète un bloc
            for (int d = 1; d <= radius; d++) {
                // Appelle une méthode
                final long remaining = radiusSquared - (long) d * d;
                // Appelle une méthode
                count += 4L * (2L * floorSqrt(remaining) + 1);
            // Fin d'un bloc/d'une expression
            }
            // Boucle : répète un bloc
            for (int dx = 1; dx < radius; dx++) {
                // Appelle une méthode
                final long dxSquared = (long) dx * dx;
                // Boucle : répète un bloc
                for (int dy = 1; dy < radius; dy++) {
                    // Appelle une méthode
                    final long remaining = radiusSquared - dxSquared - (long) dy * dy;
                    // Embranchement : vérifie une condition
                    if (remaining < 0) break; // dy only grows; further values stay negative
                    // Appelle une méthode
                    count += 4L * (2L * floorSqrt(remaining) + 1);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return count;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void emitSliceCuboids(List<Area.Cuboid> out, int[] slice, int count, int zStart, int zEnd) {
        // Boucle : répète un bloc
        for (int i = 0; i < count; i++) {
            // Affecte une valeur
            final int base = i * 4;
            // Instruction de code
            out.add(new AreaImpl.Cuboid(
                    // Crée un nouvel objet
                    new BlockVec(slice[base], slice[base + 2], zStart),
                    // Crée un nouvel objet
                    new BlockVec(slice[base + 1], slice[base + 3], zEnd)));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static Cuboid buildRunCuboid(int startX, int startY, int startZ,
                                         // Début d'une méthode/d'un bloc
                                         int endX, int endY, int endZ) {
        // Appelle une méthode
        final BlockVec startVec = new BlockVec(startX, startY, startZ);
        // Affecte une valeur
        final BlockVec endVec = (startX == endX && startY == endY && startZ == endZ)
                // Instruction de code
                ? startVec
                // Appelle une méthode
                : new BlockVec(endX, endY, endZ);
        // Renvoie une valeur à l'appelant
        return new Cuboid(startVec, endVec);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean withinSphereRadius(long dx, long dy, long dz, int radius) {
        // Embranchement : vérifie une condition
        if (dx < -radius || dx > radius || dy < -radius || dy > radius || dz < -radius || dz > radius) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return dx * dx + dy * dy + dz * dz <= (long) radius * radius;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static boolean canExtendAxisAlignedRun(int startX, int startY, int startZ,
                                                   // Instruction de code
                                                   int endX, int endY, int endZ,
                                                   // Début d'une méthode/d'un bloc
                                                   int nextX, int nextY, int nextZ) {
        // Affecte une valeur
        final boolean sameX = startX == endX && endX == nextX;
        // Affecte une valeur
        final boolean sameY = startY == endY && endY == nextY;
        // Affecte une valeur
        final boolean sameZ = startZ == endZ && endZ == nextZ;
        // Appelle une méthode
        final boolean adjacentX = Math.abs(nextX - endX) == 1;
        // Appelle une méthode
        final boolean adjacentY = Math.abs(nextY - endY) == 1;
        // Appelle une méthode
        final boolean adjacentZ = Math.abs(nextZ - endZ) == 1;
        // Renvoie une valeur à l'appelant
        return sameY && sameZ && adjacentX || sameX && sameZ && adjacentY || sameX && sameY && adjacentZ;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static boolean sectionInsideSphere(int sectionMinX, int sectionMinY, int sectionMinZ,
                                               // Instruction de code
                                               int sectionMaxX, int sectionMaxY, int sectionMaxZ,
                                               // Début d'une méthode/d'un bloc
                                               int centerX, int centerY, int centerZ, int radius) {
        // Appelle une méthode
        final long farX = Math.max(Math.abs((long) sectionMinX - centerX), Math.abs((long) sectionMaxX - centerX));
        // Appelle une méthode
        final long farY = Math.max(Math.abs((long) sectionMinY - centerY), Math.abs((long) sectionMaxY - centerY));
        // Appelle une méthode
        final long farZ = Math.max(Math.abs((long) sectionMinZ - centerZ), Math.abs((long) sectionMaxZ - centerZ));
        // Renvoie une valeur à l'appelant
        return withinSphereRadius(farX, farY, farZ, radius);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static long floorSqrt(long value) {
        // Appelle une méthode
        long sqrt = (long) Math.sqrt(value);
        // Math.sqrt may round up to an exact integer for value > 2^52; correct by one if so.
        // Embranchement : vérifie une condition
        if (sqrt > 0 && sqrt * sqrt > value) sqrt--;
        // Renvoie une valeur à l'appelant
        return sqrt;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
