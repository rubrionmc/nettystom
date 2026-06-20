// Package declaration for this file
package net.minestom.server.instance.light;

// Import of a required class
import it.unimi.dsi.fastutil.shorts.ShortArrayFIFOQueue;
// Import of a required class
import net.minestom.server.collision.Shape;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import net.minestom.server.utils.Direction;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Objects;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.SECTION_BLOCK_COUNT;

// Type declaration (class/interface/enum/record)
public final class LightCompute {
    // Calls a method
    static final Direction[] DIRECTIONS = Direction.values();
    // Calls a method
    static final BlockFace[] FACES = BlockFace.values();
    // Assigns a value
    static final int LIGHT_LENGTH = SECTION_BLOCK_COUNT / 2;
    // Assigns a value
    static final int SECTION_SIZE = 16;

    // Assigns a value
    public static final byte[] UNSET_CONTENT = new byte[0];
    // Assigns a value
    public static final byte[] EMPTY_CONTENT = new byte[LIGHT_LENGTH];
    // Assigns a value
    public static final byte[] CONTENT_FULLY_LIT = new byte[LIGHT_LENGTH];

    // Start of a method/block
    static {
        // Calls a method
        Arrays.fill(CONTENT_FULLY_LIT, (byte) -1);
    // End of a block/expression
    }

    // Start of a method/block
    static byte[] lazyArray(byte[] content) {
        // Branch: checks a condition
        if (content == null || content.length == 0) return EMPTY_CONTENT;
        // Branch: checks a condition
        else if (Arrays.equals(content, EMPTY_CONTENT)) return EMPTY_CONTENT;
        // Branch: checks a condition
        else if (Arrays.equals(content, CONTENT_FULLY_LIT)) return CONTENT_FULLY_LIT;
        // Alternative branch of the condition
        else return content.clone();
    // End of a block/expression
    }

    // Code statement
    static ShortArrayFIFOQueue buildExternalQueue(Palette blockPalette,
                                                  // Code statement
                                                  Point[] neighbors, byte[] content,
                                                  // Code statement
                                                  Light.LightLookup lightLookup,
                                                  // Start of a method/block
                                                  Light.PaletteLookup paletteLookup) {
        // Calls a method
        ShortArrayFIFOQueue lightSources = new ShortArrayFIFOQueue();
        // Loop: repeats a block
        for (int i = 0; i < neighbors.length; i++) {
            // Assigns a value
            Point neighborSection = neighbors[i];
            // Branch: checks a condition
            if (neighborSection == null) continue;
            // Calls a method
            Palette otherPalette = paletteLookup.palette(neighborSection.blockX(), neighborSection.blockY(), neighborSection.blockZ());
            // Branch: checks a condition
            if (otherPalette == null) continue;
            // Calls a method
            Light otherLight = lightLookup.light(neighborSection.blockX(), neighborSection.blockY(), neighborSection.blockZ());
            // Branch: checks a condition
            if (otherLight == null) continue;

            // Assigns a value
            final BlockFace face = FACES[i];
            // Assigns a value
            final int k = switch (face) {
                // Multiple branching (switch/case)
                case WEST, BOTTOM, NORTH -> 0;
                // Multiple branching (switch/case)
                case EAST, TOP, SOUTH -> 15;
            // End of a block/expression
            };
            // Loop: repeats a block
            for (int bx = 0; bx < 16; bx++) {
                // Loop: repeats a block
                for (int by = 0; by < 16; by++) {
                    // Assigns a value
                    final byte lightEmission = (byte) Math.max(switch (face) {
                        // Multiple branching (switch/case)
                        case NORTH, SOUTH -> (byte) otherLight.getLevel(bx, by, 15 - k);
                        // Multiple branching (switch/case)
                        case WEST, EAST -> (byte) otherLight.getLevel(15 - k, bx, by);
                        // Multiple branching (switch/case)
                        default -> (byte) otherLight.getLevel(bx, 15 - k, by);
                    // Code statement
                    } - 1, 0);
                    // Branch: checks a condition
                    if (lightEmission <= 0) continue;

                    // Assigns a value
                    final int posTo = switch (face) {
                        // Multiple branching (switch/case)
                        case NORTH, SOUTH -> bx | (k << 4) | (by << 8);
                        // Multiple branching (switch/case)
                        case WEST, EAST -> k | (by << 4) | (bx << 8);
                        // Multiple branching (switch/case)
                        default -> bx | (by << 4) | (k << 8);
                    // End of a block/expression
                    };

                    // Branch: checks a condition
                    if (content != null) {
                        // Calls a method
                        final int internalEmission = (byte) (Math.max(getLight(content, posTo) - 1, 0));
                        // Branch: checks a condition
                        if (lightEmission <= internalEmission) continue;
                    // End of a block/expression
                    }

                    // Assigns a value
                    final Block blockTo = switch (face) {
                        // Multiple branching (switch/case)
                        case NORTH, SOUTH -> getBlock(blockPalette, bx, by, k);
                        // Multiple branching (switch/case)
                        case WEST, EAST -> getBlock(blockPalette, k, bx, by);
                        // Multiple branching (switch/case)
                        default -> getBlock(blockPalette, bx, k, by);
                    // End of a block/expression
                    };

                    // Assigns a value
                    final Block blockFrom = switch (face) {
                        // Multiple branching (switch/case)
                        case NORTH, SOUTH -> getBlock(otherPalette, bx, by, 15 - k);
                        // Multiple branching (switch/case)
                        case WEST, EAST -> getBlock(otherPalette, 15 - k, bx, by);
                        // Multiple branching (switch/case)
                        default -> getBlock(otherPalette, bx, 15 - k, by);
                    // End of a block/expression
                    };

                    // Branch: checks a condition
                    if (blockTo == null && blockFrom != null) {
                        // Branch: checks a condition
                        if (blockFrom.registry().occlusionShape().isOccluded(Block.AIR.registry().occlusionShape(), face.getOppositeFace()))
                            // Continues to the next loop iteration
                            continue;
                    // Branch: checks a condition
                    } else if (blockTo != null && blockFrom == null) {
                        // Branch: checks a condition
                        if (Block.AIR.registry().occlusionShape().isOccluded(blockTo.registry().occlusionShape(), face))
                            // Continues to the next loop iteration
                            continue;
                    // Branch: checks a condition
                    } else if (blockTo != null && blockFrom != null) {
                        // Branch: checks a condition
                        if (blockFrom.registry().occlusionShape().isOccluded(blockTo.registry().occlusionShape(), face.getOppositeFace()))
                            // Continues to the next loop iteration
                            continue;
                    // End of a block/expression
                    }

                    // Calls a method
                    final int index = posTo | (lightEmission << 12);
                    // Calls a method
                    lightSources.enqueue((short) index);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return lightSources;
    // End of a block/expression
    }

    /**
     * Computes light in one section
     * <p>
     * Takes queue of lights positions and spreads light from this positions in 3d using Breadth-first search
     *
     * @param blockPalette blocks placed in section
     * @param lightPre     shorts queue in format: [4bit light level][4bit y][4bit z][4bit x]
     * @return lighting wrapped in Result
     */
    // Start of a method/block
    static byte [] compute(Palette blockPalette, ShortArrayFIFOQueue lightPre) {
        // Branch: checks a condition
        if (lightPre.isEmpty()) return EMPTY_CONTENT;

        // Assigns a value
        final byte[] lightArray = new byte[LIGHT_LENGTH];

        // Calls a method
        final ShortArrayFIFOQueue lightSources = new ShortArrayFIFOQueue();

        // Loop: repeats a block
        while (!lightPre.isEmpty()) {
            // Calls a method
            final int index = lightPre.dequeueShort();

            // Calls a method
            final int newLightLevel = (index >> 12) & 15;
            // Assigns a value
            final int newIndex = index & 0xFFF;

            // Calls a method
            final int oldLightLevel = getLight(lightArray, newIndex);

            // Branch: checks a condition
            if (oldLightLevel < newLightLevel) {
                // Calls a method
                placeLight(lightArray, newIndex, newLightLevel);
                // Calls a method
                lightSources.enqueue((short) index);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Loop: repeats a block
        while (!lightSources.isEmpty()) {
            // Calls a method
            final int index = lightSources.dequeueShort();
            // Assigns a value
            final int x = index & 15;
            // Calls a method
            final int z = (index >> 4) & 15;
            // Calls a method
            final int y = (index >> 8) & 15;
            // Calls a method
            final int lightLevel = (index >> 12) & 15;
            // Calls a method
            final byte newLightLevel = (byte) (lightLevel - 1);

            // Loop: repeats a block
            for (Direction direction : DIRECTIONS) {
                // Calls a method
                final int xO = x + direction.normalX();
                // Calls a method
                final int yO = y + direction.normalY();
                // Calls a method
                final int zO = z + direction.normalZ();

                // Handler border
                // Branch: checks a condition
                if (xO < 0 || xO >= SECTION_SIZE || yO < 0 || yO >= SECTION_SIZE || zO < 0 || zO >= SECTION_SIZE) {
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }

                // Section
                // Calls a method
                final int newIndex = xO | (zO << 4) | (yO << 8);

                // Branch: checks a condition
                if (getLight(lightArray, newIndex) < newLightLevel) {
                    // Calls a method
                    final Block currentBlock = Objects.requireNonNullElse(getBlock(blockPalette, x, y, z), Block.AIR);
                    // Calls a method
                    final Block propagatedBlock = Objects.requireNonNullElse(getBlock(blockPalette, xO, yO, zO), Block.AIR);

                    // Calls a method
                    final Shape currentShape = currentBlock.registry().occlusionShape();
                    // Calls a method
                    final Shape propagatedShape = propagatedBlock.registry().occlusionShape();

                    // Calls a method
                    final boolean airAir = currentBlock.isAir() && propagatedBlock.isAir();
                    // Branch: checks a condition
                    if (!airAir && currentShape.isOccluded(propagatedShape, BlockFace.fromDirection(direction)))
                        // Continues to the next loop iteration
                        continue;

                    // Calls a method
                    placeLight(lightArray, newIndex, newLightLevel);
                    // Calls a method
                    lightSources.enqueue((short) (newIndex | (newLightLevel << 12)));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return lightArray;
    // End of a block/expression
    }

    // Start of a method/block
    private static void placeLight(byte[] light, int index, int value) {
        // Calls a method
        final int shift = (index & 1) << 2;
        // Assigns a value
        final int i = index >>> 1;
        // Calls a method
        light[i] = (byte) ((light[i] & (0xF0 >>> shift)) | (value << shift));
    // End of a block/expression
    }

    // Start of a method/block
    static int getLight(byte[] light, int x, int y, int z) {
        // Returns a value to the caller
        return getLight(light, x | (z << 4) | (y << 8));
    // End of a block/expression
    }

    // Start of a method/block
    static int getLight(byte[] light, int index) {
        // Branch: checks a condition
        if (index >>> 1 >= light.length) return 0;
        // Assigns a value
        final int value = light[index >>> 1];
        // Returns a value to the caller
        return ((value >>> ((index & 1) << 2)) & 0xF);
    // End of a block/expression
    }

    // Start of a method/block
    public static Block getBlock(Palette palette, int x, int y, int z) {
        // Returns a value to the caller
        return Block.fromStateId(palette.get(x, y, z));
    // End of a block/expression
    }

    // Start of a method/block
    public static byte[] bake(byte[] content1, byte[] content2) {
        // Branch: checks a condition
        if (content1 == null && content2 == null) return EMPTY_CONTENT;
        // Branch: checks a condition
        if (content1 == EMPTY_CONTENT && content2 == EMPTY_CONTENT) return EMPTY_CONTENT;

        // Branch: checks a condition
        if (content1 == CONTENT_FULLY_LIT) return CONTENT_FULLY_LIT;
        // Branch: checks a condition
        if (content2 == CONTENT_FULLY_LIT) return CONTENT_FULLY_LIT;

        // Branch: checks a condition
        if (content1 == null) return content2;
        // Branch: checks a condition
        if (content2 == null) return content1;

        // Branch: checks a condition
        if (content1 == content2) return content1;

        // Branch: checks a condition
        if (Arrays.equals(content1, EMPTY_CONTENT) && Arrays.equals(content2, EMPTY_CONTENT)) return EMPTY_CONTENT;

        // Branch: checks a condition
        if (Arrays.equals(content1, CONTENT_FULLY_LIT)) return CONTENT_FULLY_LIT;
        // Branch: checks a condition
        if (Arrays.equals(content2, CONTENT_FULLY_LIT)) return CONTENT_FULLY_LIT;

        // Assigns a value
        byte[] lightMax = new byte[LIGHT_LENGTH];
        // Loop: repeats a block
        for (int i = 0; i < LIGHT_LENGTH; i++) {
            // Assigns a value
            final byte c1 = content1[i];
            // Assigns a value
            final byte c2 = content2[i];

            // Lower
            // Calls a method
            final byte l1 = (byte) (c1 & 0x0F);
            // Calls a method
            final byte l2 = (byte) (c2 & 0x0F);

            // Upper
            // Calls a method
            final byte u1 = (byte) ((c1 >> 4) & 0x0F);
            // Calls a method
            final byte u2 = (byte) ((c2 >> 4) & 0x0F);

            // Calls a method
            final byte lower = (byte) Math.max(l1, l2);
            // Calls a method
            final byte upper = (byte) Math.max(u1, u2);

            // Calls a method
            lightMax[i] = (byte) (lower | (upper << 4));
        // End of a block/expression
        }
        // Returns a value to the caller
        return lightMax;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean compareBorders(byte[] content, byte[] contentPropagation, byte[] contentPropagationTemp, BlockFace face) {
        // Branch: checks a condition
        if (content == null && contentPropagation == null && contentPropagationTemp == null) return true;

        // Assigns a value
        final int k = switch (face) {
            // Multiple branching (switch/case)
            case WEST, BOTTOM, NORTH -> 0;
            // Multiple branching (switch/case)
            case EAST, TOP, SOUTH -> 15;
        // End of a block/expression
        };
        // Loop: repeats a block
        for (int bx = 0; bx < SECTION_SIZE; bx++) {
            // Loop: repeats a block
            for (int by = 0; by < SECTION_SIZE; by++) {
                // Assigns a value
                final int posFrom = switch (face) {
                    // Multiple branching (switch/case)
                    case NORTH, SOUTH -> bx | (k << 4) | (by << 8);
                    // Multiple branching (switch/case)
                    case WEST, EAST -> k | (by << 4) | (bx << 8);
                    // Multiple branching (switch/case)
                    default -> bx | (by << 4) | (k << 8);
                // End of a block/expression
                };

                // Code statement
                int valueFrom;
                // Branch: checks a condition
                if (content == null && contentPropagation == null) valueFrom = 0;
                // Branch: checks a condition
                else if (content != null && contentPropagation == null) valueFrom = getLight(content, posFrom);
                // Branch: checks a condition
                else if (content == null) valueFrom = getLight(contentPropagation, posFrom);
                // Alternative branch of the condition
                else valueFrom = Math.max(getLight(content, posFrom), getLight(contentPropagation, posFrom));

                // Calls a method
                final int valueTo = getLight(contentPropagationTemp, posFrom);
                // Branch: checks a condition
                if (valueFrom < valueTo) return false;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }
// End of a block/expression
}
