// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record BlocksAttacks(
        // Code statement
        float blockDelaySeconds,
        // Code statement
        float disableCooldownScale,
        // Code statement
        List<DamageReduction> damageReductions,
        // Code statement
        ItemDamageFunction itemDamage,
        // Annotation for the following element
        @Nullable RegistryTag<DamageType> bypassedBy,
        // Annotation for the following element
        @Nullable SoundEvent blockSound,
        // Annotation for the following element
        @Nullable SoundEvent disableSound
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<BlocksAttacks> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.FLOAT, BlocksAttacks::blockDelaySeconds,
            // Code statement
            NetworkBuffer.FLOAT, BlocksAttacks::disableCooldownScale,
            // Code statement
            DamageReduction.NETWORK_TYPE.list(Short.MAX_VALUE), BlocksAttacks::damageReductions,
            // Code statement
            ItemDamageFunction.NETWORK_TYPE, BlocksAttacks::itemDamage,
            // Code statement
            RegistryTag.networkType(Registries::damageType).optional(), BlocksAttacks::bypassedBy,
            // Code statement
            SoundEvent.NETWORK_TYPE.optional(), BlocksAttacks::blockSound,
            // Code statement
            SoundEvent.NETWORK_TYPE.optional(), BlocksAttacks::disableSound,
            // Code statement
            BlocksAttacks::new);
    // Assigns a value
    public static final Codec<BlocksAttacks> NBT_TYPE = StructCodec.struct(
            // Code statement
            "block_delay_seconds", Codec.FLOAT.optional(0f), BlocksAttacks::blockDelaySeconds,
            // Code statement
            "disable_cooldown_scale", Codec.FLOAT.optional(1f), BlocksAttacks::disableCooldownScale,
            // Code statement
            "damage_reductions", DamageReduction.CODEC.list().optional(List.of(DamageReduction.DEFAULT)), BlocksAttacks::damageReductions,
            // Code statement
            "item_damage", ItemDamageFunction.CODEC.optional(ItemDamageFunction.DEFAULT), BlocksAttacks::itemDamage,
            // Code statement
            "bypassed_by", RegistryTag.codec(Registries::damageType).optional(), BlocksAttacks::bypassedBy,
            // Code statement
            "block_sound", SoundEvent.CODEC.optional(), BlocksAttacks::blockSound,
            // Code statement
            "disabled_sound", SoundEvent.CODEC.optional(), BlocksAttacks::disableSound,
            // Code statement
            BlocksAttacks::new);

    // Start of a method/block
    public BlocksAttacks {
        // Calls a method
        damageReductions = List.copyOf(damageReductions);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record ItemDamageFunction(float threshold, float base, float factor) {
        // Calls a method
        public static final ItemDamageFunction DEFAULT = new ItemDamageFunction(1f, 0f, 1f);

        // Assigns a value
        public static final NetworkBuffer.Type<ItemDamageFunction> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.FLOAT, ItemDamageFunction::threshold,
                // Code statement
                NetworkBuffer.FLOAT, ItemDamageFunction::base,
                // Code statement
                NetworkBuffer.FLOAT, ItemDamageFunction::factor,
                // Code statement
                ItemDamageFunction::new);
        // Assigns a value
        public static final Codec<ItemDamageFunction> CODEC = StructCodec.struct(
                // Code statement
                "threshold", Codec.FLOAT, ItemDamageFunction::threshold,
                // Code statement
                "base", Codec.FLOAT, ItemDamageFunction::base,
                // Code statement
                "factor", Codec.FLOAT, ItemDamageFunction::factor,
                // Code statement
                ItemDamageFunction::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record DamageReduction(
            // Code statement
            float horizontalBlockingAngle,
            // Annotation for the following element
            @Nullable RegistryTag<DamageType> type,
            // Code statement
            float base, float factor
    // Start of a method/block
    ) {
        // Calls a method
        public static final DamageReduction DEFAULT = new DamageReduction(90.0f, null, 0.0f, 1.0f);

        // Assigns a value
        public static final NetworkBuffer.Type<DamageReduction> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.FLOAT, DamageReduction::horizontalBlockingAngle,
                // Code statement
                RegistryTag.networkType(Registries::damageType).optional(), DamageReduction::type,
                // Code statement
                NetworkBuffer.FLOAT, DamageReduction::base,
                // Code statement
                NetworkBuffer.FLOAT, DamageReduction::factor,
                // Code statement
                DamageReduction::new);
        // Assigns a value
        public static final Codec<DamageReduction> CODEC = StructCodec.struct(
                // Code statement
                "horizontal_blocking_angle", Codec.FLOAT.optional(90f), DamageReduction::horizontalBlockingAngle,
                // Code statement
                "type", RegistryTag.codec(Registries::damageType).optional(), DamageReduction::type,
                // Code statement
                "base", Codec.FLOAT, DamageReduction::base,
                // Code statement
                "factor", Codec.FLOAT, DamageReduction::factor,
                // Code statement
                DamageReduction::new);

    // End of a block/expression
    }
// End of a block/expression
}
