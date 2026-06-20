// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record BedRule(
        // Code statement
        Rule canSleep,
        // Code statement
        Rule canSetSpawn,
        // Code statement
        boolean explodes,
        // Annotation for the following element
        @Nullable Component errorMessage
// Start of a method/block
) {
    /// The default vanilla overworld bed behavior.
    // Assigns a value
    public static final BedRule CAN_SLEEP_WHEN_DARK = new BedRule(BedRule.Rule.WHEN_DARK, BedRule.Rule.ALWAYS,
            // Calls a method
            false, Component.translatable("block.minecraft.bed.no_sleep"));
    /// THe default vanilla nether/end bed behavior.
    // Calls a method
    public static final BedRule EXPLODES = new BedRule(BedRule.Rule.NEVER, BedRule.Rule.NEVER, true, null);

    // Assigns a value
    public static final Codec<BedRule> CODEC = StructCodec.struct(
            // Code statement
            "can_sleep", Rule.CODEC, BedRule::canSleep,
            // Code statement
            "can_set_spawn", Rule.CODEC, BedRule::canSetSpawn,
            // Code statement
            "explodes", Codec.BOOLEAN.optional(false), BedRule::explodes,
            // Code statement
            "error_message", Codec.COMPONENT.optional(), BedRule::errorMessage,
            // Code statement
            BedRule::new);

    // Type declaration (class/interface/enum/record)
    public enum Rule {
        // Code statement
        ALWAYS,
        // Code statement
        WHEN_DARK,
        // Code statement
        NEVER;

        // Calls a method
        public static final Codec<Rule> CODEC = Codec.Enum(Rule.class);
    // End of a block/expression
    }
// End of a block/expression
}
