// Déclaration du paquet de ce fichier
package net.minestom.server.message;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record ChatTypeImpl(
        // Instruction de code
        ChatTypeDecoration chat,
        // Instruction de code
        ChatTypeDecoration narration
// Début d'une méthode/d'un bloc
) implements ChatType {

    // Début d'une méthode/d'un bloc
    ChatTypeImpl {
        // Appelle une méthode
        Objects.requireNonNull(chat, "missing chat");
        // Appelle une méthode
        Objects.requireNonNull(narration, "missing narration");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
