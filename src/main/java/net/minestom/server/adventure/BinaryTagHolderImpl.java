// Déclaration du paquet de ce fichier
package net.minestom.server.adventure;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.api.BinaryTagHolder;
// Import d'une classe nécessaire
import net.kyori.adventure.util.Codec;

// Import d'une classe nécessaire
import java.io.IOException;

// Déclaration de type (classe/interface/enum/record)
public record BinaryTagHolderImpl(BinaryTag nbt) implements BinaryTagHolder {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String string() {
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return MinestomAdventure.tagStringIO().asString(nbt);
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Lève une exception
            throw new RuntimeException("Failed to convert BinaryTag to String", e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T, DX extends Exception> T get(Codec<T, String, DX, ?> codec) throws DX {
        // Renvoie une valeur à l'appelant
        return codec.decode(string());
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
