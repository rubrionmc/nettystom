// Déclaration du paquet de ce fichier
package net.minestom.server.dialog;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;

// Déclaration de type (classe/interface/enum/record)
public enum DialogAfterAction {
    // Instruction de code
    CLOSE,
    // Instruction de code
    NONE,
    // Instruction de code
    WAIT_FOR_RESPONSE;

    // Appelle une méthode
    public static final Codec<DialogAfterAction> CODEC = Codec.Enum(DialogAfterAction.class);
// Fin d'un bloc/d'une expression
}
