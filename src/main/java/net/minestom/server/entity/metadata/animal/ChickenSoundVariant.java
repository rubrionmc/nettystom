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

// Type declaration (class/interface/enum/record)
public sealed interface ChickenSoundVariant extends ChickenSoundVariants permits ChickenSoundVariantImpl {
    // Calls a method
    NetworkBuffer.Type<RegistryKey<ChickenSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::chickenSoundVariant);
    // Calls a method
    Codec<RegistryKey<ChickenSoundVariant>> CODEC = RegistryKey.codec(Registries::chickenSoundVariant);

    // Assigns a value
    Codec<ChickenSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "adult_sounds", ChickenSoundSet.CODEC, ChickenSoundVariant::adultSounds,
            // Code statement
            "baby_sounds", ChickenSoundSet.CODEC, ChickenSoundVariant::babySounds,
            // Code statement
            ChickenSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:chicken_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<ChickenSoundVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("chicken_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.CHICKEN_SOUND_VARIANTS);
    // End of a block/expression
    }

    // Code statement
    static ChickenSoundVariant create(
            // Code statement
            ChickenSoundSet adultSounds,
            // Code statement
            ChickenSoundSet babySounds
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new ChickenSoundVariantImpl(
                // Code statement
                adultSounds,
                // Code statement
                babySounds
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Calls a method
    ChickenSoundSet adultSounds();

    // Calls a method
    ChickenSoundSet babySounds();

    // Type declaration (class/interface/enum/record)
    sealed interface ChickenSoundSet permits ChickenSoundVariantImpl.ChickenSoundSetImpl {
        // Assigns a value
        Codec<ChickenSoundSet> CODEC = StructCodec.struct(
                // Code statement
                "ambient_sound", SoundEvent.CODEC, ChickenSoundSet::ambientSound,
                // Code statement
                "hurt_sound", SoundEvent.CODEC, ChickenSoundSet::hurtSound,
                // Code statement
                "death_sound", SoundEvent.CODEC, ChickenSoundSet::deathSound,
                // Code statement
                "step_sound", SoundEvent.CODEC, ChickenSoundSet::stepSound,
                // Code statement
                ChickenSoundSet::create);

        // Code statement
        static ChickenSoundSet create(
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
            return new ChickenSoundVariantImpl.ChickenSoundSetImpl(
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
        static ChickenSoundVariant.Builder builder() {
            // Returns a value to the caller
            return new ChickenSoundVariant.Builder();
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
            public ChickenSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Access to the current/parent object
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public ChickenSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Access to the current/parent object
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public ChickenSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Access to the current/parent object
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public ChickenSoundSet.Builder stepSound(SoundEvent stepSound) {
                // Access to the current/parent object
                this.stepSound = Objects.requireNonNull(stepSound, "stepSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public ChickenSoundSet build() {
                // Returns a value to the caller
                return new ChickenSoundVariantImpl.ChickenSoundSetImpl(
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
        private @UnknownNullability ChickenSoundSet adultSounds;
        // Code statement
        private @UnknownNullability ChickenSoundSet babySounds;

        // Start of a method/block
        public Builder adultSounds(ChickenSoundSet adultSounds) {
            // Access to the current/parent object
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder babySounds(ChickenSoundSet babySounds) {
            // Access to the current/parent object
            this.babySounds = Objects.requireNonNull(babySounds, "babySounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public ChickenSoundVariant build() {
            // Returns a value to the caller
            return new ChickenSoundVariantImpl(
                    // Code statement
                    adultSounds,
                    // Code statement
                    babySounds
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
