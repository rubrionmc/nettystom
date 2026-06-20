// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public non-sealed interface LocationEffect extends Enchantment.Effect {

    // Assigns a value
    StructCodec<LocationEffect> CODEC = Codec.RegistryTaggedUnion(
            // Code statement
            Registries::enchantmentLocationEffects, LocationEffect::codec);

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<StructCodec<? extends LocationEffect>> createDefaultRegistry() {
        // Calls a method
        final DynamicRegistry<StructCodec<? extends LocationEffect>> registry = DynamicRegistry.create(Key.key("minestom:enchantment_value_effect"));
        // Calls a method
        registry.register("all_of", AllOf.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("apply_mob_effect", EntityEffect.ApplyPotionEffect.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("attribute", AttributeEffect.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("change_item_damage", EntityEffect.ChangeItemDamage.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("damage_entity", EntityEffect.DamageEntity.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("explode", EntityEffect.Explode.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("ignite", EntityEffect.Ignite.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("apply_impulse", EntityEffect.ApplyImpulse.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("apply_exhaustion", EntityEffect.ApplyExhaustion.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("play_sound", EntityEffect.PlaySound.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("replace_block", EntityEffect.ReplaceBlock.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("replace_disk", EntityEffect.ReplaceDisc.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("run_function", EntityEffect.RunFunction.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("set_block_properties", EntityEffect.SetBlockProperties.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("spawn_particles", EntityEffect.SpawnParticles.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("summon_entity", EntityEffect.SummonEntity.CODEC, DataPack.MINECRAFT_CORE);
        // Returns a value to the caller
        return registry;
    // End of a block/expression
    }

    // Calls a method
    StructCodec<? extends LocationEffect> codec();

    // Type declaration (class/interface/enum/record)
    record AllOf(List<LocationEffect> effect) implements LocationEffect {
        // Assigns a value
        public static final StructCodec<AllOf> CODEC = StructCodec.struct(
                // Code statement
                "effects", LocationEffect.CODEC.list(), AllOf::effect,
                // Code statement
                AllOf::new
        // End of a block/expression
        );

        // Start of a method/block
        public AllOf {
            // Calls a method
            effect = List.copyOf(effect);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<AllOf> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
