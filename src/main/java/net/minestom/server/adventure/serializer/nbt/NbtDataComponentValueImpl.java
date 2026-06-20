// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.serializer.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
record NbtDataComponentValueImpl(@Nullable BinaryTag value) implements NbtDataComponentValue {
    // Déclaration de type (classe/interface/enum/record)
    record Removed() implements DataComponentValue.Removed, NbtDataComponentValue {
        // Appelle une méthode
        static final NbtDataComponentValueImpl.Removed INSTANCE = new NbtDataComponentValueImpl.Removed();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable BinaryTag value() {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
