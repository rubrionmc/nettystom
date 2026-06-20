// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.*;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.SECTION_BOUND;
// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.SECTION_SIZE;

// Type declaration (class/interface/enum/record)
final class AreaImpl {

    // Start of a method/block
    static Area.Cuboid section(int sectionX, int sectionY, int sectionZ) {
        // Calls a method
        final BlockVec min = new BlockVec(sectionX * SECTION_SIZE, sectionY * SECTION_SIZE, sectionZ * SECTION_SIZE);
        // Returns a value to the caller
        return new Cuboid(min, min.add(SECTION_BOUND));
    // End of a block/expression
    }

    // Start of a method/block
    static Area.Cuboid cube(Point center, int size) {
        // Calls a method
        Check.argCondition(size < 0, "Cube size must be non-negative: {0}", size);
        // Returns a value to the caller
        return new Cuboid(
                // Code statement
                center.sub((double) size / 2).asBlockVec(),
                // Calls a method
                center.add((double) size / 2).asBlockVec());
    // End of a block/expression
    }

    // Start of a method/block
    static Area.Cuboid box(Point center, Point size) {
        // Code statement
        Check.argCondition(size.x() < 0 || size.y() < 0 || size.z() < 0,
                // Calls a method
                "Box size must be non-negative on each axis: ({0}, {1}, {2})", size.x(), size.y(), size.z());
        // Calls a method
        final Point half = size.div(2);
        // Returns a value to the caller
        return new Cuboid(center.sub(half).asBlockVec(), center.add(half).asBlockVec());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Single(BlockVec point) implements Area.Single {
        // Start of a method/block
        public Single {
            // Calls a method
            Objects.requireNonNull(point, "Point cannot be null");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<BlockVec> iterator() {
            // Returns a value to the caller
            return List.of(point).iterator();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<Area.Cuboid> split() {
            // Returns a value to the caller
            return List.of(new AreaImpl.Cuboid(point, point));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean contains(int x, int y, int z) {
            // Returns a value to the caller
            return this.point.blockX() == x && this.point.blockY() == y && this.point.blockZ() == z;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public long blockCount() {
            // Returns a value to the caller
            return 1;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Line(BlockVec start, BlockVec end) implements Area.Line {
        // Start of a method/block
        public Line {
            // Calls a method
            Objects.requireNonNull(start, "Start point cannot be null");
            // Calls a method
            Objects.requireNonNull(end, "End point cannot be null");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<BlockVec> iterator() {
            // Branch: checks a condition
            if (start.samePoint(end)) return List.of(start).iterator();
            // Calls a method
            final int x1 = start.blockX(), y1 = start.blockY(), z1 = start.blockZ();
            // Calls a method
            final int x2 = end.blockX(), y2 = end.blockY(), z2 = end.blockZ();
            // Calls a method
            final int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), dz = Math.abs(z2 - z1);
            // Assigns a value
            final int sx = x1 < x2 ? 1 : -1, sy = y1 < y2 ? 1 : -1, sz = z1 < z2 ? 1 : -1;
            // Calls a method
            final int errInit = Math.max(dx, Math.max(dy, dz)) / 2;
            // Returns a value to the caller
            return new Iterator<>() {
                // Assigns a value
                private int x = x1, y = y1, z = z1;
                // Assigns a value
                private int err1 = errInit, err2 = errInit;
                // Code statement
                private boolean done;

                // Annotation for the following element
                @Override
                // Start of a method/block
                public boolean hasNext() {
                    // Returns a value to the caller
                    return !done;
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public BlockVec next() {
                    // Branch: checks a condition
                    if (done) throw new NoSuchElementException();
                    // Calls a method
                    final BlockVec result = new BlockVec(x, y, z);
                    // Branch: checks a condition
                    if (x == x2 && y == y2 && z == z2) {
                        // Assigns a value
                        done = true;
                        // Returns a value to the caller
                        return result;
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (dx >= dy && dx >= dz) {
                        // Code statement
                        x += sx;
                        // Code statement
                        err1 -= dy;
                        // Code statement
                        err2 -= dz;
                        // Branch: checks a condition
                        if (err1 < 0) {
                            // Code statement
                            y += sy;
                            // Code statement
                            err1 += dx;
                        // End of a block/expression
                        }
                        // Branch: checks a condition
                        if (err2 < 0) {
                            // Code statement
                            z += sz;
                            // Code statement
                            err2 += dx;
                        // End of a block/expression
                        }
                    // Branch: checks a condition
                    } else if (dy >= dz) {
                        // Code statement
                        y += sy;
                        // Code statement
                        err1 -= dx;
                        // Code statement
                        err2 -= dz;
                        // Branch: checks a condition
                        if (err1 < 0) {
                            // Code statement
                            x += sx;
                            // Code statement
                            err1 += dy;
                        // End of a block/expression
                        }
                        // Branch: checks a condition
                        if (err2 < 0) {
                            // Code statement
                            z += sz;
                            // Code statement
                            err2 += dy;
                        // End of a block/expression
                        }
                    // Alternative branch of the condition
                    } else {
                        // Code statement
                        z += sz;
                        // Code statement
                        err1 -= dx;
                        // Code statement
                        err2 -= dy;
                        // Branch: checks a condition
                        if (err1 < 0) {
                            // Code statement
                            x += sx;
                            // Code statement
                            err1 += dz;
                        // End of a block/expression
                        }
                        // Branch: checks a condition
                        if (err2 < 0) {
                            // Code statement
                            y += sy;
                            // Code statement
                            err2 += dz;
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return result;
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<Area.Cuboid> split() {
            // Calls a method
            final int x1 = start.blockX(), y1 = start.blockY(), z1 = start.blockZ();
            // Calls a method
            final int x2 = end.blockX(), y2 = end.blockY(), z2 = end.blockZ();
            // Branch: checks a condition
            if (x1 == x2 && y1 == y2 && z1 == z2) {
                // Returns a value to the caller
                return List.of(new AreaImpl.Cuboid(start, end));
            // End of a block/expression
            }
            // Calls a method
            final int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), dz = Math.abs(z2 - z1);
            // Axis-aligned lines cover the same blocks as an inclusive cuboid; reuse Cuboid.split.
            // Branch: checks a condition
            if ((dx == 0 ? 1 : 0) + (dy == 0 ? 1 : 0) + (dz == 0 ? 1 : 0) >= 2) {
                // Returns a value to the caller
                return new AreaImpl.Cuboid(
                        // Creates a new object
                        new BlockVec(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2)),
                        // Creates a new object
                        new BlockVec(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2))).split();
            // End of a block/expression
            }
            // Calls a method
            final List<Area.Cuboid> result = new ArrayList<>();
            // Assigns a value
            final int sx = x1 < x2 ? 1 : -1, sy = y1 < y2 ? 1 : -1, sz = z1 < z2 ? 1 : -1;
            // Calls a method
            int err1 = Math.max(dx, Math.max(dy, dz)) / 2, err2 = err1;
            // Assigns a value
            int runStartX = x1, runStartY = y1, runStartZ = z1;
            // Assigns a value
            int runStartSecX = x1 >> 4, runStartSecY = y1 >> 4, runStartSecZ = z1 >> 4;
            // Assigns a value
            int runEndX = x1, runEndY = y1, runEndZ = z1;
            // Loop: repeats a block
            while (runEndX != x2 || runEndY != y2 || runEndZ != z2) {
                // Assigns a value
                int nextX = runEndX, nextY = runEndY, nextZ = runEndZ;
                // Branch: checks a condition
                if (dx >= dy && dx >= dz) {
                    // Code statement
                    nextX += sx;
                    // Code statement
                    err1 -= dy;
                    // Code statement
                    err2 -= dz;
                    // Branch: checks a condition
                    if (err1 < 0) {
                        // Code statement
                        nextY += sy;
                        // Code statement
                        err1 += dx;
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (err2 < 0) {
                        // Code statement
                        nextZ += sz;
                        // Code statement
                        err2 += dx;
                    // End of a block/expression
                    }
                // Branch: checks a condition
                } else if (dy >= dz) {
                    // Code statement
                    nextY += sy;
                    // Code statement
                    err1 -= dx;
                    // Code statement
                    err2 -= dz;
                    // Branch: checks a condition
                    if (err1 < 0) {
                        // Code statement
                        nextX += sx;
                        // Code statement
                        err1 += dy;
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (err2 < 0) {
                        // Code statement
                        nextZ += sz;
                        // Code statement
                        err2 += dy;
                    // End of a block/expression
                    }
                // Alternative branch of the condition
                } else {
                    // Code statement
                    nextZ += sz;
                    // Code statement
                    err1 -= dx;
                    // Code statement
                    err2 -= dy;
                    // Branch: checks a condition
                    if (err1 < 0) {
                        // Code statement
                        nextX += sx;
                        // Code statement
                        err1 += dz;
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (err2 < 0) {
                        // Code statement
                        nextY += sy;
                        // Code statement
                        err2 += dz;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Assigns a value
                final int nextSecX = nextX >> 4, nextSecY = nextY >> 4, nextSecZ = nextZ >> 4;
                // Assigns a value
                final boolean sameSection = runStartSecX == nextSecX && runStartSecY == nextSecY && runStartSecZ == nextSecZ;
                // Branch: checks a condition
                if (!sameSection || !canExtendAxisAlignedRun(runStartX, runStartY, runStartZ, runEndX, runEndY, runEndZ, nextX, nextY, nextZ)) {
                    // Calls a method
                    result.add(buildRunCuboid(runStartX, runStartY, runStartZ, runEndX, runEndY, runEndZ));
                    // Assigns a value
                    runStartX = nextX;
                    // Assigns a value
                    runStartY = nextY;
                    // Assigns a value
                    runStartZ = nextZ;
                    // Assigns a value
                    runStartSecX = nextSecX;
                    // Assigns a value
                    runStartSecY = nextSecY;
                    // Assigns a value
                    runStartSecZ = nextSecZ;
                // End of a block/expression
                }
                // Assigns a value
                runEndX = nextX;
                // Assigns a value
                runEndY = nextY;
                // Assigns a value
                runEndZ = nextZ;
            // End of a block/expression
            }
            // Calls a method
            result.add(buildRunCuboid(runStartX, runStartY, runStartZ, runEndX, runEndY, runEndZ));
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean contains(int targetX, int targetY, int targetZ) {
            // Calls a method
            final int x1 = start.blockX(), y1 = start.blockY(), z1 = start.blockZ();
            // Calls a method
            final int x2 = end.blockX(), y2 = end.blockY(), z2 = end.blockZ();
            // Branch: checks a condition
            if (targetX < Math.min(x1, x2) || targetX > Math.max(x1, x2) ||
                    // Code statement
                    targetY < Math.min(y1, y2) || targetY > Math.max(y1, y2) ||
                    // Start of a method/block
                    targetZ < Math.min(z1, z2) || targetZ > Math.max(z1, z2)) {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
            // Calls a method
            final int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), dz = Math.abs(z2 - z1);
            // Assigns a value
            final int sx = x1 < x2 ? 1 : -1, sy = y1 < y2 ? 1 : -1, sz = z1 < z2 ? 1 : -1;
            // Assigns a value
            int x = x1, y = y1, z = z1;
            // Calls a method
            int err1 = Math.max(dx, Math.max(dy, dz)) / 2, err2 = err1;
            // Loop: repeats a block
            while (true) {
                // Branch: checks a condition
                if (x == targetX && y == targetY && z == targetZ) return true;
                // Branch: checks a condition
                if (x == x2 && y == y2 && z == z2) return false;
                // Branch: checks a condition
                if (dx >= dy && dx >= dz) {
                    // Code statement
                    x += sx;
                    // Code statement
                    err1 -= dy;
                    // Code statement
                    err2 -= dz;
                    // Branch: checks a condition
                    if (err1 < 0) {
                        // Code statement
                        y += sy;
                        // Code statement
                        err1 += dx;
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (err2 < 0) {
                        // Code statement
                        z += sz;
                        // Code statement
                        err2 += dx;
                    // End of a block/expression
                    }
                // Branch: checks a condition
                } else if (dy >= dz) {
                    // Code statement
                    y += sy;
                    // Code statement
                    err1 -= dx;
                    // Code statement
                    err2 -= dz;
                    // Branch: checks a condition
                    if (err1 < 0) {
                        // Code statement
                        x += sx;
                        // Code statement
                        err1 += dy;
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (err2 < 0) {
                        // Code statement
                        z += sz;
                        // Code statement
                        err2 += dy;
                    // End of a block/expression
                    }
                // Alternative branch of the condition
                } else {
                    // Code statement
                    z += sz;
                    // Code statement
                    err1 -= dx;
                    // Code statement
                    err2 -= dy;
                    // Branch: checks a condition
                    if (err1 < 0) {
                        // Code statement
                        x += sx;
                        // Code statement
                        err1 += dz;
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (err2 < 0) {
                        // Code statement
                        y += sy;
                        // Code statement
                        err2 += dz;
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
        public long blockCount() {
            // Calls a method
            final int startX = start.blockX(), startY = start.blockY(), startZ = start.blockZ();
            // Calls a method
            final int endX = end.blockX(), endY = end.blockY(), endZ = end.blockZ();
            // Calls a method
            final long dx = Math.abs((long) endX - startX), dy = Math.abs((long) endY - startY), dz = Math.abs((long) endZ - startZ);
            // Returns a value to the caller
            return Math.max(dx, Math.max(dy, dz)) + 1;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Cuboid(BlockVec min, BlockVec max) implements Area.Cuboid {
        // Start of a method/block
        public Cuboid {
            // Calls a method
            Objects.requireNonNull(min, "min cannot be null");
            // Calls a method
            Objects.requireNonNull(max, "max cannot be null");
            // Branch: checks a condition
            if (min.blockX() > max.blockX() || min.blockY() > max.blockY() || min.blockZ() > max.blockZ()) {
                // Assigns a value
                final BlockVec origMin = min;
                // Calls a method
                min = origMin.min(max);
                // Calls a method
                max = origMin.max(max);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<BlockVec> iterator() {
            // Calls a method
            final int minX = min.blockX(), minY = min.blockY(), minZ = min.blockZ();
            // Calls a method
            final int maxX = max.blockX(), maxY = max.blockY(), maxZ = max.blockZ();
            // Returns a value to the caller
            return new Iterator<>() {
                // Assigns a value
                private int x = minX;
                // Assigns a value
                private int y = minY;
                // Assigns a value
                private int z = minZ;
                // Assigns a value
                private boolean hasNext = true;

                // Annotation for the following element
                @Override
                // Start of a method/block
                public boolean hasNext() {
                    // Returns a value to the caller
                    return hasNext;
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public BlockVec next() {
                    // Branch: checks a condition
                    if (!hasNext) throw new NoSuchElementException();
                    // Calls a method
                    final BlockVec vec = new BlockVec(x, y, z);
                    // Branch: checks a condition
                    if (x < maxX) {
                        // Code statement
                        x++;
                    // Branch: checks a condition
                    } else if (y < maxY) {
                        // Assigns a value
                        x = minX;
                        // Code statement
                        y++;
                    // Branch: checks a condition
                    } else if (z < maxZ) {
                        // Assigns a value
                        x = minX;
                        // Assigns a value
                        y = minY;
                        // Code statement
                        z++;
                    // Alternative branch of the condition
                    } else {
                        // Assigns a value
                        hasNext = false;
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return vec;
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<Area.Cuboid> split() {
            // Calls a method
            final int minX = min.blockX(), minY = min.blockY(), minZ = min.blockZ();
            // Calls a method
            final int maxX = max.blockX(), maxY = max.blockY(), maxZ = max.blockZ();
            // Assigns a value
            final int minSecX = minX >> 4, minSecY = minY >> 4, minSecZ = minZ >> 4;
            // Assigns a value
            final int maxSecX = maxX >> 4, maxSecY = maxY >> 4, maxSecZ = maxZ >> 4;
            // Branch: checks a condition
            if (minSecX == maxSecX && minSecY == maxSecY && minSecZ == maxSecZ) {
                // Returns a value to the caller
                return List.of(this);
            // End of a block/expression
            }
            // Calls a method
            final long total = (long) (maxSecX - minSecX + 1) * (maxSecY - minSecY + 1) * (maxSecZ - minSecZ + 1);
            // Calls a method
            final List<Area.Cuboid> result = new ArrayList<>(total > 1_000_000 ? 16 : (int) total);
            // Loop: repeats a block
            for (int sx = minSecX; sx <= maxSecX; sx++) {
                // Assigns a value
                final int sectionBaseX = sx << 4;
                // Assigns a value
                final int ixMin = sx == minSecX ? minX : sectionBaseX;
                // Assigns a value
                final int ixMax = sx == maxSecX ? maxX : sectionBaseX | SECTION_BOUND;
                // Loop: repeats a block
                for (int sy = minSecY; sy <= maxSecY; sy++) {
                    // Assigns a value
                    final int sectionBaseY = sy << 4;
                    // Assigns a value
                    final int iyMin = sy == minSecY ? minY : sectionBaseY;
                    // Assigns a value
                    final int iyMax = sy == maxSecY ? maxY : sectionBaseY | SECTION_BOUND;
                    // Loop: repeats a block
                    for (int sz = minSecZ; sz <= maxSecZ; sz++) {
                        // Assigns a value
                        final int sectionBaseZ = sz << 4;
                        // Assigns a value
                        final int izMin = sz == minSecZ ? minZ : sectionBaseZ;
                        // Assigns a value
                        final int izMax = sz == maxSecZ ? maxZ : sectionBaseZ | SECTION_BOUND;
                        // Code statement
                        result.add(new AreaImpl.Cuboid(
                                // Creates a new object
                                new BlockVec(ixMin, iyMin, izMin),
                                // Creates a new object
                                new BlockVec(ixMax, iyMax, izMax)));
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean contains(int x, int y, int z) {
            // Calls a method
            final int minX = min.blockX(), minY = min.blockY(), minZ = min.blockZ();
            // Calls a method
            final int maxX = max.blockX(), maxY = max.blockY(), maxZ = max.blockZ();
            // Returns a value to the caller
            return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public long blockCount() {
            // Calls a method
            final int minX = min.blockX(), minY = min.blockY(), minZ = min.blockZ();
            // Calls a method
            final int maxX = max.blockX(), maxY = max.blockY(), maxZ = max.blockZ();
            // Calls a method
            final long width = (long) maxX - minX + 1, height = (long) maxY - minY + 1, depth = (long) maxZ - minZ + 1;
            // Returns a value to the caller
            return width * height * depth;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Sphere(BlockVec center, int radius) implements Area.Sphere {
        // Start of a method/block
        public Sphere {
            // Calls a method
            Objects.requireNonNull(center, "Center cannot be null");
            // Calls a method
            Check.argCondition(radius < 0, "Radius must be non-negative: {0}", radius);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<BlockVec> iterator() {
            // Calls a method
            final int centerX = center.blockX(), centerY = center.blockY(), centerZ = center.blockZ();
            // Assigns a value
            final int radius = this.radius;
            // Calls a method
            final long radiusSquared = (long) radius * radius;
            // Returns a value to the caller
            return new Iterator<>() {
                // Code statement
                private int x;
                // Assigns a value
                private int xEnd = Integer.MIN_VALUE;
                // Assigns a value
                private int y = -radius;
                // Assigns a value
                private int z = -radius;
                // Code statement
                private boolean done;

                // Start of a block
                {
                    // Calls a method
                    advance();
                // End of a block/expression
                }

                // Start of a method/block
                private void advance() {
                    // Loop: repeats a block
                    while (z <= radius) {
                        // Loop: repeats a block
                        while (y <= radius) {
                            // Assigns a value
                            final long dy = y, dz = z;
                            // Assigns a value
                            final long remaining = radiusSquared - dy * dy - dz * dz;
                            // Branch: checks a condition
                            if (remaining >= 0) {
                                // Calls a method
                                final int halfWidth = (int) floorSqrt(remaining);
                                // Assigns a value
                                x = -halfWidth;
                                // Assigns a value
                                xEnd = halfWidth;
                                // Returns a value to the caller
                                return;
                            // End of a block/expression
                            }
                            // Code statement
                            y++;
                        // End of a block/expression
                        }
                        // Assigns a value
                        y = -radius;
                        // Code statement
                        z++;
                    // End of a block/expression
                    }
                    // Assigns a value
                    done = true;
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public boolean hasNext() {
                    // Returns a value to the caller
                    return !done;
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public BlockVec next() {
                    // Branch: checks a condition
                    if (done) throw new NoSuchElementException();
                    // Calls a method
                    final BlockVec result = new BlockVec(centerX + x, centerY + y, centerZ + z);
                    // Branch: checks a condition
                    if (++x > xEnd) {
                        // Code statement
                        y++;
                        // Calls a method
                        advance();
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return result;
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<Area.Cuboid> split() {
            // Calls a method
            final int centerX = center.blockX(), centerY = center.blockY(), centerZ = center.blockZ();
            // Assigns a value
            final int radius = this.radius;
            // Branch: checks a condition
            if (radius == 0) {
                // Returns a value to the caller
                return List.of(new AreaImpl.Cuboid(center, center));
            // End of a block/expression
            }
            // Calls a method
            final long radiusSquared = (long) radius * radius;
            // Assigns a value
            final int bbMinX = centerX - radius, bbMinY = centerY - radius, bbMinZ = centerZ - radius;
            // Assigns a value
            final int bbMaxX = centerX + radius, bbMaxY = centerY + radius, bbMaxZ = centerZ + radius;
            // Assigns a value
            final int minSecX = bbMinX >> 4, minSecY = bbMinY >> 4, minSecZ = bbMinZ >> 4;
            // Assigns a value
            final int maxSecX = bbMaxX >> 4, maxSecY = bbMaxY >> 4, maxSecZ = bbMaxZ >> 4;
            // Calls a method
            final long sectionCount = (long) (maxSecX - minSecX + 1) * (maxSecY - minSecY + 1) * (maxSecZ - minSecZ + 1);
            // Calls a method
            final List<Area.Cuboid> result = new ArrayList<>(sectionCount > 1_000_000 ? 16 : (int) sectionCount);
            // Scratch buffers for the current and previous z-slice rectangles (xMin, xMax, yStart, yEnd per rect).
            // Assigns a value
            int[] prevSlice = new int[64];
            // Assigns a value
            int[] currentSlice = new int[64];
            // Loop: repeats a block
            for (int sx = minSecX; sx <= maxSecX; sx++) {
                // Assigns a value
                final int sectionMinX = sx << 4, sectionMaxX = sectionMinX | SECTION_BOUND;
                // Loop: repeats a block
                for (int sy = minSecY; sy <= maxSecY; sy++) {
                    // Assigns a value
                    final int sectionMinY = sy << 4, sectionMaxY = sectionMinY | SECTION_BOUND;
                    // Calls a method
                    final int yLo = Math.max(bbMinY, sectionMinY), yHi = Math.min(bbMaxY, sectionMaxY);
                    // Loop: repeats a block
                    for (int sz = minSecZ; sz <= maxSecZ; sz++) {
                        // Assigns a value
                        final int sectionMinZ = sz << 4, sectionMaxZ = sectionMinZ | SECTION_BOUND;
                        // Calls a method
                        final int zLo = Math.max(bbMinZ, sectionMinZ), zHi = Math.min(bbMaxZ, sectionMaxZ);
                        // Branch: checks a condition
                        if (sectionInsideSphere(sectionMinX, sectionMinY, sectionMinZ, sectionMaxX, sectionMaxY, sectionMaxZ, centerX, centerY, centerZ, radius)) {
                            // Calls a method
                            result.add(AreaImpl.section(sx, sy, sz));
                            // Continues to the next loop iteration
                            continue;
                        // End of a block/expression
                        }
                        // Assigns a value
                        int prevCount = 0;
                        // Assigns a value
                        int prevZStart = Integer.MIN_VALUE;
                        // Loop: repeats a block
                        for (int z = zLo; z <= zHi; z++) {
                            // Calls a method
                            final long dz = (long) z - centerZ;
                            // Assigns a value
                            final long remZ = radiusSquared - dz * dz;
                            // Branch: checks a condition
                            if (remZ < 0) {
                                // Branch: checks a condition
                                if (prevCount > 0) {
                                    // Calls a method
                                    emitSliceCuboids(result, prevSlice, prevCount, prevZStart, z - 1);
                                    // Assigns a value
                                    prevCount = 0;
                                // End of a block/expression
                                }
                                // Continues to the next loop iteration
                                continue;
                            // End of a block/expression
                            }
                            // Build the (x, y) rectangles for this z slice via y-row merging.
                            // Assigns a value
                            int currentCount = 0;
                            // Assigns a value
                            int runMinX = 0, runMaxX = -1;
                            // Assigns a value
                            int runStartY = Integer.MIN_VALUE, runEndY = Integer.MIN_VALUE;
                            // Loop: repeats a block
                            for (int y = yLo; y <= yHi; y++) {
                                // Calls a method
                                final long dy = (long) y - centerY;
                                // Assigns a value
                                final long rem = remZ - dy * dy;
                                // Assigns a value
                                int stripMinX = 0, stripMaxX = -1;
                                // Branch: checks a condition
                                if (rem >= 0) {
                                    // Calls a method
                                    final int halfWidth = (int) floorSqrt(rem);
                                    // Calls a method
                                    stripMinX = Math.max(sectionMinX, centerX - halfWidth);
                                    // Calls a method
                                    stripMaxX = Math.min(sectionMaxX, centerX + halfWidth);
                                // End of a block/expression
                                }
                                // Assigns a value
                                final boolean hasStrip = stripMinX <= stripMaxX;
                                // Branch: checks a condition
                                if (hasStrip && runStartY != Integer.MIN_VALUE && stripMinX == runMinX && stripMaxX == runMaxX) {
                                    // Assigns a value
                                    runEndY = y;
                                // Alternative branch of the condition
                                } else {
                                    // Branch: checks a condition
                                    if (runStartY != Integer.MIN_VALUE) {
                                        // Branch: checks a condition
                                        if ((currentCount + 1) * 4 > currentSlice.length) {
                                            // Calls a method
                                            currentSlice = Arrays.copyOf(currentSlice, currentSlice.length * 2);
                                        // End of a block/expression
                                        }
                                        // Assigns a value
                                        final int base = currentCount * 4;
                                        // Assigns a value
                                        currentSlice[base] = runMinX;
                                        // Assigns a value
                                        currentSlice[base + 1] = runMaxX;
                                        // Assigns a value
                                        currentSlice[base + 2] = runStartY;
                                        // Assigns a value
                                        currentSlice[base + 3] = runEndY;
                                        // Code statement
                                        currentCount++;
                                        // Assigns a value
                                        runStartY = Integer.MIN_VALUE;
                                    // End of a block/expression
                                    }
                                    // Branch: checks a condition
                                    if (hasStrip) {
                                        // Assigns a value
                                        runMinX = stripMinX;
                                        // Assigns a value
                                        runMaxX = stripMaxX;
                                        // Assigns a value
                                        runStartY = y;
                                        // Assigns a value
                                        runEndY = y;
                                    // End of a block/expression
                                    }
                                // End of a block/expression
                                }
                            // End of a block/expression
                            }
                            // Branch: checks a condition
                            if (runStartY != Integer.MIN_VALUE) {
                                // Branch: checks a condition
                                if ((currentCount + 1) * 4 > currentSlice.length) {
                                    // Calls a method
                                    currentSlice = Arrays.copyOf(currentSlice, currentSlice.length * 2);
                                // End of a block/expression
                                }
                                // Assigns a value
                                final int base = currentCount * 4;
                                // Assigns a value
                                currentSlice[base] = runMinX;
                                // Assigns a value
                                currentSlice[base + 1] = runMaxX;
                                // Assigns a value
                                currentSlice[base + 2] = runStartY;
                                // Assigns a value
                                currentSlice[base + 3] = runEndY;
                                // Code statement
                                currentCount++;
                            // End of a block/expression
                            }
                            // Compare this z slice to the prev one to extend a z-run if they match.
                            // Branch: checks a condition
                            if (currentCount > 0 && currentCount == prevCount
                                    // Start of a method/block
                                    && Arrays.equals(currentSlice, 0, currentCount * 4, prevSlice, 0, currentCount * 4)) {
                                // Continues to the next loop iteration
                                continue;
                            // End of a block/expression
                            }
                            // Branch: checks a condition
                            if (prevCount > 0) {
                                // Calls a method
                                emitSliceCuboids(result, prevSlice, prevCount, prevZStart, z - 1);
                            // End of a block/expression
                            }
                            // Assigns a value
                            final int[] tmp = prevSlice;
                            // Assigns a value
                            prevSlice = currentSlice;
                            // Assigns a value
                            currentSlice = tmp;
                            // Assigns a value
                            prevCount = currentCount;
                            // Assigns a value
                            prevZStart = z;
                        // End of a block/expression
                        }
                        // Branch: checks a condition
                        if (prevCount > 0) {
                            // Calls a method
                            emitSliceCuboids(result, prevSlice, prevCount, prevZStart, zHi);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean contains(int x, int y, int z) {
            // Calls a method
            final int centerX = center.blockX(), centerY = center.blockY(), centerZ = center.blockZ();
            // Calls a method
            final long dx = (long) x - centerX, dy = (long) y - centerY, dz = (long) z - centerZ;
            // Returns a value to the caller
            return withinSphereRadius(dx, dy, dz, radius);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public long blockCount() {
            // Assigns a value
            final int radius = this.radius;
            // Branch: checks a condition
            if (radius == 0) return 1;
            // Calls a method
            final long radiusSquared = (long) radius * radius;
            // Assigns a value
            long count = 2L * radius + 1; // center column
            // Loop: repeats a block
            for (int d = 1; d <= radius; d++) {
                // Calls a method
                final long remaining = radiusSquared - (long) d * d;
                // Calls a method
                count += 4L * (2L * floorSqrt(remaining) + 1);
            // End of a block/expression
            }
            // Loop: repeats a block
            for (int dx = 1; dx < radius; dx++) {
                // Calls a method
                final long dxSquared = (long) dx * dx;
                // Loop: repeats a block
                for (int dy = 1; dy < radius; dy++) {
                    // Calls a method
                    final long remaining = radiusSquared - dxSquared - (long) dy * dy;
                    // Branch: checks a condition
                    if (remaining < 0) break; // dy only grows; further values stay negative
                    // Calls a method
                    count += 4L * (2L * floorSqrt(remaining) + 1);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return count;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void emitSliceCuboids(List<Area.Cuboid> out, int[] slice, int count, int zStart, int zEnd) {
        // Loop: repeats a block
        for (int i = 0; i < count; i++) {
            // Assigns a value
            final int base = i * 4;
            // Code statement
            out.add(new AreaImpl.Cuboid(
                    // Creates a new object
                    new BlockVec(slice[base], slice[base + 2], zStart),
                    // Creates a new object
                    new BlockVec(slice[base + 1], slice[base + 3], zEnd)));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private static Cuboid buildRunCuboid(int startX, int startY, int startZ,
                                         // Start of a method/block
                                         int endX, int endY, int endZ) {
        // Calls a method
        final BlockVec startVec = new BlockVec(startX, startY, startZ);
        // Assigns a value
        final BlockVec endVec = (startX == endX && startY == endY && startZ == endZ)
                // Code statement
                ? startVec
                // Calls a method
                : new BlockVec(endX, endY, endZ);
        // Returns a value to the caller
        return new Cuboid(startVec, endVec);
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean withinSphereRadius(long dx, long dy, long dz, int radius) {
        // Branch: checks a condition
        if (dx < -radius || dx > radius || dy < -radius || dy > radius || dz < -radius || dz > radius) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return dx * dx + dy * dy + dz * dz <= (long) radius * radius;
    // End of a block/expression
    }

    // Code statement
    private static boolean canExtendAxisAlignedRun(int startX, int startY, int startZ,
                                                   // Code statement
                                                   int endX, int endY, int endZ,
                                                   // Start of a method/block
                                                   int nextX, int nextY, int nextZ) {
        // Assigns a value
        final boolean sameX = startX == endX && endX == nextX;
        // Assigns a value
        final boolean sameY = startY == endY && endY == nextY;
        // Assigns a value
        final boolean sameZ = startZ == endZ && endZ == nextZ;
        // Calls a method
        final boolean adjacentX = Math.abs(nextX - endX) == 1;
        // Calls a method
        final boolean adjacentY = Math.abs(nextY - endY) == 1;
        // Calls a method
        final boolean adjacentZ = Math.abs(nextZ - endZ) == 1;
        // Returns a value to the caller
        return sameY && sameZ && adjacentX || sameX && sameZ && adjacentY || sameX && sameY && adjacentZ;
    // End of a block/expression
    }

    // Code statement
    private static boolean sectionInsideSphere(int sectionMinX, int sectionMinY, int sectionMinZ,
                                               // Code statement
                                               int sectionMaxX, int sectionMaxY, int sectionMaxZ,
                                               // Start of a method/block
                                               int centerX, int centerY, int centerZ, int radius) {
        // Calls a method
        final long farX = Math.max(Math.abs((long) sectionMinX - centerX), Math.abs((long) sectionMaxX - centerX));
        // Calls a method
        final long farY = Math.max(Math.abs((long) sectionMinY - centerY), Math.abs((long) sectionMaxY - centerY));
        // Calls a method
        final long farZ = Math.max(Math.abs((long) sectionMinZ - centerZ), Math.abs((long) sectionMaxZ - centerZ));
        // Returns a value to the caller
        return withinSphereRadius(farX, farY, farZ, radius);
    // End of a block/expression
    }

    // Start of a method/block
    private static long floorSqrt(long value) {
        // Calls a method
        long sqrt = (long) Math.sqrt(value);
        // Math.sqrt may round up to an exact integer for value > 2^52; correct by one if so.
        // Branch: checks a condition
        if (sqrt > 0 && sqrt * sqrt > value) sqrt--;
        // Returns a value to the caller
        return sqrt;
    // End of a block/expression
    }
// End of a block/expression
}
