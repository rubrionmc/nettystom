// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

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
public sealed interface ChickenSoundVariant extends ChickenSoundVariants permits ChickenSoundVariantImpl {
    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<ChickenSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::chickenSoundVariant);
    // Appelle une méthode
    Codec<RegistryKey<ChickenSoundVariant>> CODEC = RegistryKey.codec(Registries::chickenSoundVariant);

    // Affecte une valeur
    Codec<ChickenSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "adult_sounds", ChickenSoundSet.CODEC, ChickenSoundVariant::adultSounds,
            // Instruction de code
            "baby_sounds", ChickenSoundSet.CODEC, ChickenSoundVariant::babySounds,
            // Instruction de code
            ChickenSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:chicken_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<ChickenSoundVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("chicken_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.CHICKEN_SOUND_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static ChickenSoundVariant create(
            // Instruction de code
            ChickenSoundSet adultSounds,
            // Instruction de code
            ChickenSoundSet babySounds
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new ChickenSoundVariantImpl(
                // Instruction de code
                adultSounds,
                // Instruction de code
                babySounds
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    ChickenSoundSet adultSounds();

    // Appelle une méthode
    ChickenSoundSet babySounds();

    // Déclaration de type (classe/interface/enum/record)
    sealed interface ChickenSoundSet permits ChickenSoundVariantImpl.ChickenSoundSetImpl {
        // Affecte une valeur
        Codec<ChickenSoundSet> CODEC = StructCodec.struct(
                // Instruction de code
                "ambient_sound", SoundEvent.CODEC, ChickenSoundSet::ambientSound,
                // Instruction de code
                "hurt_sound", SoundEvent.CODEC, ChickenSoundSet::hurtSound,
                // Instruction de code
                "death_sound", SoundEvent.CODEC, ChickenSoundSet::deathSound,
                // Instruction de code
                "step_sound", SoundEvent.CODEC, ChickenSoundSet::stepSound,
                // Instruction de code
                ChickenSoundSet::create);

        // Instruction de code
        static ChickenSoundSet create(
                // Instruction de code
                SoundEvent ambientSound,
                // Instruction de code
                SoundEvent hurtSound,
                // Instruction de code
                SoundEvent deathSound,
                // Instruction de code
                SoundEvent stepSound
        // Début d'une méthode/d'un bloc
        ) {
            // Renvoie une valeur à l'appelant
            return new ChickenSoundVariantImpl.ChickenSoundSetImpl(
                    // Instruction de code
                    ambientSound,
                    // Instruction de code
                    hurtSound,
                    // Instruction de code
                    deathSound,
                    // Instruction de code
                    stepSound
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static ChickenSoundVariant.Builder builder() {
            // Renvoie une valeur à l'appelant
            return new ChickenSoundVariant.Builder();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        SoundEvent ambientSound();

        // Appelle une méthode
        SoundEvent hurtSound();

        // Appelle une méthode
        SoundEvent deathSound();

        // Appelle une méthode
        SoundEvent stepSound();

        // Déclaration de type (classe/interface/enum/record)
        final class Builder {
            // Instruction de code
            private @UnknownNullability SoundEvent ambientSound;
            // Instruction de code
            private @UnknownNullability SoundEvent deathSound;
            // Instruction de code
            private @UnknownNullability SoundEvent hurtSound;
            // Instruction de code
            private @UnknownNullability SoundEvent stepSound;

            // Début d'une méthode/d'un bloc
            public ChickenSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Accès à l'objet courant/parent
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public ChickenSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Accès à l'objet courant/parent
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public ChickenSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Accès à l'objet courant/parent
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public ChickenSoundSet.Builder stepSound(SoundEvent stepSound) {
                // Accès à l'objet courant/parent
                this.stepSound = Objects.requireNonNull(stepSound, "stepSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public ChickenSoundSet build() {
                // Renvoie une valeur à l'appelant
                return new ChickenSoundVariantImpl.ChickenSoundSetImpl(
                        // Instruction de code
                        ambientSound,
                        // Instruction de code
                        hurtSound,
                        // Instruction de code
                        deathSound,
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
        private @UnknownNullability ChickenSoundSet adultSounds;
        // Instruction de code
        private @UnknownNullability ChickenSoundSet babySounds;

        // Début d'une méthode/d'un bloc
        public Builder adultSounds(ChickenSoundSet adultSounds) {
            // Accès à l'objet courant/parent
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder babySounds(ChickenSoundSet babySounds) {
            // Accès à l'objet courant/parent
            this.babySounds = Objects.requireNonNull(babySounds, "babySounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public ChickenSoundVariant build() {
            // Renvoie une valeur à l'appelant
            return new ChickenSoundVariantImpl(
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
