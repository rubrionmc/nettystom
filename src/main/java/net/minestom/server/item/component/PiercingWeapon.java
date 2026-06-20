// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record PiercingWeapon(
        // Code statement
        boolean dealsKnockback,
        // Code statement
        boolean dismounts,
        // Annotation for the following element
        @Nullable SoundEvent sound,
        // Annotation for the following element
        @Nullable SoundEvent hitSound
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<PiercingWeapon> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BOOLEAN, PiercingWeapon::dealsKnockback,
            // Code statement
            NetworkBuffer.BOOLEAN, PiercingWeapon::dismounts,
            // Code statement
            SoundEvent.NETWORK_TYPE.optional(), PiercingWeapon::sound,
            // Code statement
            SoundEvent.NETWORK_TYPE.optional(), PiercingWeapon::hitSound,
            // Code statement
            PiercingWeapon::new);
    // Assigns a value
    public static final Codec<PiercingWeapon> CODEC = StructCodec.struct(
            // Code statement
            "deals_knockback", Codec.BOOLEAN.optional(true), PiercingWeapon::dealsKnockback,
            // Code statement
            "dismounts", Codec.BOOLEAN.optional(false), PiercingWeapon::dismounts,
            // Code statement
            "sound", SoundEvent.CODEC.optional(), PiercingWeapon::sound,
            // Code statement
            "hit_sound", SoundEvent.CODEC.optional(), PiercingWeapon::hitSound,
            // Code statement
            PiercingWeapon::new);
// End of a block/expression
}
