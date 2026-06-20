// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.serializer.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
sealed class NbtDataComponentValueImpl implements NbtDataComponentValue permits NbtDataComponentValueImpl.Removed {
    // Instruction de code
    private final BinaryTag tag;

    // Début d'une méthode/d'un bloc
    NbtDataComponentValueImpl(@Nullable BinaryTag tag) {
        // Accès à l'objet courant/parent
        this.tag = tag;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable BinaryTag value() {
        // Renvoie une valeur à l'appelant
        return tag;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class Removed extends NbtDataComponentValueImpl implements DataComponentValue.Removed {
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
