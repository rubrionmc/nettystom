// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

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
public sealed interface WolfSoundVariant extends WolfSoundVariants permits WolfSoundVariantImpl {
    // Calls a method
    NetworkBuffer.Type<RegistryKey<WolfSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::wolfSoundVariant);
    // Calls a method
    Codec<RegistryKey<WolfSoundVariant>> CODEC = RegistryKey.codec(Registries::wolfSoundVariant);

    // Assigns a value
    Codec<WolfSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "adult_sounds", WolfSoundSet.CODEC, WolfSoundVariant::adultSounds,
            // Code statement
            "baby_sounds", WolfSoundSet.CODEC, WolfSoundVariant::babySounds,
            // Code statement
            WolfSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:wolf_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<WolfSoundVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("wolf_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.WOLF_SOUND_VARIANTS);
    // End of a block/expression
    }

    // Code statement
    static WolfSoundVariant create(
            // Code statement
            WolfSoundSet adultSounds,
            // Code statement
            WolfSoundSet babySounds
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new WolfSoundVariantImpl(
                // Code statement
                adultSounds,
                // Code statement
                babySounds
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Calls a method
    WolfSoundSet adultSounds();

    // Calls a method
    WolfSoundSet babySounds();

    // Type declaration (class/interface/enum/record)
    sealed interface WolfSoundSet permits WolfSoundVariantImpl.WolfSoundSetImpl {
        // Assigns a value
        Codec<WolfSoundSet> CODEC = StructCodec.struct(
                // Code statement
                "ambient_sound", SoundEvent.CODEC, WolfSoundSet::ambientSound,
                // Code statement
                "death_sound", SoundEvent.CODEC, WolfSoundSet::deathSound,
                // Code statement
                "growl_sound", SoundEvent.CODEC, WolfSoundSet::growlSound,
                // Code statement
                "hurt_sound", SoundEvent.CODEC, WolfSoundSet::hurtSound,
                // Code statement
                "pant_sound", SoundEvent.CODEC, WolfSoundSet::pantSound,
                // Code statement
                "whine_sound", SoundEvent.CODEC, WolfSoundSet::whineSound,
                // Code statement
                "step_sound", SoundEvent.CODEC, WolfSoundSet::stepSound,
                // Code statement
                WolfSoundSet::create);

        // Code statement
        static WolfSoundSet create(
                // Code statement
                SoundEvent ambientSound,
                // Code statement
                SoundEvent deathSound,
                // Code statement
                SoundEvent growlSound,
                // Code statement
                SoundEvent hurtSound,
                // Code statement
                SoundEvent pantSound,
                // Code statement
                SoundEvent whineSound,
                // Code statement
                SoundEvent stepSound
        // Start of a method/block
        ) {
            // Returns a value to the caller
            return new WolfSoundVariantImpl.WolfSoundSetImpl(
                    // Code statement
                    ambientSound,
                    // Code statement
                    deathSound,
                    // Code statement
                    growlSound,
                    // Code statement
                    hurtSound,
                    // Code statement
                    pantSound,
                    // Code statement
                    whineSound,
                    // Code statement
                    stepSound
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Start of a method/block
        static WolfSoundVariant.Builder builder() {
            // Returns a value to the caller
            return new WolfSoundVariant.Builder();
        // End of a block/expression
        }

        // Calls a method
        SoundEvent ambientSound();

        // Calls a method
        SoundEvent deathSound();

        // Calls a method
        SoundEvent growlSound();

        // Calls a method
        SoundEvent hurtSound();

        // Calls a method
        SoundEvent pantSound();

        // Calls a method
        SoundEvent whineSound();

        // Calls a method
        SoundEvent stepSound();

        // Type declaration (class/interface/enum/record)
        final class Builder {
            // Code statement
            private @UnknownNullability SoundEvent ambientSound;
            // Code statement
            private @UnknownNullability SoundEvent deathSound;
            // Code statement
            private @UnknownNullability SoundEvent growlSound;
            // Code statement
            private @UnknownNullability SoundEvent hurtSound;
            // Code statement
            private @UnknownNullability SoundEvent pantSound;
            // Code statement
            private @UnknownNullability SoundEvent whineSound;
            // Code statement
            private @UnknownNullability SoundEvent stepSound;

            // Start of a method/block
            public WolfSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Access to the current/parent object
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public WolfSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Access to the current/parent object
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public WolfSoundSet.Builder growlSound(SoundEvent growlSound) {
                // Access to the current/parent object
                this.growlSound = Objects.requireNonNull(growlSound, "growlSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public WolfSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Access to the current/parent object
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public WolfSoundSet.Builder pantSound(SoundEvent pantSound) {
                // Access to the current/parent object
                this.pantSound = Objects.requireNonNull(pantSound, "pantSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public WolfSoundSet.Builder whineSound(SoundEvent whineSound) {
                // Access to the current/parent object
                this.whineSound = Objects.requireNonNull(whineSound, "whineSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public WolfSoundSet.Builder stepSound(SoundEvent stepSound) {
                // Access to the current/parent object
                this.stepSound = Objects.requireNonNull(stepSound, "stepSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public WolfSoundSet build() {
                // Returns a value to the caller
                return new WolfSoundVariantImpl.WolfSoundSetImpl(
                        // Code statement
                        ambientSound,
                        // Code statement
                        deathSound,
                        // Code statement
                        growlSound,
                        // Code statement
                        hurtSound,
                        // Code statement
                        pantSound,
                        // Code statement
                        whineSound,
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
        private @UnknownNullability WolfSoundSet adultSounds;
        // Code statement
        private @UnknownNullability WolfSoundSet babySounds;

        // Start of a method/block
        public Builder adultSounds(WolfSoundSet adultSounds) {
            // Access to the current/parent object
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder babySounds(WolfSoundSet babySounds) {
            // Access to the current/parent object
            this.babySounds = Objects.requireNonNull(babySounds, "babySounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public WolfSoundVariant build() {
            // Returns a value to the caller
            return new WolfSoundVariantImpl(
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
