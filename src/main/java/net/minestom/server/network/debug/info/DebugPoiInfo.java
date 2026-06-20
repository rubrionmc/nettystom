// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record DebugPoiInfo(
        // Instruction de code
        Point position,
        // Instruction de code
        Type type,
        // Instruction de code
        int freeTicketCount
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugPoiInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, DebugPoiInfo::position,
            // Instruction de code
            Type.SERIALIZER, DebugPoiInfo::type,
            // Instruction de code
            NetworkBuffer.INT, DebugPoiInfo::freeTicketCount,
            // Instruction de code
            DebugPoiInfo::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Type {
        // Instruction de code
        ARMORER,
        // Instruction de code
        BUTCHER,
        // Instruction de code
        CARTOGRAPHER,
        // Instruction de code
        CLERIC,
        // Instruction de code
        FARMER,
        // Instruction de code
        FISHERMAN,
        // Instruction de code
        FLETCHER,
        // Instruction de code
        LEATHERWORKER,
        // Instruction de code
        LIBRARIAN,
        // Instruction de code
        MASON,
        // Instruction de code
        SHEPHERD,
        // Instruction de code
        TOOLSMITH,
        // Instruction de code
        WEAPONSMITH,
        // Instruction de code
        HOME,
        // Instruction de code
        MEETING,
        // Instruction de code
        BEEHIVE,
        // Instruction de code
        BEE_NEST,
        // Instruction de code
        NETHER_PORTAL,
        // Instruction de code
        LODESTONE,
        // Instruction de code
        TEST_INSTANCE,
        // Instruction de code
        LIGHTNING_ROD;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Type> SERIALIZER = NetworkBuffer.Enum(Type.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
