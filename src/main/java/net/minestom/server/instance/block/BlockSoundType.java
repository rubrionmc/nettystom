// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

/**
 * Represents a BlockSoundType object, a set of sounds associated with a particular block (or group of blocks).
 * <p>
 * Note: Although this extends StaticProtocolObject, it's not actually Registry sent through the protocol, and purely for data organization.
 */
// Type declaration (class/interface/enum/record)
public sealed interface BlockSoundType extends StaticProtocolObject<BlockSoundType>, BlockSoundTypes permits BlockSoundImpl {

    /**
     * Returns the 'registry' data for the block sound type. Note: Block sound types are not an actual minecraft registry
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    RegistryData.BlockSoundTypeEntry registry();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Key key() {
        // Returns a value to the caller
        return registry().key();
    // End of a block/expression
    }

    // Start of a method/block
    default float volume() {
        // Returns a value to the caller
        return registry().volume();
    // End of a block/expression
    }

    // Start of a method/block
    default float pitch() {
        // Returns a value to the caller
        return registry().pitch();
    // End of a block/expression
    }

    // Start of a method/block
    default SoundEvent breakSound() {
        // Returns a value to the caller
        return registry().breakSound();
    // End of a block/expression
    }

    // Start of a method/block
    default SoundEvent hitSound() {
        // Returns a value to the caller
        return registry().hitSound();
    // End of a block/expression
    }

    // Start of a method/block
    default SoundEvent fallSound() {
        // Returns a value to the caller
        return registry().fallSound();
    // End of a block/expression
    }

    // Start of a method/block
    default SoundEvent placeSound() {
        // Returns a value to the caller
        return registry().placeSound();
    // End of a block/expression
    }

    // Start of a method/block
    default SoundEvent stepSound() {
        // Returns a value to the caller
        return registry().stepSound();
    // End of a block/expression
    }

    // Start of a method/block
    default int id() {
        // Returns a value to the caller
        return 0; // Not sent through packets in the protocol, also must be between 0 and [size of block sound type list] because id mappings are stored in an array
    // End of a block/expression
    }

    // Start of a method/block
    static Collection<BlockSoundType> values() {
        // Returns a value to the caller
        return BlockSoundImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable BlockSoundType fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable BlockSoundType fromKey(Key key) {
        // Returns a value to the caller
        return BlockSoundImpl.REGISTRY.get(key);
    // End of a block/expression
    }

// End of a block/expression
}
