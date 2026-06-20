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
public sealed interface PigSoundVariant extends PigSoundVariants permits PigSoundVariantImpl {
    // Calls a method
    NetworkBuffer.Type<RegistryKey<PigSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::pigSoundVariant);
    // Calls a method
    Codec<RegistryKey<PigSoundVariant>> CODEC = RegistryKey.codec(Registries::pigSoundVariant);

    // Assigns a value
    Codec<PigSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "adult_sounds", PigSoundSet.CODEC, PigSoundVariant::adultSounds,
            // Code statement
            "baby_sounds", PigSoundSet.CODEC, PigSoundVariant::babySounds,
            // Code statement
            PigSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:pig_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<PigSoundVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("pig_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.PIG_SOUND_VARIANTS);
    // End of a block/expression
    }

    // Code statement
    static PigSoundVariant create(
            // Code statement
            PigSoundSet adultSounds,
            // Code statement
            PigSoundSet babySounds
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new PigSoundVariantImpl(
                // Code statement
                adultSounds,
                // Code statement
                babySounds
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Calls a method
    PigSoundSet adultSounds();

    // Calls a method
    PigSoundSet babySounds();

    // Type declaration (class/interface/enum/record)
    sealed interface PigSoundSet permits PigSoundVariantImpl.PigSoundSetImpl {
        // Assigns a value
        Codec<PigSoundSet> CODEC = StructCodec.struct(
                // Code statement
                "ambient_sound", SoundEvent.CODEC, PigSoundSet::ambientSound,
                // Code statement
                "hurt_sound", SoundEvent.CODEC, PigSoundSet::hurtSound,
                // Code statement
                "death_sound", SoundEvent.CODEC, PigSoundSet::deathSound,
                // Code statement
                "step_sound", SoundEvent.CODEC, PigSoundSet::stepSound,
                // Code statement
                "eat_sound", SoundEvent.CODEC, PigSoundSet::eatSound,
                // Code statement
                PigSoundSet::create);

        // Code statement
        static PigSoundSet create(
                // Code statement
                SoundEvent ambientSound,
                // Code statement
                SoundEvent hurtSound,
                // Code statement
                SoundEvent deathSound,
                // Code statement
                SoundEvent stepSound,
                // Code statement
                SoundEvent eatSound
        // Start of a method/block
        ) {
            // Returns a value to the caller
            return new PigSoundVariantImpl.PigSoundSetImpl(
                    // Code statement
                    ambientSound,
                    // Code statement
                    hurtSound,
                    // Code statement
                    deathSound,
                    // Code statement
                    stepSound,
                    // Code statement
                    eatSound
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Start of a method/block
        static PigSoundVariant.Builder builder() {
            // Returns a value to the caller
            return new PigSoundVariant.Builder();
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

        // Calls a method
        SoundEvent eatSound();

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
            // Code statement
            private @UnknownNullability SoundEvent eatSound;

            // Start of a method/block
            public PigSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Access to the current/parent object
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public PigSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Access to the current/parent object
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public PigSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Access to the current/parent object
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public PigSoundSet.Builder stepSound(SoundEvent stepSound) {
                // Access to the current/parent object
                this.stepSound = Objects.requireNonNull(stepSound, "stepSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public PigSoundSet.Builder eatSound(SoundEvent eatSound) {
                // Access to the current/parent object
                this.eatSound = Objects.requireNonNull(eatSound, "eatSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public PigSoundSet build() {
                // Returns a value to the caller
                return new PigSoundVariantImpl.PigSoundSetImpl(
                        // Code statement
                        ambientSound,
                        // Code statement
                        hurtSound,
                        // Code statement
                        deathSound,
                        // Code statement
                        stepSound,
                        // Code statement
                        eatSound
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
        private @UnknownNullability PigSoundSet adultSounds;
        // Code statement
        private @UnknownNullability PigSoundSet babySounds;

        // Start of a method/block
        public Builder adultSounds(PigSoundSet adultSounds) {
            // Access to the current/parent object
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder babySounds(PigSoundSet babySounds) {
            // Access to the current/parent object
            this.babySounds = Objects.requireNonNull(babySounds, "babySounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public PigSoundVariant build() {
            // Returns a value to the caller
            return new PigSoundVariantImpl(
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
