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
public sealed interface PigSoundVariant extends PigSoundVariants permits PigSoundVariantImpl {
    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<PigSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::pigSoundVariant);
    // Appelle une méthode
    Codec<RegistryKey<PigSoundVariant>> CODEC = RegistryKey.codec(Registries::pigSoundVariant);

    // Affecte une valeur
    Codec<PigSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "adult_sounds", PigSoundSet.CODEC, PigSoundVariant::adultSounds,
            // Instruction de code
            "baby_sounds", PigSoundSet.CODEC, PigSoundVariant::babySounds,
            // Instruction de code
            PigSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:pig_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<PigSoundVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("pig_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.PIG_SOUND_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static PigSoundVariant create(
            // Instruction de code
            PigSoundSet adultSounds,
            // Instruction de code
            PigSoundSet babySounds
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new PigSoundVariantImpl(
                // Instruction de code
                adultSounds,
                // Instruction de code
                babySounds
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    PigSoundSet adultSounds();

    // Appelle une méthode
    PigSoundSet babySounds();

    // Déclaration de type (classe/interface/enum/record)
    sealed interface PigSoundSet permits PigSoundVariantImpl.PigSoundSetImpl {
        // Affecte une valeur
        Codec<PigSoundSet> CODEC = StructCodec.struct(
                // Instruction de code
                "ambient_sound", SoundEvent.CODEC, PigSoundSet::ambientSound,
                // Instruction de code
                "hurt_sound", SoundEvent.CODEC, PigSoundSet::hurtSound,
                // Instruction de code
                "death_sound", SoundEvent.CODEC, PigSoundSet::deathSound,
                // Instruction de code
                "step_sound", SoundEvent.CODEC, PigSoundSet::stepSound,
                // Instruction de code
                "eat_sound", SoundEvent.CODEC, PigSoundSet::eatSound,
                // Instruction de code
                PigSoundSet::create);

        // Instruction de code
        static PigSoundSet create(
                // Instruction de code
                SoundEvent ambientSound,
                // Instruction de code
                SoundEvent hurtSound,
                // Instruction de code
                SoundEvent deathSound,
                // Instruction de code
                SoundEvent stepSound,
                // Instruction de code
                SoundEvent eatSound
        // Début d'une méthode/d'un bloc
        ) {
            // Renvoie une valeur à l'appelant
            return new PigSoundVariantImpl.PigSoundSetImpl(
                    // Instruction de code
                    ambientSound,
                    // Instruction de code
                    hurtSound,
                    // Instruction de code
                    deathSound,
                    // Instruction de code
                    stepSound,
                    // Instruction de code
                    eatSound
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static PigSoundVariant.Builder builder() {
            // Renvoie une valeur à l'appelant
            return new PigSoundVariant.Builder();
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

        // Appelle une méthode
        SoundEvent eatSound();

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
            // Instruction de code
            private @UnknownNullability SoundEvent eatSound;

            // Début d'une méthode/d'un bloc
            public PigSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Accès à l'objet courant/parent
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public PigSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Accès à l'objet courant/parent
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public PigSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Accès à l'objet courant/parent
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public PigSoundSet.Builder stepSound(SoundEvent stepSound) {
                // Accès à l'objet courant/parent
                this.stepSound = Objects.requireNonNull(stepSound, "stepSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public PigSoundSet.Builder eatSound(SoundEvent eatSound) {
                // Accès à l'objet courant/parent
                this.eatSound = Objects.requireNonNull(eatSound, "eatSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public PigSoundSet build() {
                // Renvoie une valeur à l'appelant
                return new PigSoundVariantImpl.PigSoundSetImpl(
                        // Instruction de code
                        ambientSound,
                        // Instruction de code
                        hurtSound,
                        // Instruction de code
                        deathSound,
                        // Instruction de code
                        stepSound,
                        // Instruction de code
                        eatSound
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
        private @UnknownNullability PigSoundSet adultSounds;
        // Instruction de code
        private @UnknownNullability PigSoundSet babySounds;

        // Début d'une méthode/d'un bloc
        public Builder adultSounds(PigSoundSet adultSounds) {
            // Accès à l'objet courant/parent
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder babySounds(PigSoundSet babySounds) {
            // Accès à l'objet courant/parent
            this.babySounds = Objects.requireNonNull(babySounds, "babySounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public PigSoundVariant build() {
            // Renvoie une valeur à l'appelant
            return new PigSoundVariantImpl(
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
