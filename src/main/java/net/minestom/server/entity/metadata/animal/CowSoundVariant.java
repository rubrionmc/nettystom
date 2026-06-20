// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Objects;

/**
 * Sounds used by the cow, set with {@link net.minestom.server.component.DataComponents#COW_SOUND_VARIANT}
 * currently {@link #adultSounds()} are shared between baby and adult. This is expected to change in a future release.
 */
// Type declaration (class/interface/enum/record)
public sealed interface CowSoundVariant extends CowSoundVariants permits CowSoundVariantImpl {
    // Calls a method
    NetworkBuffer.Type<RegistryKey<CowSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::cowSoundVariant);
    // Calls a method
    Codec<RegistryKey<CowSoundVariant>> CODEC = RegistryKey.codec(Registries::cowSoundVariant);

    // Assigns a value
    Codec<CowSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            StructCodec.INLINE, CowSoundSet.CODEC, CowSoundVariant::adultSounds,
            // Code statement
            CowSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:cow_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<CowSoundVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("cow_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.COW_SOUND_VARIANTS);
    // End of a block/expression
    }

    // Code statement
    static CowSoundVariant create(
            // Code statement
            CowSoundSet adultSounds
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new CowSoundVariantImpl(
                // Code statement
                adultSounds
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Calls a method
    CowSoundSet adultSounds();

    // Type declaration (class/interface/enum/record)
    sealed interface CowSoundSet permits CowSoundVariantImpl.CowSoundSetImpl {
        // Assigns a value
        Codec<CowSoundSet> CODEC = StructCodec.struct(
                // Code statement
                "ambient_sound", SoundEvent.CODEC, CowSoundSet::ambientSound,
                // Code statement
                "hurt_sound", SoundEvent.CODEC, CowSoundSet::hurtSound,
                // Code statement
                "death_sound", SoundEvent.CODEC, CowSoundSet::deathSound,
                // Code statement
                "step_sound", SoundEvent.CODEC, CowSoundSet::stepSound,
                // Code statement
                CowSoundSet::create);

        // Code statement
        static CowSoundSet create(
                // Code statement
                SoundEvent ambientSound,
                // Code statement
                SoundEvent hurtSound,
                // Code statement
                SoundEvent deathSound,
                // Code statement
                SoundEvent stepSound
        // Start of a method/block
        ) {
            // Returns a value to the caller
            return new CowSoundVariantImpl.CowSoundSetImpl(
                    // Code statement
                    ambientSound,
                    // Code statement
                    hurtSound,
                    // Code statement
                    deathSound,
                    // Code statement
                    stepSound
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Start of a method/block
        static CowSoundVariant.Builder builder() {
            // Returns a value to the caller
            return new CowSoundVariant.Builder();
        // End of a block/expression
        }

        // Calls a method
        SoundEvent ambientSound();

        // Calls a method
        SoundEvent hurtSound();

        // Calls a method
        SoundEvent deathSound();

        // Calls a method
        SoundEvent stepSound();

        // Type declaration (class/interface/enum/record)
        final class Builder {
            // Code statement
            private @UnknownNullability SoundEvent ambientSound;
            // Code statement
            private @UnknownNullability SoundEvent deathSound;
            // Code statement
            private @UnknownNullability SoundEvent hurtSound;
            // Code statement
            private @UnknownNullability SoundEvent stepSound;

            // Start of a method/block
            public CowSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Access to the current/parent object
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CowSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Access to the current/parent object
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CowSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Access to the current/parent object
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CowSoundSet.Builder stepSound(SoundEvent stepSound) {
                // Access to the current/parent object
                this.stepSound = Objects.requireNonNull(stepSound, "stepSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CowSoundSet build() {
                // Returns a value to the caller
                return new CowSoundVariantImpl.CowSoundSetImpl(
                        // Code statement
                        ambientSound,
                        // Code statement
                        hurtSound,
                        // Code statement
                        deathSound,
                        // Code statement
                        stepSound
                // End of a block/expression
                );
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private @UnknownNullability CowSoundSet adultSounds;

        // Start of a method/block
        public Builder adultSounds(CowSoundSet adultSounds) {
            // Access to the current/parent object
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public CowSoundVariant build() {
            // Returns a value to the caller
            return new CowSoundVariantImpl(
                    // Code statement
                    adultSounds
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
