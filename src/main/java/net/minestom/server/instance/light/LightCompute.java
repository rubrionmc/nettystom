// Déclaration du paquet de ce fichier
package net.minestom.server.instance.light;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.shorts.ShortArrayFIFOQueue;
// Import d'une classe nécessaire
import net.minestom.server.collision.Shape;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.SECTION_BLOCK_COUNT;

// Déclaration de type (classe/interface/enum/record)
public final class LightCompute {
    // Appelle une méthode
    static final Direction[] DIRECTIONS = Direction.values();
    // Appelle une méthode
    static final BlockFace[] FACES = BlockFace.values();
    // Affecte une valeur
    static final int LIGHT_LENGTH = SECTION_BLOCK_COUNT / 2;
    // Affecte une valeur
    static final int SECTION_SIZE = 16;

    // Affecte une valeur
    public static final byte[] UNSET_CONTENT = new byte[0];
    // Affecte une valeur
    public static final byte[] EMPTY_CONTENT = new byte[LIGHT_LENGTH];
    // Affecte une valeur
    public static final byte[] CONTENT_FULLY_LIT = new byte[LIGHT_LENGTH];

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        Arrays.fill(CONTENT_FULLY_LIT, (byte) -1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static byte[] lazyArray(byte[] content) {
        // Embranchement : vérifie une condition
        if (content == null || content.length == 0) return EMPTY_CONTENT;
        // Embranchement : vérifie une condition
        else if (Arrays.equals(content, EMPTY_CONTENT)) return EMPTY_CONTENT;
        // Embranchement : vérifie une condition
        else if (Arrays.equals(content, CONTENT_FULLY_LIT)) return CONTENT_FULLY_LIT;
        // Branche alternative de la condition
        else return content.clone();
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static ShortArrayFIFOQueue buildExternalQueue(Palette blockPalette,
                                                  // Instruction de code
                                                  Point[] neighbors, byte[] content,
                                                  // Instruction de code
                                                  Light.LightLookup lightLookup,
                                                  // Début d'une méthode/d'un bloc
                                                  Light.PaletteLookup paletteLookup) {
        // Appelle une méthode
        ShortArrayFIFOQueue lightSources = new ShortArrayFIFOQueue();
        // Boucle : répète un bloc
        for (int i = 0; i < neighbors.length; i++) {
            // Affecte une valeur
            Point neighborSection = neighbors[i];
            // Embranchement : vérifie une condition
            if (neighborSection == null) continue;
            // Appelle une méthode
            Palette otherPalette = paletteLookup.palette(neighborSection.blockX(), neighborSection.blockY(), neighborSection.blockZ());
            // Embranchement : vérifie une condition
            if (otherPalette == null) continue;
            // Appelle une méthode
            Light otherLight = lightLookup.light(neighborSection.blockX(), neighborSection.blockY(), neighborSection.blockZ());
            // Embranchement : vérifie une condition
            if (otherLight == null) continue;

            // Affecte une valeur
            final BlockFace face = FACES[i];
            // Affecte une valeur
            final int k = switch (face) {
                // Embranchement multiple (switch/case)
                case WEST, BOTTOM, NORTH -> 0;
                // Embranchement multiple (switch/case)
                case EAST, TOP, SOUTH -> 15;
            // Fin d'un bloc/d'une expression
            };
            // Boucle : répète un bloc
            for (int bx = 0; bx < 16; bx++) {
                // Boucle : répète un bloc
                for (int by = 0; by < 16; by++) {
                    // Affecte une valeur
                    final byte lightEmission = (byte) Math.max(switch (face) {
                        // Embranchement multiple (switch/case)
                        case NORTH, SOUTH -> (byte) otherLight.getLevel(bx, by, 15 - k);
                        // Embranchement multiple (switch/case)
                        case WEST, EAST -> (byte) otherLight.getLevel(15 - k, bx, by);
                        // Appelle une méthode
                        default -> (byte) otherLight.getLevel(bx, 15 - k, by);
                    // Instruction de code
                    } - 1, 0);
                    // Embranchement : vérifie une condition
                    if (lightEmission <= 0) continue;

                    // Affecte une valeur
                    final int posTo = switch (face) {
                        // Embranchement multiple (switch/case)
                        case NORTH, SOUTH -> bx | (k << 4) | (by << 8);
                        // Embranchement multiple (switch/case)
                        case WEST, EAST -> k | (by << 4) | (bx << 8);
                        // Instruction de code
                        default -> bx | (by << 4) | (k << 8);
                    // Fin d'un bloc/d'une expression
                    };

                    // Embranchement : vérifie une condition
                    if (content != null) {
                        // Appelle une méthode
                        final int internalEmission = (byte) (Math.max(getLight(content, posTo) - 1, 0));
                        // Embranchement : vérifie une condition
                        if (lightEmission <= internalEmission) continue;
                    // Fin d'un bloc/d'une expression
                    }

                    // Affecte une valeur
                    final Block blockTo = switch (face) {
                        // Embranchement multiple (switch/case)
                        case NORTH, SOUTH -> getBlock(blockPalette, bx, by, k);
                        // Embranchement multiple (switch/case)
                        case WEST, EAST -> getBlock(blockPalette, k, bx, by);
                        // Appelle une méthode
                        default -> getBlock(blockPalette, bx, k, by);
                    // Fin d'un bloc/d'une expression
                    };

                    // Affecte une valeur
                    final Block blockFrom = switch (face) {
                        // Embranchement multiple (switch/case)
                        case NORTH, SOUTH -> getBlock(otherPalette, bx, by, 15 - k);
                        // Embranchement multiple (switch/case)
                        case WEST, EAST -> getBlock(otherPalette, 15 - k, bx, by);
                        // Appelle une méthode
                        default -> getBlock(otherPalette, bx, 15 - k, by);
                    // Fin d'un bloc/d'une expression
                    };

                    // Embranchement : vérifie une condition
                    if (blockTo == null && blockFrom != null) {
                        // Embranchement : vérifie une condition
                        if (blockFrom.registry().occlusionShape().isOccluded(Block.AIR.registry().occlusionShape(), face.getOppositeFace()))
                            // Passe à l'itération suivante de la boucle
                            continue;
                    // Embranchement : vérifie une condition
                    } else if (blockTo != null && blockFrom == null) {
                        // Embranchement : vérifie une condition
                        if (Block.AIR.registry().occlusionShape().isOccluded(blockTo.registry().occlusionShape(), face))
                            // Passe à l'itération suivante de la boucle
                            continue;
                    // Embranchement : vérifie une condition
                    } else if (blockTo != null && blockFrom != null) {
                        // Embranchement : vérifie une condition
                        if (blockFrom.registry().occlusionShape().isOccluded(blockTo.registry().occlusionShape(), face.getOppositeFace()))
                            // Passe à l'itération suivante de la boucle
                            continue;
                    // Fin d'un bloc/d'une expression
                    }

                    // Affecte une valeur
                    final int index = posTo | (lightEmission << 12);
                    // Appelle une méthode
                    lightSources.enqueue((short) index);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return lightSources;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    static byte [] compute(Palette blockPalette, ShortArrayFIFOQueue lightPre) {
        // Embranchement : vérifie une condition
        if (lightPre.isEmpty()) return EMPTY_CONTENT;

        // Affecte une valeur
        final byte[] lightArray = new byte[LIGHT_LENGTH];

        // Appelle une méthode
        final ShortArrayFIFOQueue lightSources = new ShortArrayFIFOQueue();

        // Boucle : répète un bloc
        while (!lightPre.isEmpty()) {
            // Appelle une méthode
            final int index = lightPre.dequeueShort();

            // Affecte une valeur
            final int newLightLevel = (index >> 12) & 15;
            // Affecte une valeur
            final int newIndex = index & 0xFFF;

            // Appelle une méthode
            final int oldLightLevel = getLight(lightArray, newIndex);

            // Embranchement : vérifie une condition
            if (oldLightLevel < newLightLevel) {
                // Appelle une méthode
                placeLight(lightArray, newIndex, newLightLevel);
                // Appelle une méthode
                lightSources.enqueue((short) index);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        while (!lightSources.isEmpty()) {
            // Appelle une méthode
            final int index = lightSources.dequeueShort();
            // Affecte une valeur
            final int x = index & 15;
            // Affecte une valeur
            final int z = (index >> 4) & 15;
            // Affecte une valeur
            final int y = (index >> 8) & 15;
            // Affecte une valeur
            final int lightLevel = (index >> 12) & 15;
            // Affecte une valeur
            final byte newLightLevel = (byte) (lightLevel - 1);

            // Boucle : répète un bloc
            for (Direction direction : DIRECTIONS) {
                // Appelle une méthode
                final int xO = x + direction.normalX();
                // Appelle une méthode
                final int yO = y + direction.normalY();
                // Appelle une méthode
                final int zO = z + direction.normalZ();

                // Handler border
                // Embranchement : vérifie une condition
                if (xO < 0 || xO >= SECTION_SIZE || yO < 0 || yO >= SECTION_SIZE || zO < 0 || zO >= SECTION_SIZE) {
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }

                // Section
                // Affecte une valeur
                final int newIndex = xO | (zO << 4) | (yO << 8);

                // Embranchement : vérifie une condition
                if (getLight(lightArray, newIndex) < newLightLevel) {
                    // Appelle une méthode
                    final Block currentBlock = Objects.requireNonNullElse(getBlock(blockPalette, x, y, z), Block.AIR);
                    // Appelle une méthode
                    final Block propagatedBlock = Objects.requireNonNullElse(getBlock(blockPalette, xO, yO, zO), Block.AIR);

                    // Appelle une méthode
                    final Shape currentShape = currentBlock.registry().occlusionShape();
                    // Appelle une méthode
                    final Shape propagatedShape = propagatedBlock.registry().occlusionShape();

                    // Appelle une méthode
                    final boolean airAir = currentBlock.isAir() && propagatedBlock.isAir();
                    // Embranchement : vérifie une condition
                    if (!airAir && currentShape.isOccluded(propagatedShape, BlockFace.fromDirection(direction)))
                        // Passe à l'itération suivante de la boucle
                        continue;

                    // Appelle une méthode
                    placeLight(lightArray, newIndex, newLightLevel);
                    // Appelle une méthode
                    lightSources.enqueue((short) (newIndex | (newLightLevel << 12)));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return lightArray;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void placeLight(byte[] light, int index, int value) {
        // Affecte une valeur
        final int shift = (index & 1) << 2;
        // Affecte une valeur
        final int i = index >>> 1;
        // Affecte une valeur
        light[i] = (byte) ((light[i] & (0xF0 >>> shift)) | (value << shift));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static int getLight(byte[] light, int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return getLight(light, x | (z << 4) | (y << 8));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static int getLight(byte[] light, int index) {
        // Embranchement : vérifie une condition
        if (index >>> 1 >= light.length) return 0;
        // Affecte une valeur
        final int value = light[index >>> 1];
        // Renvoie une valeur à l'appelant
        return ((value >>> ((index & 1) << 2)) & 0xF);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Block getBlock(Palette palette, int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return Block.fromStateId(palette.get(x, y, z));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static byte[] bake(byte[] content1, byte[] content2) {
        // Embranchement : vérifie une condition
        if (content1 == null && content2 == null) return EMPTY_CONTENT;
        // Embranchement : vérifie une condition
        if (content1 == EMPTY_CONTENT && content2 == EMPTY_CONTENT) return EMPTY_CONTENT;

        // Embranchement : vérifie une condition
        if (content1 == CONTENT_FULLY_LIT) return CONTENT_FULLY_LIT;
        // Embranchement : vérifie une condition
        if (content2 == CONTENT_FULLY_LIT) return CONTENT_FULLY_LIT;

        // Embranchement : vérifie une condition
        if (content1 == null) return content2;
        // Embranchement : vérifie une condition
        if (content2 == null) return content1;

        // Embranchement : vérifie une condition
        if (content1 == content2) return content1;

        // Embranchement : vérifie une condition
        if (Arrays.equals(content1, EMPTY_CONTENT) && Arrays.equals(content2, EMPTY_CONTENT)) return EMPTY_CONTENT;

        // Embranchement : vérifie une condition
        if (Arrays.equals(content1, CONTENT_FULLY_LIT)) return CONTENT_FULLY_LIT;
        // Embranchement : vérifie une condition
        if (Arrays.equals(content2, CONTENT_FULLY_LIT)) return CONTENT_FULLY_LIT;

        // Affecte une valeur
        byte[] lightMax = new byte[LIGHT_LENGTH];
        // Boucle : répète un bloc
        for (int i = 0; i < LIGHT_LENGTH; i++) {
            // Affecte une valeur
            final byte c1 = content1[i];
            // Affecte une valeur
            final byte c2 = content2[i];

            // Lower
            // Affecte une valeur
            final byte l1 = (byte) (c1 & 0x0F);
            // Affecte une valeur
            final byte l2 = (byte) (c2 & 0x0F);

            // Upper
            // Affecte une valeur
            final byte u1 = (byte) ((c1 >> 4) & 0x0F);
            // Affecte une valeur
            final byte u2 = (byte) ((c2 >> 4) & 0x0F);

            // Appelle une méthode
            final byte lower = (byte) Math.max(l1, l2);
            // Appelle une méthode
            final byte upper = (byte) Math.max(u1, u2);

            // Affecte une valeur
            lightMax[i] = (byte) (lower | (upper << 4));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return lightMax;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean compareBorders(byte[] content, byte[] contentPropagation, byte[] contentPropagationTemp, BlockFace face) {
        // Embranchement : vérifie une condition
        if (content == null && contentPropagation == null && contentPropagationTemp == null) return true;

        // Affecte une valeur
        final int k = switch (face) {
            // Embranchement multiple (switch/case)
            case WEST, BOTTOM, NORTH -> 0;
            // Embranchement multiple (switch/case)
            case EAST, TOP, SOUTH -> 15;
        // Fin d'un bloc/d'une expression
        };
        // Boucle : répète un bloc
        for (int bx = 0; bx < SECTION_SIZE; bx++) {
            // Boucle : répète un bloc
            for (int by = 0; by < SECTION_SIZE; by++) {
                // Affecte une valeur
                final int posFrom = switch (face) {
                    // Embranchement multiple (switch/case)
                    case NORTH, SOUTH -> bx | (k << 4) | (by << 8);
                    // Embranchement multiple (switch/case)
                    case WEST, EAST -> k | (by << 4) | (bx << 8);
                    // Instruction de code
                    default -> bx | (by << 4) | (k << 8);
                // Fin d'un bloc/d'une expression
                };

                // Instruction de code
                int valueFrom;
                // Embranchement : vérifie une condition
                if (content == null && contentPropagation == null) valueFrom = 0;
                // Embranchement : vérifie une condition
                else if (content != null && contentPropagation == null) valueFrom = getLight(content, posFrom);
                // Embranchement : vérifie une condition
                else if (content == null) valueFrom = getLight(contentPropagation, posFrom);
                // Branche alternative de la condition
                else valueFrom = Math.max(getLight(content, posFrom), getLight(contentPropagation, posFrom));

                // Appelle une méthode
                final int valueTo = getLight(contentPropagationTemp, posFrom);
                // Embranchement : vérifie une condition
                if (valueFrom < valueTo) return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
