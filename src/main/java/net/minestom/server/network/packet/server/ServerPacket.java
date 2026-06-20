// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server;

// Import d'une classe nécessaire
import net.minestom.server.adventure.ComponentHolder;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;

/**
 * Represents a packet which can be sent to a player using {@link PlayerConnection#sendPacket(SendablePacket)}.
 * <p>
 * Packets are value-based, and should therefore not be reliant on identity.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface ServerPacket extends SendablePacket permits ServerPacket.Configuration, ServerPacket.Login, ServerPacket.Play, ServerPacket.Status {

    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Configuration extends ServerPacket {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Status extends ServerPacket {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Login extends ServerPacket {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Play extends ServerPacket {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    interface ComponentHolding extends ComponentHolder<ServerPacket> {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
