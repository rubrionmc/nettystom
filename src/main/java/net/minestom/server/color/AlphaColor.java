// Package declaration for this file
package net.minestom.server.color;

// Import of a required class
import net.kyori.adventure.text.format.ShadowColor;
// Import of a required class
import net.kyori.adventure.util.ARGBLike;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.intellij.lang.annotations.Pattern;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.HexFormat;
// Import of a required class
import java.util.Objects;

/**
 * A general purpose class for representing colors.
 * <p>
 * Colors must be in the range of 0-255.
 */
// Type declaration (class/interface/enum/record)
public final class AlphaColor extends Color implements ARGBLike {
    // Assigns a value
    private static final int BIT_MASK = 0xff;

    // Assigns a value
    public static final NetworkBuffer.Type<ARGBLike> NETWORK_TYPE = NetworkBuffer.INT.transform(
            // Calls a method
            AlphaColor::new, color -> fromARGBLike(color).asARGB());

    // Assigns a value
    public static final Codec<ARGBLike> CODEC = Codec.INT.<ARGBLike>transform(AlphaColor::new, color -> fromARGBLike(color).asARGB())
            // Calls a method
            .orElse(Codec.FLOAT.list(4), floats -> new AlphaColor(floats.get(3), floats.get(0), floats.get(1), floats.get(2)));

    /**
     * Use {@link AlphaColor#RGBA_STRING_CODEC} or {@link AlphaColor#ARGB_STRING_CODEC} instead.
     * This codec uses RGBA.
     */
    // Annotation for the following element
    @Deprecated
    // Assigns a value
    public static final Codec<ARGBLike> STRING_CODEC = Codec.STRING.transform(
            // Code statement
            hex -> (ARGBLike) Objects.requireNonNull(ShadowColor.fromHexString(hex)),
            // Calls a method
            color -> ShadowColor.shadowColor(color).asHexString()).orElse(CODEC);

    // Assigns a value
    public static final Codec<ARGBLike> RGBA_STRING_CODEC = Codec.STRING.transform(
            // Code statement
            hex -> (ARGBLike) Objects.requireNonNull(fromRGBAHexString(hex)),
            // Calls a method
            color -> String.format("#%08X", AlphaColor.fromARGBLike(color).asRGBA())).orElse(CODEC);

    // Assigns a value
    public static final Codec<ARGBLike> ARGB_STRING_CODEC = Codec.STRING.transform(
            // Code statement
            hex -> (ARGBLike) Objects.requireNonNull(fromARGBHexString(hex)),
            // Calls a method
            color -> String.format("#%08X", AlphaColor.fromARGBLike(color).asARGB())).orElse(CODEC);

    // Calls a method
    public static final AlphaColor WHITE = new AlphaColor(255, 255, 255, 255);
    // Calls a method
    public static final AlphaColor BLACK = new AlphaColor(255, 0, 0, 0);
    // Calls a method
    public static final AlphaColor TRANSPARENT = new AlphaColor(0, 0, 0, 0);

    // Start of a method/block
    public static AlphaColor fromARGBLike(ARGBLike argbLike) {
        // Branch: checks a condition
        if (argbLike instanceof AlphaColor alphaColor) return alphaColor;
        // Returns a value to the caller
        return new AlphaColor(argbLike.alpha(), argbLike.red(), argbLike.green(), argbLike.blue());
    // End of a block/expression
    }

    // Code statement
    private final int alpha;

    // Start of a method/block
    public AlphaColor(float alpha, float red, float green, float blue) {
        // Calls a method
        this((int) (alpha * 255), (int) (red * 255), (int) (green * 255), (int) (blue * 255));
    // End of a block/expression
    }

    // Start of a method/block
    public AlphaColor(int alpha, int red, int green, int blue) {
        // Access to the current/parent object
        super(red, green, blue);
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(alpha, 0, 255), "Alpha is not between 0-255: {0}", alpha);
        // Access to the current/parent object
        this.alpha = alpha;
    // End of a block/expression
    }

    /**
     * Creates an alpha color from an integer. This is done by reading each color component
     * from the lowest order 32 bits of the integer, and creating a color from those
     * components.
     *
     * @param argb the integer
     */
    // Start of a method/block
    public AlphaColor(int argb) {
        // Calls a method
        this((argb >> 24) & BIT_MASK, (argb >> 16) & BIT_MASK, (argb >> 8) & BIT_MASK, argb & BIT_MASK);
    // End of a block/expression
    }

    /**
     * Creates a color from an RGB-like color.
     *
     * @param rgbLike the color
     */
    // Start of a method/block
    public AlphaColor(int alpha, RGBLike rgbLike) {
        // Calls a method
        this(alpha, rgbLike.red(), rgbLike.green(), rgbLike.blue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public AlphaColor withRed(int red) {
        // Returns a value to the caller
        return new AlphaColor(alpha(), red, green(), blue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public AlphaColor withGreen(int green) {
        // Returns a value to the caller
        return new AlphaColor(alpha(), red(), green, blue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public AlphaColor withBlue(int blue) {
        // Returns a value to the caller
        return new AlphaColor(alpha(), red(), green(), blue);
    // End of a block/expression
    }

    // Start of a method/block
    public AlphaColor withAlpha(int alpha) {
        // Returns a value to the caller
        return new AlphaColor(alpha, red(), green(), blue());
    // End of a block/expression
    }

    /**
     * Gets the color as an ARGB integer.
     *
     * @return An integer representation of this color, as 0xAARRGGBB
     */
    // Start of a method/block
    public int asARGB() {
        // Returns a value to the caller
        return (alpha << 24) + asRGB();
    // End of a block/expression
    }

    /**
     * Gets the color as an RGBA integer.
     *
     * @return An integer representation of this color, as 0xRRGGBBAA
     */
    // Start of a method/block
    public int asRGBA() {
        // Returns a value to the caller
        return (asRGB() << 8) + alpha;
    // End of a block/expression
    }

    /**
     * Attempt to parse a color from a {@code #}-prefixed hex string.
     * <p>
     * This string must be in the format {@code #RRGGBBAA}.
     *
     * @param hexRGBA the input value
     * @return a color if possible, or null if any components are invalid
     */
    // Start of a method/block
    public static @Nullable AlphaColor fromRGBAHexString(@Pattern("#[0-9a-fA-F]{8}") final String hexRGBA) {
        // Branch: checks a condition
        if (hexRGBA.length() != 9) return null;
        // Branch: checks a condition
        if (!hexRGBA.startsWith("#")) return null;

        // Exception handling
        try {
            // Calls a method
            int rgba = HexFormat.fromHexDigits(hexRGBA, 1, 9);
            // Calls a method
            int argb = Integer.rotateRight(rgba, 8);
            // Returns a value to the caller
            return new AlphaColor(argb);
        // Start of a method/block
        } catch (NumberFormatException _) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Attempt to parse a color from a {@code #}-prefixed hex string.
     * <p>
     * This string must be in the format {@code #AARRGGBB}.
     *
     * @param hexARGB the input value
     * @return a color if possible, or null if any components are invalid
     */
    // Start of a method/block
    public static @Nullable AlphaColor fromARGBHexString(@Pattern("#[0-9a-fA-F]{8}") final String hexARGB) {
        // Branch: checks a condition
        if (hexARGB.length() != 9) return null;
        // Branch: checks a condition
        if (!hexARGB.startsWith("#")) return null;

        // Exception handling
        try {
            // Calls a method
            int argb = HexFormat.fromHexDigits(hexARGB, 1, 9);
            // Returns a value to the caller
            return new AlphaColor(argb);
        // Start of a method/block
        } catch (NumberFormatException _) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int alpha() {
        // Returns a value to the caller
        return alpha;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object obj) {
        // Branch: checks a condition
        if (obj == this) return true;
        // Branch: checks a condition
        if (obj == null || obj.getClass() != this.getClass()) return false;
        // Calls a method
        var that = (AlphaColor) obj;
        // Returns a value to the caller
        return this.alpha == that.alpha &&
                // Code statement
                red() == that.red() &&
                // Access to the current/parent object
                this.green() == that.green() &&
                // Access to the current/parent object
                this.blue() == that.blue();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return Objects.hash(alpha, red(), green(), blue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return "AlphaColor[" +
                // Code statement
                "alpha=" + alpha + ", " +
                // Code statement
                "red=" + red() + ", " +
                // Code statement
                "green=" + green() + ", " +
                // Calls a method
                "blue=" + blue() + ']';
    // End of a block/expression
    }

// End of a block/expression
}
