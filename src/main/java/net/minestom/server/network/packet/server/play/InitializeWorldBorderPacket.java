// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record InitializeWorldBorderPacket(double x, double z,
                                          // Boucle : répète un bloc
                                          double oldDiameter, double newDiameter, long speed,
                                          // Instruction de code
                                          int portalTeleportBoundary, int warningTime,
                                          // Début d'une méthode/d'un bloc
                                          int warningBlocks) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<InitializeWorldBorderPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Boucle : répète un bloc
            DOUBLE, InitializeWorldBorderPacket::x,
            // Boucle : répète un bloc
            DOUBLE, InitializeWorldBorderPacket::z,
            // Boucle : répète un bloc
            DOUBLE, InitializeWorldBorderPacket::oldDiameter,
            // Boucle : répète un bloc
            DOUBLE, InitializeWorldBorderPacket::newDiameter,
            // Instruction de code
            VAR_LONG, InitializeWorldBorderPacket::speed,
            // Instruction de code
            VAR_INT, InitializeWorldBorderPacket::portalTeleportBoundary,
            // Instruction de code
            VAR_INT, InitializeWorldBorderPacket::warningTime,
            // Instruction de code
            VAR_INT, InitializeWorldBorderPacket::warningBlocks,
            // Instruction de code
            InitializeWorldBorderPacket::new);
// Fin d'un bloc/d'une expression
}
