// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Packet permits ClientPacket, ServerPacket {
// Fin d'un bloc/d'une expression
}
