// Déclaration du paquet de ce fichier
package net.minestom.server.map.framebuffers;

// Import d'une classe nécessaire
import net.minestom.server.map.Framebuffer;

/**
 * {@link Framebuffer} with direct access to the colors array
 */
// Déclaration de type (classe/interface/enum/record)
public class DirectFramebuffer implements Framebuffer {

    // Affecte une valeur
    private final byte[] colors = new byte[WIDTH * HEIGHT];

    /**
     * Mutable colors array
     *
     * @return
     */
    // Début d'une méthode/d'un bloc
    public byte[] getColors() {
        // Renvoie une valeur à l'appelant
        return colors;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte get(int x, int z) {
        // Renvoie une valeur à l'appelant
        return colors[Framebuffer.index(x, z)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public DirectFramebuffer set(int x, int z, byte color) {
        // Appelle une méthode
        colors[Framebuffer.index(x, z)] = color;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte[] toMapColors() {
        // Renvoie une valeur à l'appelant
        return colors;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
