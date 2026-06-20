// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record BedRule(
        // Instruction de code
        Rule canSleep,
        // Instruction de code
        Rule canSetSpawn,
        // Instruction de code
        boolean explodes,
        // Annotation pour l'élément suivant
        @Nullable Component errorMessage
// Début d'une méthode/d'un bloc
) {
    /// The default vanilla overworld bed behavior.
    // Affecte une valeur
    public static final BedRule CAN_SLEEP_WHEN_DARK = new BedRule(BedRule.Rule.WHEN_DARK, BedRule.Rule.ALWAYS,
            // Appelle une méthode
            false, Component.translatable("block.minecraft.bed.no_sleep"));
    /// THe default vanilla nether/end bed behavior.
    // Appelle une méthode
    public static final BedRule EXPLODES = new BedRule(BedRule.Rule.NEVER, BedRule.Rule.NEVER, true, null);

    // Affecte une valeur
    public static final Codec<BedRule> CODEC = StructCodec.struct(
            // Instruction de code
            "can_sleep", Rule.CODEC, BedRule::canSleep,
            // Instruction de code
            "can_set_spawn", Rule.CODEC, BedRule::canSetSpawn,
            // Instruction de code
            "explodes", Codec.BOOLEAN.optional(false), BedRule::explodes,
            // Instruction de code
            "error_message", Codec.COMPONENT.optional(), BedRule::errorMessage,
            // Instruction de code
            BedRule::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Rule {
        // Instruction de code
        ALWAYS,
        // Instruction de code
        WHEN_DARK,
        // Instruction de code
        NEVER;

        // Appelle une méthode
        public static final Codec<Rule> CODEC = Codec.Enum(Rule.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
