// Déclaration du paquet de ce fichier
package net.minestom.server.gamedata;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;

// Déclaration de type (classe/interface/enum/record)
record DataPackImpl(Key key, boolean isSynced) implements DataPack {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isSynced() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
