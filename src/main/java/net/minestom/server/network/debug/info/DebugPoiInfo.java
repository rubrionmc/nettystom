// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Type declaration (class/interface/enum/record)
public record DebugPoiInfo(
        // Code statement
        Point position,
        // Code statement
        Type type,
        // Code statement
        int freeTicketCount
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugPoiInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BLOCK_POSITION, DebugPoiInfo::position,
            // Code statement
            Type.SERIALIZER, DebugPoiInfo::type,
            // Code statement
            NetworkBuffer.INT, DebugPoiInfo::freeTicketCount,
            // Code statement
            DebugPoiInfo::new);

    // Type declaration (class/interface/enum/record)
    public enum Type {
        // Code statement
        ARMORER,
        // Code statement
        BUTCHER,
        // Code statement
        CARTOGRAPHER,
        // Code statement
        CLERIC,
        // Code statement
        FARMER,
        // Code statement
        FISHERMAN,
        // Code statement
        FLETCHER,
        // Code statement
        LEATHERWORKER,
        // Code statement
        LIBRARIAN,
        // Code statement
        MASON,
        // Code statement
        SHEPHERD,
        // Code statement
        TOOLSMITH,
        // Code statement
        WEAPONSMITH,
        // Code statement
        HOME,
        // Code statement
        MEETING,
        // Code statement
        BEEHIVE,
        // Code statement
        BEE_NEST,
        // Code statement
        NETHER_PORTAL,
        // Code statement
        LODESTONE,
        // Code statement
        TEST_INSTANCE,
        // Code statement
        LIGHTNING_ROD;

        // Calls a method
        public static final NetworkBuffer.Type<Type> SERIALIZER = NetworkBuffer.Enum(Type.class);
    // End of a block/expression
    }
// End of a block/expression
}
