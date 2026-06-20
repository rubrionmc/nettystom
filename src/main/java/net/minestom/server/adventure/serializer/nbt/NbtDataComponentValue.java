// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.serializer.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public sealed interface NbtDataComponentValue extends DataComponentValue permits NbtDataComponentValueImpl {

    // Début d'une méthode/d'un bloc
    static NbtDataComponentValue removed() {
        // Renvoie une valeur à l'appelant
        return new NbtDataComponentValueImpl.Removed();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NbtDataComponentValue nbtDataComponentValue(BinaryTag tag) {
        // Renvoie une valeur à l'appelant
        return new NbtDataComponentValueImpl(tag);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable BinaryTag value();
// Fin d'un bloc/d'une expression
}
