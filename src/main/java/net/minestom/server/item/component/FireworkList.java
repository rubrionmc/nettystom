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
import java.util.List;

// Type declaration (class/interface/enum/record)
public record FireworkList(int flightDuration, List<FireworkExplosion> explosions) {
    // Calls a method
    public static final FireworkList EMPTY = new FireworkList(0, List.of());

    // Assigns a value
    public static final NetworkBuffer.Type<FireworkList> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, FireworkList::flightDuration,
            // Code statement
            FireworkExplosion.NETWORK_TYPE.list(256), FireworkList::explosions,
            // Code statement
            FireworkList::new);
    // Assigns a value
    public static final Codec<FireworkList> NBT_TYPE = StructCodec.struct(
            // Mojang uses a byte here but var int for protocol so we map to byte here
            // Code statement
            "flight_duration", Codec.BYTE.transform(Byte::intValue, Integer::byteValue), FireworkList::flightDuration,
            // Code statement
            "explosions", FireworkExplosion.CODEC.list().optional(List.of()), FireworkList::explosions,
            // Code statement
            FireworkList::new);

    // Start of a method/block
    public FireworkList {
        // Calls a method
        explosions = List.copyOf(explosions);
    // End of a block/expression
    }

    // Start of a method/block
    public FireworkList withFlightDuration(int flightDuration) {
        // Returns a value to the caller
        return new FireworkList(flightDuration, explosions);
    // End of a block/expression
    }

    // Start of a method/block
    public FireworkList withExplosions(List<FireworkExplosion> explosions) {
        // Returns a value to the caller
        return new FireworkList(flightDuration, explosions);
    // End of a block/expression
    }
// End of a block/expression
}
