// Déclaration du paquet de ce fichier
package net.minestom.server.entity.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public enum AttributeOperation {
    // Instruction de code
    ADD_VALUE(0),
    // Instruction de code
    ADD_MULTIPLIED_BASE(1),
    // Appelle une méthode
    ADD_MULTIPLIED_TOTAL(2);

    // Appelle une méthode
    public static final NetworkBuffer.Type<AttributeOperation> NETWORK_TYPE = NetworkBuffer.Enum(AttributeOperation.class);
    // Appelle une méthode
    public static final Codec<AttributeOperation> CODEC = Codec.Enum(AttributeOperation.class);

    // Affecte une valeur
    private static final AttributeOperation[] VALUES = new AttributeOperation[]{ADD_VALUE, ADD_MULTIPLIED_BASE, ADD_MULTIPLIED_TOTAL};
    // Instruction de code
    private final int id;

    // Début d'une méthode/d'un bloc
    AttributeOperation(int id) {
        // Accès à l'objet courant/parent
        this.id = id;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getId() {
        // Renvoie une valeur à l'appelant
        return this.id;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @Nullable AttributeOperation fromId(int id) {
        // Embranchement : vérifie une condition
        if (id >= 0 && id < VALUES.length) {
            // Renvoie une valeur à l'appelant
            return VALUES[id];
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
