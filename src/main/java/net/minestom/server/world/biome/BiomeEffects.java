// Déclaration du paquet de ce fichier
package net.minestom.server.world.biome;

// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record BiomeEffects(
        // Instruction de code
        RGBLike waterColor,
        // Annotation pour l'élément suivant
        @Nullable RGBLike foliageColor,
        // Annotation pour l'élément suivant
        @Nullable RGBLike dryFoliageColor,
        // Annotation pour l'élément suivant
        @Nullable RGBLike grassColor,
        // Instruction de code
        GrassColorModifier grassColorModifier
// Début d'une méthode/d'un bloc
) {
    // Appelle une méthode
    public static final BiomeEffects DEFAULT = new BiomeEffects(new Color(0x3f76e4), null, null, null, GrassColorModifier.NONE);

    // Affecte une valeur
    public static final Codec<BiomeEffects> CODEC = StructCodec.struct(
            // Instruction de code
            "water_color", Color.STRING_CODEC, BiomeEffects::waterColor,
            // Instruction de code
            "foliage_color", Color.STRING_CODEC.optional(), BiomeEffects::foliageColor,
            // Instruction de code
            "dry_foliage_color", Color.STRING_CODEC.optional(), BiomeEffects::dryFoliageColor,
            // Instruction de code
            "grass_color", Color.STRING_CODEC.optional(), BiomeEffects::grassColor,
            // Instruction de code
            "grass_color_modifier", GrassColorModifier.CODEC.optional(GrassColorModifier.NONE), BiomeEffects::grassColorModifier,
            // Instruction de code
            BiomeEffects::new);

    // Début d'une méthode/d'un bloc
    public static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum GrassColorModifier {
        // Instruction de code
        NONE, DARK_FOREST, SWAMP;

        // Appelle une méthode
        public static final Codec<GrassColorModifier> CODEC = Codec.Enum(GrassColorModifier.class);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Builder {
        // Appelle une méthode
        private RGBLike waterColor = new Color(0x3f76e4);
        // Instruction de code
        private @Nullable RGBLike foliageColor;
        // Instruction de code
        private @Nullable RGBLike dryFoliageColor;
        // Instruction de code
        private @Nullable RGBLike grassColor;
        // Affecte une valeur
        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;

        // Début d'une méthode/d'un bloc
        Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder waterColor(RGBLike waterColor) {
            // Accès à l'objet courant/parent
            this.waterColor = waterColor;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder foliageColor(@Nullable RGBLike foliageColor) {
            // Accès à l'objet courant/parent
            this.foliageColor = foliageColor;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder dryFoliageColor(@Nullable RGBLike dryFoliageColor) {
            // Accès à l'objet courant/parent
            this.dryFoliageColor = dryFoliageColor;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder grassColor(@Nullable RGBLike grassColor) {
            // Accès à l'objet courant/parent
            this.grassColor = grassColor;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
            // Accès à l'objet courant/parent
            this.grassColorModifier = grassColorModifier;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true, value = "-> new")
        // Début d'une méthode/d'un bloc
        public BiomeEffects build() {
            // Appelle une méthode
            Check.argCondition(waterColor == null, "waterColor is required");

            // Renvoie une valeur à l'appelant
            return new BiomeEffects(waterColor, foliageColor, dryFoliageColor, grassColor, grassColorModifier);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
