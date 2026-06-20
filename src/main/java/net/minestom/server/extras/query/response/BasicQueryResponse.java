// Déclaration du paquet de ce fichier
package net.minestom.server.extras.query.response;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.SHORT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING_TERMINATED;

/**
 * A basic query response containing a fixed set of responses.
 */
// Déclaration de type (classe/interface/enum/record)
public record BasicQueryResponse(String motd, String gameType,
                                 // Instruction de code
                                 String map,
                                 // Instruction de code
                                 String numPlayers, String maxPlayers,
                                 // Début d'une méthode/d'un bloc
                                 short port, String address) {
    /**
     * Creates a new basic query response with pre-filled default values.
     */
    // Début d'une méthode/d'un bloc
    public BasicQueryResponse() {
        // Instruction de code
        this(
                // Instruction de code
                "A Minestom Server",
                // Instruction de code
                "SMP",
                // Instruction de code
                "world",
                // Instruction de code
                String.valueOf(MinecraftServer.getConnectionManager().getOnlinePlayerCount()),
                // Instruction de code
                "9999",
                // Instruction de code
                (short) MinecraftServer.getServer().getPort(),
                // Instruction de code
                Objects.requireNonNullElse(MinecraftServer.getServer().getAddress(), "")
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<BasicQueryResponse> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING_TERMINATED, BasicQueryResponse::motd,
            // Instruction de code
            STRING_TERMINATED, BasicQueryResponse::gameType,
            // Instruction de code
            STRING_TERMINATED, BasicQueryResponse::map,
            // Instruction de code
            STRING_TERMINATED, BasicQueryResponse::numPlayers,
            // Instruction de code
            STRING_TERMINATED, BasicQueryResponse::maxPlayers,
            // Instruction de code
            SHORT, BasicQueryResponse::port, // TODO little endian?
            // Instruction de code
            STRING_TERMINATED, BasicQueryResponse::address,
            // Instruction de code
            BasicQueryResponse::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
