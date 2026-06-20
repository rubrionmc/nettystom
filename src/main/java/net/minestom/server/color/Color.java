// Déclaration du paquet de ce fichier
package net.minestom.server.color;

// Import d'une classe nécessaire
import net.kyori.adventure.text.format.TextColor;
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
import java.util.Objects;

/**
 * A general purpose class for representing colors.
 * <p>
 * Colors must be in the range of 0-255.
 */
// Déclaration de type (classe/interface/enum/record)
public class Color implements RGBLike {
    // Affecte une valeur
    private static final int BIT_MASK = 0xff;

    // Affecte une valeur
    public static final NetworkBuffer.Type<RGBLike> NETWORK_TYPE = NetworkBuffer.INT.transform(
            // Instruction de code
            Color::new,
            // Instruction de code
            color -> Color.fromRGBLike(color).asRGB()
    // Fin d'un bloc/d'une expression
    );

    // Affecte une valeur
    public static final NetworkBuffer.Type<RGBLike> RGB_BYTE_NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, RGBLike value) {
            // Appelle une méthode
            buffer.write(NetworkBuffer.BYTE, (byte) value.red());
            // Appelle une méthode
            buffer.write(NetworkBuffer.BYTE, (byte) value.green());
            // Appelle une méthode
            buffer.write(NetworkBuffer.BYTE, (byte) value.blue());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public RGBLike read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int red = buffer.read(NetworkBuffer.BYTE);
            // Appelle une méthode
            final int green = buffer.read(NetworkBuffer.BYTE);
            // Appelle une méthode
            final int blue = buffer.read(NetworkBuffer.BYTE);
            // Renvoie une valeur à l'appelant
            return new Color(red, green, blue);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Affecte une valeur
    public static final Codec<RGBLike> CODEC = Codec.INT.<RGBLike>transform(Color::new, color -> Color.fromRGBLike(color).asRGB())
            // Appelle une méthode
            .orElse(Codec.VECTOR3D, vector -> new Color((float) vector.x(), (float) vector.y(), (float) vector.z()));
    // Affecte une valeur
    public static final Codec<RGBLike> STRING_CODEC = Codec.STRING.transform(
            // Instruction de code
            hex -> (RGBLike) Objects.requireNonNull(TextColor.fromHexString(hex)),
            // Appelle une méthode
            color -> TextColor.color(color).asHexString()).orElse(CODEC);

    // Appelle une méthode
    public static final RGBLike WHITE = new Color(255, 255, 255);
    // Appelle une méthode
    public static final RGBLike BLACK = new Color(0, 0, 0);

    // Instruction de code
    private final int red;
    // Instruction de code
    private final int green;
    // Instruction de code
    private final int blue;

    // Début d'une méthode/d'un bloc
    public Color(float red, float green, float blue) {
        // Appelle une méthode
        this((int) (red * 255), (int) (green * 255), (int) (blue * 255));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Color(int red, int green, int blue) {
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(red, 0, 255), "Red is not between 0-255: {0}", red);
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(green, 0, 255), "Green is not between 0-255: {0}", green);
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(blue, 0, 255), "Blue is not between 0-255: {0}", blue);
        // Accès à l'objet courant/parent
        this.red = red;
        // Accès à l'objet courant/parent
        this.green = green;
        // Accès à l'objet courant/parent
        this.blue = blue;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a color from an integer. This is done by reading each color component
     * from the lowest order 24 bits of the integer, and creating a color from those
     * components.
     *
     * @param rgb the integer
     */
    // Début d'une méthode/d'un bloc
    public Color(int rgb) {
        // Appelle une méthode
        this((rgb >> 16) & BIT_MASK, (rgb >> 8) & BIT_MASK, rgb & BIT_MASK);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a color from an RGB-like color.
     *
     * @param rgbLike the color
     */
    // Début d'une méthode/d'un bloc
    public Color(RGBLike rgbLike) {
        // Appelle une méthode
        this(rgbLike.red(), rgbLike.green(), rgbLike.blue());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Color fromRGBLike(RGBLike rgbLike) {
        // Embranchement : vérifie une condition
        if (rgbLike instanceof Color color) return color;
        // Renvoie une valeur à l'appelant
        return new Color(rgbLike.red(), rgbLike.green(), rgbLike.blue());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Color withRed(int red) {
        // Renvoie une valeur à l'appelant
        return new Color(red, green, blue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Color withGreen(int green) {
        // Renvoie une valeur à l'appelant
        return new Color(red, green, blue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Color withBlue(int blue) {
        // Renvoie une valeur à l'appelant
        return new Color(red, green, blue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AlphaColor withAlpha(int alpha) {
        // Renvoie une valeur à l'appelant
        return new AlphaColor(alpha, red, green, blue);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the color as an RGB integer.
     *
     * @return An integer representation of this color, as 0xRRGGBB
     */
    // Début d'une méthode/d'un bloc
    public int asRGB() {
        // Affecte une valeur
        int rgb = red;
        // Affecte une valeur
        rgb = (rgb << 8) + green;
        // Renvoie une valeur à l'appelant
        return (rgb << 8) + blue;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public Color mixWith(RGBLike... colors) {
        // Affecte une valeur
        int r = red, g = green, b = blue;

        // store the current highest component
        // Appelle une méthode
        int max = Math.max(Math.max(r, g), b);

        // now combine all the color components, adding to the max
        // Boucle : répète un bloc
        for (RGBLike color : colors) {
            // Appelle une méthode
            r += color.red();
            // Appelle une méthode
            g += color.green();
            // Appelle une méthode
            b += color.blue();
            // Appelle une méthode
            max += Math.max(Math.max(color.red(), color.green()), color.blue());
        // Fin d'un bloc/d'une expression
        }

        // work out the averages
        // Affecte une valeur
        float count = colors.length + 1;
        // Affecte une valeur
        float averageRed = r / count;
        // Affecte une valeur
        float averageGreen = g / count;
        // Affecte une valeur
        float averageBlue = b / count;
        // Affecte une valeur
        float averageMax = max / count;

        // work out the scale factor
        // Appelle une méthode
        float maximumOfAverages = Math.max(Math.max(averageRed, averageGreen), averageBlue);
        // Affecte une valeur
        float gainFactor = averageMax / maximumOfAverages;

        // round and multiply
        // Appelle une méthode
        r = Math.round(averageRed * gainFactor);
        // Appelle une méthode
        g = Math.round(averageGreen * gainFactor);
        // Appelle une méthode
        b = Math.round(averageBlue * gainFactor);
        // Renvoie une valeur à l'appelant
        return new Color(r, g, b);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public int getRed() {
        // Renvoie une valeur à l'appelant
        return this.red;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public int getGreen() {
        // Renvoie une valeur à l'appelant
        return this.green;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public int getBlue() {
        // Renvoie une valeur à l'appelant
        return this.blue;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int red() {
        // Renvoie une valeur à l'appelant
        return red;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int green() {
        // Renvoie une valeur à l'appelant
        return green;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int blue() {
        // Renvoie une valeur à l'appelant
        return blue;
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
        // Affecte une valeur
        var that = (Color) obj;
        // Renvoie une valeur à l'appelant
        return this.red == that.red &&
                // Accès à l'objet courant/parent
                this.green == that.green &&
                // Accès à l'objet courant/parent
                this.blue == that.blue;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return Objects.hash(red, green, blue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return "Color[" +
                // Affecte une valeur
                "red=" + red + ", " +
                // Affecte une valeur
                "green=" + green + ", " +
                // Affecte une valeur
                "blue=" + blue + ']';
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
