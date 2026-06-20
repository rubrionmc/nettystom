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
public sealed interface CatSoundVariant extends CatSoundVariants permits CatSoundVariantImpl {
    // Calls a method
    NetworkBuffer.Type<RegistryKey<CatSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::catSoundVariant);
    // Calls a method
    Codec<RegistryKey<CatSoundVariant>> CODEC = RegistryKey.codec(Registries::catSoundVariant);

    // Assigns a value
    Codec<CatSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "adult_sounds", CatSoundSet.CODEC, CatSoundVariant::adultSounds,
            // Code statement
            "baby_sounds", CatSoundSet.CODEC, CatSoundVariant::babySounds,
            // Code statement
            CatSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:cat_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<CatSoundVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("cat_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.CAT_SOUND_VARIANTS);
    // End of a block/expression
    }

    // Code statement
    static CatSoundVariant create(
            // Code statement
            CatSoundSet adultSounds,
            // Code statement
            CatSoundSet babySounds
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new CatSoundVariantImpl(
                // Code statement
                adultSounds,
                // Code statement
                babySounds
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    // Calls a method
    CatSoundSet adultSounds();

    // Calls a method
    CatSoundSet babySounds();

    // Type declaration (class/interface/enum/record)
    sealed interface CatSoundSet permits CatSoundVariantImpl.CatSoundSetImpl {
        // Assigns a value
        Codec<CatSoundSet> CODEC = StructCodec.struct(
                // Code statement
                "ambient_sound", SoundEvent.CODEC, CatSoundSet::ambientSound,
                // Code statement
                "stray_ambient_sound", SoundEvent.CODEC, CatSoundSet::deathSound,
                // Code statement
                "hiss_sound", SoundEvent.CODEC, CatSoundSet::hissSound,
                // Code statement
                "hurt_sound", SoundEvent.CODEC, CatSoundSet::hurtSound,
                // Code statement
                "death_sound", SoundEvent.CODEC, CatSoundSet::deathSound,
                // Code statement
                "eat_sound", SoundEvent.CODEC, CatSoundSet::eatSound,
                // Code statement
                "beg_for_food_sound", SoundEvent.CODEC, CatSoundSet::begForFoodSound,
                // Code statement
                "purr_sound", SoundEvent.CODEC, CatSoundSet::purrSound,
                // Code statement
                "purreow_sound", SoundEvent.CODEC, CatSoundSet::purreowSound,
                // Code statement
                CatSoundSet::create);

        // Code statement
        static CatSoundSet create(
                // Code statement
                SoundEvent ambientSound,
                // Code statement
                SoundEvent strayAmbientSound,
                // Code statement
                SoundEvent hissSound,
                // Code statement
                SoundEvent hurtSound,
                // Code statement
                SoundEvent deathSound,
                // Code statement
                SoundEvent eatSound,
                // Code statement
                SoundEvent begForFoodSound,
                // Code statement
                SoundEvent purrSound,
                // Code statement
                SoundEvent purreowSound
        // Start of a method/block
        ) {
            // Returns a value to the caller
            return new CatSoundVariantImpl.CatSoundSetImpl(
                    // Code statement
                    ambientSound,
                    // Code statement
                    strayAmbientSound,
                    // Code statement
                    hissSound,
                    // Code statement
                    hurtSound,
                    // Code statement
                    deathSound,
                    // Code statement
                    eatSound,
                    // Code statement
                    begForFoodSound,
                    // Code statement
                    purrSound,
                    // Code statement
                    purreowSound
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Start of a method/block
        static CatSoundVariant.Builder builder() {
            // Returns a value to the caller
            return new CatSoundVariant.Builder();
        // End of a block/expression
        }

        // Calls a method
        SoundEvent ambientSound();

        // Calls a method
        SoundEvent strayAmbientSound();

        // Calls a method
        SoundEvent hissSound();

        // Calls a method
        SoundEvent hurtSound();

        // Calls a method
        SoundEvent deathSound();

        // Calls a method
        SoundEvent eatSound();

        // Calls a method
        SoundEvent begForFoodSound();

        // Calls a method
        SoundEvent purrSound();

        // Calls a method
        SoundEvent purreowSound();

        // Type declaration (class/interface/enum/record)
        final class Builder {
            // Code statement
            private @UnknownNullability SoundEvent ambientSound;
            // Code statement
            private @UnknownNullability SoundEvent strayAmbientSound;
            // Code statement
            private @UnknownNullability SoundEvent hissSound;
            // Code statement
            private @UnknownNullability SoundEvent hurtSound;
            // Code statement
            private @UnknownNullability SoundEvent deathSound;
            // Code statement
            private @UnknownNullability SoundEvent eatSound;
            // Code statement
            private @UnknownNullability SoundEvent begForFoodSound;
            // Code statement
            private @UnknownNullability SoundEvent purrSound;
            // Code statement
            private @UnknownNullability SoundEvent purreowSound;

            // Start of a method/block
            public CatSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Access to the current/parent object
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet.Builder strayAmbientSound(SoundEvent strayAmbientSound) {
                // Access to the current/parent object
                this.strayAmbientSound = Objects.requireNonNull(strayAmbientSound, "strayAmbientSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet.Builder hissSound(SoundEvent hissSound) {
                // Access to the current/parent object
                this.hissSound = Objects.requireNonNull(hissSound, "hissSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Access to the current/parent object
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Access to the current/parent object
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet.Builder eatSound(SoundEvent eatSound) {
                // Access to the current/parent object
                this.eatSound = Objects.requireNonNull(eatSound, "eatSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet.Builder begForFoodSound(SoundEvent begForFoodSound) {
                // Access to the current/parent object
                this.begForFoodSound = Objects.requireNonNull(begForFoodSound, "begForFoodSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet.Builder purrSound(SoundEvent purrSound) {
                // Access to the current/parent object
                this.purrSound = Objects.requireNonNull(purrSound, "purrSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet.Builder purreowSound(SoundEvent purreowSound) {
                // Access to the current/parent object
                this.purreowSound = Objects.requireNonNull(purreowSound, "purreowSound");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public CatSoundSet build() {
                // Returns a value to the caller
                return new CatSoundVariantImpl.CatSoundSetImpl(
                        // Code statement
                        ambientSound,
                        // Code statement
                        strayAmbientSound,
                        // Code statement
                        hissSound,
                        // Code statement
                        hurtSound,
                        // Code statement
                        deathSound,
                        // Code statement
                        eatSound,
                        // Code statement
                        begForFoodSound,
                        // Code statement
                        purrSound,
                        // Code statement
                        purreowSound
                // End of a block/expression
                );
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private @UnknownNullability CatSoundSet adultSounds;
        // Code statement
        private @UnknownNullability CatSoundSet babySounds;

        // Start of a method/block
        public Builder adultSounds(CatSoundSet adultSounds) {
            // Access to the current/parent object
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder babySounds(CatSoundSet babySounds) {
            // Access to the current/parent object
            this.babySounds = Objects.requireNonNull(babySounds, "babySounds");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public CatSoundVariant build() {
            // Returns a value to the caller
            return new CatSoundVariantImpl(
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
