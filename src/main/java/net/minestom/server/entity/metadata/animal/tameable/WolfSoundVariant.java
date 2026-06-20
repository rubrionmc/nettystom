// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public sealed interface WolfSoundVariant extends WolfSoundVariants permits WolfSoundVariantImpl {
    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<WolfSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::wolfSoundVariant);
    // Appelle une méthode
    Codec<RegistryKey<WolfSoundVariant>> CODEC = RegistryKey.codec(Registries::wolfSoundVariant);

    // Affecte une valeur
    Codec<WolfSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "adult_sounds", WolfSoundSet.CODEC, WolfSoundVariant::adultSounds,
            // Instruction de code
            "baby_sounds", WolfSoundSet.CODEC, WolfSoundVariant::babySounds,
            // Instruction de code
            WolfSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:wolf_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<WolfSoundVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("wolf_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.WOLF_SOUND_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static WolfSoundVariant create(
            // Instruction de code
            WolfSoundSet adultSounds,
            // Instruction de code
            WolfSoundSet babySounds
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new WolfSoundVariantImpl(
                // Instruction de code
                adultSounds,
                // Instruction de code
                babySounds
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    WolfSoundSet adultSounds();

    // Appelle une méthode
    WolfSoundSet babySounds();

    // Déclaration de type (classe/interface/enum/record)
    sealed interface WolfSoundSet permits WolfSoundVariantImpl.WolfSoundSetImpl {
        // Affecte une valeur
        Codec<WolfSoundSet> CODEC = StructCodec.struct(
                // Instruction de code
                "ambient_sound", SoundEvent.CODEC, WolfSoundSet::ambientSound,
                // Instruction de code
                "death_sound", SoundEvent.CODEC, WolfSoundSet::deathSound,
                // Instruction de code
                "growl_sound", SoundEvent.CODEC, WolfSoundSet::growlSound,
                // Instruction de code
                "hurt_sound", SoundEvent.CODEC, WolfSoundSet::hurtSound,
                // Instruction de code
                "pant_sound", SoundEvent.CODEC, WolfSoundSet::pantSound,
                // Instruction de code
                "whine_sound", SoundEvent.CODEC, WolfSoundSet::whineSound,
                // Instruction de code
                "step_sound", SoundEvent.CODEC, WolfSoundSet::stepSound,
                // Instruction de code
                WolfSoundSet::create);

        // Instruction de code
        static WolfSoundSet create(
                // Instruction de code
                SoundEvent ambientSound,
                // Instruction de code
                SoundEvent deathSound,
                // Instruction de code
                SoundEvent growlSound,
                // Instruction de code
                SoundEvent hurtSound,
                // Instruction de code
                SoundEvent pantSound,
                // Instruction de code
                SoundEvent whineSound,
                // Instruction de code
                SoundEvent stepSound
        // Début d'une méthode/d'un bloc
        ) {
            // Renvoie une valeur à l'appelant
            return new WolfSoundVariantImpl.WolfSoundSetImpl(
                    // Instruction de code
                    ambientSound,
                    // Instruction de code
                    deathSound,
                    // Instruction de code
                    growlSound,
                    // Instruction de code
                    hurtSound,
                    // Instruction de code
                    pantSound,
                    // Instruction de code
                    whineSound,
                    // Instruction de code
                    stepSound
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static WolfSoundVariant.Builder builder() {
            // Renvoie une valeur à l'appelant
            return new WolfSoundVariant.Builder();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        SoundEvent ambientSound();

        // Appelle une méthode
        SoundEvent deathSound();

        // Appelle une méthode
        SoundEvent growlSound();

        // Appelle une méthode
        SoundEvent hurtSound();

        // Appelle une méthode
        SoundEvent pantSound();

        // Appelle une méthode
        SoundEvent whineSound();

        // Appelle une méthode
        SoundEvent stepSound();

        // Déclaration de type (classe/interface/enum/record)
        final class Builder {
            // Instruction de code
            private @UnknownNullability SoundEvent ambientSound;
            // Instruction de code
            private @UnknownNullability SoundEvent deathSound;
            // Instruction de code
            private @UnknownNullability SoundEvent growlSound;
            // Instruction de code
            private @UnknownNullability SoundEvent hurtSound;
            // Instruction de code
            private @UnknownNullability SoundEvent pantSound;
            // Instruction de code
            private @UnknownNullability SoundEvent whineSound;
            // Instruction de code
            private @UnknownNullability SoundEvent stepSound;

            // Début d'une méthode/d'un bloc
            public WolfSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Accès à l'objet courant/parent
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public WolfSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Accès à l'objet courant/parent
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public WolfSoundSet.Builder growlSound(SoundEvent growlSound) {
                // Accès à l'objet courant/parent
                this.growlSound = Objects.requireNonNull(growlSound, "growlSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public WolfSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Accès à l'objet courant/parent
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public WolfSoundSet.Builder pantSound(SoundEvent pantSound) {
                // Accès à l'objet courant/parent
                this.pantSound = Objects.requireNonNull(pantSound, "pantSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public WolfSoundSet.Builder whineSound(SoundEvent whineSound) {
                // Accès à l'objet courant/parent
                this.whineSound = Objects.requireNonNull(whineSound, "whineSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public WolfSoundSet.Builder stepSound(SoundEvent stepSound) {
                // Accès à l'objet courant/parent
                this.stepSound = Objects.requireNonNull(stepSound, "stepSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public WolfSoundSet build() {
                // Renvoie une valeur à l'appelant
                return new WolfSoundVariantImpl.WolfSoundSetImpl(
                        // Instruction de code
                        ambientSound,
                        // Instruction de code
                        deathSound,
                        // Instruction de code
                        growlSound,
                        // Instruction de code
                        hurtSound,
                        // Instruction de code
                        pantSound,
                        // Instruction de code
                        whineSound,
                        // Instruction de code
                        stepSound
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private @UnknownNullability WolfSoundSet adultSounds;
        // Instruction de code
        private @UnknownNullability WolfSoundSet babySounds;

        // Début d'une méthode/d'un bloc
        public Builder adultSounds(WolfSoundSet adultSounds) {
            // Accès à l'objet courant/parent
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder babySounds(WolfSoundSet babySounds) {
            // Accès à l'objet courant/parent
            this.babySounds = Objects.requireNonNull(babySounds, "babySounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public WolfSoundVariant build() {
            // Renvoie une valeur à l'appelant
            return new WolfSoundVariantImpl(
                    // Instruction de code
                    adultSounds,
                    // Instruction de code
                    babySounds
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
