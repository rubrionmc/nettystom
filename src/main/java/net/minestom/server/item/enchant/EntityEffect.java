// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;


// Type declaration (class/interface/enum/record)
public non-sealed interface EntityEffect extends Enchantment.Effect {

    // Assigns a value
    StructCodec<EntityEffect> CODEC = Codec.RegistryTaggedUnion(
            // Code statement
            Registries::enchantmentEntityEffects, EntityEffect::codec);

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<StructCodec<? extends EntityEffect>> createDefaultRegistry() {
        // Calls a method
        final DynamicRegistry<StructCodec<? extends EntityEffect>> registry = DynamicRegistry.create(Key.key("minestom:enchantment_value_effect"));
        // Calls a method
        registry.register("all_of", AllOf.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("apply_mob_effect", ApplyPotionEffect.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("change_item_damage", ChangeItemDamage.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("damage_entity", DamageEntity.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("explode", Explode.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("ignite", Ignite.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("apply_impulse", ApplyImpulse.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("apply_exhaustion", ApplyExhaustion.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("play_sound", PlaySound.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("replace_block", ReplaceBlock.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("replace_disk", ReplaceDisc.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("run_function", RunFunction.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("set_block_properties", SetBlockProperties.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("spawn_particles", SpawnParticles.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("summon_entity", SummonEntity.CODEC, DataPack.MINECRAFT_CORE);
        // Returns a value to the caller
        return registry;
    // End of a block/expression
    }

    // Calls a method
    StructCodec<? extends EntityEffect> codec();

    // Type declaration (class/interface/enum/record)
    record AllOf(List<EntityEffect> effect) implements EntityEffect {
        // Assigns a value
        public static final StructCodec<AllOf> CODEC = StructCodec.struct(
                // Code statement
                "effects", EntityEffect.CODEC.list(), AllOf::effect,
                // Code statement
                AllOf::new);

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

    // Type declaration (class/interface/enum/record)
    record ApplyPotionEffect(
            // Code statement
            RegistryTag<PotionEffect> toApply,
            // Code statement
            LevelBasedValue minDuration,
            // Code statement
            LevelBasedValue maxDuration,
            // Code statement
            LevelBasedValue minAmplifier,
            // Code statement
            LevelBasedValue maxAmplifier
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<ApplyPotionEffect> CODEC = StructCodec.struct(
                // Code statement
                "to_apply", RegistryTag.codec(Registries::potionEffect), ApplyPotionEffect::toApply,
                // Code statement
                "min_duration", LevelBasedValue.CODEC, ApplyPotionEffect::minDuration,
                // Code statement
                "max_duration", LevelBasedValue.CODEC, ApplyPotionEffect::maxDuration,
                // Code statement
                "min_amplifier", LevelBasedValue.CODEC, ApplyPotionEffect::minAmplifier,
                // Code statement
                "max_amplifier", LevelBasedValue.CODEC, ApplyPotionEffect::maxAmplifier,
                // Code statement
                ApplyPotionEffect::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ApplyPotionEffect> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DamageEntity(
            // Code statement
            RegistryKey<DamageType> damageType,
            // Code statement
            LevelBasedValue minDamage,
            // Code statement
            LevelBasedValue maxDamage
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<DamageEntity> CODEC = StructCodec.struct(
                // Code statement
                "damage_type", DamageType.CODEC, DamageEntity::damageType,
                // Code statement
                "min_damage", LevelBasedValue.CODEC, DamageEntity::minDamage,
                // Code statement
                "max_damage", LevelBasedValue.CODEC, DamageEntity::maxDamage,
                // Code statement
                DamageEntity::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<DamageEntity> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ChangeItemDamage(LevelBasedValue amount) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<ChangeItemDamage> CODEC = StructCodec.struct(
                // Code statement
                "amount", LevelBasedValue.CODEC, ChangeItemDamage::amount,
                // Code statement
                ChangeItemDamage::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ChangeItemDamage> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Explode(
            // Code statement
            boolean attributeToUser,
            // Annotation for the following element
            @Nullable RegistryKey<DamageType> damageType,
            // Annotation for the following element
            @Nullable LevelBasedValue knockbackMultiplier,
            // Annotation for the following element
            @Nullable Codec.RawValue immuneBlocks,
            // Code statement
            Point offset,
            // Code statement
            LevelBasedValue radius,
            // Code statement
            boolean createFire,
            // Code statement
            Codec.RawValue blockInteraction,
            // Code statement
            Codec.RawValue smallParticle,
            // Code statement
            Codec.RawValue largeParticle,
            // Code statement
            SoundEvent sound
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<Explode> CODEC = StructCodec.struct(
                // Code statement
                "attribute_to_user", Codec.BOOLEAN.optional(false), Explode::attributeToUser,
                // Code statement
                "damage_type", DamageType.CODEC.optional(), Explode::damageType,
                // Code statement
                "knockback_multiplier", LevelBasedValue.CODEC.optional(), Explode::knockbackMultiplier,
                // Code statement
                "immune_blocks", Codec.RAW_VALUE.optional(), Explode::immuneBlocks,
                // Code statement
                "offset", Codec.VECTOR3D.optional(Vec.ZERO), Explode::offset,
                // Code statement
                "radius", LevelBasedValue.CODEC, Explode::radius,
                // Code statement
                "create_fire", Codec.BOOLEAN.optional(false), Explode::createFire,
                // Code statement
                "block_interaction", Codec.RAW_VALUE, Explode::blockInteraction,
                // Code statement
                "small_particle", Codec.RAW_VALUE, Explode::smallParticle,
                // Code statement
                "large_particle", Codec.RAW_VALUE, Explode::largeParticle,
                // Code statement
                "sound", SoundEvent.CODEC, Explode::sound,
                // Code statement
                Explode::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Explode> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Ignite(LevelBasedValue duration) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<Ignite> CODEC = StructCodec.struct(
                // Code statement
                "duration", LevelBasedValue.CODEC, Ignite::duration,
                // Code statement
                Ignite::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Ignite> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ApplyImpulse(
            // Code statement
            Point direction,
            // Code statement
            Point coordinateScale,
            // Code statement
            LevelBasedValue magnitude
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<ApplyImpulse> CODEC = StructCodec.struct(
                // Code statement
                "direction", Codec.VECTOR3D, ApplyImpulse::direction,
                // Code statement
                "coordinate_scale", Codec.VECTOR3D, ApplyImpulse::coordinateScale,
                // Code statement
                "magnitude", LevelBasedValue.CODEC, ApplyImpulse::magnitude,
                // Code statement
                ApplyImpulse::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ApplyImpulse> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ApplyExhaustion(LevelBasedValue amount) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<ApplyExhaustion> CODEC = StructCodec.struct(
                // Code statement
                "amount", LevelBasedValue.CODEC, ApplyExhaustion::amount,
                // Code statement
                ApplyExhaustion::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ApplyExhaustion> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record PlaySound(
            // Code statement
            List<SoundEvent> soundEvent,
            // Code statement
            Codec.RawValue volume,
            // Code statement
            Codec.RawValue pitch
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<PlaySound> CODEC = StructCodec.struct(
                // Code statement
                "sound", SoundEvent.CODEC.listOrSingle(255), PlaySound::soundEvent,
                // Code statement
                "volume", Codec.RAW_VALUE, PlaySound::volume,
                // Code statement
                "pitch", Codec.RAW_VALUE, PlaySound::pitch,
                // Code statement
                PlaySound::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<PlaySound> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ReplaceBlock(
            // Code statement
            Codec.RawValue offset,
            // Annotation for the following element
            @Nullable Codec.RawValue predicate,
            // Code statement
            Codec.RawValue blockState,
            // Annotation for the following element
            @Nullable Codec.RawValue triggerGameEvent
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<ReplaceBlock> CODEC = StructCodec.struct(
                // Code statement
                "offset", Codec.RAW_VALUE, ReplaceBlock::offset,
                // Code statement
                "predicate", Codec.RAW_VALUE, ReplaceBlock::predicate,
                // Code statement
                "block_state", Codec.RAW_VALUE, ReplaceBlock::blockState,
                // Code statement
                "trigger_game_event", Codec.RAW_VALUE, ReplaceBlock::triggerGameEvent,
                // Code statement
                ReplaceBlock::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ReplaceBlock> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ReplaceDisc(
            // Code statement
            LevelBasedValue radius,
            // Code statement
            LevelBasedValue height,
            // Code statement
            Codec.RawValue offset,
            // Annotation for the following element
            @Nullable Codec.RawValue predicate,
            // Code statement
            Codec.RawValue blockState,
            // Annotation for the following element
            @Nullable Codec.RawValue triggerGameEvent
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<ReplaceDisc> CODEC = StructCodec.struct(
                // Code statement
                "radius", LevelBasedValue.CODEC, ReplaceDisc::radius,
                // Code statement
                "height", LevelBasedValue.CODEC, ReplaceDisc::height,
                // Code statement
                "offset", Codec.RAW_VALUE, ReplaceDisc::offset,
                // Code statement
                "predicate", Codec.RAW_VALUE.optional(), ReplaceDisc::predicate,
                // Code statement
                "block_state", Codec.RAW_VALUE, ReplaceDisc::blockState,
                // Code statement
                "trigger_game_event", Codec.RAW_VALUE.optional(), ReplaceDisc::triggerGameEvent,
                // Code statement
                ReplaceDisc::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ReplaceDisc> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RunFunction(
            // Code statement
            String function
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<RunFunction> CODEC = StructCodec.struct(
                // Code statement
                "function", Codec.STRING, RunFunction::function,
                // Code statement
                RunFunction::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<RunFunction> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SetBlockProperties(
            // Code statement
            Codec.RawValue properties,
            // Code statement
            Codec.RawValue offset,
            // Annotation for the following element
            @Nullable Codec.RawValue triggerGameEvent
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<SetBlockProperties> CODEC = StructCodec.struct(
                // Code statement
                "properties", Codec.RAW_VALUE, SetBlockProperties::properties,
                // Code statement
                "offset", Codec.RAW_VALUE, SetBlockProperties::offset,
                // Code statement
                "trigger_game_event", Codec.RAW_VALUE.optional(), SetBlockProperties::triggerGameEvent,
                // Code statement
                SetBlockProperties::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<SetBlockProperties> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SpawnParticles(
            // Code statement
            Codec.RawValue particle,
            // Code statement
            Codec.RawValue horizontalPosition,
            // Code statement
            Codec.RawValue verticalPosition,
            // Code statement
            Codec.RawValue horizontalVelocity,
            // Code statement
            Codec.RawValue verticalVelocity,
            // Code statement
            Codec.RawValue speed
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<SpawnParticles> CODEC = StructCodec.struct(
                // Code statement
                "particle", Codec.RAW_VALUE, SpawnParticles::particle,
                // Code statement
                "horizontal_position", Codec.RAW_VALUE, SpawnParticles::horizontalPosition,
                // Code statement
                "vertical_position", Codec.RAW_VALUE, SpawnParticles::verticalPosition,
                // Code statement
                "horizontal_velocity", Codec.RAW_VALUE, SpawnParticles::horizontalVelocity,
                // Code statement
                "vertical_velocity", Codec.RAW_VALUE, SpawnParticles::verticalVelocity,
                // Code statement
                "speed", Codec.RAW_VALUE, SpawnParticles::speed,
                // Code statement
                SpawnParticles::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<SpawnParticles> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SummonEntity(
            // Code statement
            Codec.RawValue entityTypes,
            // Code statement
            boolean joinTeam
    // Start of a method/block
    ) implements EntityEffect, LocationEffect {
        // Assigns a value
        public static final StructCodec<SummonEntity> CODEC = StructCodec.struct(
                // Code statement
                "entity", Codec.RAW_VALUE, SummonEntity::entityTypes,
                // Code statement
                "join_team", Codec.BOOLEAN.optional(false), SummonEntity::joinTeam,
                // Code statement
                SummonEntity::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<SummonEntity> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
