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

// Type declaration (class/interface/enum/record)
public record Weapon(int itemDamagePerAttack, float disableBlockingForSeconds) {
    // Calls a method
    public static final Weapon DEFAULT = new Weapon(1, 0.0f);

    // Assigns a value
    public static final NetworkBuffer.Type<Weapon> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, Weapon::itemDamagePerAttack,
            // Code statement
            NetworkBuffer.FLOAT, Weapon::disableBlockingForSeconds,
            // Code statement
            Weapon::new);
    // Assigns a value
    public static final Codec<Weapon> CODEC = StructCodec.struct(
            // Code statement
            "item_damage_per_attack", Codec.INT.optional(1), Weapon::itemDamagePerAttack,
            // Code statement
            "disable_blocking_for_seconds", Codec.FLOAT.optional(0f), Weapon::disableBlockingForSeconds,
            // Code statement
            Weapon::new);

    // Start of a method/block
    public Weapon(int itemDamagePerAttack) {
        // Calls a method
        this(itemDamagePerAttack, 0.0f);
    // End of a block/expression
    }

    // Start of a method/block
    public Weapon withItemDamagePerAttack(int itemDamagePerAttack) {
        // Returns a value to the caller
        return new Weapon(itemDamagePerAttack, this.disableBlockingForSeconds);
    // End of a block/expression
    }

    // Start of a method/block
    public Weapon withDisableBlockingForSeconds(float disableBlockingForSeconds) {
        // Returns a value to the caller
        return new Weapon(this.itemDamagePerAttack, disableBlockingForSeconds);
    // End of a block/expression
    }

// End of a block/expression
}
