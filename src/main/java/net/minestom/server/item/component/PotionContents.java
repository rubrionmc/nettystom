// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.potion.CustomPotionEffect;
// Import of a required class
import net.minestom.server.potion.PotionType;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record PotionContents(
        // Annotation for the following element
        @Nullable PotionType potion,
        // Annotation for the following element
        @Nullable RGBLike customColor,
        // Code statement
        List<CustomPotionEffect> customEffects,
        // Annotation for the following element
        @Nullable String customName
// Start of a method/block
) {
    // Calls a method
    public static final PotionContents EMPTY = new PotionContents(null, null, List.of(), null);

    // Assigns a value
    public static final NetworkBuffer.Type<PotionContents> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            PotionType.NETWORK_TYPE.optional(), PotionContents::potion,
            // Code statement
            Color.NETWORK_TYPE.optional(), PotionContents::customColor,
            // Code statement
            CustomPotionEffect.NETWORK_TYPE.list(Short.MAX_VALUE), PotionContents::customEffects,
            // Code statement
            NetworkBuffer.STRING.optional(), PotionContents::customName,
            // Code statement
            PotionContents::new);
    // Calls a method
    private static final Codec<PotionContents> POTION_CODEC = PotionType.CODEC.transform(PotionContents::new, PotionContents::potion);
    // Assigns a value
    public static final Codec<PotionContents> CODEC = StructCodec.struct(
            // Code statement
            "potion", PotionType.CODEC.optional(), PotionContents::potion,
            // Code statement
            "custom_color", Color.CODEC.optional(), PotionContents::customColor,
            // Code statement
            "custom_effects", CustomPotionEffect.CODEC.list().optional(List.of()), PotionContents::customEffects,
            // Code statement
            "custom_name", Codec.STRING.optional(), PotionContents::customName,
            // Calls a method
            PotionContents::new).orElse(POTION_CODEC);

    // Start of a method/block
    public PotionContents {
        // Calls a method
        customEffects = List.copyOf(customEffects);
    // End of a block/expression
    }

    // Start of a method/block
    public PotionContents(PotionType potion) {
        // Calls a method
        this(potion, null, List.of(), null);
    // End of a block/expression
    }

    // Start of a method/block
    public PotionContents(PotionType potion, RGBLike customColor) {
        // Calls a method
        this(potion, customColor, List.of(), null);
    // End of a block/expression
    }

    // Start of a method/block
    public PotionContents(List<CustomPotionEffect> customEffects) {
        // Calls a method
        this(null, null, customEffects, null);
    // End of a block/expression
    }

    // Start of a method/block
    public PotionContents(CustomPotionEffect customEffect) {
        // Calls a method
        this(null, null, List.of(customEffect), null);
    // End of a block/expression
    }

    // Start of a method/block
    public PotionContents(@Nullable PotionType potion, @Nullable RGBLike customColor, List<CustomPotionEffect> customEffects) {
        // Calls a method
        this(potion, customColor, customEffects, null);
    // End of a block/expression
    }

// End of a block/expression
}
