// Déclaration du paquet de ce fichier
package net.minestom.server.map.framebuffers;

// Import d'une classe nécessaire
import net.minestom.server.map.Framebuffer;
// Import d'une classe nécessaire
import net.minestom.server.map.MapColors;

// Import d'une classe nécessaire
import java.awt.*;
// Import d'une classe nécessaire
import java.awt.image.BufferedImage;
// Import d'une classe nécessaire
import java.awt.image.DataBufferInt;

/**
 * {@link Framebuffer} that embeds a BufferedImage, allowing for rendering directly via Graphics2D or its pixel array.
 */
// Déclaration de type (classe/interface/enum/record)
public class Graphics2DFramebuffer implements Framebuffer {

    // Affecte une valeur
    private final byte[] colors = new byte[WIDTH * HEIGHT];
    // Appelle une méthode
    private final BufferedImage backingImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    // Instruction de code
    private final Graphics2D renderer;
    // Instruction de code
    private final int[] pixels;

    // Début d'une méthode/d'un bloc
    public Graphics2DFramebuffer() {
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
        return pixels[x + z * WIDTH]; // stride is always the width of the image
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Graphics2DFramebuffer set(int x, int z, int rgb) {
        // Affecte une valeur
        pixels[x + z * WIDTH] = rgb;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte[] toMapColors() {
        // TODO: update subparts only
        // Boucle : répète un bloc
        for (int x = 0; x < 128; x++) {
            // Boucle : répète un bloc
            for (int z = 0; z < 128; z++) {
                // Appelle une méthode
                colors[Framebuffer.index(x, z)] = MapColors.closestColor(get(x, z)).getIndex();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return colors;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
