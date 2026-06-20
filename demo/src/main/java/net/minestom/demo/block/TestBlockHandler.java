// Déclaration du paquet de ce fichier
package net.minestom.demo.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;

// Déclaration de type (classe/interface/enum/record)
public class TestBlockHandler implements BlockHandler {
    // Appelle une méthode
    public static final BlockHandler INSTANCE = new TestBlockHandler();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key getKey() {
        // Renvoie une valeur à l'appelant
        return Key.key("minestom", "test");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void onPlace(Placement placement) {
        // Appelle une méthode
        System.out.println(placement);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void onDestroy(Destroy destroy) {
        // Appelle une méthode
        System.out.println(destroy);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
