// Package declaration for this file
package net.minestom.server.world.biome;

// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record BiomeEffects(
        // Code statement
        RGBLike waterColor,
        // Annotation for the following element
        @Nullable RGBLike foliageColor,
        // Annotation for the following element
        @Nullable RGBLike dryFoliageColor,
        // Annotation for the following element
        @Nullable RGBLike grassColor,
        // Code statement
        GrassColorModifier grassColorModifier
// Start of a method/block
) {
    // Calls a method
    public static final BiomeEffects DEFAULT = new BiomeEffects(new Color(0x3f76e4), null, null, null, GrassColorModifier.NONE);

    // Assigns a value
    public static final Codec<BiomeEffects> CODEC = StructCodec.struct(
            // Code statement
            "water_color", Color.STRING_CODEC, BiomeEffects::waterColor,
            // Code statement
            "foliage_color", Color.STRING_CODEC.optional(), BiomeEffects::foliageColor,
            // Code statement
            "dry_foliage_color", Color.STRING_CODEC.optional(), BiomeEffects::dryFoliageColor,
            // Code statement
            "grass_color", Color.STRING_CODEC.optional(), BiomeEffects::grassColor,
            // Code statement
            "grass_color_modifier", GrassColorModifier.CODEC.optional(GrassColorModifier.NONE), BiomeEffects::grassColorModifier,
            // Code statement
            BiomeEffects::new);

    // Start of a method/block
    public static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum GrassColorModifier {
        // Code statement
        NONE, DARK_FOREST, SWAMP;

        // Calls a method
        public static final Codec<GrassColorModifier> CODEC = Codec.Enum(GrassColorModifier.class);
    // End of a block/expression
    }

    // Start of a method/block
    public static final class Builder {
        // Calls a method
        private RGBLike waterColor = new Color(0x3f76e4);
        // Code statement
        private @Nullable RGBLike foliageColor;
        // Code statement
        private @Nullable RGBLike dryFoliageColor;
        // Code statement
        private @Nullable RGBLike grassColor;
        // Assigns a value
        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;

        // Start of a method/block
        Builder() {
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder waterColor(RGBLike waterColor) {
            // Access to the current/parent object
            this.waterColor = waterColor;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder foliageColor(@Nullable RGBLike foliageColor) {
            // Access to the current/parent object
            this.foliageColor = foliageColor;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder dryFoliageColor(@Nullable RGBLike dryFoliageColor) {
            // Access to the current/parent object
            this.dryFoliageColor = dryFoliageColor;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder grassColor(@Nullable RGBLike grassColor) {
            // Access to the current/parent object
            this.grassColor = grassColor;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
            // Access to the current/parent object
            this.grassColorModifier = grassColorModifier;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true, value = "-> new")
        // Start of a method/block
        public BiomeEffects build() {
            // Calls a method
            Check.argCondition(waterColor == null, "waterColor is required");

            // Returns a value to the caller
            return new BiomeEffects(waterColor, foliageColor, dryFoliageColor, grassColor, grassColorModifier);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
