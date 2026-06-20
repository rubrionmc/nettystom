// Déclaration du paquet de ce fichier
package net.minestom.server.map.framebuffers;

// Import d'une classe nécessaire
import net.minestom.server.map.Framebuffer;
// Import d'une classe nécessaire
import net.minestom.server.map.LargeFramebuffer;
// Import d'une classe nécessaire
import net.minestom.server.map.MapColors;

// Import d'une classe nécessaire
import java.awt.*;
// Import d'une classe nécessaire
import java.awt.image.BufferedImage;
// Import d'une classe nécessaire
import java.awt.image.DataBufferInt;

/**
 * {@link LargeFramebuffer} that embeds a {@link BufferedImage},
 * allowing for rendering directly via {@link Graphics2D} or its pixel array.
 */
// Déclaration de type (classe/interface/enum/record)
public class LargeGraphics2DFramebuffer implements LargeFramebuffer {

    // Instruction de code
    private final BufferedImage backingImage;
    // Instruction de code
    private final Graphics2D renderer;
    // Instruction de code
    private final int[] pixels;
    // Instruction de code
    private final int width;
    // Instruction de code
    private final int height;

    // Début d'une méthode/d'un bloc
    public LargeGraphics2DFramebuffer(int width, int height) {
        // Accès à l'objet courant/parent
        this.width = width;
        // Accès à l'objet courant/parent
        this.height = height;
        // Appelle une méthode
        backingImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Appelle une méthode
        renderer = backingImage.createGraphics();
        // Appelle une méthode
        pixels = ((DataBufferInt) backingImage.getRaster().getDataBuffer()).getData();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Graphics2D getRenderer() {
        // Renvoie une valeur à l'appelant
        return renderer;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BufferedImage getBackingImage() {
        // Renvoie une valeur à l'appelant
        return backingImage;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int get(int x, int z) {
        // Renvoie une valeur à l'appelant
        return pixels[x + z * width]; // stride is always the width of the image
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public LargeGraphics2DFramebuffer set(int x, int z, int rgb) {
        // Affecte une valeur
        pixels[x + z * width] = rgb;
        // Renvoie une valeur à l'appelant
        return this;
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte getMapColor(int x, int y) {
        // Renvoie une valeur à l'appelant
        return MapColors.closestColor(get(x, y)).getIndex();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
