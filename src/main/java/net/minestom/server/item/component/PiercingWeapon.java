// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record PiercingWeapon(
        // Instruction de code
        boolean dealsKnockback,
        // Instruction de code
        boolean dismounts,
        // Annotation pour l'élément suivant
        @Nullable SoundEvent sound,
        // Annotation pour l'élément suivant
        @Nullable SoundEvent hitSound
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<PiercingWeapon> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BOOLEAN, PiercingWeapon::dealsKnockback,
            // Instruction de code
            NetworkBuffer.BOOLEAN, PiercingWeapon::dismounts,
            // Instruction de code
            SoundEvent.NETWORK_TYPE.optional(), PiercingWeapon::sound,
            // Instruction de code
            SoundEvent.NETWORK_TYPE.optional(), PiercingWeapon::hitSound,
            // Instruction de code
            PiercingWeapon::new);
    // Affecte une valeur
    public static final Codec<PiercingWeapon> CODEC = StructCodec.struct(
            // Instruction de code
            "deals_knockback", Codec.BOOLEAN.optional(true), PiercingWeapon::dealsKnockback,
            // Instruction de code
            "dismounts", Codec.BOOLEAN.optional(false), PiercingWeapon::dismounts,
            // Instruction de code
            "sound", SoundEvent.CODEC.optional(), PiercingWeapon::sound,
            // Instruction de code
            "hit_sound", SoundEvent.CODEC.optional(), PiercingWeapon::hitSound,
            // Instruction de code
            PiercingWeapon::new);
// Fin d'un bloc/d'une expression
}
