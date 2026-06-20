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

// Déclaration de type (classe/interface/enum/record)
public sealed interface WolfSoundVariant extends WolfSoundVariants permits WolfSoundVariantImpl {
    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<WolfSoundVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::wolfSoundVariant);
    // Appelle une méthode
    Codec<RegistryKey<WolfSoundVariant>> CODEC = RegistryKey.codec(Registries::wolfSoundVariant);

    // Affecte une valeur
    Codec<WolfSoundVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "ambient_sound", SoundEvent.CODEC, WolfSoundVariant::ambientSound,
            // Instruction de code
            "death_sound", SoundEvent.CODEC, WolfSoundVariant::deathSound,
            // Instruction de code
            "growl_sound", SoundEvent.CODEC, WolfSoundVariant::growlSound,
            // Instruction de code
            "hurt_sound", SoundEvent.CODEC, WolfSoundVariant::hurtSound,
            // Instruction de code
            "pant_sound", SoundEvent.CODEC, WolfSoundVariant::pantSound,
            // Instruction de code
            "whine_sound", SoundEvent.CODEC, WolfSoundVariant::whineSound,
            // Instruction de code
            WolfSoundVariantImpl::new);

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
            SoundEvent whineSound
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new WolfSoundVariantImpl(
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
                whineSound
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

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private SoundEvent ambientSound;
        // Instruction de code
        private SoundEvent deathSound;
        // Instruction de code
        private SoundEvent growlSound;
        // Instruction de code
        private SoundEvent hurtSound;
        // Instruction de code
        private SoundEvent pantSound;
        // Instruction de code
        private SoundEvent whineSound;

        // Début d'une méthode/d'un bloc
        public Builder ambientSound(SoundEvent ambientSound) {
            // Accès à l'objet courant/parent
            this.ambientSound = ambientSound;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder deathSound(SoundEvent deathSound) {
            // Accès à l'objet courant/parent
            this.deathSound = deathSound;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder growlSound(SoundEvent growlSound) {
            // Accès à l'objet courant/parent
            this.growlSound = growlSound;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder hurtSound(SoundEvent hurtSound) {
            // Accès à l'objet courant/parent
            this.hurtSound = hurtSound;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder pantSound(SoundEvent pantSound) {
            // Accès à l'objet courant/parent
            this.pantSound = pantSound;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder whineSound(SoundEvent whineSound) {
            // Accès à l'objet courant/parent
            this.whineSound = whineSound;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public WolfSoundVariant build() {
            // Renvoie une valeur à l'appelant
            return new WolfSoundVariantImpl(
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
                    whineSound
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
