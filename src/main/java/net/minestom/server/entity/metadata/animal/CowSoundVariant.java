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

/**
 * Sounds used by the cow, set with {@link net.minestom.server.component.DataComponents#COW_SOUND_VARIANT}
 * currently {@link #adultSounds()} are shared between baby and adult. This is expected to change in a future release.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface CowSoundVariant extends CowSoundVariants permits CowSoundVariantImpl {
    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<CowSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::cowSoundVariant);
    // Appelle une méthode
    Codec<RegistryKey<CowSoundVariant>> CODEC = RegistryKey.codec(Registries::cowSoundVariant);

    // Affecte une valeur
    Codec<CowSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            StructCodec.INLINE, CowSoundSet.CODEC, CowSoundVariant::adultSounds,
            // Instruction de code
            CowSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:cow_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<CowSoundVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("cow_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.COW_SOUND_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static CowSoundVariant create(
            // Instruction de code
            CowSoundSet adultSounds
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new CowSoundVariantImpl(
                // Instruction de code
                adultSounds
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    CowSoundSet adultSounds();

    // Déclaration de type (classe/interface/enum/record)
    sealed interface CowSoundSet permits CowSoundVariantImpl.CowSoundSetImpl {
        // Affecte une valeur
        Codec<CowSoundSet> CODEC = StructCodec.struct(
                // Instruction de code
                "ambient_sound", SoundEvent.CODEC, CowSoundSet::ambientSound,
                // Instruction de code
                "hurt_sound", SoundEvent.CODEC, CowSoundSet::hurtSound,
                // Instruction de code
                "death_sound", SoundEvent.CODEC, CowSoundSet::deathSound,
                // Instruction de code
                "step_sound", SoundEvent.CODEC, CowSoundSet::stepSound,
                // Instruction de code
                CowSoundSet::create);

        // Instruction de code
        static CowSoundSet create(
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
            return new CowSoundVariantImpl.CowSoundSetImpl(
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
        static CowSoundVariant.Builder builder() {
            // Renvoie une valeur à l'appelant
            return new CowSoundVariant.Builder();
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
            public CowSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Accès à l'objet courant/parent
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CowSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Accès à l'objet courant/parent
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CowSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Accès à l'objet courant/parent
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CowSoundSet.Builder stepSound(SoundEvent stepSound) {
                // Accès à l'objet courant/parent
                this.stepSound = Objects.requireNonNull(stepSound, "stepSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CowSoundSet build() {
                // Renvoie une valeur à l'appelant
                return new CowSoundVariantImpl.CowSoundSetImpl(
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
        private @UnknownNullability CowSoundSet adultSounds;

        // Début d'une méthode/d'un bloc
        public Builder adultSounds(CowSoundSet adultSounds) {
            // Accès à l'objet courant/parent
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public CowSoundVariant build() {
            // Renvoie une valeur à l'appelant
            return new CowSoundVariantImpl(
                    // Instruction de code
                    adultSounds
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
