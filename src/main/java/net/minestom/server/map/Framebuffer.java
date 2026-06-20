// Déclaration du paquet de ce fichier
package net.minestom.server.map;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.MapDataPacket;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Framebuffer to render to a map
 */
// Déclaration de type (classe/interface/enum/record)
public interface Framebuffer {

    // Affecte une valeur
    int WIDTH = 128;
    // Affecte une valeur
    int HEIGHT = 128;

    // Appelle une méthode
    byte[] toMapColors();

    // Début d'une méthode/d'un bloc
    default MapDataPacket preparePacket(int mapId) {
        // Renvoie une valeur à l'appelant
        return preparePacket(mapId, 0, 0, WIDTH, HEIGHT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default MapDataPacket preparePacket(int mapId, int minX, int minY, int width, int height) {
        // Instruction de code
        byte[] colors;
        // Embranchement : vérifie une condition
        if (minX == 0 && minY == 0 && width == WIDTH && height == HEIGHT) {
            // Appelle une méthode
            colors = toMapColors();
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            colors = new byte[width * height];
            // Appelle une méthode
            final byte[] mapColors = toMapColors();
            // Boucle : répète un bloc
            for (int y = minY; y < Math.min(HEIGHT, minY + height); y++) {
                // Boucle : répète un bloc
                for (int x = minX; x < Math.min(WIDTH, minX + width); x++) {
                    // Appelle une méthode
                    byte color = mapColors[index(x, y, WIDTH)];
                    // Appelle une méthode
                    colors[index(x - minX, y - minY, width)] = color;
                // Fin d'un bloc/d'une expression
                }
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
                        (byte) minX, (byte) minY,
                        // Instruction de code
                        colors));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static int index(int x, int z) {
        // Renvoie une valeur à l'appelant
        return index(x, z, WIDTH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static int index(int x, int z, int stride) {
        // Renvoie une valeur à l'appelant
        return z * stride + x;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
