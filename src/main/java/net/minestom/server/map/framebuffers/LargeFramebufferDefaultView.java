// Déclaration du paquet de ce fichier
package net.minestom.server.map.framebuffers;

// Import d'une classe nécessaire
import net.minestom.server.map.Framebuffer;
// Import d'une classe nécessaire
import net.minestom.server.map.LargeFramebuffer;
// Import d'une classe nécessaire
import net.minestom.server.map.MapColors;

// Déclaration de type (classe/interface/enum/record)
public class LargeFramebufferDefaultView implements Framebuffer {
    // Instruction de code
    private final LargeFramebuffer parent;
    // Instruction de code
    private final int x;
    // Instruction de code
    private final int y;
    // Affecte une valeur
    private final byte[] colors = new byte[WIDTH*HEIGHT];

    // Début d'une méthode/d'un bloc
    public LargeFramebufferDefaultView(LargeFramebuffer parent, int x, int y) {
        // Accès à l'objet courant/parent
        this.parent = parent;
        // Accès à l'objet courant/parent
        this.x = x;
        // Accès à l'objet courant/parent
        this.y = y;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean bounds(int x, int y) {
        // Renvoie une valeur à l'appelant
        return x >= 0 && x < parent.width() && y >= 0 && y < parent.height();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private byte colorOrNone(int x, int y) {
        // Embranchement : vérifie une condition
        if(!bounds(x, y)) return MapColors.NONE.baseColor();
        // Renvoie une valeur à l'appelant
        return parent.getMapColor(x, y);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte[] toMapColors() {
        // Boucle : répète un bloc
        for (int y = 0; y < HEIGHT; y++) {
            // Boucle : répète un bloc
            for (int x = 0; x < WIDTH; x++) {
                // Appelle une méthode
                colors[Framebuffer.index(x, y)] = colorOrNone(x+this.x, y+this.y);
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
