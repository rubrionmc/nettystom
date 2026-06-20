// Package declaration for this file
package net.minestom.server.world;

// Import of a required class
import net.minestom.server.codec.Codec;

// Type declaration (class/interface/enum/record)
public enum MoonPhase {
    // Code statement
    FULL_MOON,
    // Code statement
    WANING_GIBBOUS,
    // Code statement
    THIRD_QUARTER,
    // Code statement
    WANING_CRESCENT,
    // Code statement
    NEW_MOON,
    // Code statement
    WAXING_CRESCENT,
    // Code statement
    FIRST_QUARTER,
    // Code statement
    WAXING_GIBBOUS;

    // Calls a method
    public static final Codec<MoonPhase> CODEC = Codec.Enum(MoonPhase.class);
// End of a block/expression
}
