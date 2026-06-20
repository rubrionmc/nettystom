// Déclaration du paquet de ce fichier
package net.minestom.server.adventure;

// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
sealed class MinestomDataComponentValueImpl implements MinestomDataComponentValue permits MinestomDataComponentValueImpl.Removed {
    // Instruction de code
    private final Object value;

    // Début d'une méthode/d'un bloc
    MinestomDataComponentValueImpl(@Nullable Object value) {
        // Accès à l'objet courant/parent
        this.value = value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Object value() {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class Removed extends MinestomDataComponentValueImpl implements DataComponentValue.Removed {
        // Appelle une méthode
        static final MinestomDataComponentValue INSTANCE = new MinestomDataComponentValueImpl.Removed();

        // Début d'une méthode/d'un bloc
        public Removed() {
            // Accès à l'objet courant/parent
            super(null);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
