// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.Packet;

/**
 * Represents a packet received from a client.
 * <p>
 * Packets are value-based, and should therefore not be reliant on identity.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface ClientPacket extends Packet {
    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Handshake extends ClientPacket {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Status extends ClientPacket {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Login extends ClientPacket {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Configuration extends ClientPacket {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    non-sealed interface Play extends ClientPacket {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}