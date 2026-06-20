// Package declaration for this file
package net.minestom.server.map;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
public enum MapColors {
    // Code statement
    NONE(0, 0, 0),
    // Code statement
    GRASS(127, 178, 56),
    // Code statement
    SAND(247, 233, 163),
    // Code statement
    WOOL(199, 199, 199),
    // Code statement
    FIRE(255, 0, 0),
    // Code statement
    ICE(160, 160, 255),
    // Code statement
    METAL(167, 167, 167),
    // Code statement
    PLANT(0, 124, 0),
    // Code statement
    SNOW(255, 255, 255),
    // Code statement
    CLAY(164, 168, 184),
    // Code statement
    DIRT(151, 109, 77),
    // Code statement
    STONE(112, 112, 112),
    // Code statement
    WATER(64, 64, 255),
    // Code statement
    WOOD(143, 119, 72),
    // Code statement
    QUARTZ(255, 252, 245),
    // Code statement
    COLOR_ORANGE(216, 127, 51),
    // Code statement
    COLOR_MAGENTA(178, 76, 216),
    // Code statement
    COLOR_LIGHT_BLUE(102, 153, 216),
    // Code statement
    COLOR_YELLOW(229, 229, 51),
    // Code statement
    COLOR_LIGHT_GREEN(127, 204, 25),
    // Code statement
    COLOR_PINK(242, 127, 165),
    // Code statement
    COLOR_GRAY(76, 76, 76),
    // Code statement
    COLOR_LIGHT_GRAY(153, 153, 153),
    // Code statement
    COLOR_CYAN(76, 127, 153),
    // Code statement
    COLOR_PURPLE(127, 63, 178),
    // Code statement
    COLOR_BLUE(51, 76, 178),
    // Code statement
    COLOR_BROWN(102, 76, 51),
    // Code statement
    COLOR_GREEN(102, 127, 51),
    // Code statement
    COLOR_RED(153, 51, 51),
    // Code statement
    COLOR_BLACK(25, 25, 25),
    // Code statement
    GOLD(250, 238, 77),
    // Code statement
    DIAMOND(92, 219, 213),
    // Code statement
    LAPIS(74, 128, 255),
    // Code statement
    EMERALD(0, 217, 58),
    // Code statement
    PODZOL(129, 86, 49),
    // Code statement
    NETHER(112, 2, 0),
    // Code statement
    TERRACOTTA_WHITE(209, 177, 161),
    // Code statement
    TERRACOTTA_ORANGE(159, 82, 36),
    // Code statement
    TERRACOTTA_MAGENTA(149, 87, 108),
    // Code statement
    TERRACOTTA_LIGHT_BLUE(112, 108, 138),
    // Code statement
    TERRACOTTA_YELLOW(186, 133, 36),
    // Code statement
    TERRACOTTA_LIGHT_GREEN(103, 117, 53),
    // Code statement
    TERRACOTTA_PINK(160, 77, 78),
    // Code statement
    TERRACOTTA_GRAY(57, 41, 35),
    // Code statement
    TERRACOTTA_LIGHT_GRAY(135, 107, 98),
    // Code statement
    TERRACOTTA_CYAN(87, 92, 92),
    // Code statement
    TERRACOTTA_PURPLE(122, 73, 88),
    // Code statement
    TERRACOTTA_BLUE(76, 62, 92),
    // Code statement
    TERRACOTTA_BROWN(76, 50, 35),
    // Code statement
    TERRACOTTA_GREEN(76, 82, 42),
    // Code statement
    TERRACOTTA_RED(142, 60, 46),
    // Code statement
    TERRACOTTA_BLACK(37, 22, 16),
    // Code statement
    CRIMSON_NYLIUM(189, 48, 49),
    // Code statement
    CRIMSON_STEM(148, 63, 97),
    // Code statement
    CRIMSON_HYPHAE(92, 25, 29),
    // Code statement
    WARPED_NYLIUM(22, 126, 134),
    // Code statement
    WARPED_STEM(58, 142, 140),
    // Code statement
    WARPED_HYPHAE(86, 44, 62),
    // Calls a method
    WARPED_WART_BLOCK(20, 180, 133);

    // Code statement
    private final int red;
    // Code statement
    private final int green;
    // Code statement
    private final int blue;

    // Calls a method
    private static final Logger logger = LoggerFactory.getLogger(MapColors.class);
    // Calls a method
    private static final ConcurrentHashMap<Integer, PreciseMapColor> rgbMap = new ConcurrentHashMap<>();
    // only used if mappingStrategy == ColorMappingStrategy.PRECISE
    // Assigns a value
    private static volatile PreciseMapColor[] rgbArray = null;

    // Code statement
    private static final ColorMappingStrategy mappingStrategy;
    // Code statement
    private static final int colorReduction;

    // Start of a method/block
    static {
        // Code statement
        ColorMappingStrategy strategy;
        // Exception handling
        try {
            // Calls a method
            strategy = ColorMappingStrategy.valueOf(ServerFlag.MAP_RGB_MAPPING.toUpperCase());
        // Start of a method/block
        } catch (IllegalArgumentException e) {
            // Calls a method
            logger.warn("Unknown color mapping strategy '{}', defaulting to LAZY.", ServerFlag.MAP_RGB_MAPPING);
            // Assigns a value
            strategy = ColorMappingStrategy.LAZY;
        // End of a block/expression
        }
        // Assigns a value
        mappingStrategy = strategy;

        // Assigns a value
        int reduction = 10;
        // Branch: checks a condition
        if (ServerFlag.MAP_RGB_REDUCTION != -1) {
            // Assigns a value
            reduction = ServerFlag.MAP_RGB_REDUCTION;

            // Branch: checks a condition
            if (reduction < 0 || reduction > 255) {
                // Calls a method
                logger.warn("Reduction was found to be invalid: {}. Must in 0-255, defaulting to 10.", reduction);
                // Assigns a value
                reduction = 10;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Assigns a value
        colorReduction = reduction;
    // End of a block/expression
    }

    // Start of a method/block
    MapColors(int red, int green, int blue) {
        // Access to the current/parent object
        this.red = red;
        // Access to the current/parent object
        this.green = green;
        // Access to the current/parent object
        this.blue = blue;
    // End of a block/expression
    }

    // From the wiki: https://minecraft.wiki/w/Map_item_format
    // Map Color ID 	Multiply R,G,B By 	= Multiplier
    //Base Color ID*4 + 0 	180 	0.71
    //Base Color ID*4 + 1 	220 	0.86
    //Base Color ID*4 + 2 	255 (same color) 	1
    //Base Color ID*4 + 3 	135 	0.53

    /**
     * Returns the color index with RGB multiplied by 0.53, to use on a map
     */
    // Start of a method/block
    public byte multiply53() {
        // Returns a value to the caller
        return (byte) ((ordinal() << 2) + 3);
    // End of a block/expression
    }

    /**
     * Returns the color index with RGB multiplied by 0.86, to use on a map
     */
    // Start of a method/block
    public byte multiply86() {
        // Returns a value to the caller
        return (byte) ((ordinal() << 2) + 1);
    // End of a block/expression
    }

    /**
     * Returns the color index with RGB multiplied by 0.71, to use on a map
     */
    // Start of a method/block
    public byte multiply71() {
        // Returns a value to the caller
        return (byte) (ordinal() << 2);
    // End of a block/expression
    }

    /**
     * Returns the color index to use on a map
     */
    // Start of a method/block
    public byte baseColor() {
        // Returns a value to the caller
        return (byte) ((ordinal() << 2) + 2);
    // End of a block/expression
    }

    // Start of a method/block
    public int red() {
        // Returns a value to the caller
        return red;
    // End of a block/expression
    }

    // Start of a method/block
    public int green() {
        // Returns a value to the caller
        return green;
    // End of a block/expression
    }

    // Start of a method/block
    public int blue() {
        // Returns a value to the caller
        return blue;
    // End of a block/expression
    }

    // Start of a method/block
    private static void fillRGBMap() {
        // Loop: repeats a block
        for (MapColors base : values()) {
            // Branch: checks a condition
            if (base == NONE)
                // Continues to the next loop iteration
                continue;
            // Loop: repeats a block
            for (Multiplier m : Multiplier.values()) {
                // Calls a method
                PreciseMapColor preciseMapColor = new PreciseMapColor(base, m);
                // Calls a method
                int rgb = preciseMapColor.toRGB();

                // Branch: checks a condition
                if (mappingStrategy == ColorMappingStrategy.APPROXIMATE) {
                    // Calls a method
                    rgb = reduceColor(rgb);
                // End of a block/expression
                }
                // Calls a method
                rgbMap.put(rgb, preciseMapColor);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void fillRGBArray() {
        // Assigns a value
        rgbArray = new PreciseMapColor[0xFFFFFF + 1];
        // Loop: repeats a block
        for (int rgb = 0; rgb <= 0xFFFFFF; rgb++) {
            // Calls a method
            rgbArray[rgb] = mapColor(rgb);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static PreciseMapColor closestColor(int argb) {
        // Assigns a value
        int noAlpha = argb & 0xFFFFFF;
        // Branch: checks a condition
        if (mappingStrategy == ColorMappingStrategy.PRECISE) {
            // Branch: checks a condition
            if (rgbArray == null) {
                // Start of a method/block
                synchronized (MapColors.class) {
                    // Branch: checks a condition
                    if (rgbArray == null) {
                        // Calls a method
                        fillRGBArray();
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return rgbArray[noAlpha];
        // End of a block/expression
        }
        // Branch: checks a condition
        if (rgbMap.isEmpty()) {
            // Start of a method/block
            synchronized (rgbMap) {
                // Branch: checks a condition
                if (rgbMap.isEmpty()) {
                    // Calls a method
                    fillRGBMap();
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (mappingStrategy == ColorMappingStrategy.APPROXIMATE) {
            // Calls a method
            noAlpha = reduceColor(noAlpha);
        // End of a block/expression
        }
        // Returns a value to the caller
        return rgbMap.computeIfAbsent(noAlpha, MapColors::mapColor);
    // End of a block/expression
    }

    // Start of a method/block
    private static int reduceColor(int rgb) {
        // Calls a method
        int red = (rgb >> 16) & 0xFF;
        // Calls a method
        int green = (rgb >> 8) & 0xFF;
        // Assigns a value
        int blue = rgb & 0xFF;

        // Assigns a value
        red = red / colorReduction;
        // Assigns a value
        green = green / colorReduction;
        // Assigns a value
        blue = blue / colorReduction;
        // Returns a value to the caller
        return (red << 16) | (green << 8) | blue;
    // End of a block/expression
    }

    // Start of a method/block
    private static PreciseMapColor mapColor(int rgb) {
        // Assigns a value
        PreciseMapColor closest = null;
        // Assigns a value
        int closestDistance = Integer.MAX_VALUE;
        // Loop: repeats a block
        for (MapColors base : values()) {
            // Branch: checks a condition
            if (base == NONE)
                // Continues to the next loop iteration
                continue;
            // Loop: repeats a block
            for (Multiplier m : Multiplier.values()) {
                // Calls a method
                final int rgbKey = PreciseMapColor.toRGB(base, m);
                // Calls a method
                final int redKey = (rgbKey >> 16) & 0xFF;
                // Calls a method
                final int greenKey = (rgbKey >> 8) & 0xFF;
                // Assigns a value
                final int blueKey = rgbKey & 0xFF;

                // Calls a method
                final int red = (rgb >> 16) & 0xFF;
                // Calls a method
                final int green = (rgb >> 8) & 0xFF;
                // Assigns a value
                final int blue = rgb & 0xFF;

                // Assigns a value
                final int dr = redKey - red;
                // Assigns a value
                final int dg = greenKey - green;
                // Assigns a value
                final int db = blueKey - blue;
                // Calls a method
                final int dist = (dr * dr + dg * dg + db * db);
                // Branch: checks a condition
                if (dist < closestDistance) {
                    // Calls a method
                    closest = new PreciseMapColor(base, m);
                    // Assigns a value
                    closestDistance = dist;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return closest;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public static class PreciseMapColor {
        // Code statement
        private final MapColors baseColor;
        // Code statement
        private final Multiplier multiplier;

        // Start of a method/block
        PreciseMapColor(MapColors base, Multiplier multiplier) {
            // Access to the current/parent object
            this.baseColor = base;
            // Access to the current/parent object
            this.multiplier = multiplier;
        // End of a block/expression
        }

        // Start of a method/block
        public MapColors getBaseColor() {
            // Returns a value to the caller
            return baseColor;
        // End of a block/expression
        }

        // Start of a method/block
        public Multiplier getMultiplier() {
            // Returns a value to the caller
            return multiplier;
        // End of a block/expression
        }

        // Start of a method/block
        public byte getIndex() {
            // Returns a value to the caller
            return multiplier.apply(baseColor);
        // End of a block/expression
        }

        // Start of a method/block
        public int toRGB() {
            // Returns a value to the caller
            return toRGB(baseColor, multiplier);
        // End of a block/expression
        }

        // Start of a method/block
        public static int toRGB(MapColors baseColor, Multiplier multiplier) {
            // Calls a method
            double r = baseColor.red();
            // Calls a method
            double g = baseColor.green();
            // Calls a method
            double b = baseColor.blue();

            // Calls a method
            r *= multiplier.multiplier();
            // Calls a method
            g *= multiplier.multiplier();
            // Calls a method
            b *= multiplier.multiplier();

            // Calls a method
            final int red = (int) r;
            // Calls a method
            final int green = (int) g;
            // Calls a method
            final int blue = (int) b;
            // Returns a value to the caller
            return (red << 16) | (green << 8) | blue;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Multiplier {
        // Code statement
        x1_00(MapColors::baseColor, 1.00),
        // Code statement
        x0_53(MapColors::multiply53, 0.53),
        // Code statement
        x0_71(MapColors::multiply71, 0.71),
        // Calls a method
        x0_86(MapColors::multiply86, 0.86);

        // Code statement
        private final Function<MapColors, Byte> indexGetter;
        // Code statement
        private final double multiplier;

        // Start of a method/block
        Multiplier(Function<MapColors, Byte> indexGetter, double multiplier) {
            // Access to the current/parent object
            this.indexGetter = indexGetter;
            // Access to the current/parent object
            this.multiplier = multiplier;
        // End of a block/expression
        }

        // Start of a method/block
        public double multiplier() {
            // Returns a value to the caller
            return multiplier;
        // End of a block/expression
        }

        // Start of a method/block
        public byte apply(MapColors baseColor) {
            // Returns a value to the caller
            return indexGetter.apply(baseColor);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * How does Minestom compute RGB to MapColor transitions?
     */
    // Type declaration (class/interface/enum/record)
    public enum ColorMappingStrategy {
        /**
         * If already computed, send the result. Otherwise, compute the closest color in a RGB Map, and add it to the map
         */
        // Code statement
        LAZY,

        /**
         * All colors are already in the map after the first call. Heavy hit on the memory:
         * (2^24) * 4 bytes at the min (~64MB)
         */
        // Code statement
        PRECISE,

        /**
         * RGB components are divided by 10 before issuing a lookup (as with the PRECISE strategy), but saves on memory usage
         */
        // Code statement
        APPROXIMATE
    // End of a block/expression
    }
// End of a block/expression
}
