// Déclaration du paquet de ce fichier
package net.minestom.server.color;

// Import d'une classe nécessaire
import net.kyori.adventure.text.format.ShadowColor;
// Import d'une classe nécessaire
import net.kyori.adventure.util.ARGBLike;
// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.intellij.lang.annotations.Pattern;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.HexFormat;
// Import d'une classe nécessaire
import java.util.Objects;

/**
 * A general purpose class for representing colors.
 * <p>
 * Colors must be in the range of 0-255.
 */
// Déclaration de type (classe/interface/enum/record)
public final class AlphaColor extends Color implements ARGBLike {
    // Affecte une valeur
    private static final int BIT_MASK = 0xff;

    // Affecte une valeur
    public static final NetworkBuffer.Type<ARGBLike> NETWORK_TYPE = NetworkBuffer.INT.transform(
            // Appelle une méthode
            AlphaColor::new, color -> fromARGBLike(color).asARGB());

    // Affecte une valeur
    public static final Codec<ARGBLike> CODEC = Codec.INT.<ARGBLike>transform(AlphaColor::new, color -> fromARGBLike(color).asARGB())
            // Appelle une méthode
            .orElse(Codec.FLOAT.list(4), floats -> new AlphaColor(floats.get(3), floats.get(0), floats.get(1), floats.get(2)));

    /**
     * Use {@link AlphaColor#RGBA_STRING_CODEC} or {@link AlphaColor#ARGB_STRING_CODEC} instead.
     * This codec uses RGBA.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Affecte une valeur
    public static final Codec<ARGBLike> STRING_CODEC = Codec.STRING.transform(
            // Instruction de code
            hex -> (ARGBLike) Objects.requireNonNull(ShadowColor.fromHexString(hex)),
            // Appelle une méthode
            color -> ShadowColor.shadowColor(color).asHexString()).orElse(CODEC);

    // Affecte une valeur
    public static final Codec<ARGBLike> RGBA_STRING_CODEC = Codec.STRING.transform(
            // Instruction de code
            hex -> (ARGBLike) Objects.requireNonNull(fromRGBAHexString(hex)),
            // Appelle une méthode
            color -> String.format("#%08X", AlphaColor.fromARGBLike(color).asRGBA())).orElse(CODEC);

    // Affecte une valeur
    public static final Codec<ARGBLike> ARGB_STRING_CODEC = Codec.STRING.transform(
            // Instruction de code
            hex -> (ARGBLike) Objects.requireNonNull(fromARGBHexString(hex)),
            // Appelle une méthode
            color -> String.format("#%08X", AlphaColor.fromARGBLike(color).asARGB())).orElse(CODEC);

    // Appelle une méthode
    public static final AlphaColor WHITE = new AlphaColor(255, 255, 255, 255);
    // Appelle une méthode
    public static final AlphaColor BLACK = new AlphaColor(255, 0, 0, 0);
    // Appelle une méthode
    public static final AlphaColor TRANSPARENT = new AlphaColor(0, 0, 0, 0);

    // Début d'une méthode/d'un bloc
    public static AlphaColor fromARGBLike(ARGBLike argbLike) {
        // Embranchement : vérifie une condition
        if (argbLike instanceof AlphaColor alphaColor) return alphaColor;
        // Renvoie une valeur à l'appelant
        return new AlphaColor(argbLike.alpha(), argbLike.red(), argbLike.green(), argbLike.blue());
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final int alpha;

    // Début d'une méthode/d'un bloc
    public AlphaColor(float alpha, float red, float green, float blue) {
        // Appelle une méthode
        this((int) (alpha * 255), (int) (red * 255), (int) (green * 255), (int) (blue * 255));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AlphaColor(int alpha, int red, int green, int blue) {
        // Accès à l'objet courant/parent
        super(red, green, blue);
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(alpha, 0, 255), "Alpha is not between 0-255: {0}", alpha);
        // Accès à l'objet courant/parent
        this.alpha = alpha;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an alpha color from an integer. This is done by reading each color component
     * from the lowest order 32 bits of the integer, and creating a color from those
     * components.
     *
     * @param argb the integer
     */
    // Début d'une méthode/d'un bloc
    public AlphaColor(int argb) {
        // Appelle une méthode
        this((argb >> 24) & BIT_MASK, (argb >> 16) & BIT_MASK, (argb >> 8) & BIT_MASK, argb & BIT_MASK);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a color from an RGB-like color.
     *
     * @param rgbLike the color
     */
    // Début d'une méthode/d'un bloc
    public AlphaColor(int alpha, RGBLike rgbLike) {
        // Appelle une méthode
        this(alpha, rgbLike.red(), rgbLike.green(), rgbLike.blue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public AlphaColor withRed(int red) {
        // Renvoie une valeur à l'appelant
        return new AlphaColor(alpha(), red, green(), blue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public AlphaColor withGreen(int green) {
        // Renvoie une valeur à l'appelant
        return new AlphaColor(alpha(), red(), green, blue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public AlphaColor withBlue(int blue) {
        // Renvoie une valeur à l'appelant
        return new AlphaColor(alpha(), red(), green(), blue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AlphaColor withAlpha(int alpha) {
        // Renvoie une valeur à l'appelant
        return new AlphaColor(alpha, red(), green(), blue());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the color as an ARGB integer.
     *
     * @return An integer representation of this color, as 0xAARRGGBB
     */
    // Début d'une méthode/d'un bloc
    public int asARGB() {
        // Renvoie une valeur à l'appelant
        return (alpha << 24) + asRGB();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the color as an RGBA integer.
     *
     * @return An integer representation of this color, as 0xRRGGBBAA
     */
    // Début d'une méthode/d'un bloc
    public int asRGBA() {
        // Renvoie une valeur à l'appelant
        return (asRGB() << 8) + alpha;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempt to parse a color from a {@code #}-prefixed hex string.
     * <p>
     * This string must be in the format {@code #RRGGBBAA}.
     *
     * @param hexRGBA the input value
     * @return a color if possible, or null if any components are invalid
     */
    // Début d'une méthode/d'un bloc
    public static @Nullable AlphaColor fromRGBAHexString(@Pattern("#[0-9a-fA-F]{8}") final String hexRGBA) {
        // Embranchement : vérifie une condition
        if (hexRGBA.length() != 9) return null;
        // Embranchement : vérifie une condition
        if (!hexRGBA.startsWith("#")) return null;

        // Gestion des exceptions
        try {
            // Appelle une méthode
            int rgba = HexFormat.fromHexDigits(hexRGBA, 1, 9);
            // Appelle une méthode
            int argb = Integer.rotateRight(rgba, 8);
            // Renvoie une valeur à l'appelant
            return new AlphaColor(argb);
        // Début d'une méthode/d'un bloc
        } catch (NumberFormatException _) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempt to parse a color from a {@code #}-prefixed hex string.
     * <p>
     * This string must be in the format {@code #AARRGGBB}.
     *
     * @param hexARGB the input value
     * @return a color if possible, or null if any components are invalid
     */
    // Début d'une méthode/d'un bloc
    public static @Nullable AlphaColor fromARGBHexString(@Pattern("#[0-9a-fA-F]{8}") final String hexARGB) {
        // Embranchement : vérifie une condition
        if (hexARGB.length() != 9) return null;
        // Embranchement : vérifie une condition
        if (!hexARGB.startsWith("#")) return null;

        // Gestion des exceptions
        try {
            // Appelle une méthode
            int argb = HexFormat.fromHexDigits(hexARGB, 1, 9);
            // Renvoie une valeur à l'appelant
            return new AlphaColor(argb);
        // Début d'une méthode/d'un bloc
        } catch (NumberFormatException _) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int alpha() {
        // Renvoie une valeur à l'appelant
        return alpha;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object obj) {
        // Embranchement : vérifie une condition
        if (obj == this) return true;
        // Embranchement : vérifie une condition
        if (obj == null || obj.getClass() != this.getClass()) return false;
        // Appelle une méthode
        var that = (AlphaColor) obj;
        // Renvoie une valeur à l'appelant
        return this.alpha == that.alpha &&
                // Instruction de code
                red() == that.red() &&
                // Accès à l'objet courant/parent
                this.green() == that.green() &&
                // Accès à l'objet courant/parent
                this.blue() == that.blue();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return Objects.hash(alpha, red(), green(), blue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return "AlphaColor[" +
                // Instruction de code
                "alpha=" + alpha + ", " +
                // Instruction de code
                "red=" + red() + ", " +
                // Instruction de code
                "green=" + green() + ", " +
                // Appelle une méthode
                "blue=" + blue() + ']';
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
