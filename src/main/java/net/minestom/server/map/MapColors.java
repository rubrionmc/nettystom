// Déclaration du paquet de ce fichier
package net.minestom.server.map;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
public enum MapColors {
    // Instruction de code
    NONE(0, 0, 0),
    // Instruction de code
    GRASS(127, 178, 56),
    // Instruction de code
    SAND(247, 233, 163),
    // Instruction de code
    WOOL(199, 199, 199),
    // Instruction de code
    FIRE(255, 0, 0),
    // Instruction de code
    ICE(160, 160, 255),
    // Instruction de code
    METAL(167, 167, 167),
    // Instruction de code
    PLANT(0, 124, 0),
    // Instruction de code
    SNOW(255, 255, 255),
    // Instruction de code
    CLAY(164, 168, 184),
    // Instruction de code
    DIRT(151, 109, 77),
    // Instruction de code
    STONE(112, 112, 112),
    // Instruction de code
    WATER(64, 64, 255),
    // Instruction de code
    WOOD(143, 119, 72),
    // Instruction de code
    QUARTZ(255, 252, 245),
    // Instruction de code
    COLOR_ORANGE(216, 127, 51),
    // Instruction de code
    COLOR_MAGENTA(178, 76, 216),
    // Instruction de code
    COLOR_LIGHT_BLUE(102, 153, 216),
    // Instruction de code
    COLOR_YELLOW(229, 229, 51),
    // Instruction de code
    COLOR_LIGHT_GREEN(127, 204, 25),
    // Instruction de code
    COLOR_PINK(242, 127, 165),
    // Instruction de code
    COLOR_GRAY(76, 76, 76),
    // Instruction de code
    COLOR_LIGHT_GRAY(153, 153, 153),
    // Instruction de code
    COLOR_CYAN(76, 127, 153),
    // Instruction de code
    COLOR_PURPLE(127, 63, 178),
    // Instruction de code
    COLOR_BLUE(51, 76, 178),
    // Instruction de code
    COLOR_BROWN(102, 76, 51),
    // Instruction de code
    COLOR_GREEN(102, 127, 51),
    // Instruction de code
    COLOR_RED(153, 51, 51),
    // Instruction de code
    COLOR_BLACK(25, 25, 25),
    // Instruction de code
    GOLD(250, 238, 77),
    // Instruction de code
    DIAMOND(92, 219, 213),
    // Instruction de code
    LAPIS(74, 128, 255),
    // Instruction de code
    EMERALD(0, 217, 58),
    // Instruction de code
    PODZOL(129, 86, 49),
    // Instruction de code
    NETHER(112, 2, 0),
    // Instruction de code
    TERRACOTTA_WHITE(209, 177, 161),
    // Instruction de code
    TERRACOTTA_ORANGE(159, 82, 36),
    // Instruction de code
    TERRACOTTA_MAGENTA(149, 87, 108),
    // Instruction de code
    TERRACOTTA_LIGHT_BLUE(112, 108, 138),
    // Instruction de code
    TERRACOTTA_YELLOW(186, 133, 36),
    // Instruction de code
    TERRACOTTA_LIGHT_GREEN(103, 117, 53),
    // Instruction de code
    TERRACOTTA_PINK(160, 77, 78),
    // Instruction de code
    TERRACOTTA_GRAY(57, 41, 35),
    // Instruction de code
    TERRACOTTA_LIGHT_GRAY(135, 107, 98),
    // Instruction de code
    TERRACOTTA_CYAN(87, 92, 92),
    // Instruction de code
    TERRACOTTA_PURPLE(122, 73, 88),
    // Instruction de code
    TERRACOTTA_BLUE(76, 62, 92),
    // Instruction de code
    TERRACOTTA_BROWN(76, 50, 35),
    // Instruction de code
    TERRACOTTA_GREEN(76, 82, 42),
    // Instruction de code
    TERRACOTTA_RED(142, 60, 46),
    // Instruction de code
    TERRACOTTA_BLACK(37, 22, 16),
    // Instruction de code
    CRIMSON_NYLIUM(189, 48, 49),
    // Instruction de code
    CRIMSON_STEM(148, 63, 97),
    // Instruction de code
    CRIMSON_HYPHAE(92, 25, 29),
    // Instruction de code
    WARPED_NYLIUM(22, 126, 134),
    // Instruction de code
    WARPED_STEM(58, 142, 140),
    // Instruction de code
    WARPED_HYPHAE(86, 44, 62),
    // Appelle une méthode
    WARPED_WART_BLOCK(20, 180, 133);

    // Instruction de code
    private final int red;
    // Instruction de code
    private final int green;
    // Instruction de code
    private final int blue;

    // Appelle une méthode
    private static final Logger logger = LoggerFactory.getLogger(MapColors.class);
    // Appelle une méthode
    private static final ConcurrentHashMap<Integer, PreciseMapColor> rgbMap = new ConcurrentHashMap<>();
    // only used if mappingStrategy == ColorMappingStrategy.PRECISE
    // Affecte une valeur
    private static volatile PreciseMapColor[] rgbArray = null;

    // Instruction de code
    private static final ColorMappingStrategy mappingStrategy;
    // Instruction de code
    private static final int colorReduction;

    // Début d'une méthode/d'un bloc
    static {
        // Instruction de code
        ColorMappingStrategy strategy;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            strategy = ColorMappingStrategy.valueOf(ServerFlag.MAP_RGB_MAPPING.toUpperCase());
        // Début d'une méthode/d'un bloc
        } catch (IllegalArgumentException e) {
            // Appelle une méthode
            logger.warn("Unknown color mapping strategy '{}', defaulting to LAZY.", ServerFlag.MAP_RGB_MAPPING);
            // Affecte une valeur
            strategy = ColorMappingStrategy.LAZY;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        mappingStrategy = strategy;

        // Affecte une valeur
        int reduction = 10;
        // Embranchement : vérifie une condition
        if (ServerFlag.MAP_RGB_REDUCTION != -1) {
            // Affecte une valeur
            reduction = ServerFlag.MAP_RGB_REDUCTION;

            // Embranchement : vérifie une condition
            if (reduction < 0 || reduction > 255) {
                // Appelle une méthode
                logger.warn("Reduction was found to be invalid: {}. Must in 0-255, defaulting to 10.", reduction);
                // Affecte une valeur
                reduction = 10;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        colorReduction = reduction;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    MapColors(int red, int green, int blue) {
        // Accès à l'objet courant/parent
        this.red = red;
        // Accès à l'objet courant/parent
        this.green = green;
        // Accès à l'objet courant/parent
        this.blue = blue;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public byte multiply53() {
        // Renvoie une valeur à l'appelant
        return (byte) ((ordinal() << 2) + 3);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the color index with RGB multiplied by 0.86, to use on a map
     */
    // Début d'une méthode/d'un bloc
    public byte multiply86() {
        // Renvoie une valeur à l'appelant
        return (byte) ((ordinal() << 2) + 1);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the color index with RGB multiplied by 0.71, to use on a map
     */
    // Début d'une méthode/d'un bloc
    public byte multiply71() {
        // Renvoie une valeur à l'appelant
        return (byte) (ordinal() << 2);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the color index to use on a map
     */
    // Début d'une méthode/d'un bloc
    public byte baseColor() {
        // Renvoie une valeur à l'appelant
        return (byte) ((ordinal() << 2) + 2);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int red() {
        // Renvoie une valeur à l'appelant
        return red;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int green() {
        // Renvoie une valeur à l'appelant
        return green;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int blue() {
        // Renvoie une valeur à l'appelant
        return blue;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void fillRGBMap() {
        // Boucle : répète un bloc
        for (MapColors base : values()) {
            // Embranchement : vérifie une condition
            if (base == NONE)
                // Passe à l'itération suivante de la boucle
                continue;
            // Boucle : répète un bloc
            for (Multiplier m : Multiplier.values()) {
                // Appelle une méthode
                PreciseMapColor preciseMapColor = new PreciseMapColor(base, m);
                // Appelle une méthode
                int rgb = preciseMapColor.toRGB();

                // Embranchement : vérifie une condition
                if (mappingStrategy == ColorMappingStrategy.APPROXIMATE) {
                    // Appelle une méthode
                    rgb = reduceColor(rgb);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                rgbMap.put(rgb, preciseMapColor);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void fillRGBArray() {
        // Affecte une valeur
        rgbArray = new PreciseMapColor[0xFFFFFF + 1];
        // Boucle : répète un bloc
        for (int rgb = 0; rgb <= 0xFFFFFF; rgb++) {
            // Appelle une méthode
            rgbArray[rgb] = mapColor(rgb);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static PreciseMapColor closestColor(int argb) {
        // Affecte une valeur
        int noAlpha = argb & 0xFFFFFF;
        // Embranchement : vérifie une condition
        if (mappingStrategy == ColorMappingStrategy.PRECISE) {
            // Embranchement : vérifie une condition
            if (rgbArray == null) {
                // Début d'une méthode/d'un bloc
                synchronized (MapColors.class) {
                    // Embranchement : vérifie une condition
                    if (rgbArray == null) {
                        // Appelle une méthode
                        fillRGBArray();
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return rgbArray[noAlpha];
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (rgbMap.isEmpty()) {
            // Début d'une méthode/d'un bloc
            synchronized (rgbMap) {
                // Embranchement : vérifie une condition
                if (rgbMap.isEmpty()) {
                    // Appelle une méthode
                    fillRGBMap();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (mappingStrategy == ColorMappingStrategy.APPROXIMATE) {
            // Appelle une méthode
            noAlpha = reduceColor(noAlpha);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return rgbMap.computeIfAbsent(noAlpha, MapColors::mapColor);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int reduceColor(int rgb) {
        // Appelle une méthode
        int red = (rgb >> 16) & 0xFF;
        // Appelle une méthode
        int green = (rgb >> 8) & 0xFF;
        // Affecte une valeur
        int blue = rgb & 0xFF;

        // Affecte une valeur
        red = red / colorReduction;
        // Affecte une valeur
        green = green / colorReduction;
        // Affecte une valeur
        blue = blue / colorReduction;
        // Renvoie une valeur à l'appelant
        return (red << 16) | (green << 8) | blue;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static PreciseMapColor mapColor(int rgb) {
        // Affecte une valeur
        PreciseMapColor closest = null;
        // Affecte une valeur
        int closestDistance = Integer.MAX_VALUE;
        // Boucle : répète un bloc
        for (MapColors base : values()) {
            // Embranchement : vérifie une condition
            if (base == NONE)
                // Passe à l'itération suivante de la boucle
                continue;
            // Boucle : répète un bloc
            for (Multiplier m : Multiplier.values()) {
                // Appelle une méthode
                final int rgbKey = PreciseMapColor.toRGB(base, m);
                // Appelle une méthode
                final int redKey = (rgbKey >> 16) & 0xFF;
                // Appelle une méthode
                final int greenKey = (rgbKey >> 8) & 0xFF;
                // Affecte une valeur
                final int blueKey = rgbKey & 0xFF;

                // Appelle une méthode
                final int red = (rgb >> 16) & 0xFF;
                // Appelle une méthode
                final int green = (rgb >> 8) & 0xFF;
                // Affecte une valeur
                final int blue = rgb & 0xFF;

                // Affecte une valeur
                final int dr = redKey - red;
                // Affecte une valeur
                final int dg = greenKey - green;
                // Affecte une valeur
                final int db = blueKey - blue;
                // Appelle une méthode
                final int dist = (dr * dr + dg * dg + db * db);
                // Embranchement : vérifie une condition
                if (dist < closestDistance) {
                    // Appelle une méthode
                    closest = new PreciseMapColor(base, m);
                    // Affecte une valeur
                    closestDistance = dist;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return closest;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public static class PreciseMapColor {
        // Instruction de code
        private final MapColors baseColor;
        // Instruction de code
        private final Multiplier multiplier;

        // Début d'une méthode/d'un bloc
        PreciseMapColor(MapColors base, Multiplier multiplier) {
            // Accès à l'objet courant/parent
            this.baseColor = base;
            // Accès à l'objet courant/parent
            this.multiplier = multiplier;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public MapColors getBaseColor() {
            // Renvoie une valeur à l'appelant
            return baseColor;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Multiplier getMultiplier() {
            // Renvoie une valeur à l'appelant
            return multiplier;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public byte getIndex() {
            // Renvoie une valeur à l'appelant
            return multiplier.apply(baseColor);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int toRGB() {
            // Renvoie une valeur à l'appelant
            return toRGB(baseColor, multiplier);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static int toRGB(MapColors baseColor, Multiplier multiplier) {
            // Appelle une méthode
            double r = baseColor.red();
            // Appelle une méthode
            double g = baseColor.green();
            // Appelle une méthode
            double b = baseColor.blue();

            // Appelle une méthode
            r *= multiplier.multiplier();
            // Appelle une méthode
            g *= multiplier.multiplier();
            // Appelle une méthode
            b *= multiplier.multiplier();

            // Appelle une méthode
            final int red = (int) r;
            // Appelle une méthode
            final int green = (int) g;
            // Appelle une méthode
            final int blue = (int) b;
            // Renvoie une valeur à l'appelant
            return (red << 16) | (green << 8) | blue;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Multiplier {
        // Instruction de code
        x1_00(MapColors::baseColor, 1.00),
        // Instruction de code
        x0_53(MapColors::multiply53, 0.53),
        // Instruction de code
        x0_71(MapColors::multiply71, 0.71),
        // Appelle une méthode
        x0_86(MapColors::multiply86, 0.86);

        // Instruction de code
        private final Function<MapColors, Byte> indexGetter;
        // Instruction de code
        private final double multiplier;

        // Début d'une méthode/d'un bloc
        Multiplier(Function<MapColors, Byte> indexGetter, double multiplier) {
            // Accès à l'objet courant/parent
            this.indexGetter = indexGetter;
            // Accès à l'objet courant/parent
            this.multiplier = multiplier;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double multiplier() {
            // Renvoie une valeur à l'appelant
            return multiplier;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public byte apply(MapColors baseColor) {
            // Renvoie une valeur à l'appelant
            return indexGetter.apply(baseColor);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * How does Minestom compute RGB to MapColor transitions?
     */
    // Déclaration de type (classe/interface/enum/record)
    public enum ColorMappingStrategy {
        /**
         * If already computed, send the result. Otherwise, compute the closest color in a RGB Map, and add it to the map
         */
        // Instruction de code
        LAZY,

        /**
         * All colors are already in the map after the first call. Heavy hit on the memory:
         * (2^24) * 4 bytes at the min (~64MB)
         */
        // Instruction de code
        PRECISE,

        /**
         * RGB components are divided by 10 before issuing a lookup (as with the PRECISE strategy), but saves on memory usage
         */
        // Instruction de code
        APPROXIMATE
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
