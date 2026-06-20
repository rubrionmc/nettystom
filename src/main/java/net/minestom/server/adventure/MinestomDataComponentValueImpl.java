// Déclaration du paquet de ce fichier
package net.minestom.server.adventure;

// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
record MinestomDataComponentValueImpl(@Nullable Object value) implements MinestomDataComponentValue {
    // Déclaration de type (classe/interface/enum/record)
    record Removed() implements MinestomDataComponentValue, DataComponentValue.Removed {
        // Appelle une méthode
        static final MinestomDataComponentValue INSTANCE = new MinestomDataComponentValueImpl.Removed();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable Object value() {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
