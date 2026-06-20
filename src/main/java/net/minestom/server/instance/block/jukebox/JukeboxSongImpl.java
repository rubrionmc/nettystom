// Package declaration for this file
package net.minestom.server.instance.block.jukebox;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Type declaration (class/interface/enum/record)
record JukeboxSongImpl(
        // Code statement
        SoundEvent soundEvent,
        // Code statement
        Component description,
        // Code statement
        float lengthInSeconds,
        // Code statement
        int comparatorOutput
// Start of a method/block
) implements JukeboxSong {

    // Annotation for the following element
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Start of a method/block
    JukeboxSongImpl {
        // Calls a method
        Check.argCondition(soundEvent == null, "missing sound event");
        // Calls a method
        Check.argCondition(description == null, "missing description");
    // End of a block/expression
    }

// End of a block/expression
}
