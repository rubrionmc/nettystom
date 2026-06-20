// Déclaration du paquet de ce fichier
package net.minestom.server.map;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.MapDataPacket;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Framebuffer that is meant to be split in sub-framebuffers.
 * Contrary to {@link Framebuffer}, LargeFramebuffer supports sizes over 128x128 pixels.
 */
// Déclaration de type (classe/interface/enum/record)
public interface LargeFramebuffer {

    // Appelle une méthode
    int width();

    // Appelle une méthode
    int height();

    /**
     * Returns a new {@link Framebuffer} that represent a 128x128 sub-view of this framebuffer.
     * Implementations are free (but not guaranteed) to throw exceptions if left &amp; top produces out-of-bounds coordinates.
     *
     * @param left
     * @param top
     * @return the sub-view {@link Framebuffer}
     */
    // Appelle une méthode
    Framebuffer createSubView(int left, int top);

    // Appelle une méthode
    byte getMapColor(int x, int y);

    /**
     * Prepares the packet to render a 128x128 sub view of this framebuffer
     */
    // Début d'une méthode/d'un bloc
    default MapDataPacket preparePacket(int mapId, int left, int top) {
        // Affecte une valeur
        byte[] colors = new byte[Framebuffer.WIDTH * Framebuffer.WIDTH];
        // Appelle une méthode
        final int width = Math.min(width(), left + Framebuffer.WIDTH) - left;
        // Appelle une méthode
        final int height = Math.min(height(), top + Framebuffer.HEIGHT) - top;
        // Boucle : répète un bloc
        for (int y = top; y < top+height; y++) {
            // Boucle : répète un bloc
            for (int x = left; x < left+width; x++) {
                // Appelle une méthode
                final byte color = getMapColor(x, y);
                // Appelle une méthode
                colors[Framebuffer.index(x - left, y - top)] = color;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new MapDataPacket(mapId, (byte) 0, false,
                // Instruction de code
                false, List.of(),
                // Crée un nouvel objet
                new MapDataPacket.ColorContent((byte) width, (byte) height,
                        // Instruction de code
                        (byte) 0, (byte) 0,
                        // Instruction de code
                        colors));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
