// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.condition.DataPredicate;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record ConditionalEffect<E extends Enchantment.Effect>(
        // Instruction de code
        E effect,
        // Annotation pour l'élément suivant
        @Nullable DataPredicate requirements
// Début d'une méthode/d'un bloc
) implements Enchantment.Effect {

    // Début d'une méthode/d'un bloc
    public static <E extends Enchantment.Effect> Codec<ConditionalEffect<E>> codec(Codec<E> effectType) {
        // Renvoie une valeur à l'appelant
        return StructCodec.struct(
                // Instruction de code
                "effect", effectType, ConditionalEffect::effect,
                // Instruction de code
                "requirements", DataPredicate.NBT_TYPE.optional(), ConditionalEffect::requirements,
                // Instruction de code
                ConditionalEffect::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
