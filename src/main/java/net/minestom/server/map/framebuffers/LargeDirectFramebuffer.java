// Déclaration du paquet de ce fichier
package net.minestom.server.map.framebuffers;

// Import d'une classe nécessaire
import net.minestom.server.map.Framebuffer;
// Import d'une classe nécessaire
import net.minestom.server.map.LargeFramebuffer;
// Import d'une classe nécessaire
import net.minestom.server.map.MapColors;

/**
 * {@link LargeFramebuffer} with direct access to the colors array.
 * <p>
 * This implementation does not throw errors when accessing out-of-bounds coordinates through sub-views, and will instead
 * use {@link MapColors#NONE}. This is only the case for sub-views, access through {@link #setMapColor(int, int, byte)}
 * and {@link #getMapColor(int, int)} will throw an exception if out-of-bounds coordinates are inputted.
 */
// Déclaration de type (classe/interface/enum/record)
public class LargeDirectFramebuffer implements LargeFramebuffer {

    // Instruction de code
    private final int width;
    // Instruction de code
    private final int height;
    // Instruction de code
    private final byte[] colors;

    /**
     * Creates a new {@link LargeDirectFramebuffer} with the desired size
     *
     * @param width
     * @param height
     */
    // Début d'une méthode/d'un bloc
    public LargeDirectFramebuffer(int width, int height) {
        // Accès à l'objet courant/parent
        this.width = width;
        // Accès à l'objet courant/parent
        this.height = height;
        // Accès à l'objet courant/parent
        this.colors = new byte[width * height];
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int width() {
        // Renvoie une valeur à l'appelant
        return width;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int height() {
        // Renvoie une valeur à l'appelant
        return height;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Framebuffer createSubView(int left, int top) {
        // Renvoie une valeur à l'appelant
        return new LargeFramebufferDefaultView(this, left, top);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public LargeDirectFramebuffer setMapColor(int x, int y, byte color) {
        // Embranchement : vérifie une condition
        if (!bounds(x, y)) throw new IndexOutOfBoundsException("Invalid x;y coordinate: " + x + ";" + y);
        // Affecte une valeur
        colors[y * width + x] = color;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte getMapColor(int x, int y) {
        // Embranchement : vérifie une condition
        if (!bounds(x, y)) throw new IndexOutOfBoundsException("Invalid x;y coordinate: " + x + ";" + y);
        // Renvoie une valeur à l'appelant
        return colors[y * width + x];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean bounds(int x, int y) {
        // Renvoie une valeur à l'appelant
        return x >= 0 && x < width && y >= 0 && y < height;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte[] getColors() {
        // Renvoie une valeur à l'appelant
        return colors;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
