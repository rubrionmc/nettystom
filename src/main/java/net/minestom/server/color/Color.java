// Package declaration for this file
package net.minestom.server.color;

// Import of a required class
import net.kyori.adventure.text.format.TextColor;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.Objects;

/**
 * A general purpose class for representing colors.
 * <p>
 * Colors must be in the range of 0-255.
 */
// Type declaration (class/interface/enum/record)
public class Color implements RGBLike {
    // Assigns a value
    private static final int BIT_MASK = 0xff;

    // Assigns a value
    public static final NetworkBuffer.Type<RGBLike> NETWORK_TYPE = NetworkBuffer.INT.transform(
            // Code statement
            Color::new,
            // Code statement
            color -> Color.fromRGBLike(color).asRGB()
    // End of a block/expression
    );

    // Assigns a value
    public static final NetworkBuffer.Type<RGBLike> RGB_BYTE_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BYTE, color -> (byte) color.red(),
            // Code statement
            NetworkBuffer.BYTE, color -> (byte) color.green(),
            // Code statement
            NetworkBuffer.BYTE, color -> (byte) color.blue(),
            // Code statement
            Color::new
    // End of a block/expression
    );

    // Assigns a value
    public static final Codec<RGBLike> CODEC = Codec.INT.<RGBLike>transform(Color::new, color -> Color.fromRGBLike(color).asRGB())
            // Calls a method
            .orElse(Codec.VECTOR3D, vector -> new Color((float) vector.x(), (float) vector.y(), (float) vector.z()));
    // Assigns a value
    public static final Codec<RGBLike> STRING_CODEC = Codec.STRING.transform(
            // Code statement
            hex -> (RGBLike) Objects.requireNonNull(TextColor.fromHexString(hex)),
            // Calls a method
            color -> TextColor.color(color).asHexString()).orElse(CODEC);

    // Calls a method
    public static final RGBLike WHITE = new Color(255, 255, 255);
    // Calls a method
    public static final RGBLike BLACK = new Color(0, 0, 0);

    // Code statement
    private final int red;
    // Code statement
    private final int green;
    // Code statement
    private final int blue;

    // Start of a method/block
    public Color(float red, float green, float blue) {
        // Calls a method
        this((int) (red * 255), (int) (green * 255), (int) (blue * 255));
    // End of a block/expression
    }

    // Start of a method/block
    public Color(int red, int green, int blue) {
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(red, 0, 255), "Red is not between 0-255: {0}", red);
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(green, 0, 255), "Green is not between 0-255: {0}", green);
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(blue, 0, 255), "Blue is not between 0-255: {0}", blue);
        // Access to the current/parent object
        this.red = red;
        // Access to the current/parent object
        this.green = green;
        // Access to the current/parent object
        this.blue = blue;
    // End of a block/expression
    }

    /**
     * Creates a color from an integer. This is done by reading each color component
     * from the lowest order 24 bits of the integer, and creating a color from those
     * components.
     *
     * @param rgb the integer
     */
    // Start of a method/block
    public Color(int rgb) {
        // Calls a method
        this((rgb >> 16) & BIT_MASK, (rgb >> 8) & BIT_MASK, rgb & BIT_MASK);
    // End of a block/expression
    }

    /**
     * Creates a color from an RGB-like color.
     *
     * @param rgbLike the color
     */
    // Start of a method/block
    public Color(RGBLike rgbLike) {
        // Calls a method
        this(rgbLike.red(), rgbLike.green(), rgbLike.blue());
    // End of a block/expression
    }

    // Start of a method/block
    public static Color fromRGBLike(RGBLike rgbLike) {
        // Branch: checks a condition
        if (rgbLike instanceof Color color) return color;
        // Returns a value to the caller
        return new Color(rgbLike.red(), rgbLike.green(), rgbLike.blue());
    // End of a block/expression
    }

    // Start of a method/block
    public Color withRed(int red) {
        // Returns a value to the caller
        return new Color(red, green, blue);
    // End of a block/expression
    }

    // Start of a method/block
    public Color withGreen(int green) {
        // Returns a value to the caller
        return new Color(red, green, blue);
    // End of a block/expression
    }

    // Start of a method/block
    public Color withBlue(int blue) {
        // Returns a value to the caller
        return new Color(red, green, blue);
    // End of a block/expression
    }

    // Start of a method/block
    public AlphaColor withAlpha(int alpha) {
        // Returns a value to the caller
        return new AlphaColor(alpha, red, green, blue);
    // End of a block/expression
    }

    /**
     * Gets the color as an RGB integer.
     *
     * @return An integer representation of this color, as 0xRRGGBB
     */
    // Start of a method/block
    public int asRGB() {
        // Assigns a value
        int rgb = red;
        // Calls a method
        rgb = (rgb << 8) + green;
        // Returns a value to the caller
        return (rgb << 8) + blue;
    // End of a block/expression
    }

    /**
     * Mixes this color with a series of other colors, as if they were combined in a
     * crafting table. This function works out the average of each RGB component and then
     * multiplies the components by a scale factor that is calculated from the average
     * of all maximum values divided by the maximum of each average value. This is how
     * Minecraft mixes colors.
     *
     * @param colors the colors
     */
    // Start of a method/block
    public Color mixWith(RGBLike... colors) {
        // Assigns a value
        int r = red, g = green, b = blue;

        // store the current highest component
        // Calls a method
        int max = Math.max(Math.max(r, g), b);

        // now combine all the color components, adding to the max
        // Loop: repeats a block
        for (RGBLike color : colors) {
            // Calls a method
            r += color.red();
            // Calls a method
            g += color.green();
            // Calls a method
            b += color.blue();
            // Calls a method
            max += Math.max(Math.max(color.red(), color.green()), color.blue());
        // End of a block/expression
        }

        // work out the averages
        // Assigns a value
        float count = colors.length + 1;
        // Assigns a value
        float averageRed = r / count;
        // Assigns a value
        float averageGreen = g / count;
        // Assigns a value
        float averageBlue = b / count;
        // Assigns a value
        float averageMax = max / count;

        // work out the scale factor
        // Calls a method
        float maximumOfAverages = Math.max(Math.max(averageRed, averageGreen), averageBlue);
        // Assigns a value
        float gainFactor = averageMax / maximumOfAverages;

        // round and multiply
        // Calls a method
        r = Math.round(averageRed * gainFactor);
        // Calls a method
        g = Math.round(averageGreen * gainFactor);
        // Calls a method
        b = Math.round(averageBlue * gainFactor);
        // Returns a value to the caller
        return new Color(r, g, b);
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public int getRed() {
        // Returns a value to the caller
        return this.red;
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public int getGreen() {
        // Returns a value to the caller
        return this.green;
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public int getBlue() {
        // Returns a value to the caller
        return this.blue;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int red() {
        // Returns a value to the caller
        return red;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int green() {
        // Returns a value to the caller
        return green;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int blue() {
        // Returns a value to the caller
        return blue;
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
        var that = (Color) obj;
        // Returns a value to the caller
        return this.red == that.red &&
                // Access to the current/parent object
                this.green == that.green &&
                // Access to the current/parent object
                this.blue == that.blue;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return Objects.hash(red, green, blue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return "Color[" +
                // Code statement
                "red=" + red + ", " +
                // Code statement
                "green=" + green + ", " +
                // Code statement
                "blue=" + blue + ']';
    // End of a block/expression
    }
// End of a block/expression
}
