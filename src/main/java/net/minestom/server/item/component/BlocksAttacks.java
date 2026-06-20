// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.registry.TagKey;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record BlocksAttacks(
        // Instruction de code
        float blockDelaySeconds,
        // Instruction de code
        float disableCooldownScale,
        // Instruction de code
        List<DamageReduction> damageReductions,
        // Instruction de code
        ItemDamageFunction itemDamage,
        // Annotation pour l'élément suivant
        @Nullable TagKey<DamageType> bypassedBy,
        // Annotation pour l'élément suivant
        @Nullable SoundEvent blockSound,
        // Annotation pour l'élément suivant
        @Nullable SoundEvent disableSound
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<BlocksAttacks> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.FLOAT, BlocksAttacks::blockDelaySeconds,
            // Instruction de code
            NetworkBuffer.FLOAT, BlocksAttacks::disableCooldownScale,
            // Instruction de code
            DamageReduction.NETWORK_TYPE.list(Short.MAX_VALUE), BlocksAttacks::damageReductions,
            // Instruction de code
            ItemDamageFunction.NETWORK_TYPE, BlocksAttacks::itemDamage,
            // Instruction de code
            TagKey.networkType(Registries::damageType).optional(), BlocksAttacks::bypassedBy,
            // Instruction de code
            SoundEvent.NETWORK_TYPE.optional(), BlocksAttacks::blockSound,
            // Instruction de code
            SoundEvent.NETWORK_TYPE.optional(), BlocksAttacks::disableSound,
            // Instruction de code
            BlocksAttacks::new);
    // Affecte une valeur
    public static final Codec<BlocksAttacks> NBT_TYPE = StructCodec.struct(
            // Instruction de code
            "block_delay_seconds", Codec.FLOAT.optional(0f), BlocksAttacks::blockDelaySeconds,
            // Instruction de code
            "disable_cooldown_scale", Codec.FLOAT.optional(1f), BlocksAttacks::disableCooldownScale,
            // Instruction de code
            "damage_reductions", DamageReduction.CODEC.list().optional(List.of(DamageReduction.DEFAULT)), BlocksAttacks::damageReductions,
            // Instruction de code
            "item_damage", ItemDamageFunction.CODEC.optional(ItemDamageFunction.DEFAULT), BlocksAttacks::itemDamage,
            // Instruction de code
            "bypassed_by", TagKey.hashCodec(Registries::damageType).optional(), BlocksAttacks::bypassedBy,
            // Instruction de code
            "block_sound", SoundEvent.CODEC.optional(), BlocksAttacks::blockSound,
            // Instruction de code
            "disabled_sound", SoundEvent.CODEC.optional(), BlocksAttacks::disableSound,
            // Instruction de code
            BlocksAttacks::new);

    // Début d'une méthode/d'un bloc
    public BlocksAttacks {
        // Appelle une méthode
        damageReductions = List.copyOf(damageReductions);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record ItemDamageFunction(float threshold, float base, float factor) {
        // Appelle une méthode
        public static final ItemDamageFunction DEFAULT = new ItemDamageFunction(1f, 0f, 1f);

        // Affecte une valeur
        public static final NetworkBuffer.Type<ItemDamageFunction> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.FLOAT, ItemDamageFunction::threshold,
                // Instruction de code
                NetworkBuffer.FLOAT, ItemDamageFunction::base,
                // Instruction de code
                NetworkBuffer.FLOAT, ItemDamageFunction::factor,
                // Instruction de code
                ItemDamageFunction::new);
        // Affecte une valeur
        public static final Codec<ItemDamageFunction> CODEC = StructCodec.struct(
                // Instruction de code
                "threshold", Codec.FLOAT, ItemDamageFunction::threshold,
                // Instruction de code
                "base", Codec.FLOAT, ItemDamageFunction::base,
                // Instruction de code
                "factor", Codec.FLOAT, ItemDamageFunction::factor,
                // Instruction de code
                ItemDamageFunction::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record DamageReduction(
            // Instruction de code
            float horizontalBlockingAngle,
            // Annotation pour l'élément suivant
            @Nullable RegistryTag<DamageType> type,
            // Instruction de code
            float base, float factor
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        public static final DamageReduction DEFAULT = new DamageReduction(90.0f, null, 0.0f, 1.0f);

        // Affecte une valeur
        public static final NetworkBuffer.Type<DamageReduction> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.FLOAT, DamageReduction::horizontalBlockingAngle,
                // Instruction de code
                RegistryTag.networkType(Registries::damageType).optional(), DamageReduction::type,
                // Instruction de code
                NetworkBuffer.FLOAT, DamageReduction::base,
                // Instruction de code
                NetworkBuffer.FLOAT, DamageReduction::factor,
                // Instruction de code
                DamageReduction::new);
        // Affecte une valeur
        public static final Codec<DamageReduction> CODEC = StructCodec.struct(
                // Instruction de code
                "horizontal_blocking_angle", Codec.FLOAT.optional(90f), DamageReduction::horizontalBlockingAngle,
                // Instruction de code
                "type", RegistryTag.codec(Registries::damageType).optional(), DamageReduction::type,
                // Instruction de code
                "base", Codec.FLOAT, DamageReduction::base,
                // Instruction de code
                "factor", Codec.FLOAT, DamageReduction::factor,
                // Instruction de code
                DamageReduction::new);

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
