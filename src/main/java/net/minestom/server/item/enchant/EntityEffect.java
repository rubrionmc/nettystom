// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;


// Déclaration de type (classe/interface/enum/record)
public non-sealed interface EntityEffect extends Enchantment.Effect {

    // Affecte une valeur
    StructCodec<EntityEffect> CODEC = Codec.RegistryTaggedUnion(
            // Instruction de code
            Registries::enchantmentEntityEffects, EntityEffect::codec);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<StructCodec<? extends EntityEffect>> createDefaultRegistry() {
        // Appelle une méthode
        final DynamicRegistry<StructCodec<? extends EntityEffect>> registry = DynamicRegistry.create(Key.key("minestom:enchantment_value_effect"));
        // Appelle une méthode
        registry.register("all_of", AllOf.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("apply_mob_effect", ApplyPotionEffect.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("change_item_damage", ChangeItemDamage.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("damage_entity", DamageEntity.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("explode", Explode.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("ignite", Ignite.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("apply_impulse", ApplyImpulse.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("apply_exhaustion", ApplyExhaustion.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("play_sound", PlaySound.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("replace_block", ReplaceBlock.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("replace_disk", ReplaceDisc.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("run_function", RunFunction.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("set_block_properties", SetBlockProperties.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("spawn_particles", SpawnParticles.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("summon_entity", SummonEntity.CODEC, DataPack.MINECRAFT_CORE);
        // Renvoie une valeur à l'appelant
        return registry;
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    StructCodec<? extends EntityEffect> codec();

    // Déclaration de type (classe/interface/enum/record)
    record AllOf(List<EntityEffect> effect) implements EntityEffect {
        // Affecte une valeur
        public static final StructCodec<AllOf> CODEC = StructCodec.struct(
                // Instruction de code
                "effects", EntityEffect.CODEC.list(), AllOf::effect,
                // Instruction de code
                AllOf::new);

        // Début d'une méthode/d'un bloc
        public AllOf {
            // Appelle une méthode
            effect = List.copyOf(effect);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<AllOf> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ApplyPotionEffect(
            // Instruction de code
            RegistryTag<PotionEffect> toApply,
            // Instruction de code
            LevelBasedValue minDuration,
            // Instruction de code
            LevelBasedValue maxDuration,
            // Instruction de code
            LevelBasedValue minAmplifier,
            // Instruction de code
            LevelBasedValue maxAmplifier
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<ApplyPotionEffect> CODEC = StructCodec.struct(
                // Instruction de code
                "to_apply", RegistryTag.codec(Registries::potionEffect), ApplyPotionEffect::toApply,
                // Instruction de code
                "min_duration", LevelBasedValue.CODEC, ApplyPotionEffect::minDuration,
                // Instruction de code
                "max_duration", LevelBasedValue.CODEC, ApplyPotionEffect::maxDuration,
                // Instruction de code
                "min_amplifier", LevelBasedValue.CODEC, ApplyPotionEffect::minAmplifier,
                // Instruction de code
                "max_amplifier", LevelBasedValue.CODEC, ApplyPotionEffect::maxAmplifier,
                // Instruction de code
                ApplyPotionEffect::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ApplyPotionEffect> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DamageEntity(
            // Instruction de code
            RegistryKey<DamageType> damageType,
            // Instruction de code
            LevelBasedValue minDamage,
            // Instruction de code
            LevelBasedValue maxDamage
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<DamageEntity> CODEC = StructCodec.struct(
                // Instruction de code
                "damage_type", DamageType.CODEC, DamageEntity::damageType,
                // Instruction de code
                "min_damage", LevelBasedValue.CODEC, DamageEntity::minDamage,
                // Instruction de code
                "max_damage", LevelBasedValue.CODEC, DamageEntity::maxDamage,
                // Instruction de code
                DamageEntity::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<DamageEntity> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ChangeItemDamage(LevelBasedValue amount) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<ChangeItemDamage> CODEC = StructCodec.struct(
                // Instruction de code
                "amount", LevelBasedValue.CODEC, ChangeItemDamage::amount,
                // Instruction de code
                ChangeItemDamage::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ChangeItemDamage> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Explode(
            // Instruction de code
            boolean attributeToUser,
            // Annotation pour l'élément suivant
            @Nullable RegistryKey<DamageType> damageType,
            // Annotation pour l'élément suivant
            @Nullable LevelBasedValue knockbackMultiplier,
            // Annotation pour l'élément suivant
            @Nullable Codec.RawValue immuneBlocks,
            // Instruction de code
            Point offset,
            // Instruction de code
            LevelBasedValue radius,
            // Instruction de code
            boolean createFire,
            // Instruction de code
            Codec.RawValue blockInteraction,
            // Instruction de code
            Codec.RawValue smallParticle,
            // Instruction de code
            Codec.RawValue largeParticle,
            // Instruction de code
            SoundEvent sound
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<Explode> CODEC = StructCodec.struct(
                // Instruction de code
                "attribute_to_user", Codec.BOOLEAN.optional(false), Explode::attributeToUser,
                // Instruction de code
                "damage_type", DamageType.CODEC.optional(), Explode::damageType,
                // Instruction de code
                "knockback_multiplier", LevelBasedValue.CODEC.optional(), Explode::knockbackMultiplier,
                // Instruction de code
                "immune_blocks", Codec.RAW_VALUE.optional(), Explode::immuneBlocks,
                // Instruction de code
                "offset", Codec.VECTOR3D.optional(Vec.ZERO), Explode::offset,
                // Instruction de code
                "radius", LevelBasedValue.CODEC, Explode::radius,
                // Instruction de code
                "create_fire", Codec.BOOLEAN.optional(false), Explode::createFire,
                // Instruction de code
                "block_interaction", Codec.RAW_VALUE, Explode::blockInteraction,
                // Instruction de code
                "small_particle", Codec.RAW_VALUE, Explode::smallParticle,
                // Instruction de code
                "large_particle", Codec.RAW_VALUE, Explode::largeParticle,
                // Instruction de code
                "sound", SoundEvent.CODEC, Explode::sound,
                // Instruction de code
                Explode::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Explode> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Ignite(LevelBasedValue duration) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<Ignite> CODEC = StructCodec.struct(
                // Instruction de code
                "duration", LevelBasedValue.CODEC, Ignite::duration,
                // Instruction de code
                Ignite::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Ignite> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ApplyImpulse(
            // Instruction de code
            Point direction,
            // Instruction de code
            Point coordinateScale,
            // Instruction de code
            LevelBasedValue magnitude
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<ApplyImpulse> CODEC = StructCodec.struct(
                // Instruction de code
                "direction", Codec.VECTOR3D, ApplyImpulse::direction,
                // Instruction de code
                "coordinate_scale", Codec.VECTOR3D, ApplyImpulse::coordinateScale,
                // Instruction de code
                "magnitude", LevelBasedValue.CODEC, ApplyImpulse::magnitude,
                // Instruction de code
                ApplyImpulse::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ApplyImpulse> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ApplyExhaustion(LevelBasedValue amount) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<ApplyExhaustion> CODEC = StructCodec.struct(
                // Instruction de code
                "amount", LevelBasedValue.CODEC, ApplyExhaustion::amount,
                // Instruction de code
                ApplyExhaustion::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ApplyExhaustion> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record PlaySound(
            // Instruction de code
            List<SoundEvent> soundEvent,
            // Instruction de code
            Codec.RawValue volume,
            // Instruction de code
            Codec.RawValue pitch
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<PlaySound> CODEC = StructCodec.struct(
                // Instruction de code
                "sound", SoundEvent.CODEC.listOrSingle(255), PlaySound::soundEvent,
                // Instruction de code
                "volume", Codec.RAW_VALUE, PlaySound::volume,
                // Instruction de code
                "pitch", Codec.RAW_VALUE, PlaySound::pitch,
                // Instruction de code
                PlaySound::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<PlaySound> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ReplaceBlock(
            // Instruction de code
            Codec.RawValue offset,
            // Annotation pour l'élément suivant
            @Nullable Codec.RawValue predicate,
            // Instruction de code
            Codec.RawValue blockState,
            // Annotation pour l'élément suivant
            @Nullable Codec.RawValue triggerGameEvent
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<ReplaceBlock> CODEC = StructCodec.struct(
                // Instruction de code
                "offset", Codec.RAW_VALUE, ReplaceBlock::offset,
                // Instruction de code
                "predicate", Codec.RAW_VALUE, ReplaceBlock::predicate,
                // Instruction de code
                "block_state", Codec.RAW_VALUE, ReplaceBlock::blockState,
                // Instruction de code
                "trigger_game_event", Codec.RAW_VALUE, ReplaceBlock::triggerGameEvent,
                // Instruction de code
                ReplaceBlock::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ReplaceBlock> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ReplaceDisc(
            // Instruction de code
            LevelBasedValue radius,
            // Instruction de code
            LevelBasedValue height,
            // Instruction de code
            Codec.RawValue offset,
            // Annotation pour l'élément suivant
            @Nullable Codec.RawValue predicate,
            // Instruction de code
            Codec.RawValue blockState,
            // Annotation pour l'élément suivant
            @Nullable Codec.RawValue triggerGameEvent
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<ReplaceDisc> CODEC = StructCodec.struct(
                // Instruction de code
                "radius", LevelBasedValue.CODEC, ReplaceDisc::radius,
                // Instruction de code
                "height", LevelBasedValue.CODEC, ReplaceDisc::height,
                // Instruction de code
                "offset", Codec.RAW_VALUE, ReplaceDisc::offset,
                // Instruction de code
                "predicate", Codec.RAW_VALUE.optional(), ReplaceDisc::predicate,
                // Instruction de code
                "block_state", Codec.RAW_VALUE, ReplaceDisc::blockState,
                // Instruction de code
                "trigger_game_event", Codec.RAW_VALUE.optional(), ReplaceDisc::triggerGameEvent,
                // Instruction de code
                ReplaceDisc::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ReplaceDisc> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RunFunction(
            // Instruction de code
            String function
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<RunFunction> CODEC = StructCodec.struct(
                // Instruction de code
                "function", Codec.STRING, RunFunction::function,
                // Instruction de code
                RunFunction::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<RunFunction> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SetBlockProperties(
            // Instruction de code
            Codec.RawValue properties,
            // Instruction de code
            Codec.RawValue offset,
            // Annotation pour l'élément suivant
            @Nullable Codec.RawValue triggerGameEvent
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<SetBlockProperties> CODEC = StructCodec.struct(
                // Instruction de code
                "properties", Codec.RAW_VALUE, SetBlockProperties::properties,
                // Instruction de code
                "offset", Codec.RAW_VALUE, SetBlockProperties::offset,
                // Instruction de code
                "trigger_game_event", Codec.RAW_VALUE.optional(), SetBlockProperties::triggerGameEvent,
                // Instruction de code
                SetBlockProperties::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<SetBlockProperties> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SpawnParticles(
            // Instruction de code
            Codec.RawValue particle,
            // Instruction de code
            Codec.RawValue horizontalPosition,
            // Instruction de code
            Codec.RawValue verticalPosition,
            // Instruction de code
            Codec.RawValue horizontalVelocity,
            // Instruction de code
            Codec.RawValue verticalVelocity,
            // Instruction de code
            Codec.RawValue speed
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<SpawnParticles> CODEC = StructCodec.struct(
                // Instruction de code
                "particle", Codec.RAW_VALUE, SpawnParticles::particle,
                // Instruction de code
                "horizontal_position", Codec.RAW_VALUE, SpawnParticles::horizontalPosition,
                // Instruction de code
                "vertical_position", Codec.RAW_VALUE, SpawnParticles::verticalPosition,
                // Instruction de code
                "horizontal_velocity", Codec.RAW_VALUE, SpawnParticles::horizontalVelocity,
                // Instruction de code
                "vertical_velocity", Codec.RAW_VALUE, SpawnParticles::verticalVelocity,
                // Instruction de code
                "speed", Codec.RAW_VALUE, SpawnParticles::speed,
                // Instruction de code
                SpawnParticles::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<SpawnParticles> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SummonEntity(
            // Instruction de code
            Codec.RawValue entityTypes,
            // Instruction de code
            boolean joinTeam
    // Début d'une méthode/d'un bloc
    ) implements EntityEffect, LocationEffect {
        // Affecte une valeur
        public static final StructCodec<SummonEntity> CODEC = StructCodec.struct(
                // Instruction de code
                "entity", Codec.RAW_VALUE, SummonEntity::entityTypes,
                // Instruction de code
                "join_team", Codec.BOOLEAN.optional(false), SummonEntity::joinTeam,
                // Instruction de code
                SummonEntity::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<SummonEntity> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
