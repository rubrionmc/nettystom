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
public sealed interface CatSoundVariant extends CatSoundVariants permits CatSoundVariantImpl {
    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<CatSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::catSoundVariant);
    // Appelle une méthode
    Codec<RegistryKey<CatSoundVariant>> CODEC = RegistryKey.codec(Registries::catSoundVariant);

    // Affecte une valeur
    Codec<CatSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "adult_sounds", CatSoundSet.CODEC, CatSoundVariant::adultSounds,
            // Instruction de code
            "baby_sounds", CatSoundSet.CODEC, CatSoundVariant::babySounds,
            // Instruction de code
            CatSoundVariant::create);

    /**
     * Creates a new instance of the "minecraft:cat_sound_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<CatSoundVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("cat_sound_variant"), REGISTRY_CODEC, RegistryData.Resource.CAT_SOUND_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static CatSoundVariant create(
            // Instruction de code
            CatSoundSet adultSounds,
            // Instruction de code
            CatSoundSet babySounds
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new CatSoundVariantImpl(
                // Instruction de code
                adultSounds,
                // Instruction de code
                babySounds
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    CatSoundSet adultSounds();

    // Appelle une méthode
    CatSoundSet babySounds();

    // Déclaration de type (classe/interface/enum/record)
    sealed interface CatSoundSet permits CatSoundVariantImpl.CatSoundSetImpl {
        // Affecte une valeur
        Codec<CatSoundSet> CODEC = StructCodec.struct(
                // Instruction de code
                "ambient_sound", SoundEvent.CODEC, CatSoundSet::ambientSound,
                // Instruction de code
                "stray_ambient_sound", SoundEvent.CODEC, CatSoundSet::deathSound,
                // Instruction de code
                "hiss_sound", SoundEvent.CODEC, CatSoundSet::hissSound,
                // Instruction de code
                "hurt_sound", SoundEvent.CODEC, CatSoundSet::hurtSound,
                // Instruction de code
                "death_sound", SoundEvent.CODEC, CatSoundSet::deathSound,
                // Instruction de code
                "eat_sound", SoundEvent.CODEC, CatSoundSet::eatSound,
                // Instruction de code
                "beg_for_food_sound", SoundEvent.CODEC, CatSoundSet::begForFoodSound,
                // Instruction de code
                "purr_sound", SoundEvent.CODEC, CatSoundSet::purrSound,
                // Instruction de code
                "purreow_sound", SoundEvent.CODEC, CatSoundSet::purreowSound,
                // Instruction de code
                CatSoundSet::create);

        // Instruction de code
        static CatSoundSet create(
                // Instruction de code
                SoundEvent ambientSound,
                // Instruction de code
                SoundEvent strayAmbientSound,
                // Instruction de code
                SoundEvent hissSound,
                // Instruction de code
                SoundEvent hurtSound,
                // Instruction de code
                SoundEvent deathSound,
                // Instruction de code
                SoundEvent eatSound,
                // Instruction de code
                SoundEvent begForFoodSound,
                // Instruction de code
                SoundEvent purrSound,
                // Instruction de code
                SoundEvent purreowSound
        // Début d'une méthode/d'un bloc
        ) {
            // Renvoie une valeur à l'appelant
            return new CatSoundVariantImpl.CatSoundSetImpl(
                    // Instruction de code
                    ambientSound,
                    // Instruction de code
                    strayAmbientSound,
                    // Instruction de code
                    hissSound,
                    // Instruction de code
                    hurtSound,
                    // Instruction de code
                    deathSound,
                    // Instruction de code
                    eatSound,
                    // Instruction de code
                    begForFoodSound,
                    // Instruction de code
                    purrSound,
                    // Instruction de code
                    purreowSound
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static CatSoundVariant.Builder builder() {
            // Renvoie une valeur à l'appelant
            return new CatSoundVariant.Builder();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        SoundEvent ambientSound();

        // Appelle une méthode
        SoundEvent strayAmbientSound();

        // Appelle une méthode
        SoundEvent hissSound();

        // Appelle une méthode
        SoundEvent hurtSound();

        // Appelle une méthode
        SoundEvent deathSound();

        // Appelle une méthode
        SoundEvent eatSound();

        // Appelle une méthode
        SoundEvent begForFoodSound();

        // Appelle une méthode
        SoundEvent purrSound();

        // Appelle une méthode
        SoundEvent purreowSound();

        // Déclaration de type (classe/interface/enum/record)
        final class Builder {
            // Instruction de code
            private @UnknownNullability SoundEvent ambientSound;
            // Instruction de code
            private @UnknownNullability SoundEvent strayAmbientSound;
            // Instruction de code
            private @UnknownNullability SoundEvent hissSound;
            // Instruction de code
            private @UnknownNullability SoundEvent hurtSound;
            // Instruction de code
            private @UnknownNullability SoundEvent deathSound;
            // Instruction de code
            private @UnknownNullability SoundEvent eatSound;
            // Instruction de code
            private @UnknownNullability SoundEvent begForFoodSound;
            // Instruction de code
            private @UnknownNullability SoundEvent purrSound;
            // Instruction de code
            private @UnknownNullability SoundEvent purreowSound;

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder ambientSound(SoundEvent ambientSound) {
                // Accès à l'objet courant/parent
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambientSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder strayAmbientSound(SoundEvent strayAmbientSound) {
                // Accès à l'objet courant/parent
                this.strayAmbientSound = Objects.requireNonNull(strayAmbientSound, "strayAmbientSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder hissSound(SoundEvent hissSound) {
                // Accès à l'objet courant/parent
                this.hissSound = Objects.requireNonNull(hissSound, "hissSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder hurtSound(SoundEvent hurtSound) {
                // Accès à l'objet courant/parent
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurtSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder deathSound(SoundEvent deathSound) {
                // Accès à l'objet courant/parent
                this.deathSound = Objects.requireNonNull(deathSound, "deathSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder eatSound(SoundEvent eatSound) {
                // Accès à l'objet courant/parent
                this.eatSound = Objects.requireNonNull(eatSound, "eatSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder begForFoodSound(SoundEvent begForFoodSound) {
                // Accès à l'objet courant/parent
                this.begForFoodSound = Objects.requireNonNull(begForFoodSound, "begForFoodSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder purrSound(SoundEvent purrSound) {
                // Accès à l'objet courant/parent
                this.purrSound = Objects.requireNonNull(purrSound, "purrSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet.Builder purreowSound(SoundEvent purreowSound) {
                // Accès à l'objet courant/parent
                this.purreowSound = Objects.requireNonNull(purreowSound, "purreowSound");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public CatSoundSet build() {
                // Renvoie une valeur à l'appelant
                return new CatSoundVariantImpl.CatSoundSetImpl(
                        // Instruction de code
                        ambientSound,
                        // Instruction de code
                        strayAmbientSound,
                        // Instruction de code
                        hissSound,
                        // Instruction de code
                        hurtSound,
                        // Instruction de code
                        deathSound,
                        // Instruction de code
                        eatSound,
                        // Instruction de code
                        begForFoodSound,
                        // Instruction de code
                        purrSound,
                        // Instruction de code
                        purreowSound
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private @UnknownNullability CatSoundSet adultSounds;
        // Instruction de code
        private @UnknownNullability CatSoundSet babySounds;

        // Début d'une méthode/d'un bloc
        public Builder adultSounds(CatSoundSet adultSounds) {
            // Accès à l'objet courant/parent
            this.adultSounds = Objects.requireNonNull(adultSounds, "adultSounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder babySounds(CatSoundSet babySounds) {
            // Accès à l'objet courant/parent
            this.babySounds = Objects.requireNonNull(babySounds, "babySounds");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public CatSoundVariant build() {
            // Renvoie une valeur à l'appelant
            return new CatSoundVariantImpl(
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
