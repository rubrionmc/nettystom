// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeOperation;

// Déclaration de type (classe/interface/enum/record)
public record AttributeEffect(
        // Instruction de code
        Key id,
        // Instruction de code
        Attribute attribute,
        // Instruction de code
        LevelBasedValue amount,
        // Instruction de code
        AttributeOperation operation
// Début d'une méthode/d'un bloc
) implements Enchantment.Effect, LocationEffect {

    // Affecte une valeur
    public static final StructCodec<AttributeEffect> CODEC = StructCodec.struct(
            // Instruction de code
            "id", Codec.KEY, AttributeEffect::id,
            // Instruction de code
            "attribute", Attribute.CODEC, AttributeEffect::attribute,
            // Instruction de code
            "amount", LevelBasedValue.CODEC, AttributeEffect::amount,
            // Instruction de code
            "operation", AttributeOperation.CODEC, AttributeEffect::operation,
            // Instruction de code
            AttributeEffect::new);

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public StructCodec<AttributeEffect> codec() {
        // Renvoie une valeur à l'appelant
        return CODEC;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
