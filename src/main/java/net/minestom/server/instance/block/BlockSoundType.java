// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

/**
 * Represents a BlockSoundType object, a set of sounds associated with a particular block (or group of blocks).
 * <p>
 * Note: Although this extends StaticProtocolObject, it's not actually Registry sent through the protocol, and purely for data organization.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface BlockSoundType extends StaticProtocolObject<BlockSoundType>, BlockSoundTypes permits BlockSoundImpl {

    /**
     * Returns the 'registry' data for the block sound type. Note: Block sound types are not an actual minecraft registry
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    RegistryData.BlockSoundTypeEntry registry();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Key key() {
        // Renvoie une valeur à l'appelant
        return registry().key();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default float volume() {
        // Renvoie une valeur à l'appelant
        return registry().volume();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default float pitch() {
        // Renvoie une valeur à l'appelant
        return registry().pitch();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default SoundEvent breakSound() {
        // Renvoie une valeur à l'appelant
        return registry().breakSound();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default SoundEvent hitSound() {
        // Renvoie une valeur à l'appelant
        return registry().hitSound();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default SoundEvent fallSound() {
        // Renvoie une valeur à l'appelant
        return registry().fallSound();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default SoundEvent placeSound() {
        // Renvoie une valeur à l'appelant
        return registry().placeSound();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default SoundEvent stepSound() {
        // Renvoie une valeur à l'appelant
        return registry().stepSound();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default int id() {
        // Renvoie une valeur à l'appelant
        return 0; // Not sent through packets in the protocol, also must be between 0 and [size of block sound type list] because id mappings are stored in an array
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Collection<BlockSoundType> values() {
        // Renvoie une valeur à l'appelant
        return BlockSoundImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable BlockSoundType fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable BlockSoundType fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return BlockSoundImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
