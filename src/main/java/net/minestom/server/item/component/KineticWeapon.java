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
public record KineticWeapon(
        // Code statement
        int contactCooldownTicks,
        // Code statement
        int delayTicks,
        // Annotation for the following element
        @Nullable Condition dismountConditions,
        // Annotation for the following element
        @Nullable Condition knockbackConditions,
        // Annotation for the following element
        @Nullable Condition damageConditions,
        // Code statement
        float forwardMovement,
        // Code statement
        float damageMultiplier,
        // Annotation for the following element
        @Nullable SoundEvent sound,
        // Annotation for the following element
        @Nullable SoundEvent hitSound
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<KineticWeapon> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, KineticWeapon::contactCooldownTicks,
            // Code statement
            NetworkBuffer.VAR_INT, KineticWeapon::delayTicks,
            // Code statement
            Condition.NETWORK_TYPE.optional(), KineticWeapon::dismountConditions,
            // Code statement
            Condition.NETWORK_TYPE.optional(), KineticWeapon::knockbackConditions,
            // Code statement
            Condition.NETWORK_TYPE.optional(), KineticWeapon::damageConditions,
            // Code statement
            NetworkBuffer.FLOAT, KineticWeapon::forwardMovement,
            // Code statement
            NetworkBuffer.FLOAT, KineticWeapon::damageMultiplier,
            // Code statement
            SoundEvent.NETWORK_TYPE.optional(), KineticWeapon::sound,
            // Code statement
            SoundEvent.NETWORK_TYPE.optional(), KineticWeapon::hitSound,
            // Code statement
            KineticWeapon::new);
    // Assigns a value
    public static final Codec<KineticWeapon> CODEC = StructCodec.struct(
            // Code statement
            "contact_cooldown_ticks", Codec.INT.optional(10), KineticWeapon::contactCooldownTicks,
            // Code statement
            "delay_ticks", Codec.INT.optional(0), KineticWeapon::delayTicks,
            // Code statement
            "dismount_conditions", Condition.CODEC.optional(), KineticWeapon::dismountConditions,
            // Code statement
            "knockback_conditions", Condition.CODEC.optional(), KineticWeapon::knockbackConditions,
            // Code statement
            "damage_conditions", Condition.CODEC.optional(), KineticWeapon::damageConditions,
            // Code statement
            "forward_movement", Codec.FLOAT.optional(0f), KineticWeapon::forwardMovement,
            // Code statement
            "damage_multiplier", Codec.FLOAT.optional(1f), KineticWeapon::damageMultiplier,
            // Code statement
            "sound", SoundEvent.CODEC.optional(), KineticWeapon::sound,
            // Code statement
            "hit_sound", SoundEvent.CODEC.optional(), KineticWeapon::hitSound,
            // Code statement
            KineticWeapon::new);

    // Type declaration (class/interface/enum/record)
    public record Condition(int maxDurationTicks, float minSpeed, float minRelativeSpeed) {
        // Assigns a value
        public static final NetworkBuffer.Type<Condition> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.VAR_INT, Condition::maxDurationTicks,
                // Code statement
                NetworkBuffer.FLOAT, Condition::minSpeed,
                // Code statement
                NetworkBuffer.FLOAT, Condition::minRelativeSpeed,
                // Code statement
                Condition::new);
        // Assigns a value
        public static final Codec<Condition> CODEC = StructCodec.struct(
                // Code statement
                "max_duration_ticks", Codec.INT, Condition::maxDurationTicks,
                // Code statement
                "min_speed", Codec.FLOAT.optional(0f), Condition::minSpeed,
                // Code statement
                "min_relative_speed", Codec.FLOAT.optional(0f), Condition::minRelativeSpeed,
                // Code statement
                Condition::new);
    // End of a block/expression
    }
// End of a block/expression
}
