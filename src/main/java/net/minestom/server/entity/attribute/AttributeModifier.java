// Déclaration du paquet de ce fichier
package net.minestom.server.entity.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

/**
 * Represent an attribute modifier.
 */
// Déclaration de type (classe/interface/enum/record)
public record AttributeModifier(Key id, double amount, AttributeOperation operation) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<AttributeModifier> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.KEY, AttributeModifier::id,
            // Instruction de code
            NetworkBuffer.DOUBLE, AttributeModifier::amount,
            // Instruction de code
            AttributeOperation.NETWORK_TYPE, AttributeModifier::operation,
            // Instruction de code
            AttributeModifier::new);
    // Affecte une valeur
    public static final Codec<AttributeModifier> CODEC = StructCodec.struct(
            // Instruction de code
            "id", Codec.KEY, AttributeModifier::id,
            // Instruction de code
            "amount", Codec.DOUBLE, AttributeModifier::amount,
            // Instruction de code
            "operation", AttributeOperation.CODEC, AttributeModifier::operation,
            // Instruction de code
            AttributeModifier::new);

    /**
     * Creates a new modifier with a random id.
     *
     * @param id        the (namespace) id of this modifier
     * @param amount    the value of this modifier
     * @param operation the operation to apply this modifier with
     */
    // Début d'une méthode/d'un bloc
    public AttributeModifier(@KeyPattern String id, double amount, AttributeOperation operation) {
        // Appelle une méthode
        this(Key.key(id), amount, operation);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
