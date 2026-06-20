// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.potion.CustomPotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionType;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record PotionContents(
        // Annotation pour l'élément suivant
        @Nullable PotionType potion,
        // Annotation pour l'élément suivant
        @Nullable RGBLike customColor,
        // Instruction de code
        List<CustomPotionEffect> customEffects,
        // Annotation pour l'élément suivant
        @Nullable String customName
// Début d'une méthode/d'un bloc
) {
    // Appelle une méthode
    public static final PotionContents EMPTY = new PotionContents(null, null, List.of(), null);

    // Affecte une valeur
    public static final NetworkBuffer.Type<PotionContents> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            PotionType.NETWORK_TYPE.optional(), PotionContents::potion,
            // Instruction de code
            Color.NETWORK_TYPE.optional(), PotionContents::customColor,
            // Instruction de code
            CustomPotionEffect.NETWORK_TYPE.list(Short.MAX_VALUE), PotionContents::customEffects,
            // Instruction de code
            NetworkBuffer.STRING.optional(), PotionContents::customName,
            // Instruction de code
            PotionContents::new);
    // Appelle une méthode
    private static final Codec<PotionContents> POTION_CODEC = PotionType.CODEC.transform(PotionContents::new, PotionContents::potion);
    // Affecte une valeur
    public static final Codec<PotionContents> CODEC = StructCodec.struct(
            // Instruction de code
            "potion", PotionType.CODEC.optional(), PotionContents::potion,
            // Instruction de code
            "custom_color", Color.CODEC.optional(), PotionContents::customColor,
            // Instruction de code
            "custom_effects", CustomPotionEffect.CODEC.list().optional(List.of()), PotionContents::customEffects,
            // Instruction de code
            "custom_name", Codec.STRING.optional(), PotionContents::customName,
            // Appelle une méthode
            PotionContents::new).orElse(POTION_CODEC);

    // Début d'une méthode/d'un bloc
    public PotionContents {
        // Appelle une méthode
        customEffects = List.copyOf(customEffects);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PotionContents(PotionType potion) {
        // Appelle une méthode
        this(potion, null, List.of(), null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PotionContents(PotionType potion, RGBLike customColor) {
        // Appelle une méthode
        this(potion, customColor, List.of(), null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PotionContents(List<CustomPotionEffect> customEffects) {
        // Appelle une méthode
        this(null, null, customEffects, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PotionContents(CustomPotionEffect customEffect) {
        // Appelle une méthode
        this(null, null, List.of(customEffect), null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PotionContents(@Nullable PotionType potion, @Nullable RGBLike customColor, List<CustomPotionEffect> customEffects) {
        // Appelle une méthode
        this(potion, customColor, customEffects, null);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
