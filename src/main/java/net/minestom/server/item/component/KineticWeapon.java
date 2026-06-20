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
public record KineticWeapon(
        // Instruction de code
        int contactCooldownTicks,
        // Instruction de code
        int delayTicks,
        // Annotation pour l'élément suivant
        @Nullable Condition dismountConditions,
        // Annotation pour l'élément suivant
        @Nullable Condition knockbackConditions,
        // Annotation pour l'élément suivant
        @Nullable Condition damageConditions,
        // Instruction de code
        float forwardMovement,
        // Instruction de code
        float damageMultiplier,
        // Annotation pour l'élément suivant
        @Nullable SoundEvent sound,
        // Annotation pour l'élément suivant
        @Nullable SoundEvent hitSound
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<KineticWeapon> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, KineticWeapon::contactCooldownTicks,
            // Instruction de code
            NetworkBuffer.VAR_INT, KineticWeapon::delayTicks,
            // Instruction de code
            Condition.NETWORK_TYPE.optional(), KineticWeapon::dismountConditions,
            // Instruction de code
            Condition.NETWORK_TYPE.optional(), KineticWeapon::knockbackConditions,
            // Instruction de code
            Condition.NETWORK_TYPE.optional(), KineticWeapon::damageConditions,
            // Instruction de code
            NetworkBuffer.FLOAT, KineticWeapon::forwardMovement,
            // Instruction de code
            NetworkBuffer.FLOAT, KineticWeapon::damageMultiplier,
            // Instruction de code
            SoundEvent.NETWORK_TYPE.optional(), KineticWeapon::sound,
            // Instruction de code
            SoundEvent.NETWORK_TYPE.optional(), KineticWeapon::hitSound,
            // Instruction de code
            KineticWeapon::new);
    // Affecte une valeur
    public static final Codec<KineticWeapon> CODEC = StructCodec.struct(
            // Instruction de code
            "contact_cooldown_ticks", Codec.INT.optional(10), KineticWeapon::contactCooldownTicks,
            // Instruction de code
            "delay_ticks", Codec.INT.optional(0), KineticWeapon::delayTicks,
            // Instruction de code
            "dismount_conditions", Condition.CODEC.optional(), KineticWeapon::dismountConditions,
            // Instruction de code
            "knockback_conditions", Condition.CODEC.optional(), KineticWeapon::knockbackConditions,
            // Instruction de code
            "damage_conditions", Condition.CODEC.optional(), KineticWeapon::damageConditions,
            // Instruction de code
            "forward_movement", Codec.FLOAT.optional(0f), KineticWeapon::forwardMovement,
            // Instruction de code
            "damage_multiplier", Codec.FLOAT.optional(1f), KineticWeapon::damageMultiplier,
            // Instruction de code
            "sound", SoundEvent.CODEC.optional(), KineticWeapon::sound,
            // Instruction de code
            "hit_sound", SoundEvent.CODEC.optional(), KineticWeapon::hitSound,
            // Instruction de code
            KineticWeapon::new);

    // Déclaration de type (classe/interface/enum/record)
    public record Condition(int maxDurationTicks, float minSpeed, float minRelativeSpeed) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Condition> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.VAR_INT, Condition::maxDurationTicks,
                // Instruction de code
                NetworkBuffer.FLOAT, Condition::minSpeed,
                // Instruction de code
                NetworkBuffer.FLOAT, Condition::minRelativeSpeed,
                // Instruction de code
                Condition::new);
        // Affecte une valeur
        public static final Codec<Condition> CODEC = StructCodec.struct(
                // Instruction de code
                "max_duration_ticks", Codec.INT, Condition::maxDurationTicks,
                // Instruction de code
                "min_speed", Codec.FLOAT.optional(0f), Condition::minSpeed,
                // Instruction de code
                "min_relative_speed", Codec.FLOAT.optional(0f), Condition::minRelativeSpeed,
                // Instruction de code
                Condition::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
