// Déclaration du paquet de ce fichier
package net.minestom.server.adventure;

// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public sealed interface MinestomDataComponentValue extends DataComponentValue permits MinestomDataComponentValueImpl {

    // Début d'une méthode/d'un bloc
    static MinestomDataComponentValue removed() {
        // Renvoie une valeur à l'appelant
        return MinestomDataComponentValueImpl.Removed.INSTANCE;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static MinestomDataComponentValue dataComponentValue(final @Nullable Object data) {
        // Renvoie une valeur à l'appelant
        return new MinestomDataComponentValueImpl(data);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable Object value();
// Fin d'un bloc/d'une expression
}
