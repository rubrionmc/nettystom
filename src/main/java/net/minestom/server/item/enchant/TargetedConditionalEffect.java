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
public record TargetedConditionalEffect<E extends Enchantment.Effect>(
        // Instruction de code
        Enchantment.Target enchanted,
        // Annotation pour l'élément suivant
        @Nullable Enchantment.Target affected,
        // Instruction de code
        E effect,
        // Annotation pour l'élément suivant
        @Nullable DataPredicate requirements
// Début d'une méthode/d'un bloc
) implements Enchantment.Effect {

    // Début d'une méthode/d'un bloc
    public static <E extends Enchantment.Effect> Codec<TargetedConditionalEffect<E>> codec(Codec<E> effectType) {
        // Renvoie une valeur à l'appelant
        return StructCodec.struct(
                // Instruction de code
                "enchanted", Enchantment.Target.CODEC, TargetedConditionalEffect::enchanted,
                // Instruction de code
                "affected", Enchantment.Target.CODEC.optional(), TargetedConditionalEffect::affected,
                // Instruction de code
                "effect", effectType, TargetedConditionalEffect::effect,
                // Instruction de code
                "requirements", DataPredicate.NBT_TYPE.optional(), TargetedConditionalEffect::requirements,
                // Instruction de code
                TargetedConditionalEffect::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
