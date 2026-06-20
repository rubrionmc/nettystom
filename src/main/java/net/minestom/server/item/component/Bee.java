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
public record Bee(CustomData entityData, int ticksInHive, int minTicksInHive) {
    // Assigns a value
    public static final NetworkBuffer.Type<Bee> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            CustomData.NETWORK_TYPE, Bee::entityData,
            // Code statement
            NetworkBuffer.VAR_INT, Bee::ticksInHive,
            // Code statement
            NetworkBuffer.VAR_INT, Bee::minTicksInHive,
            // Code statement
            Bee::new);
    // Assigns a value
    public static final Codec<Bee> CODEC = StructCodec.struct(
            // Code statement
            "entity_data", CustomData.CODEC, Bee::entityData,
            // Code statement
            "ticks_in_hive", Codec.INT, Bee::ticksInHive,
            // Code statement
            "min_ticks_in_hive", Codec.INT, Bee::minTicksInHive,
            // Code statement
            Bee::new);

    // Start of a method/block
    public Bee withEntityData(CustomData entityData) {
        // Returns a value to the caller
        return new Bee(entityData, ticksInHive, minTicksInHive);
    // End of a block/expression
    }

    // Start of a method/block
    public Bee withTicksInHive(int ticksInHive) {
        // Returns a value to the caller
        return new Bee(entityData, ticksInHive, minTicksInHive);
    // End of a block/expression
    }

    // Start of a method/block
    public Bee withMinTicksInHive(int minTicksInHive) {
        // Returns a value to the caller
        return new Bee(entityData, ticksInHive, minTicksInHive);
    // End of a block/expression
    }
// End of a block/expression
}
