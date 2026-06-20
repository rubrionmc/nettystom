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
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record FireworkList(int flightDuration, List<FireworkExplosion> explosions) {
    // Appelle une méthode
    public static final FireworkList EMPTY = new FireworkList(0, List.of());

    // Affecte une valeur
    public static final NetworkBuffer.Type<FireworkList> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, FireworkList::flightDuration,
            // Instruction de code
            FireworkExplosion.NETWORK_TYPE.list(256), FireworkList::explosions,
            // Instruction de code
            FireworkList::new);
    // Affecte une valeur
    public static final Codec<FireworkList> NBT_TYPE = StructCodec.struct(
            // Mojang uses a byte here but var int for protocol so we map to byte here
            // Instruction de code
            "flight_duration", Codec.BYTE.transform(Byte::intValue, Integer::byteValue), FireworkList::flightDuration,
            // Instruction de code
            "explosions", FireworkExplosion.CODEC.list().optional(List.of()), FireworkList::explosions,
            // Instruction de code
            FireworkList::new);

    // Début d'une méthode/d'un bloc
    public FireworkList {
        // Appelle une méthode
        explosions = List.copyOf(explosions);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public FireworkList withFlightDuration(int flightDuration) {
        // Renvoie une valeur à l'appelant
        return new FireworkList(flightDuration, explosions);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public FireworkList withExplosions(List<FireworkExplosion> explosions) {
        // Renvoie une valeur à l'appelant
        return new FireworkList(flightDuration, explosions);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
